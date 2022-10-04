package com.lostcatbox.trackingpost.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.example.trackingpostcore.aop") //aop LogConfig 현재 module실행시 Bean에 등록
@Configuration
public class ServiceConfig {
}
