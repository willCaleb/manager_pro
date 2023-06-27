package com.project.pro.model.dto;

import com.project.pro.model.entity.Comentario;
import com.project.pro.model.entity.Pessoa;
import lombok.Data;

import java.util.Date;

@Data
public class ComentarioDTO extends AbstractDTO<Integer, Comentario>{

    private Integer id;

    private String comentario;

    private String titulo;

    private Date dataInclusao;

    private Date dataAlteracao;

    private boolean publico;

    private Pessoa remetente;

    private Pessoa destinatario;
}
