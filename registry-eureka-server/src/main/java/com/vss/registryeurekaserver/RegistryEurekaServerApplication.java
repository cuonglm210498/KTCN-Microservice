package com.vss.registryeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryEurekaServerApplication.class, args);
    }

}
