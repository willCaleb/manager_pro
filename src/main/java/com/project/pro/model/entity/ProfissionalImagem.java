package com.project.pro.model.entity;


import com.project.pro.model.dto.ProfissionalImagemDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "profissional_imagem")
public class ProfissionalImagem extends AbstractEntity<Integer, ProfissionalImagemDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "url")
    private String url;

    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profissional profissional;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Transient
    private MultipartFile file;
}
