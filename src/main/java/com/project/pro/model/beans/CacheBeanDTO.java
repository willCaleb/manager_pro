package com.project.pro.model.beans;

import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.CacheBean;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CacheBeanDTO extends AbstractDTO<Integer, CacheBean> {

    private Integer id;

    private String name;

    private Object value;
}
