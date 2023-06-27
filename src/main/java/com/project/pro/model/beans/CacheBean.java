package com.project.pro.model.beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CacheBean implements Serializable {

    private String name;

    private Object value;
}
