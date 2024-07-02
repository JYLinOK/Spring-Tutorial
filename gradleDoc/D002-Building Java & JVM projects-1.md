# Gradle 构建Java和JVM项目-配置

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

## Gradle 构建Java-JVM项目的两种格式

### Gradle Kotlin: build.gradle.kts

```gradle
plugins {
    `java-library`
}

java {
    toolchain {
        // 定义JDK=17
        languageVersion = JavaLanguageVersion.of(17)
    }
}

version = "1.2.1"
```

### Gradle Groovy: buid.gradle

```gradle
plugins {
    // 多了个id
    id 'java-library'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

version = '1.2.1'
```

## Gradle 通过源集合声明源代码集

1. Java 项目中最重要的目录就是源代码目录。
2. Gradle可以通过声明源集合的方式声明源代码目录集合。
3. 不同类型的源代码可以通过Gradle声明来分类。
4. 注意：Gradle配置源代码的不同类型代码不需要位于同一目录下。
5. 通过声明源代码集，Gradle定义了源代码的依赖项目录以及编译生成后的目录。
6. 重要功能就是定义目录。

## Gradle管理依赖项

### Gradle Kotlin: build.gradle.kts

```gradle
repositories {
    // 引入Maven中心仓库
    mavenCentral()
}

dependencies {
    // 括号定义
    implementation("org.hibernate:hibernate-core:3.6.7.Final")
}
```

### Gradle Groovy: buid.gradle

```gradle
repositories {
    // 引入Maven中心仓库
    mavenCentral()
}

dependencies {
    // 直接单引号
    implementation 'org.hibernate:hibernate-core:3.6.7.Final'
}
```

### 主要配置关键词


* *Repository* (ex: `mavenCentral()`) —  在Repository其中查找您声明为依赖的模块
* *Configuration* (ex: `implementation`) — 命名的依赖项集合，为特定的目标(如编译或运行模块)分组在一起——一种更灵活的Maven作用域形式
* *Module coordinate* (ex: `org.hibernate:hibernate-core-3.6.7.Final`) — 依赖项的ID，常见格式：\<group\>:\<module\>:\<version\>; 或者：\<group\>:\<artifactld\>:\<version\>出现在Maven中。

### 其他配置关键词

* `compileOnly` — 用于编译生产代码所必需的依赖项，但不应该成为运行时类路径的一部分
* `implementation` (supersedes `compile`) — (取代 compile ) 用于编译和运行时
* `runtimeOnly` (supersedes `runtime`) — (取代 runtime ) 仅在运行时使用，不用于编译
* `testCompileOnly` — 与 compileOnly 相同，只是用于测试
* `testImplementation` —测试实现；等效 implementation
* `testRuntimeOnly` — 测试运行时；等效 runtimeOnly














1
