package com.example.hope.common.utils;

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
            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .claim("userId",user.getId())
                    .claim("email",user.getEmail())
                    .claim("type",user.getType())
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
     * @param token
     * @return
     */
    public static long getUserId(String token){
        long userId = parseJWT(token).get("userId", Integer.class);
        return userId;
    }

    public static boolean is_admin(String token){
        String type = parseJWT(token).get("type", String.class);
        if(type.equals("2")){
            return true;
        }
        return false;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("ZGh5NjI3CiA="))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            throw new RuntimeException("Token过期");
        } catch (Exception e){
            throw new RuntimeException("token解析异常");
        }
    }
}
