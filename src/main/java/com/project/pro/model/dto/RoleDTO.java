package com.project.pro.model.dto;

import com.project.pro.enums.EnumRole;
import com.project.pro.model.entity.Role;
import lombok.Data;

@Data
public class RoleDTO extends AbstractDTO<Integer, Role>{

    private Integer id;

    private EnumRole enumRole;

}
