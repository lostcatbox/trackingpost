package com.example.trackingpostcore.domain;

import java.util.Arrays;
import java.util.Optional;

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
    public static Optional<PostCompanyEnum> valueOfName(String name) { //core모듈에존재하므로 각자 service에서 null 에러처리할수있도록 optional 반환
        return Optional.of(Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findAny()
                .orElse(null));
    }
}
