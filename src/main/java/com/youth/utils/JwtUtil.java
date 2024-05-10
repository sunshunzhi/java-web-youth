package com.youth.utils;

import com.alibaba.fastjson.JSON;
import com.youth.common.Token;
import com.youth.enums.RedisKeyEnum;
import com.youth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName JwtUtil工具类
 * @Description 双令牌认证 黑名单机制
 * @Version 1.0
 */
@Slf4j
@Component
@Data
public class JwtUtil {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.token.access}")
    private Long accessTokenExpire;

    @Value("${jwt.expiration.token.refresh}")
    private Long refreshTokenExpire;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 生成双令牌
     *
     * @param claims
     * @return
     */
    public Token createTokens(Map<String, Object> claims) {
        Token token = new Token();
        token.setAccessToken(createToken(claims, accessTokenExpire));
        token.setRefreshToken(createToken(claims, refreshTokenExpire));
        return token;
    }

    /**
     * 生成token
     * 使用Hs256算法，私钥使用固定密钥
     *
     * @param claims 设置的信息
     * @return
     */
    public String createToken(Map<String, Object> claims, Long expiration) {
        //指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        //生成JWT的时间
        long expMillis = System.currentTimeMillis() + expiration;
        Date exp = new Date(expMillis);
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        String compact = Jwts.builder()
                //设置自定义负载信息
                .claims(claims)
                //设置签名使用的签名算法和签名使用的秘钥
                //如果有私有声明，一点要先设置这个自己创建的私有的声明，这个是给builder的claims赋值，一旦卸载标准的声明赋值之后，就是覆盖了那些标准的声明的
                .signWith(key, algorithm)
                //设置过期时间
                .expiration(exp)
                .compact();
        return compact;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token) {
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Jws<Claims> claimsJws = Jwts.parser()
                //设置签名的密钥
                .verifyWith(key)
                .build()
                //设置要解析的jwt
                .parseSignedClaims(token);
        return claimsJws;
    }

    /**
     * 获取 token 中注册信息
     * 不管token过不过前期都可以获取到注册信息
     *
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token) {
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims;
        try {
            claims = Jwts.parser()
                    //设置签名的密钥
                    .verifyWith(key)
                    .build()
                    //设置要解析的jwt
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

    /**
     * 验证token 合法性
     *
     * @param token
     * @return
     */
    public boolean isRightToken(String token) {
        //密钥实例
        SecretKey
                key = Keys.hmacShaKeyFor(secret.getBytes());
        try {
            Jwts.parser()
                    //设置签名的密钥
                    .verifyWith(key)
                    .build()
                    //设置要解析的jwt
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证 token 是否过期失效
     *
     * @param token
     * @return true 过期 false 未过期
     */
    public Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * 获取 token 失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDate(String token) {
        return getTokenClaim(token).getExpiration();
    }


    /**
     * 获取 token 发布时间
     *
     * @param token
     * @return
     */
    public Date getIssuedDate(String token) {
        return getTokenClaim(token).getIssuedAt();
    }


    /**
     * 保存 刷新令牌 与 访问令牌 关联关系 到redis
     *
     * @param token
     */
    public void tokenAssociation(Token token) {
        Date expirationDate = getExpirationDate(token.getRefreshToken());
        Long time = (expirationDate.getTime() - System.currentTimeMillis()) / 1000 + 100;
        redisUtil.set(RedisKeyEnum.TOKEN_REFRESH_PREFIX.getKey() + token.getRefreshToken(), token.getAccessToken(), time);
    }

    /**
     * 根据 刷新令牌 获取 访问令牌
     *
     * @param refreshToken
     */
    public String getAccessTokenByRefresh(String refreshToken) {
        Object value = redisUtil.get(RedisKeyEnum.TOKEN_REFRESH_PREFIX.getKey() + refreshToken);
        return value == null ? null : String.valueOf(value);
    }


    /**
     * 添加至黑名单
     *
     * @param token
     * @param expireTime
     */
    public void addBlacklist(String token, Date expireTime) {
        Long expireTimeLong = (expireTime.getTime() - System.currentTimeMillis()) / 1000 + 100;
        redisUtil.set(getBlacklistPrefix(token), "1", expireTimeLong);
    }

    /**
     * 校验是否存在黑名单
     *
     * @param token
     * @return true 存在 false不存在
     */
    public Boolean checkBlacklist(String token) {
        return redisUtil.exists(getBlacklistPrefix(token));
    }

    /**
     * 获取黑名单前缀
     *
     * @param token
     * @return
     */
    public String getBlacklistPrefix(String token) {
        return RedisKeyEnum.TOKEN_BLACKLIST_PREFIX.getKey() + token;
    }

    /**
     * 获取 token 中的用户信息(根据实际情况修改)
     *
     * @param token
     * @return
     */
    public User getUserInfo(String token) {
        Claims claim = this.getTokenClaim(token);
        Object user = claim.get("user");
        return JSON.parseObject(JSON.toJSONString(user), User.class);
    }

}
