# Gradle 构建Java和JVM项目-代码与编译

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

## Gradle Java-JVM项目的源代码目录

位于：src/main/java。

## Gradle Java-JVM项目的测试代码目录

位于：src/test/java。

## 声明生产依赖

在 `compileOnly` 或 `implementation` 配置中声明生产编辑设计使用的编译依赖。

## 声明测试依赖

在 `testCompileOnly` 或 `testImplementation` 配置中声明测试使用的编译依赖

## 运行生产或者编译的任务

对于生产设计代码运行 `compileJava` 任务，对于测试设计代码运行 `compileTestJava` 任务。

## Gradle 自定义文件目录位置

### 重新自定义源目录位置->覆盖原来的源目录设置

#### Kotlin: build.gradle.kts

```gradle
// 通过sourceSets设置源代码目录
sourceSets {
    // main设置生成代码主目录
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
    // test设置测试代码主目录
    test {
        java {
            setSrcDirs(listOf("test"))
        }
    }
}
```

#### Groovy: build.gradle

```gradle
// 通过sourceSets设置源代码目录
sourceSets {
    // main设置生成代码主目录
    main {
         java {
            srcDirs = ['src']
         }
    }
    // test设置测试代码主目录
    test {
        java {
            srcDirs = ['test']
        }
    }
}
```

### 添加额外的自定义源目录位置->不覆盖原来的源目录设置

#### Kotlin: build.gradle.kts

```gradle
// 通过sourceSets设置源代码目录
sourceSets {
    main {
        java {
            // 额外源目录位置
            srcDir("thirdParty/src/main/java")
        }
    }
}
```

#### Groovy: build.gradle

```gradle
// 通过sourceSets设置源代码目录
sourceSets {
    main {
        java {
            // 额外源目录位置
            srcDir 'thirdParty/src/main/java'
        }
    }
}
```

## 为编译器使用单独的JVM进程

设置单独的JVM进程的主要目的是，为了防止原有编译失败导致构建失败。

#### Kotlin: build.gradle.kts

```gradle
// 通过compileJava设置Java编译
compileJava {
    options.incremental = true
    options.fork = true
    options.failOnError = false
}
```

#### Groovy: build.gradle

```gradle
// 通过compileJava设置Java编译
compileJava {
    options.incremental = true
    options.fork = true
    options.failOnError = false
}
```

## 为编译器指定使用特定的Java版本

1. 使用Java工具链的方式是针指定编译语言版本的首选方法。
2. 从Java10开始可以使用[`release`](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.compile.CompileOptions.html#org.gradle.api.tasks.compile.CompileOptions:release) property属性进行设置。
3. 旧版本的项目会使用：`sourceCompatibility` and `targetCompatibility` 属性进行设置。

### 1 使用工具链：在java中定义toolchain

#### Kotlin: build.gradle.kts

```gradle
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

#### Groovy: build.gradle

```gradle
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

### 2 调用发布标志：在java中定调用release标志

#### Kotlin: build.gradle.kts

```gradle
tasks.compileJava {
    options.release = 7
}
```

#### Groovy: build.gradle

```gradle
compileJava {
    options.release = 7
}
```
