package com.hzz.hzzgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient
@SpringBootApplication

public class HzzgatewayApplication {



    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(HzzgatewayApplication.class, args);

    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return  new RouteServiceimpl(builder);
    }
}
