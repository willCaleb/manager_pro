package com.project.pro.service.impl;

import com.project.pro.model.dto.ProdutoDTO;
import com.project.pro.model.entity.Produto;
import com.project.pro.repository.ProdutoRepository;
import com.project.pro.service.IChangeLogService;
import com.project.pro.service.IProdutoService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.ObjectUtils;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService extends AbstractService<Produto,ProdutoDTO, ProdutoRepository> implements IProdutoService{

    private final ProdutoRepository produtoRepository;

    private final IChangeLogService changeLogService;

    @Override
    public Produto incluir(Produto produto) {
        produto.setDataInclusao(DateUtils.getDate());
        return produtoRepository.save(produto);
    }

    @Override
    public void editar(Integer idProduto, Produto produto) {
        Produto produtoManaged = findAndValidate(idProduto);

        changeLogService.incluir(produtoManaged, produto);

        produtoManaged.setNome(Utils.nvl(produto.getNome(), produtoManaged.getNome()));
        produtoManaged.setPreco(Utils.nvl(produto.getPreco(), produtoManaged.getPreco()));
        produtoManaged.setDataAlteracao(DateUtils.getDate());

        produtoRepository.save(produtoManaged);
    }
}
