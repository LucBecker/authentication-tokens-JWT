package com.lucbecker.jwtapi.config;

import com.lucbecker.jwtapi.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instanciaBaseDeDados() {

        if (this.strategy.equals("create")) {
            this.dbService.instantiateDatabase();
        } else {
            return false;
        }

        return true;
    }
}
