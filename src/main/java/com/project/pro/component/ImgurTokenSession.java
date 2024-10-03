package com.project.pro.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenSession {

    private static String imgurToken;

    public static String getImgurToken() {
        return imgurToken;
    }

    public static void setImgurToken(String imgurToken) {
        TokenSession.imgurToken = imgurToken;
    }
}
