package com.project.pro.model.entity;

import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ProfissionalAvaliacaoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "profissional_avaliacao")
public class ProfissionalAvaliacao extends AbstractEntity<Integer, ProfissionalAvaliacaoDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nota")
    private BigDecimal nota;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @ManyToOne
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "editado")
    private boolean editado;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Transient
    private List<FileUploadDTO> files;

    @Transient
    private List<Imagem> imagens;

}
