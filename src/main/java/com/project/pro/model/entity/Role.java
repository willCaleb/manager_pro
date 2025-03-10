package com.project.pro.model.entity;

import com.project.pro.enums.EnumRole;
import com.project.pro.model.dto.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "pro_role")
public class Role extends AbstractEntity<Integer, RoleDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Convert(converter = EnumRole.EnumConverter.class)
    private EnumRole enumRole;
}
