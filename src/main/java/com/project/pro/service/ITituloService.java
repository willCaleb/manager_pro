package com.project.pro.service;

import com.project.pro.model.dto.TituloDTO;
import com.project.pro.model.entity.Titulo;
import com.project.pro.repository.TituloRepository;

public interface ITituloService extends IAbstractService<Titulo, TituloDTO, TituloRepository>{

    Titulo incluir(Titulo titulo);

    /**
        status ==
             ABERTO - editar normalmente
             PARCIALMENTE_LIQUIDADO - editar e obrigar a ter observação
             LIQUIDADO - não permitir alteração
             CANCELADO - o título só poderá ser cancelado se o status atual dele for ABERTO,
             nesse caso é obrigatório incluir uma observação. Caso o status atual
             seja CANCELADO não é permitido alteração.
     */
    void editar(Integer idTitulo, Titulo titulo);
}
