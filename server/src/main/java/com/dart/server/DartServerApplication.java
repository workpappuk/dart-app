package com.dart.server;

import com.dart.server.common.DBLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
