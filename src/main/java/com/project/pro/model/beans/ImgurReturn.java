package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pro.model.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ImgurReturn implements Serializable{
    @JsonProperty("status")
    private int status;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private Data data;
}
