package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgurReturn {
    @JsonProperty("status")
    private int status;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private Data data;
}
