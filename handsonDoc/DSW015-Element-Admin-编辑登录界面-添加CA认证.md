# DSW015-Element-Admin-编辑登录界面-添加CA认证

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code/S10-vue-element-admin-edit](../code/S10-vue-element-admin-edit/)

### 添加一个字符串输入栏

代码：src\views\login\index.vue

添加组件代码：

```html
<!-- 在此添加 -->
<el-form-item prop="cacert">
  <span class="svg-container">
    <!-- 在此修改图标 -->
    <svg-icon icon-class="guide" />
  </span>
  <!-- tabindex="1" 表示元素可以被聚焦，即光标点击响应 -->
  <el-input ref="cacert" v-model="loginForm.cacertplaceholder="CA-Certification" name="cacert" type="text"
    tabindex="1" autocomplete="on" />
</el-form-item>
```
![alt text](image-96.png)

### 设置export default的默认值
设置界面初始化时候输入框内的默认值或者提示语言：

```js
export default {
  name: 'Login',
  components: { SocialSign },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }

    // 添加-认证CA证书-初步认证
    const validateCacert = (rule, value, callback) => {
      if (!validateCacert(value)) {
        // 在此处添加-初步认证CA证书的代码
        callback(new Error('Please enter the correct CA certification'))
      } else {
        callback()
      }
    }

    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '用户名-默认值',
        cacert: 'CA证书-默认值',
        password: '登录密码-默认值',
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        cacert: [{ required: true, trigger: 'blur', validator: validateCacert }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {}
    }
  },
```

![alt text](image-97.png)


### 编辑认证js

![alt text](image-99.png)

代码：src\utils\validate.js

新增：
```js
export function validCacert(str) {
  const valid_map = ['ca']
  return valid_map.indexOf(str.trim()) >= 0
}
```

### 编辑登录 index.vue

代码：src\views\login\index.vue
```js
import { validUsername,  validCacert} from '@/utils/validate'
```

### 修改handleLogin()函数

```js
handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          // 使用store存储数据
          this.$store.dispatch('user/login', this.loginForm)
            .then(() => {
              // 正式启动后的运行代码
              this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
              this.loading = false
            })
            .catch(() => {
              this.loading = false
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
```

### 通过 FileReader读取本地 json数据

```js
readerData(rawFile) {
      this.loading = true
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onload = e => {
          console.log('reader.onload = e => ========================')

          // 获取读取到的文件内容+解析JSON文件
          const contents = reader.result
          console.log('contents = ' + contents)
          console.log('contents.byteLength = ' + contents.byteLength)
          const jsonData = String.fromCharCode.apply(null, new Uint8Array(contents))
          console.log('jsonData = ' + jsonData)
          console.log('typeof jsonData = ' + typeof jsonData);

          // ========================
          // 继续处理读取数据
          // ========================

          this.loading = false
          resolve()
        }
        reader.readAsArrayBuffer(rawFile)
      })
    },
```

### 父组件直接获取子组件的值-变量值

```js
// 父组件直接获取子组件的值
value = this.$refs.UploadJson.uploadForm.jsonData
```

---

## SpringBoot后台程序

Code: [../code/S11_mongotemplate_JWT_JSON_Login](../code/S11_mongotemplate_JWT_JSON_Login/)

### 使用 SpringBoot Initializr: https://start.spring.io/

添加依赖：
![alt text](image-103.png)


## 修改: build.gradle文件
```gradle
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.3.2'
	id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.jinwei'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

	compileOnly 'org.projectlombok:lombok:1.18.34'
	annotationProcessor 'org.projectlombok:lombok:1.18.34'
	testCompileOnly 'org.projectlombok:lombok:1.18.34'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'

	implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.12.6'
	implementation group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.12.6'
	runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.12.6'
	runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.12.6'

	implementation group: 'com.google.code.gson', name: 'gson', version: '2.11.0'

	implementation group: 'com.alibaba.fastjson2', name: 'fastjson2', version: '2.0.52'
}

tasks.named('test') {
	useJUnitPlatform()
}
```

