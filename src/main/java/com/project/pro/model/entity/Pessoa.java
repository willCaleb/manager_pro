package com.project.pro.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.pro.enums.EnumClassificacaoPessoa;
import com.project.pro.enums.EnumTipoPessoa;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.PessoaDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pro_pessoa")
public class Pessoa extends AbstractEntity<Integer, PessoaDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf_cnpj", unique = true)
    private String cpfCnpj;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "imagem_perfil")
    private String imagemPerfil;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "senha")
    private String senha;

    @Column(name = "tipo")
    private EnumTipoPessoa tipoPessoa;

    @Transient
    private FileUploadDTO file;

    @Column(name = "classificacao")
    @Convert(converter = EnumClassificacaoPessoa.EnumConverter.class)
    private EnumClassificacaoPessoa classificacao;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.EAGER)
    private List<Endereco> enderecos;

    @Transient
    private Endereco enderecoPrincipal;

    @JsonManagedReference
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "remetente", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    public boolean hasId() {
        return this.id != null;
    }

    @PostLoad
    void setEndereco() {

    }
}
