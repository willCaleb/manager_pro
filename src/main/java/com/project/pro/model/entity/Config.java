package com.project.pro.model.entity;

import com.project.pro.converter.Converter;
import com.project.pro.enums.EnumConfig;
import com.project.pro.model.dto.ConfigDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pro_config")
public class Config extends AbstractEntity<Integer, ConfigDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor_padrao")
    private String valorPadrao;

    @Column(name = "key")
    @Convert(converter = EnumConfig.EnumConverter.class)
    private EnumConfig key;

    @Column(name = "ativo")
    private boolean ativo;

    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConfigProfissional> configProfissionals;

    @Transient
    private Object valorUtilizado;

    @PostLoad
    private void postLoad() {
        this.valorUtilizado = Converter.converter(key.getValue(), key.getClazz());
    }

    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConfigProfissional> configsProfissional;

}
