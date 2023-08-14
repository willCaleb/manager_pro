package com.project.pro.service.impl;

import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.PedidoDTO;
import com.project.pro.model.entity.*;
import com.project.pro.repository.PedidoRepository;
import com.project.pro.service.*;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService extends AbstractService<Pedido, PedidoDTO, PedidoRepository> implements IPedidoService {

    private ValidadorPedido validadorPedido = new ValidadorPedido();

    private final PedidoItemService pedidoItemService;

    private final IClienteService clienteService;

    private final IEnderecoService enderecoService;

    private final IProfissionalService profissionalService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pedido incluir(Pedido pedido) {

        pedido.setDataInclusao(DateUtils.getDate());
        onPrepareInsert(pedido);
        validadorPedido.validarInsert(pedido);
        final Pedido pedidoRetorno = getRepository().save(pedido);

        pedido.getItens().forEach(item -> item.setPedido(pedidoRetorno));

        pedidoItemService.incluir(pedido.getItens());

        return pedido;
    }

    private void resolverStatus(Pedido pedido) {
        if (Utils.isEmpty(pedido.getStatusPedido())) {
            pedido.setStatusPedido(EnumStatusPedido.ABERTO);
        }
    }

    private void onPrepareInsert(Pedido pedido) {

        Profissional profissional = profissionalService.findAndValidate(pedido.getProfissional().getId());
        Cliente cliente = clienteService.findAndValidate(pedido.getCliente().getId());

        Pessoa pessoaCliente = cliente.getPessoa();

        Pessoa pessoaProfissional = profissional.getPessoa();

        Endereco enderecoPrincipalCliente = enderecoService.findEnderecoPrincipal(pessoaCliente);
        Endereco enderecoPrincipalProfissional = enderecoService.findEnderecoPrincipal(pessoaProfissional);
        pedido.setDistancia(enderecoService.calcularDistancia(enderecoPrincipalCliente.getId(), enderecoPrincipalProfissional.getId()));

        resolverStatus(pedido);
    }

    @Override
    public void editar(Integer idPedido, Pedido pedido) {

    }

    @Override
    public void finalizarPedido(Integer idPedido) {
        Pedido pedido = findAndValidate(idPedido);
        List<PedidoItem> allByPedido = pedidoItemService.findAllByPedido(pedido);

        if (EnumStatusPedido.FINALIZADO.equals(pedido.getStatusPedido())) {
            throw new CustomException("Pedido já finalizado");
        }

        for (PedidoItem pedidoItem : allByPedido) {
            if (!Utils.equals(EnumStatusPedido.FINALIZADO, pedidoItem.getStatus())) {
                throw new CustomException("O pedido não pode ser finalizados pois contém itens não finalizados!");
            }
        }
        pedido.setStatusPedido(EnumStatusPedido.FINALIZADO);
        getRepository().save(pedido);
    }
}
