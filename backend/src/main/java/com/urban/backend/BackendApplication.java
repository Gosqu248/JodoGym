package com.urban.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class BackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();
        System.setProperty("AUTH_PRIVATE_KEY", dotenv.get("AUTH_PRIVATE_KEY"));
        System.setProperty("AUTH_PUBLIC_KEY",  dotenv.get("AUTH_PUBLIC_KEY"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("FB_TOKEN", dotenv.get("FB_TOKEN"));
        System.setProperty("FB_PAGE", dotenv.get("FB_PAGE"));

        SpringApplication.run(BackendApplication.class, args);
    }

}
