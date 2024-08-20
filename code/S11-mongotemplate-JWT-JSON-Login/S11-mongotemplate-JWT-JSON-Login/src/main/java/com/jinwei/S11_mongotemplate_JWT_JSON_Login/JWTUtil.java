package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import lombok.Data;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import java.time.Instant;

@Data
public class JWTUtil {
    // 获取JWT配置-对象-GSON
    private static String jsonFile = "jwt-authorization.json";
    static JsonUtil jsonUtil = new JsonUtil();
    static String jsonStr;

    static {
        try {
            jsonStr = jsonUtil.readJSON(jsonFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Gson gson = new Gson();
    static JWTConfig jwtConfig = gson.fromJson(jsonStr, JWTConfig.class);

    // 设置token访问的过期时间-单位/分种
    private static final int DAY = 60*60*24;
    public static final int ACCESS_EXPIRE = 30 * DAY;

    // 设置秘钥的加密算法
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS512;
    // 生成私钥，只能在服务器端保存
    // 使用Jwts.SIG.HS256 算法需要SECRET至少32位
    // 使用Jwts.SIG.HS512 算法需要SECRET至少64位

    // 设置密钥字符串
    private final static String SECRET = jwtConfig.secret;
    // 使用加密算法加密密钥字符串
    public final static SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 设置jwt签发者
    private final static String JWT_ISS = jwtConfig.iss;

    // 设置jwt主题
    private final static String SUBJECT = jwtConfig.subject;

    public JWTUtil() throws IOException {
    }

    /* 常用声明：
    iss: jwt签发者-签发方
    sub: jwt主题-面向用户
    aud: jwt接受者-接受方
    exp: jwt过期时间-过期时间必须要大于签发时间
    nbf: jwt开始启用时间-定义在什么时间之前-jwt不可用的
    iat: jwt签发时间-过期时间必须要大于签发时间
    jti: jwt唯一身份标识-主要用来作为一次性token-回避重放攻击
     */
    public String genJWTToken(String inputStr)  {

        // 生成令牌id-UUID.randomUUID()-随机
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        return Jwts.builder()
                // 设置头部信息-header
                .header()
                .add("type", "JWT")
                .add("algo", "HS512")
                .and()
                // 设置负载信息-payload
                .claim("payload", inputStr+"==="+SECRET)
                // 设置令牌ID
                .id(uuid)
                // 设置过期日期
                .expiration(exprireDate)
                // 设置签发时间
                .issuedAt(new Date())
                // 设置主题
                .subject(SUBJECT)
                // 设置签发者
                .issuer(JWT_ISS)
                // 设置签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }

    // 解析token-claim
    public static Jws<Claims> parseJWTToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)  // 必须持有相同的KEY才能解析
                .build()
                .parseSignedClaims(token);
    }

    // 解析头部-Header
    public  JwsHeader parseHeader(String token) {
        return parseJWTToken(token).getHeader();
    }

    // 解析负载-Payload
    public  Claims parsePayload(String token) {
        return parseJWTToken(token).getPayload();
    }

//    // 解析签名-Signature
//    public  String parseSignature(String token) {
//        return parseJWTToken(token).getSignature();
//    }

}