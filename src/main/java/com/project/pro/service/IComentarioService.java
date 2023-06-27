package com.project.pro.service;

import com.project.pro.model.entity.Comentario;

public interface IComentarioService {

    Comentario incluir(Comentario comentario);

    void excluir(Integer idComentario);
}
