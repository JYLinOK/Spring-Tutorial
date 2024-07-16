# Gradle 构建Java和JVM项目-JVM测试

lin-jinwei

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

## 常用框架

测试JVM的两种常用框架有：JUnit和TestNG

## 基础的Test任务设计

### 一般只包含两条测试信息

1. 说明编译测试类的位置目录：property: [Test.getTestClassesDirs()](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html#org.gradle.api.tasks.testing.Test:testClassesDirs)
2. 说明编译测试类的执行路径：property: [Test.getClasspath()](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html#org.gradle.api.tasks.testing.Test:classpath)

### 使用JVM语言插件可以自动获得的内容

1. 单元测试的专用 `test` 源代码集
2. 单元测试的类型为 `Test` 的 `test` 任务

### `test` 源代码集自动的依赖配置

最重要的两项：`testImplementation` 和 `testRuntimeOnly` -> 在dependencies中配置

#### Kotlin: build.gradle.kts

```gradle
dependencies {
    // 配置测试依赖项
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// 定义测试任务
tasks.named<Test>("test") {
    // 定义测试使用JUnit插件
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
```

#### Groovy: build.gradle

```gradle
dependencies {
    // 配置测试依赖项
    testImplementation 'org.junit.jupiter:junit-jupiter:5.7.1'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test', Test) {
    // 使用Junit插件测试
    useJUnitPlatform()

    maxHeapSize = '1G'

    testLogging {
        events "passed"
    }
}
```

### 测试任务重的常见属性

tasks.named<Test>("test") 或者 tasks.named('test', Test) 中的属性

1. `maxParallelForks` -> 默认值为1：大于1则运行并行测试->速度更快。
2. `forkEvery` -> 默认是为0：一个测试进程被丢弃和创建一个新进程之前应该运行的测试类的最大数量。
3. `ignoreFailures` -> 默认值为false：如果是 `true` ， Gradle将在测试完成后继续该项目的构建。默认情况下， `Test` 任务将总是执行它检测到的每个测试，而不考虑这个设置，即不考虑测试完后进行项目构建。
4. `failFast` -> 默认值为false：对于具有多个测试任务的测试，如果为true，则在一个测试失败后立即完成测试，输出失败。如果为false则在完成所有测试后才输出失败，可能会导致长时间的测试。
5. `testLogging` -> 默认值：not set 不设置：或者通过events "passed"`设置测试完成的输出。
6. `dryRun` -> 默认值为flase: 如果为true，则通过模拟的方式执行测试，如果为false，则进行实际测试。

更多测试实例：[Test - Gradle DSL Version 8.8](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html)































1
