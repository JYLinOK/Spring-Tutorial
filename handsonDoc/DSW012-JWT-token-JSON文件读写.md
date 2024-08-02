# DSW011-JWT-token

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

## 主程序代码

```java
package com.jinwei.S8_mongotemplate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import java.io.IOException;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class S8MongotemplateApplication {
	public static void main(String[] args) throws IOException {
//		SpringApplication.run(S8MongotemplateApplication.class, args);

//		User user = new User("user2", "123");
//		String subject = new Gson().toJson(user);


		String jsonFile = "jwt-authorization.json";
		JsonUtil jsonUtil = new JsonUtil();
		String jsonStr = jsonUtil.readJSON(jsonFile);
		System.out.println("jsonStr = " + jsonStr);

	}

}
```


## JSON工具类-JSONUtil
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
        System.out.println("filePath01 = " + filePath01);

        String dirPath = filePath01 + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + jsonFile;
        System.out.println("dirPath = " + dirPath);

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

































