# DSW012-JWT-token-JSON配置文件读取

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code/](../code/)


## JWT Json-Web-Token官网：https://jwt.io/

![alt text](image-64.png)

## 使用：

### gradel引入依赖

#### JWT-必需

```gradle
implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.12.6'
implementation group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.12.6'
runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.12.6'
runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.12.6'
```

#### JWT-可选


```gradle
implementation group: 'com.google.code.gson', name: 'gson', version: '2.11.0'
```

### 创建配置文件: 
代码：com/jinwei/{资源目录=resources}/jwt-authorization.json

![alt text](image-65.png)

```json
{
  "secret": "12345678901234567890123456789012345678901234567890123456789012345678901234567890",
  "iss": "ISS_qianfafang",
  "subject": "SUBJECT_zhuti",
  "aud": "ADU_jieshoufang",
  "exp": "EXP_chaoshitime",
  "nbf": "NBF_starttime",
  "iat": "IAT_qianfatime",
  "jti": "JTI_onlyID"
}
```

注意：所有配置可以一次性写入，JAVA读取的时候按照实例化的配置类 JWTConfig实际设置的参数进行选择读取，不需要考虑与json文件的对应配置。

## JWT工具类-JWTUtil: 
代码：com/jinwei/{项目根目录}/JWTUtil.java

更新：添加了JSON配置参数文件的读取

```java
package com.jinwei.S8_mongotemplate;

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
    private static final int MINUTE = 60;
    public static final int ACCESS_EXPIRE = 1 * MINUTE;

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
                .claim("username", inputStr)
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
    public  Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)  // 必须持有相同的KEY才能解析
                .build()
                .parseSignedClaims(token);
    }

    // 解析头部-Header
    public  JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    // 解析负载-Payload
    public  Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

}
```

## JSON工具类-JsonUtil
```java
package com.jinwei.S8_mongotemplate;

import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;

public class JsonUtil {

    public String readJSON(String jsonFile) throws IOException {
//        File file0 = new File("");
//        String filePath = file0.getCanonicalPath();
//        System.out.println("filePath = " + filePath);

//        String oriPath = this.getClass().getResource("").getPath();
//        System.out.println("oriPath = " + oriPath);

        File file01 = new File("");
        String filePath01 = file01.getAbsolutePath();
//        System.out.println("filePath01 = " + filePath01);

        String dirPath = filePath01 + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + jsonFile;
//        System.out.println("dirPath = " + dirPath);

        File file = new File(dirPath);
        System.out.println("file = " + file);
        FileReader fileReader = new FileReader(file);

        Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
//        System.out.println("jsonStr = " + jsonStr);
//        System.out.println("JSON.parseObject(jsonStr) = " + JSON.parseObject(jsonStr));
        return jsonStr;
    }
}
```

## 配置类-JWTConfig
```
package com.jinwei.S8_mongotemplate;

public class JWTConfig
{
    String secret;
    String iss;
    String subject;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("JWTConfig{");
        sb.append("secret='").append(secret).append('\'');
        sb.append(", iss=").append(iss);
        sb.append(", subject=").append(subject);
        sb.append('}');
        return sb.toString();
    }
}

```

## 测试主文件

```java
package com.jinwei.S8_mongotemplate;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Date;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class S8MongotemplateApplication {

	public static void main(String[] args) {
//		SpringApplication.run(S8MongotemplateApplication.class, args);

		User user = new User("user2", "123");
		String subject = new Gson().toJson(user);

		JWTUtil jwtUtil = new JWTUtil();
		String jwtoken = jwtUtil.genJWTToken(subject);

		System.out.println("jwtoken = " + jwtoken);
		System.out.println("jwtUtil.parseClaim(jwtoken) = " + jwtUtil.parseClaim(jwtoken));
		System.out.println("jwtUtil.parsePayload(jwtoken) = " + jwtUtil.parsePayload(jwtoken));

	}

}
```

## 测试

```bash
上午11:18:30: 正在执行 ':S8MongotemplateApplication.main()'…

> Task :compileJava
> Task :processResources
> Task :classes

> Task :S8MongotemplateApplication.main()
file = F:\Tutorial\SpringTest\KEY\S8-mongotemplate\S8-mongotemplate\src\main\resources\jwt-authorization.json
jwtoken = eyJ0eXBlIjoiSldUIiwiYWxnbyI6IkhTNTEyIiwiYWxnIjoiSFM1MTIifQ.eyJ1c2VybmFtZSI6Int9IiwianRpIjoiMDQwY2E1NmYtOTNjOC00OWNmLTg3YmMtMWUzYzkyMGNjOGRjIiwiZXhwIjoxNzIyNTY4NzcxLCJpYXQiOjE3MjI1Njg3MTEsInN1YiI6IlNVQkpFQ1Rfemh1dGkiLCJpc3MiOiJJU1NfcWlhbmZhZmFuZyJ9.MuY7xOlKIPxWefdHNWleOoPqI4U3Vj_oLi4hxhJ8BgPJm7JsuG0pVdBuGYN8z_ZW5iwakBLmdmPOu0boLR4spw
jwtUtil.parseClaim(jwtoken) = header={type=JWT, algo=HS512, alg=HS512},payload={username={}, jti=040ca56f-93c8-49cf-87bc-1e3c920cc8dc, exp=1722568771, iat=1722568711, sub=SUBJECT_zhuti, iss=ISS_qianfafang},signature=MuY7xOlKIPxWefdHNWleOoPqI4U3Vj_oLi4hxhJ8BgPJm7JsuG0pVdBuGYN8z_ZW5iwakBLmdmPOu0boLR4spw
jwtUtil.parsePayload(jwtoken) = {username={}, jti=040ca56f-93c8-49cf-87bc-1e3c920cc8dc, exp=1722568771, iat=1722568711, sub=SUBJECT_zhuti, iss=ISS_qianfafang}

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.8/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 1s
3 actionable tasks: 3 executed
上午11:18:32: 执行完成 ':S8MongotemplateApplication.main()'。

```

测试成功！
