package com.project.pro.service;

import com.project.pro.model.dto.PedidoDTO;
import com.project.pro.model.entity.Pedido;
import com.project.pro.repository.PedidoRepository;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

public interface IPedidoService extends IAbstractService<Pedido, PedidoDTO, PedidoRepository> {

    Pedido incluir(Pedido pedido);

    void editar(Integer idPedido, Pedido pedido);

    void finalizarPedido(Integer idPedido);

    ResponseEntity<byte[]> gerarOrcamento();

}
