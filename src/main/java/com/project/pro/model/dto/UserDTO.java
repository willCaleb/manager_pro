package com.project.pro.model.dto;

import com.project.pro.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractDTO<Integer, User> {

    private Integer id;
}
