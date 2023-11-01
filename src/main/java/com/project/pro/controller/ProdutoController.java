package com.project.pro.controller;

import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.dto.ProdutoDTO;
import com.project.pro.model.entity.Produto;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IProdutoService;
import com.project.pro.service.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ProdutoController.PATH)
public class ProdutoController extends AbstractController<Produto, ProdutoDTO>{

    private final IProdutoService produtoService;

    public static final String PATH = "/produto";

    private final ImagemService imagemService;

    @PostMapping
    public ProdutoDTO incluir(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.incluir(produtoDTO.toEntity()).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@PathVariable(OperationsParam.ID) Integer idProduto,
                       @RequestBody ProdutoDTO produtoDTO) {
        produtoService.editar(idProduto, produtoDTO.toEntity());
    }

    @PostMapping(OperationsPath.ID + "/imagem")
    public ImagemDTO incluirImagem(@RequestBody MultipartFile file,
                                   @PathVariable(OperationsParam.ID) Integer idProduto) {
        produtoService.findAndValidate(idProduto);
        return imagemService.incluir(file, Produto.class, idProduto).toDto();
    }

    @GetMapping(OperationsPath.ID + "/imagem")
    public List<ImagemDTO> getImagemsProduto(@PathVariable(OperationsParam.ID) Integer idProduto) {
        Produto produto = produtoService.findAndValidate(idProduto);
        return toDtoList(imagemService.findAllByEntity(produto), ImagemDTO.class);
    }
}
