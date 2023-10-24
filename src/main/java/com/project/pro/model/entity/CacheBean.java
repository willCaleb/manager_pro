package com.project.pro.model.entity;

import com.project.pro.model.beans.CacheBeanDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheBean extends AbstractEntity<Integer, CacheBeanDTO>{

    private Integer id;

    private String name;

    private Object value;
}
