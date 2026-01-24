package com.dart.server;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;


@SpringBootApplication
public class DartServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DartServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println(base64Key);
    }



}
