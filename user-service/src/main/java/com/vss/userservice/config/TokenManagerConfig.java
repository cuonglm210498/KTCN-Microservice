package com.vss.userservice.config;

import com.vss.userservice.security.jwt.TokenConsumer;
import com.vss.userservice.security.jwt.TokenProducer;
import com.vss.userservice.security.jwt.util.KeyReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Configuration
@PropertySource({"classpath:token.properties"})
public class TokenManagerConfig {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.audience}")
    private String audience;

    @Value("#{'${jwt.audiences}'.split(',')}")
    private List<String> audiences;

    @Value("${jwt.expiration}")
    private long expirationInMin;

    @Value("${jwt.notBefore}")
    private float notBeforeInMin;

    @Value("${jwt.privateKey}")
    private String privateKeyPath;

    @Value("${jwt.publicKey}")
    private String publicKeyPath;

    @Bean
    public TokenProducer tokenProducer() {
        return createTokenProducer(expirationInMin);
    }

    private TokenProducer createTokenProducer(float time) {
        try {
            PrivateKey privateKey = KeyReader.getPrivateKey(privateKeyPath);
            return new TokenProducer(issuer, subject, audiences.stream().toArray(size -> new String[size]),
                    time, notBeforeInMin, privateKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    @Bean
    public TokenConsumer tokenConsumer() {
        try {

            PublicKey publicKey = KeyReader.getPublicKey(publicKeyPath);
            return new TokenConsumer(audience, publicKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }
}
