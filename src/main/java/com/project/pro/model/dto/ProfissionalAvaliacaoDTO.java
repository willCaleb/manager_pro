package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProfissionalAvaliacaoDTO extends AbstractDTO<Integer, ProfissionalAvaliacao>{

    private Integer id;

    private BigDecimal nota;

    private Date dataInclusao;

    private String observacao;

    private List<FileUploadDTO> files;

    private ClienteDTO cliente;

    private Boolean editado;

    private Date dataAlteracao;

    @OnlyField(fields = {"imgLink"})
    private List<ImagemDTO> imagens;
}
