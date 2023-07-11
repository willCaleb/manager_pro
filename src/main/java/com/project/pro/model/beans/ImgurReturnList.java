package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ImgurReturnList implements Serializable{
    private int status;

    private boolean success;

    private List<Data> data;
}
