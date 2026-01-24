package com.dart.server;

import com.dart.server.app.auth.ERole;
import com.dart.server.common.DBLoader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Base64;


@RequiredArgsConstructor
@SpringBootApplication
public class DartServerApplication implements CommandLineRunner {

    private final DBLoader dbLoader;

    public static void main(String[] args) {
        SpringApplication.run(DartServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dbLoader.warmDB();
    }

}
