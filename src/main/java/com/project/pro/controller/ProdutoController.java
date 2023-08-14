package com.project.pro.controller;

import com.project.pro.model.dto.ProdutoDTO;
import com.project.pro.model.entity.Produto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProdutoController.PATH)
public class ProdutoController extends AbstractController<Produto, ProdutoDTO>{

    public static final String PATH = "/produto";
}
