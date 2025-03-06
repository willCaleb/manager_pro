package com.project.pro.model.entity;

import com.project.pro.model.dto.ConfigProfissionalDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "pro_config_profissional")
public class ConfigProfissional extends AbstractEntity<Integer, ConfigProfissionalDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_config", referencedColumnName = "id")
    private Config config;

    @ManyToOne
    @JoinColumn(name = "id_prosissional", referencedColumnName = "id")
    private Profissional profissional;

    @Column(name = "valor")
    private String valor;

}
