package com.project.pro.controller;

import com.project.pro.model.dto.CategoriaDTO;
import com.project.pro.model.entity.Categoria;
import com.project.pro.service.ICategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController extends AbstractController<Categoria, CategoriaDTO> {

    private final ICategoriaService categoriaService;

    @PostMapping
    public CategoriaDTO incluir(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.incluir(categoriaDTO.toEntity()).toDto();
    }

    @PutMapping("/{id}")
    public void  editar(@PathVariable("id") Integer idCategoria, @RequestBody CategoriaDTO categoriaDTO) {
        categoriaService.editar(idCategoria, categoriaDTO.toEntity());
    }
}
