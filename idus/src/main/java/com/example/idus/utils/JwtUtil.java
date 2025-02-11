package com.example.idus.utils;

import com.example.idus.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "";
    private static final int EXP = 30 * 60 * 1000;


    public static User getUser(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return User.builder()
                    .idx(claims.get("userIdx", Long.class))
                    .email(claims.get("userEmail", String.class))
                    .nickname(claims.get("userNickname", String.class))
                    .username(claims.get("userName", String.class))
                    .password(claims.get("userPassword", String.class))
                    .gender(claims.get("userGender", String.class))
                    .phonenumber(claims.get("userPhoneNumber", String.class))
                    .build();

        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return null;
        }
    }

    public static String generateToken(Long userIdx, String userEmail, String userName) {
        Claims claims = Jwts.claims();
        claims.put("username", userName);
        claims.put("userEmail", userEmail);
        claims.put("userIdx", userIdx);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return false;
        }
        return true;
    }
}

