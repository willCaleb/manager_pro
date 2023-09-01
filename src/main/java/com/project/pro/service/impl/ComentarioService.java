package com.project.pro.service.impl;

import com.project.pro.model.dto.ComentarioDTO;
import com.project.pro.model.entity.Comentario;
import com.project.pro.repository.ComentarioRepository;
import com.project.pro.service.IComentarioService;
import com.project.pro.utils.DateUtils;
import com.project.pro.validator.ValidadorComentario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioService extends AbstractService<Comentario, ComentarioDTO, ComentarioRepository> implements IComentarioService{

    private final ComentarioRepository comentarioRepository;

    private ValidadorComentario validadorComentario = new ValidadorComentario();

    public Comentario incluir(Comentario comentario) {
        comentario.setDataInclusao(DateUtils.getDate());
        validadorComentario.validarInsert(comentario);
        return comentarioRepository.save(comentario);
    }

    public void excluir(Integer idComentario) {

        Comentario comentario = findAndValidate(idComentario);

        comentarioRepository.delete(comentario);
    }
}
