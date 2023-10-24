package com.project.pro.service;

import com.project.pro.model.dto.ProdutoDTO;
import com.project.pro.model.entity.Produto;
import com.project.pro.repository.ProdutoRepository;

public interface IProdutoService extends IAbstractService<Produto, ProdutoDTO, ProdutoRepository> {

    Produto incluir(Produto produto);

    void editar(Integer idProduto, Produto produto);

}
