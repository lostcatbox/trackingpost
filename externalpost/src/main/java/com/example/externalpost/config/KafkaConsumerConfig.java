package com.example.externalpost.config;

import com.example.trackingpostcore.domain.RequestInfo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

//목적 : ConcurrentKafkaListenerContainerFactory클래스를  생성하고 ConsumerFactory인터페이스를 내부 멤버변수에 Set하고 Bean 으로 등록하기 위한 class
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private Environment env;

    @Autowired
    KafkaConsumerConfig(Environment env) {
        this.env = env;
    }


    @Bean
    public ConsumerFactory<String, RequestInfo> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,env.getProperty("bootstrap.servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"test");
        return new DefaultKafkaConsumerFactory<>(
                props,new StringDeserializer(), new JsonDeserializer<>(RequestInfo.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RequestInfo> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, RequestInfo> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
