package com.project.pro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProdutoController.PATH)
public class ProdutoController {

    public static final String PATH = "/produto";
}
