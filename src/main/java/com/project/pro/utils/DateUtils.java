package com.project.pro.utils;

import com.project.pro.enums.DatePattern;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

public class DateUtils {


    public static String convertFromString(String date, DatePattern pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
        Date parse = dateFormat.parse(date);
        return dateFormat.format(parse);
    }

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public String getStringDate(DatePattern pattern) {
        return getFormatted(getDate(), pattern);
    }

    public static Supplier<Date> dateFromSupplier = Calendar.getInstance()::getTime;

    public static String getFormatted(Date date, DatePattern pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.getValue());
        return dateFormat.format(date);
    }

//    public static Date adicionar(Date date, Integer amount, int field) {
//        return Calendar.getInstance().add(field, amount);
//    }
}
