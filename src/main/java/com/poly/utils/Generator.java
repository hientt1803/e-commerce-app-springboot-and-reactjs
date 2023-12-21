package com.poly.utils;

public class Generator {
    public static String getCode() {
        // create 6 random number
        String code = String.valueOf(Math.random()).substring(2, 8);
        return code;
    }
}
