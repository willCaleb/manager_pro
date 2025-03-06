package com.project.pro.service.impl;

import com.project.pro.model.dto.PedidoItemDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Pedido;
import com.project.pro.model.entity.PedidoItem;
import com.project.pro.repository.PedidoItemRepository;
import com.project.pro.service.IPedidoItemService;
import com.project.pro.validator.ValidadorPedidoItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoItemService extends AbstractService<PedidoItem, PedidoItemDTO, PedidoItemRepository> implements IPedidoItemService{

    private ValidadorPedidoItem validadorPedidoItem = new ValidadorPedidoItem();
    private final PedidoItemRepository pedidoItemRepository;

    @Override
    public List<PedidoItem> incluir(List<PedidoItem> itens) {

        itens.forEach(item -> validadorPedidoItem.validarCamposObrigatorios(item));

         return saveAll(itens);
    }

    @Override
    public PedidoItem incluir(PedidoItem item) {
        validadorPedidoItem.validarCamposObrigatorios(item);
        return save(item);
    }

    @Override
    public List<PedidoItem> findAllByPedido(Pedido pedido) {
        return pedidoItemRepository.findAllByPedido(pedido);
    }

    @Override
    public List<PedidoItem> findAllByCliente(Cliente cliente) {
        return pedidoItemRepository.findAllByCliente(cliente);
    }
}
