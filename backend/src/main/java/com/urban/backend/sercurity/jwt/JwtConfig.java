package com.urban.backend.sercurity.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {
    private static final long ACCESS_TOKEN_TTL = 5 * 60 * 1000;
    private static final long REFRESH_TOKEN_TTL = 7 * 24 * 3600 * 1000;

    @Value("${AUTH_PRIVATE_KEY}")
    private String privateKey;

    @Value("${AUTH_PUBLIC_KEY}")
    private String publicKey;

    @Bean
    public PrivateKey generatePrivateKey() {
        try {
            var keyBytes = Base64.getDecoder().decode(privateKey);
            var privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PublicKey generatePublicKey() {
        try {
            var keyBytes = Base64.getDecoder().decode(publicKey);
            var publicKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Bean
    public long jwtExpiration() {
        return ACCESS_TOKEN_TTL;
    }

    @Bean
    public long refreshTokenExpiration() {
        return REFRESH_TOKEN_TTL;
    }
}
