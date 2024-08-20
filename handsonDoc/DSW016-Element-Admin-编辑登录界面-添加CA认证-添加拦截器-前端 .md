# DSW016-Element-Admin-编辑登录界面-添加CA认证-添加拦截器-前端 

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code/S10-vue-element-admin-edit](../code/S10-vue-element-admin-edit/)

继DSW15:

---

## SpringBoot后台程序

Code: [../code/S11_mongotemplate_JWT_JSON_Login](../code/S11_mongotemplate_JWT_JSON_Login/)

## 创建：注册：拦截器配置类-InterceptorConfig

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//        registration.addPathPatterns("/**"); //所有路径都被拦截
//        registration.excludePathPatterns("" +
//                        "/assets/**",             // assets文件夹里文件不拦截
//                "/**/*.js",              //js静态资源不拦截
//                "/**/*.css"             //css静态资源不拦截

@Configuration
@Slf4j
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    // 注册-自定义拦截器-registry
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
```

## 创建：拦截器类-AuthenticationInterceptor

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        //判断当前拦截到的是Controller的方法还是其他资源
        //当前拦截到的不是动态方法，直接放行
        if (handler instanceof HandlerMethod) {

            // 获取JWT配置-对象-GSON
            String jsonFile = "jwt-authorization.json";
            JsonUtil jsonUtil = new JsonUtil();
            String jsonStr;

            try {
                jsonStr = jsonUtil.readJSON(jsonFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Gson gson = new Gson();
            JWTConfig jwtConfig = gson.fromJson(jsonStr, JWTConfig.class);
            System.out.println("jwtConfig.secret = " + jwtConfig.secret);

            Map<String, String> ParameterMap = new HashMap<String, String>(); //map参数
            Map<String, String[]> map=request.getParameterMap(); //请求中的map数组
            for(String key :map.keySet()) { //遍历数组
                ParameterMap.put(key, map.get(key)[0]); //将值key，key对应的的value 赋值到map参数中
            }
            System.out.println("map = " + map);
            System.out.println("ParameterMap = " + ParameterMap);
            System.out.println("ParameterMap.get(\"idPW\") = " + ParameterMap.get("idPW"));
            System.out.println("ParameterMap.get(\"idName\") = " + ParameterMap.get("idName"));
            System.out.println("ParameterMap.get(\"cacert\") = " + ParameterMap.get("cacert"));

            JSONObject cacertJsonObj = JSONObject.parseObject(ParameterMap.get("cacert"));  // 将json字符串转换成对象
            String cacert = cacertJsonObj.get("cacert").toString();
            System.out.println("cacert = " + cacert);

            //2、校验令牌
            try {
                JWTUtil jwtUtil = new JWTUtil();
                Jws<Claims> jwtoken = jwtUtil.parseJWTToken(cacert);
                System.out.println("jwtoken = " + jwtoken);

                Claims payload  = jwtUtil.parsePayload(cacert);
                String payloadStr  = payload.get("payload").toString();
                System.out.println("payloadStr = " + payloadStr);

                String[] strList = payloadStr.split("===");
                System.out.println("strList = " + strList);
                System.out.println("strList[0] = " + strList[0]);
                System.out.println("strList[1] = " + strList[1]);

                if (strList[1].equals(jwtConfig.secret)) {
                    result = true;
                    System.out.println("jwt-正确-不拦截");
                } else {
                    result = false;
                    System.out.println("jwt-不正确-拦截");
                }

            } catch (Exception e) {
                //4、不通过，响应401状态码
                response.setStatus(401);
                result = false;
            }
        }

        System.out.println("=======================================================");
        return result;
    }
}
```

## 创建：用户类-User

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
//@AllArgsConstructor
public class User {
    @MongoId
    private String id;

