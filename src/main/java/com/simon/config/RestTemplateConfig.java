package com.simon.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by simon on 2017/4/13.
 */
@Configuration
public class RestTemplateConfig {
    /*@Bean
    @LoadBalanced
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }*/
}
