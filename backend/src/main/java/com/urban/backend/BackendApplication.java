package com.urban.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

public class BackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();
        System.setProperty("AUTH_PRIVATE_KEY", dotenv.get("AUTH_PRIVATE_KEY"));
        System.setProperty("AUTH_PUBLIC_KEY",  dotenv.get("AUTH_PUBLIC_KEY"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));

        SpringApplication.run(BackendApplication.class, args);
    }

}
