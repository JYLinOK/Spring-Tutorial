# DSW009-ProjectLombok-Lombok-下载安装配置-

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

## Lombok官网

### https://projectlombok.org/

Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

Project Lombok是一个java库，可以自动插入到编辑器和构建工具中，为java增添趣味。永远不要再写getter或equals方法了，只要有一个注释，你的类就有一个功能齐全的构建器、自动化日志变量等等。


![alt text](image-49.png)

## 最新版下载展示

### https://projectlombok.org/download

![alt text](image-45.png)

---

## 首先 IDEA 搜索安装 Lombok插件

![alt text](image-52.png)

![alt text](image-53.png)

## 启动IDEA的注解编译器

![alt text](image-54.png)

## 引入 gradle依赖

### lombok官方引入格式-> gradle：https://projectlombok.org/setup/gradle

代码：build.gradle

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

## 引入后重新进行主文件编译会出现自动下载 lombok依赖
![alt text](image-55.png)

注意：引入后需要重启，然后可以看到注解 @Data可用

![alt text](image-56.png)

---

## 创建实体类：Connector

代码：com/jinwei/S7_dsw_mongodb_rest_2/Connector.java

```java
package com.jinwei.S7_dsw_mongodb_rest_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "connector")
@Data  // lombok-setter-getter-toString等
@NoArgsConstructor   // lombok-无参构造
@AllArgsConstructor  //lombok-全参构造
public class Connector {
    @Id
    private String id;  // 自动赋值属性

    // 自定义属性
    private String cacert;
    private String description;
}
```



















































