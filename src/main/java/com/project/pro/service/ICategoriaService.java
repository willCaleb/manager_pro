package com.project.pro.service;

import com.project.pro.model.dto.CategoriaDTO;
import com.project.pro.model.entity.Categoria;
import com.project.pro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ICategoriaService extends IAbstractService<Categoria, CategoriaDTO, CategoriaRepository> {

    Categoria incluir(Categoria categoria);

    void editar(Integer idCategoria, Categoria categoria);

}
