package com.setusathi.backend.config;

import com.setusathi.backend.model.Role;
import com.setusathi.backend.model.UserEntity;
import com.setusathi.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            // Pehle purana data saaf karna (Fresh Start)
            repository.deleteAll();

            // Seed Users Insert
            repository.save(new UserEntity("APL-2026-0889", encoder.encode("123456"), "Ayush Sharma", Role.ROLE_APPLICANT));
            repository.save(new UserEntity("OFF-MP-808", encoder.encode("123456"), "Dr. Ramesh Sharma", Role.ROLE_OFFICIAL));
            repository.save(new UserEntity("ADMIN-ROOT-99", encoder.encode("123456"), "System Admin", Role.ROLE_ADMIN));

            System.out.println("⚡ DB Cleaned & Fresh Seed Users Initialized!");
        };
    }
}