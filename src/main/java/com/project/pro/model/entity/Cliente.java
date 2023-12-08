package com.project.pro.model.entity;


import com.project.pro.model.dto.ClienteDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pro_cliente")
public class Cliente extends AbstractEntity<Integer, ClienteDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;

    @Column(name = "imagem")
    private String imagem;

}
