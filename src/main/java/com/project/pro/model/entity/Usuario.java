package com.project.pro.model.entity;

import com.project.pro.model.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pro_usuario")
public class Usuario extends AbstractEntity<Integer, UsuarioDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

}
