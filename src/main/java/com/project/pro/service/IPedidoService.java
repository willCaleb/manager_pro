package com.project.pro.service;

import com.project.pro.model.dto.PedidoDTO;
import com.project.pro.model.entity.Pedido;
import com.project.pro.repository.PedidoRepository;

public interface IPedidoService extends IAbstractService<Pedido, PedidoDTO, PedidoRepository> {

    Pedido incluir(Pedido pedido);

    void editar(Integer idPedido, Pedido pedido);

    void finalizarPedido(Integer idPedido);

}
