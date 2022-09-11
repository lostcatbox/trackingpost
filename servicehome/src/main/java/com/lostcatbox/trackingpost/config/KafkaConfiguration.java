package com.lostcatbox.trackingpost.config;

import com.example.trackingpostcore.domain.RequestInfo;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

//kafkaTemplate 클래스를 Bean 으로 등록
@Configuration
public class KafkaConfiguration {
    private Environment env;

    @Autowired
    KafkaConfiguration(Environment env) {
        this.env = env;
    }

    //설정 정보를 Map<String,Object> 형식으로 작성해서 return 하는 일
    @Bean
    public Map<String,Object> producerConfig() {

        Map<String,Object> props = new HashMap<>();

        //server host 지정
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
                ,env.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));

        // retries 횟수
        props.put(ProducerConfig.RETRIES_CONFIG
                ,env.getProperty(ProducerConfig.RETRIES_CONFIG));

        //batcch size 지정
        props.put(ProducerConfig.BATCH_SIZE_CONFIG
                ,env.getProperty(ProducerConfig.BATCH_SIZE_CONFIG));

        // linger.ms
        props.put(ProducerConfig.LINGER_MS_CONFIG
                ,env.getProperty(ProducerConfig.LINGER_MS_CONFIG));

        //buufer memory size 지정
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG
                ,env.getProperty(ProducerConfig.BUFFER_MEMORY_CONFIG));

        //key serialize 지정
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
                , StringSerializer.class);

        //value serialize 지정
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
                ,JsonSerializer.class);

        return props;
    }

    // ProducerFactory를 return하는데 이때 위에서 만들어 놓은 producerConfig()의 설정 정보를 이용해서 ProducerFactory생성자를 호출
    @Bean
    public ProducerFactory<String, RequestInfo> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    //최종적으로 우리가 Controller에서 사용 할 KafkaTemplate을 IOC 컨테이너에 등록하는 일을 하게되고 이때 위에서 만들어 놓은 producerFactory() 를 호출해서 KafkaTemplate생성자 Parameter로 넘겨준다.
    @Bean
    public KafkaTemplate<String, RequestInfo> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
