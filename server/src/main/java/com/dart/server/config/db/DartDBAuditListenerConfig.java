package com.dart.server.config.db;

import com.dart.server.app.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DartDBAuditListenerConfig {
    @Autowired
    public void configure(UserRepository userRepository) {
        DartDBAuditListener.setUserRepository(userRepository);
    }
}

