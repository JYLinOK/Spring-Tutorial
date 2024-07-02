# Gradle 构建Java和JVM项目

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
















1
