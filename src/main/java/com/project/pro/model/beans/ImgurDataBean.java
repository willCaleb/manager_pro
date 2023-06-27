package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImgurDataBean {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("expires_in")
    private String expires_in;

    @JsonProperty("token_type")
    private String token_type;

    private String scope;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("account_id")
    private Object account_id;

    @JsonProperty("account_username")
    private String account_username;

}
