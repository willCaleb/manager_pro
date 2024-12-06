package com.project.pro.model.entity;

import com.project.pro.enums.EnumEtapaServico;
import com.project.pro.model.dto.ServicoDTO;
import com.project.pro.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "servico")
public class Servico extends AbstractEntity<Integer, ServicoDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tempo_execucao")
    private Integer tempoExecucao;

    @Column(name = "servico")
    private boolean servico;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "etapa")
    @Convert(converter = EnumEtapaServico.EnumConverter.class)
    private EnumEtapaServico etapaServico;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;

    @OneToMany
    @JoinColumn(name = "id_servico", referencedColumnName = "id")
    private List<ServicoProfissional> servicoProfissionais;

}
