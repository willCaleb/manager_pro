package com.project.pro.model.dto;

import com.project.pro.annotation.DtoFieldIgnore;
import com.project.pro.annotation.OnlyField;
import com.project.pro.model.entity.Profissional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class ProfissionalDTO extends AbstractDTO<Integer, Profissional> {

    private Integer id;

    @OnlyField(fields = {"id", "nome", "enderecos"})
    private PessoaDTO pessoa;

    private Integer qntdAvaliacao;

    private BigDecimal mediaAvaliacao;

    @OnlyField(fields = {"id", "filename", "filesize", "imgLink"})
    private List<ImagemDTO> imagens;

    private String email;

    private String cpf;

    private Boolean multiploAgendamento;

    @OnlyField(fields = {"nota", "descricao", "files", "dataInclusao"})
    private List<ProfissionalAvaliacaoDTO> avaliacoes;

    List<ServicoProfissionalDTO> servicos;

}
