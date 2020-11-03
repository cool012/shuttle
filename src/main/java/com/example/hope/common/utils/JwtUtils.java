package com.example.hope.common.utils;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

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
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("QC%*gHZH8#");
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            // 添加构成JWT的参数
            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                    .claim("userId", user.getId())
                    .claim("email", user.getEmail())
                    .claim("address", user.getAddress())
                    .claim("type", user.getType())
                    .claim("score", user.getScore())
                    .setSubject(user.getEmail())// 代表这个JWT的主体，即它的所有人
                    .setAudience(user.getEmail())// 代表这个JWT的接收对象；
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
        String type = parseJWT(token).get("type", String.class);
        if (type.equals("2")) {
            return true;
        }
        return false;
    }

    public static User getUser(String token) {
        long id = parseJWT(token).get("userId", Integer.class);
        String email = parseJWT(token).get("email", String.class);
        String address = parseJWT(token).get("address", String.class);
        int score = parseJWT(token).get("score", Integer.class);
        String type = parseJWT(token).get("type", String.class);
        return new User(id, null, email, address, token, score);
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("QC%*gHZH8#"))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            throw new BusinessException(0, "Token过期");
        } catch (Exception e) {
            throw new BusinessException(0, "token解析异常");
        }
    }
}
