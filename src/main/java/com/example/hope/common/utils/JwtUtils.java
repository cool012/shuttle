package com.example.hope.common.utils;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.config.exception.UnauthorizedException;
import com.example.hope.model.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static String key;

    @Value("${jwt.key}")
    public void setKey(String jwtKey) {
        key = jwtKey;
    }

    /**
     * 获取Token
     *
     * @param user
     * @param Exp
     * @return String
     */
    public static String createToken(User user, int Exp) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            // 添加构成JWT的参数
            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                    .claim("userId", user.getId())
                    .claim("phone", user.getPhone())
                    .claim("address", user.getAddress())
                    .claim("admin", user.isAdmin())
                    .claim("score", user.getScore())
                    .claim("name", user.getName())
                    .setSubject(user.getPhone())// 代表这个JWT的主体，即它的所有人
                    .setAudience(user.getPhone())// 代表这个JWT的接收对象；
                    .setIssuedAt(now)// 是一个时间戳，代表这个JWT的签发时间；
                    .signWith(signatureAlgorithm, signingKey);

            // 添加token过期时间
            long TTLMillis = Exp * 60 * 1000;
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                jwtBuilder.setExpiration(exp).setNotBefore(now);
            }

            return jwtBuilder.compact();
        } catch (Exception e) {
            throw new RuntimeException("生成token失败");
        }
    }

    /**
     * 获取UserId
     *
     * @param token
     * @return
     */
    public static long getUserId(String token) {
        long userId = parseJWT(token).get("userId", Integer.class);
        return userId;
    }

    public static boolean is_admin(String token) {
        boolean admin = parseJWT(token).get("admin", Boolean.class);
        return admin;
    }

//    public static User getUser(String token) {
//        long id = parseJWT(token).get("userId", Integer.class);
//        String phone = parseJWT(token).get("phone", String.class);
//        String address = parseJWT(token).get("address", String.class);
//        int score = parseJWT(token).get("score", Integer.class);
//        boolean admin = parseJWT(token).get("admin", Boolean.class);
//        String name = parseJWT(token).get("name", String.class);
//        return new User(null, phone, address, score, admin, name);
//    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new BusinessException(0, "token解析异常");
        }
    }
}
