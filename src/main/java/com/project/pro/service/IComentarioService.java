package com.project.pro.service;

import com.project.pro.model.dto.ComentarioDTO;
import com.project.pro.model.entity.Comentario;
import com.project.pro.repository.ComentarioRepository;

public interface IComentarioService extends IAbstractService<Comentario, ComentarioDTO, ComentarioRepository>{

    Comentario incluir(Comentario comentario);

    void excluir(Integer idComentario);
}
