package com.example.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

/**
 * JWT工具类
 * 
 * @author example
 */
public class JwtUtils {

    /**
     * 根据密钥生成SecretKey
     *
     * @param secretString 密钥字符串
     * @return SecretKey
     */
    public static SecretKey generateKey(String secretString) {
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @param secret 密钥
     * @param expireTime 过期时间(分钟)
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, String secret, int expireTime) {
        SecretKey secretKey = generateKey(secret);
        JwtBuilder builder = Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setClaims(claims);
        
        long nowMillis = System.currentTimeMillis();
        if (expireTime > 0) {
            long expMillis = nowMillis + expireTime * 60 * 1000L;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        
        return builder.compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 数据声明
     */
    public static Claims parseToken(String token, String secret) {
        SecretKey secretKey = generateKey(secret);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证令牌是否有效
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 是否有效
     */
    public static boolean validateToken(String token, String secret) {
        try {
            parseToken(token, secret);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token, String secret) {
        try {
            Claims claims = parseToken(token, secret);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取令牌失效时间
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 失效时间
     */
    public static Date getExpirationDateFromToken(String token, String secret) {
        Claims claims = parseToken(token, secret);
        return claims.getExpiration();
    }

    /**
     * 根据键获取令牌中的值
     *
     * @param token 令牌
     * @param secret 密钥
     * @param key 键
     * @return 值
     */
    public static Object getValueFromToken(String token, String secret, String key) {
        Claims claims = parseToken(token, secret);
        return claims.get(key);
    }
} 