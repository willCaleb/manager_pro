package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.enums.EnumClassificacaoPessoa;
import com.project.pro.model.entity.Pessoa;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    private Date dataNascimento;

    private String imagemPerfil;

    private Date dataInclusao;

    private EnumClassificacaoPessoa classificacao;

    private MultipartFile file;

//    private List<ComentarioDTO> comentarios;

    @OnlyField(fields = {"id", "logradouro", "bairro", "pessoa", "latitude", "longitude"})
    private List<EnderecoDTO> enderecos;
//
//    private EnumClassificacaoPessoa classificacao;

}
