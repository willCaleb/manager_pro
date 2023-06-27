package com.project.pro.service;

import com.project.pro.model.entity.Categoria;
import org.springframework.stereotype.Service;

@Service
public interface ICategoriaService  {

    Categoria incluir(Categoria categoria);

    void editar(Integer idCategoria, Categoria categoria);

}
