package com.lzdk.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SonarqubeMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarqubeMonitoringApplication.class, args);
    }

}
