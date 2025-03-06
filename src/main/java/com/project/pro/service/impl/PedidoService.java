package com.project.pro.service.impl;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.PedidoDTO;
import com.project.pro.model.entity.*;
import com.project.pro.repository.PedidoRepository;
import com.project.pro.service.*;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.PDFUtils;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService extends AbstractService<Pedido, PedidoDTO, PedidoRepository> implements IPedidoService {

    private ValidadorPedido validadorPedido = new ValidadorPedido();

    private final PedidoItemService pedidoItemService;

    private final IClienteService clienteService;

    private final IEnderecoService enderecoService;

    private final IProfissionalService profissionalService;

    private final IChangeLogService changeLogService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pedido incluir(Pedido pedido) {

        pedido.setDataInclusao(DateUtils.getDate());
        pedido.setCliente(getCliente());

        onPrepareInsert(pedido);
        validadorPedido.validarInsert(pedido);
        final Pedido pedidoRetorno = getRepository().save(pedido);

        pedido.getItens().forEach(item -> item.setPedido(pedidoRetorno));

        pedidoItemService.incluir(pedido.getItens());

        return pedidoRetorno;
    }

    private void resolverStatus(Pedido pedido) {
        if (Utils.isEmpty(pedido.getStatusPedido())) {
            pedido.setStatusPedido(EnumStatusPedido.ABERTO);
        }
    }

    private void onPrepareInsert(Pedido pedido) {

        Profissional profissional = getProfissional();

        if (Utils.isNotEmpty(pedido.getCliente())) {
            Cliente cliente = clienteService.findAndValidate(pedido.getCliente().getId());

            pedido.setCliente(cliente);
        }
        pedido.setProfissional(profissional);

//        Pessoa pessoaCliente = cliente.getPessoa();
//
//        Pessoa pessoaProfissional = profissional.getPessoa();

//        Endereco enderecoPrincipalCliente = enderecoService.findEnderecoPrincipal(pessoaCliente);
//        Endereco enderecoPrincipalProfissional = enderecoService.findEnderecoPrincipal(pessoaProfissional);
//        pedido.setDistancia(enderecoService.calcularDistancia(enderecoPrincipalCliente.getId(), enderecoPrincipalProfissional.getId()));

        resolverStatus(pedido);
    }

    @Override
    public void editar(Integer idPedido, Pedido pedido) {
        Pedido pedidoManaged = findAndValidate(idPedido);

        validadorPedido.validarTemObservacao(pedido);
        validadorPedido.validarPedidoCancelado(pedidoManaged);

        changeLogService.incluir(pedidoManaged, pedido);

        if (EnumStatusPedido.ABERTO.equals(pedidoManaged.getStatusPedido())) {
            pedidoManaged.setStatusPedido(pedido.getStatusPedido());
            pedidoManaged.setDataAlteracao(DateUtils.getDate());
            pedidoManaged.setObservacao(pedido.getObservacao());
            pedidoManaged.setItens(pedido.getItens());
        } else if (EnumStatusPedido.CONFIRMADO.equals(pedidoManaged.getStatusPedido()) && EnumStatusPedido.ABERTO.equals(pedido.getStatusPedido())){
            //TODO implementar edição de pedido confirmado ou aberto
        }

        getRepository().save(pedidoManaged);
    }

    @Override
    public void finalizarPedido(Integer idPedido) {
        Pedido pedido = findAndValidate(idPedido);
        List<PedidoItem> allByPedido = pedidoItemService.findAllByPedido(pedido);

        if (EnumStatusPedido.FINALIZADO.equals(pedido.getStatusPedido())) {
            throw new CustomException(EnumCustomException.PEDIDO_FINALIZADO);
        }

        for (PedidoItem pedidoItem : allByPedido) {
            if (!Utils.equals(EnumStatusPedido.FINALIZADO, pedidoItem.getStatus())) {
                throw new CustomException(EnumCustomException.PEDIDO_NAO_E_POSSIVEL_FINALIZAR);
            }
        }
        pedido.setStatusPedido(EnumStatusPedido.FINALIZADO);
        getRepository().save(pedido);
    }

    @Override
    public ResponseEntity<byte[]> gerarOrcamento() {
        return PDFUtils.generatePdf();
    }
}
