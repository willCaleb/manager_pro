package com.project.pro.service;

import com.project.pro.model.dto.PedidoItemDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Pedido;
import com.project.pro.model.entity.PedidoItem;
import com.project.pro.repository.PedidoItemRepository;

import java.util.List;

public interface IPedidoItemService extends IAbstractService<PedidoItem, PedidoItemDTO, PedidoItemRepository> {

    List<PedidoItem> incluir(List<PedidoItem> itens);

    PedidoItem incluir(PedidoItem item);

    List<PedidoItem> findAllByPedido(Pedido pedido);

    List<PedidoItem> findAllByCliente(Cliente cliente);

}
