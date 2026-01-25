package com.dart.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DartServerApplicationTest {
    @Test
    void contextLoads() {
    }

    @Test
    void mainRunsWithoutException() {
        DartServerApplication.main(new String[]{});
    }
}
