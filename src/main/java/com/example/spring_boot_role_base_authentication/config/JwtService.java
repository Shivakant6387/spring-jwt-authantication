package com.example.spring_boot_role_base_authentication.config;


import com.example.spring_boot_role_base_authentication.util.HmacSha256Signer;
import com.example.spring_boot_role_base_authentication.util.JsonUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key)
                .compact();
    }
//private static final SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//
//    public String generateToken(String username) {
//        // Create JWT Header (Base64 URL encoded)
//        Map<String, Object> header = new HashMap<>();
//        header.put("alg", "HS256");
//        header.put("typ", "JWT");
//
//        String headerJson = JsonUtil.toJson(header);
//        String headerBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
//        Map<String, Object> payload = new HashMap<>();
//        payload.put("sub", username);
//        payload.put("iat", System.currentTimeMillis() / 1000);
//        payload.put("exp", (System.currentTimeMillis() + 1000 * 60 * 60 * 10) / 1000);
//
//        String payloadJson = JsonUtil.toJson(payload);
//        String payloadBase64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());
//        String unsignedJwt = headerBase64Url + "." + payloadBase64Url;
//        String signature = HmacSha256Signer.signToBase64Url(unsignedJwt, key);
//        return unsignedJwt + "." + signature;
//    }
    public String extractUsername(String token) {
        try {
            String[] chunks = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payloadMap = mapper.readValue(payload, Map.class);
            return (String) payloadMap.get("sub"); // 'sub' is subject (username)
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
