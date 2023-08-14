package com.project.pro.repository;

import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Pedido;
import com.project.pro.model.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer>, JpaSpecificationExecutor<PedidoItem>{

    List<PedidoItem> findAllByPedido(Pedido pedido);

    @Query(value = "select pi from PedidoItem pi " +
            " join Pedido pe on pi.pedido = pe " +
            " where  pe.cliente = :cliente")
    List<PedidoItem> findAllByCliente(@Param("cliente") Cliente cliente);
}
