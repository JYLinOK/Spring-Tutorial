# DSW010-SpringBoot-MongoDB-REST-MongoRepository

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code/S6-dsw-mongodb-rest](../code/S6-dsw-mongodb-rest/)


## SpringBoot Initializr 创建项目
![alt text](image-57.png)


## 引入 gradle依赖

### lombok官方引入格式-> gradle：https://projectlombok.org/setup/gradle


```gradle
repositories {
	mavenCentral()
}

dependencies {
	compileOnly 'org.projectlombok:lombok:1.18.34'
	annotationProcessor 'org.projectlombok:lombok:1.18.34'
	
	testCompileOnly 'org.projectlombok:lombok:1.18.34'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'
}
```

举例：
代码：build.gradle

```gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
	
 	compileOnly 'org.projectlombok:lombok:1.18.34'
	annotationProcessor 'org.projectlombok:lombok:1.18.34'
	
	testCompileOnly 'org.projectlombok:lombok:1.18.34'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'
}
```

## 引入后重新进行主文件编译会出现自动下载 lombok依赖
![alt text](image-55.png)

注意：引入后需要重启，然后可以看到注解 @Data可用

![alt text](image-56.png)

完成的 gradle文件：

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
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

	compileOnly 'org.projectlombok:lombok:1.18.34'
	annotationProcessor 'org.projectlombok:lombok:1.18.34'

	testCompileOnly 'org.projectlombok:lombok:1.18.34'
	testAnnotationProcessor 'org.projectlombok:lombok:1.18.34'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

---

## 创建实体类：Connector

代码：com/jinwei/S7_dsw_mongodb_rest_2/Connector.java

```java
package com.jinwei.S7_dsw_mongodb_rest_2;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "connector")
@Data
public class Connector {
    @Id
    private String id;  // 自动赋值属性

    // 自定义属性
    private String cacert;
    private String description;
}
```

## 创建基于实体类的仓库类：ConnectorRepository

代码：com/jinwei/S7_dsw_mongodb_rest_2/ConnectorRepository.java

```java
package com.jinwei.S7_dsw_mongodb_rest_2;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ConnectorRepository extends MongoRepository<Connector, String> {
    // 自定义查询方法
    List<Connector> findByID(String id);
    List<Connector> findByCacert(String cacert);
    List<Connector> findByDescription(String description);
}
```

## 设置 IDEA-MongoDB数据库

在：resources/application.properties：中配置

格式：
```bash
# 直接通过IP:端口号+数据库名称设置
spring.data.mongodb.uri=mongodb://{IP:端口号}/{数据库名称}

# 账号+密码设置
spring.data.mongodb.uri=mongodb://用户:{密码}@{IP:端口号}/{数据库名称}

# 多节点{IP:端口号+数据库名称}设置
spring.data.mongodb.uri=mongodb://user:pwd@{IP1:端口号1},{IP2:端口号2}/{数据库名称}
```

可以通过：spring.data.mongodb.uri 属性来进行URL和其他设置的配置，例如配置数据库副本集
```bash
spring.data.mongodb.uri=mongodb://user:secret@mongoserver1.example.com:27017,mongoserver2.example.com:23456/test
```

标准格式：
```bash
spring.data.mongodb.host=mongoserver1.example.com
spring.data.mongodb.port=27017
spring.data.mongodb.additional-hosts[0]=mongoserver2.example.com:23456
spring.data.mongodb.database=test
spring.data.mongodb.username=user
spring.data.mongodb.password=secret
```

SSL通信自动化配置：
```bash
spring.data.mongodb.uri=mongodb://user:secret@mongoserver1.example.com:27017,mongoserver2.example.com:23456/test
spring.data.mongodb.ssl.enabled=true
```

SSL信任资料通信配置
```bash
spring.data.mongodb.uri=mongodb://user:secret@mongoserver1.example.com:27017,mongoserver2.example.com:23456/test
spring.data.mongodb.ssl.bundle=example
```

## application.properties 文件配置

代码：resources/application.properties

```bash
spring.application.name=S7-dsw-mongodb-rest-2

spring.data.mongodb.uri=mongodb://127.0.0.1:27017/c_db
```

## 主类程序

```java
package com.jinwei.S7_dsw_mongodb_rest_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S7DswMongodbRest2Application {

	public static void main(String[] args) {
		SpringApplication.run(S7DswMongodbRest2Application.class, args);
	}
}
```

## 创建基于实体类的服务类：ConnectorService



## 创建基于实体类的控制类：ConnectorController 

代码：com/jinwei/S7_dsw_mongodb_rest_2/ConnectorController.java

```java

```

## 运行主程序

![alt text](image-59.png)

## Postman测试

code:
```bash
http://localhost:8080/api/list
```

![alt text](image-58.png)









































