package com.example.trackingpostcore.domain;

import java.util.Arrays;

public enum PostCompanyEnum {
    CJ("CJ대한통운"),
    CU("CU편의점택배"),
    EPOST("우체국택배"),
    HANJIN("한진택배"),
    LOTTE("롯데택배"),
    EMS("EMS"),
    ILOGEN("로젠택배"),
    CVSNET("CVSNet"),
    DHL("DHL");
    private final String name;
    PostCompanyEnum(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public static PostCompanyEnum valueOfName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("해당하는enum값없음")); // 확인해야함. null말고 오류 터지면, 그냥 끝?에러처리안함냐?
    }
}
