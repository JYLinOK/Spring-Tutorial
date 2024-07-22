# DockerSpringBoot007-Docker-SpringBoot-Docker运行java容器

lin-jinwei, FaQianApp

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code](../code)

## 准备jar包
准备后编译成功并可以运行的jar包。

![alt text](image-112.png)

![alt text](image-113.png)

将其拷贝至一个新的工作目录，该目录用作docker容器的打包工作目录。

![alt text](image-114.png)

## 创建 Dockerfile文件

Dockerfile 是一个用来定义如何构建Docker镜像的格式文件，内容包含了一一步步构建镜像所需的所有指令以及特定说明。

在该目录下创建一个 Dockerfile文件。可以通过 Terminal创建：
![alt text](image-115.png)

使用 nano:
![alt text](image-116.png)
![alt text](image-119.png)

或者,使用 touch命令创建:
```bash
touch {文件名+后缀}
```
![alt text](image-117.png)
![alt text](image-118.png)

注意：Dockerfile文件没有后缀


### Dockerfile 的具体内容

```bash
# 调用环境：Java镜像
FROM openjdk:17

# 定义ARG指令变量 -> 用于传入jar包名称和生成文件的路径
ARG JAR_NAME
ARG APP_HOME=Cdir

# 创建docke容器目录
RUN mkdir -p ${APP_HOME}

# 将jar包复制到docke容器目录
COPY ${JAR_NAME} ${APP_HOME}/app.jar

# 设置docker容器的工作目录
WORKDIR ${APP_HOME}

# 暴露端口，如果没有设计Web或者端口访问，一般不需要暴露
EXPOSE 8080

# 通过CMD命令 -> 运行 Spring-Boot应用
CMD ["java", "-jar", "app.jar"]
```

#### 具体 Dockerfile文件表现--nano创建的具体流程

![alt text](image-123.png)

编写完成后，按 Ctrl+X：
![alt text](image-124.png)

按 Ctrl+Y 确定保存：
![alt text](image-126.png)

按回车：
![alt text](image-123.png)

![alt text](image-124.png)

### 命令构建容器alt text
 
下面命令的 JAR_FILE 就是 Dockerfile文件中通过 ARG关键字定义的变量，用于命令构建后的jar包。

格式：
```bash
docker build -t {docker打包的项目名称} --build-arg JAR_NAME={docker打包的jar包的新名称}.jar .
```

具体命令-举例：
```bash
sudo docker build -t docker-springboot-app --build-arg JAR_NAME=jinwei-web1-0.0.1-SNAPSHOT.jar .
```

注意：上述命令末尾的空格和点需要保留。

![alt text](image-139.png)

需要root用户密码执行：
![alt text](image-138.png)

编译过程：
![alt text](image-129.png)

编译成功：
![alt text](image-141.png)

注意：如果没有下载JDK，第一步应该是下载JDK(这里是JDK17).

上述步骤会构建生成一个名为：docker-springboot-app的镜像。

## 构建成功后查看 Docker镜像
![alt text](image-142.png)

![alt text](image-130.png)

## 目录保存
Docker 在ubuntu中的容器或者镜像的默认保存目录为：
```bash
var/lib/docker
```

## 运行容器镜像

格式：
```bash
docker run -d -p {端口：端口} 镜像名称
```
-d: 表示后台运行。

实测命令：
```bash
sudo docker run -d -p 8080:8080 docker-springboot-app
```

![alt text](image-136.png)

运行结果：

![alt text](image-140.png)

运行成功！

## 检测容器镜像：

浏览器访问：http://localhost:8080/

![alt text](image-143.png)

查看所有容器信息
```bash
docker ps
```
发现容器已经运行：
![alt text](image-144.png)

## 停止容器

```命令格式
sudo docker stop {容器名/容器ID}
```
一般来说，使用容器ID会更加准确。如果使用容器名无法关闭，则使用容器ID.

![alt text](image-145.png)


## 以命令终端控制的方式启动容器

格式：
```bash
docker run -it -p {端口：端口} 镜像名称
```
-d: 表示后台运行。

实测命令：
```bash
sudo docker run -it -p 8080:8080 docker-springboot-app
```

![alt text](image-146.png)

运行成功！
