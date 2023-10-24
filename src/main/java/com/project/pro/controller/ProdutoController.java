package com.project.pro.controller;

import com.project.pro.model.dto.ProdutoDTO;
import com.project.pro.model.entity.Produto;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IProdutoService;
import com.project.pro.service.impl.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ProdutoController.PATH)
public class ProdutoController extends AbstractController<Produto, ProdutoDTO>{

    private final IProdutoService produtoService;

    public static final String PATH = "/produto";

    @PostMapping
    public ProdutoDTO incluir(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.incluir(produtoDTO.toEntity()).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@PathVariable(OperationsParam.ID) Integer idProduto,
                       @RequestBody ProdutoDTO produtoDTO) {
        produtoService.editar(idProduto, produtoDTO.toEntity());
    }
}
