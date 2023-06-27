package com.project.pro.model.dto;

import com.project.pro.enums.EnumClassificacaoPessoa;
import com.project.pro.model.entity.Comentario;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PessoaDTO extends AbstractDTO<Integer, Pessoa>{

    private Integer id;

    private String nome;

    private String cpfCnpj;

    private String telefone;

    private String email;

    private String senha;

    private Integer idade;

    private Date dataInclusao;

    private List<ComentarioDTO> comentarios;

    private List<EnderecoDTO> enderecos;

    private EnumClassificacaoPessoa classificacao;

}
