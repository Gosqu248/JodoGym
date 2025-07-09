package com.urban.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();
        // wrzuć do System properties, żeby Spring je widział
        System.setProperty("AUTH_PRIVATE_KEY", dotenv.get("AUTH_PRIVATE_KEY"));
        System.setProperty("AUTH_PUBLIC_KEY",  dotenv.get("AUTH_PUBLIC_KEY"));

        SpringApplication.run(BackendApplication.class, args);
    }

}