## 创建配置文件: 
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

## 创建: JSON工具类-JsonUtil
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

## 创建: JWT配置类-JWTConfig

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

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

## 创建: JWT工具类-JWTUtil: 
代码：com/jinwei/{项目根目录}/JWTUtil.java
说明：添加了JSON配置参数文件的读取


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


## 创建: 用户类-User: 

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

## 创建: 用户类-控制类-UserController

```java
package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

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


@RestController
public class UserController {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 用户登录-User-通过参数-idName-idPW
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(String idName, String idPW) throws IOException {
        List<User> find_loginUser = mongoTemplate.find(Query.query(Criteria.where("idName").is(idName).and("idPW").is(idPW)), User.class);
        System.out.println("loginResult = " + find_loginUser);
        if (find_loginUser != null) {

            JWTUtil jwtUtil = new JWTUtil();
		    String jwtoken = jwtUtil.genJWTToken(idName);
            System.out.println("jwtoken = " + jwtoken);

            return ResponseEntity.ok("用户"+idName +"登录成功！");
        } else {
            return ResponseEntity.badRequest().body("用户不存在");
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

## 设置SpringBoot端口
代码：resources/application.properties
```bash
spring.application.name=S11-mongotemplate-JWT-JSON-Login

# 设置SpringBoot后台程序端口
server.port = 8081
```

## 测试主文件

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
//		SpringApplication.run(S11MongotemplateJwtJsonLoginApplication.class, args);

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
---

## 测试
```bash
下午2:14:10: 正在执行 ':S11MongotemplateJwtJsonLoginApplication.main()'…

> Task :compileJava UP-TO-DATE
> Task :processResources
> Task :classes

> Task :S11MongotemplateJwtJsonLoginApplication.main()
file = F:\Tutorial\SpringTest\S11-mongotemplate-JWT-JSON-Login\S11-mongotemplate-JWT-JSON-Login\src\main\resources\jwt-authorization.json
jwtoken = eyJ0eXBlIjoiSldUIiwiYWxnbyI6IkhTNTEyIiwiYWxnIjoiSFM1MTIifQ.eyJ1c2VybmFtZSI6Int9IiwianRpIjoiNDA0MjgxZjYtY2VhMi00YTE1LTk3YmYtOTFlYTM4MmUyNTRmIiwiZXhwIjoxNzI0MDQ4MTExLCJpYXQiOjE3MjQwNDgwNTEsInN1YiI6IlNVQkpFQ1Rfemh1dGkiLCJpc3MiOiJJU1NfcWlhbmZhZmFuZyJ9.AW1o5S2t-WshKP59fx5ZscY-uFoUo7qt7cAcfJvvY0H0Y4EsrlkrCsNhCpxESIR-Id_l6EUXEu1w5HxuwCMAzw
jwtUtil.parseClaim(jwtoken) = header={type=JWT, algo=HS512, alg=HS512},payload={username={}, jti=404281f6-cea2-4a15-97bf-91ea382e254f, exp=1724048111, iat=1724048051, sub=SUBJECT_zhuti, iss=ISS_qianfafang},signature=AW1o5S2t-WshKP59fx5ZscY-uFoUo7qt7cAcfJvvY0H0Y4EsrlkrCsNhCpxESIR-Id_l6EUXEu1w5HxuwCMAzw
jwtUtil.parsePayload(jwtoken) = {username={}, jti=404281f6-cea2-4a15-97bf-91ea382e254f, exp=1724048111, iat=1724048051, sub=SUBJECT_zhuti, iss=ISS_qianfafang}

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.8/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 1s
3 actionable tasks: 2 executed, 1 up-to-date
下午2:14:12: 执行完成 ':S11MongotemplateJwtJsonLoginApplication.main()'。
```

测试成功！



