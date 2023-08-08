package com.project.pro.controller;

import com.project.pro.model.dto.ComentarioDTO;
import com.project.pro.model.entity.Comentario;
import com.project.pro.service.IComentarioService;
import com.project.pro.service.impl.ComentarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentario")
@RequiredArgsConstructor
public class ComentarioController extends AbstractController<Comentario, ComentarioDTO> {

    private final IComentarioService comentarioService;

    @PostMapping
    public ComentarioDTO incluir(@RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioDTO.toEntity();
        return comentarioService.incluir(comentario).toDto();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable("id") Integer idComentario) {
        comentarioService.excluir(idComentario);
    }
}
