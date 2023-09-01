package com.project.pro.enums;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DatePattern {

    ddMMyyyy("dd/MM/yyyy"),
    yyyyMMdd("yyyy/MM/dd")
    ;

    private final String value;

    DatePattern(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
