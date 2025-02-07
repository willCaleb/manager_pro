package com.project.pro.controller;

import com.project.pro.model.dto.PedidoDTO;
import com.project.pro.model.entity.Pedido;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping(PedidoController.PATH)
public class PedidoController extends AbstractController<Pedido, PedidoDTO>{

    private final IPedidoService pedidoService;

    public static final String PATH = "/pedido";

    @PostMapping
    public PedidoDTO incluir(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.incluir(pedidoDTO.toEntity()).toDto();
    }

    @PutMapping(OperationsPath.ID + "/finalizar")
    public void finalizar(@PathVariable(OperationsParam.ID) Integer idPedido) {
        pedidoService.finalizarPedido(idPedido);
    }

    @GetMapping("/orcamento")
    public ResponseEntity<byte[]> gerarOrcamento() {
        return pedidoService.gerarOrcamento();
    }
}
