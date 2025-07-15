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
    private static final long ACCESS_TOKEN_TTL = 48 * 3600 * 1000;
    private static final  long REFRESH_TOKEN_TTL = 96 * 3600 * 1000;
    private static final long RESET_TOKEN_TTL = 5 * 3600 * 1000;


    @Value("${AUTH_PRIVATE_KEY}")
    private String privateKey;

    @Value("${AUTH_PUBLIC_KEY}")
    private String publicKey;

    @Bean
    public PrivateKey privateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        String privateKeyContent = privateKey
                .replace("\\n", "\n")
                .replaceAll("\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }

    @Bean
    public PublicKey publicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        String publicKeyContent = publicKey
                .replace("\\n", "\n")
                .replaceAll("\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
    }

    @Bean
    public long jwtExpiration() {
        return ACCESS_TOKEN_TTL;
    }

    @Bean
    public long refreshTokenExpiration() {
        return REFRESH_TOKEN_TTL;
    }

    @Bean
    public long resetTokenExpiration() {
        return RESET_TOKEN_TTL;
    }
}
