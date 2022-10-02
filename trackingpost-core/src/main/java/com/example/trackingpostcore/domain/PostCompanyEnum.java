package com.example.trackingpostcore.domain;

import com.example.trackingpostcore.advice.exception.NotFoundCompanyEnumCException;

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
    DHL("DHL"),
    Error("Error");
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
                .orElseThrow(()-> new NotFoundCompanyEnumCException("name에 해당하는 택배사의 enum값 없음"));
    }
}
