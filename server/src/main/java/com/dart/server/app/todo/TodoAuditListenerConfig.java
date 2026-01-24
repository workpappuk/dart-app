package com.dart.server.app.todo;

import com.dart.server.app.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoAuditListenerConfig {
    @Autowired
    public void configure(UserRepository userRepository) {
        TodoAuditListener.setUserRepository(userRepository);
    }
}

