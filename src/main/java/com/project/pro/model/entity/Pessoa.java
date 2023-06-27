package com.project.pro.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.pro.enums.EnumClassificacaoPessoa;
import com.project.pro.model.dto.PessoaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
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

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "senha")
    private String senha;

    @Column(name = "classificacao")
    @Convert(converter = EnumClassificacaoPessoa.EnumConverter.class)
    private EnumClassificacaoPessoa classificacao;

    @JsonManagedReference
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;

    @JsonManagedReference
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "remetente", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    public boolean hasId() {
        return this.id != null;
    }

}
