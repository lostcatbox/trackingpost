package com.example.trackingpostcore.domain;

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
    private final String value;
    PostCompanyEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
