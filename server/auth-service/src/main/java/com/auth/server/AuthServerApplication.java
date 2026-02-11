package com.auth.server;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@RequiredArgsConstructor
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuthServerApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