    public User(String idName, String idPW) {
    }
}
```

## 创建：用户控制类-UserController

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jinwei.S11_mongotemplate_JWT_JSON_Login.JWTUtil.jwtConfig;


@RestController
@RequestMapping("api/backuser")
public class UserController {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 用户登录-User-通过参数-idName-idPW
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(String idName, String idPW) throws IOException {
        List<User> find_loginUser = mongoTemplate.find(Query.query(Criteria.where("idName").is(idName).and("idPW").is(idPW)), User.class);
        System.out.println("find_loginUser = " + find_loginUser);
        if (find_loginUser != null && find_loginUser.size() != 0) {

            JWTUtil jwtUtil = new JWTUtil();
            String jwtoken = jwtUtil.genJWTToken(idName);
            System.out.println("jwtoken = " + jwtoken);

            System.out.println("User "+idName +" logined successfully!");
            return ResponseEntity.ok("User "+idName +" logined successfully!");
        } else {
            // 获取JWT配置-对象-GSON
//            String jsonFile = "jwt-authorization.json";
//            JsonUtil jsonUtil = new JsonUtil();
//            String jsonStr;
//
//            try {
//                jsonStr = jsonUtil.readJSON(jsonFile);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Gson gson = new Gson();
//            JWTConfig jwtConfig = gson.fromJson(jsonStr, JWTConfig.class);
//            System.out.println("jwtConfig.secret = " + jwtConfig.secret);

            System.out.println("idName = " + idName);
            System.out.println("idPW = " + idPW);
            System.out.println("jwtConfig.user0 = " + jwtConfig.user0);
            System.out.println("jwtConfig.pw0 = " + jwtConfig.pw0);

            if (idName.equals(jwtConfig.user0) && idPW.equals(jwtConfig.pw0)) {
                System.out.println("Default User logined successfully!");
                return ResponseEntity.ok("Default User logined successfully!");
            } else {
                System.out.println("用户不存在");
                return ResponseEntity.badRequest().body("用户不存在");
            }
        }
    }

    // 用户注册-User-通过参数-idName-idPW
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(String idName, String idPW) {
        List<User> find_registerUser = mongoTemplate.find(Query.query(Criteria.where("idName")), User.class);
        System.out.println("find_registerUser = " + find_registerUser);
        if (find_registerUser != null) {
            return ResponseEntity.badRequest().body("用户名已存在");
        } else {
            User user = new User(idName, idPW);
            System.out.println("idName = " + idName);
            System.out.println("idPW = " + idPW);
            User insertUser = mongoTemplate.insert(user);
            return ResponseEntity.ok("用户:"+idName +"注册成功！登录密码为:" + idPW);
        }
    }


    // 增加数据
    @RequestMapping(value="/insertUser", method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(User person) {
        User insert = mongoTemplate.insert(person);
        return ResponseEntity.ok("添加成功,添加后的用户id为：" + insert.getId());
    }

    // 删除数据
    @RequestMapping(value="/deleteUserById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        User findAndRemove = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), User.class);
        return ResponseEntity.ok("删除成功,删除的数据为：" + findAndRemove);
    }

    // 修改数据
    @RequestMapping(value = "updateUserByName", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserByName(String name, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("age", age), User.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 分页查询
    @RequestMapping(value="/findUserPage", method = RequestMethod.GET)
    public Object findUserPage(Integer currentPageNo, Integer pageSize) {
        Query limit = new Query().skip((currentPageNo - 1) * pageSize).limit(pageSize);
        List<User> findUser = mongoTemplate.find(limit, User.class);
        return findUser;
    }

    // 查询所有的数据
    @RequestMapping(value="/findAllUser", method = RequestMethod.GET)
    public Object findAllUser() {
        List<User> findUser = mongoTemplate.findAll(User.class);
        return findUser;
    }

}
```

## 创建：JWT配置类-JWTConfig

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

public class JWTConfig
{
    String secret;
    String iss;
    String subject;
    String user0;
    String pw0;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("JWTConfig{");
        sb.append("secret='").append(secret).append('\'');
        sb.append(", iss=").append(iss);
        sb.append(", subject=").append(subject);
        sb.append(", user0=").append(user0);
        sb.append(", pw0=").append(pw0);
        sb.append('}');
        return sb.toString();
    }
}
```

## 创建：JWT集成工具类-JWTUtil

```java
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
```

## 创建：Json集成工具类-JsonUtil

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

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

## 创建：主程序-测试类-S11MongotemplateJwtJsonLoginApplication

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class S11MongotemplateJwtJsonLoginApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(S11MongotemplateJwtJsonLoginApplication.class, args);

//		User user = new User("user2", "123");
//		String subject = new Gson().toJson(user);
////
//		JWTUtil jwtUtil = new JWTUtil();
////		String jwtoken = jwtUtil.genJWTToken(subject);
//
//		String jwtoken = "eyJ0eXBlIjoiSldUIiwiYWxnbyI6IkhTNTEyIiwiYWxnIjoiSFM1MTIifQ.eyJwYXlsb2FkIjoie309PT0xMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MCIsImp0aSI6ImE0MThhNzY1LWU2ZDktNDBiNC05MTFjLTAwOTU5NjVkZDI1YyIsImV4cCI6MTcyNDk1MDk3OSwiaWF0IjoxNzI0MDg2OTc5LCJzdWIiOiJTVUJKRUNUX3podXRpIiwiaXNzIjoiSVNTX3FpYW5mYWZhbmcifQ.4BOMKBjL4FEH3alLLHi1mFM5WReED7_Ip1oW9OkobDaezwWoXLwA2xd8jzUw-zA85WNRBvbBcfWCSaDvdM_86Q";
//
//		System.out.println("jwtoken = " + jwtoken);
//		System.out.println("jwtUtil.parseJWTToken(jwtoken) = " + jwtUtil.parseJWTToken(jwtoken));
//		System.out.println("jwtUtil.parseHeader(jwtoken) = " + jwtUtil.parseHeader(jwtoken));
//		System.out.println("jwtUtil.parsePayload(jwtoken) = " + jwtUtil.parsePayload(jwtoken));


	}

}
```


---

## 测试结果

![alt text](image-104.png)

![alt text](image-105.png)

![alt text](image-106.png)

![alt text](image-107.png)


