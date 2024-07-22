# DockerSpringBoot010-IDEA-Web-RESTful项目-打jar包并用postman测试
lin-jinwei, FaQianApp

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code](../code)


## 准备jar包
准备后编译成功并可以运行的jar包。

![alt text](image-198.png)

![alt text](image-199.png)

将其拷贝至一个新的工作目录，该目录用作docker容器的打包工作目录。

![alt text](image-200.png)

## 创建 Dockerfile文件

使用 touch命令创建 Dockerockerfile文件：
```bash
touch {文件名+后缀}
```
![alt text](image-201.png)

![alt text](image-202.png)

注意：Dockerfile文件没有后缀


### Dockerfile 的具体内容

使用nano或者IDEA等编辑Dockerfile文件的具体内容，按照下面格式：

```bash
# 调用环境：Java镜像
FROM openjdk:17

# 定义ARG指令变量 -> 用于传入jar包名称和生成文件的路径
ARG JAR_NAME
ARG APP_HOME=./Cdir

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

nano:

![alt text](image-203.png)

![alt text](image-204.png)

![alt text](image-205.png)


### 使用命令构建Docker容器
 
下面命令的 JAR_FILE 就是 Dockerfile文件中通过 ARG关键字定义的变量，用于命令构建后的jar包。

本次装载的jar包为：
```bash
jinwei-restful-0.0.1-SNAPSHOT.jar
```

首先查看当前本地构建的Docker镜像：
```bash
sudo docker images
```

![alt text](image-206.png)

发现之前教程的 docker-springboot-app 镜像还在。 

格式：
```bash
docker build -t {docker打包的项目名称} --build-arg JAR_NAME={docker打包的jar包的新名称}.jar .
```

具体命令-举例：
```bash
sudo docker build -t docker-springboot-restful1-app --build-arg JAR_NAME=jinwei-restful-0.0.1-SNAPSHOT.jar .
```

![alt text](image-207.png)

构建Docker镜像成功：

![alt text](image-208.png)

上述步骤会构建生成一个名为：docker-springboot-restful1-app的镜像。

## 构建成功后查看 Docker镜像

再次查看当前本地构建的Docker镜像：
```bash
sudo docker images
```

![alt text](image-209.png)

具体信息：
```bash
(base) jy@jy:~/CODE/DockerBC/RESTful1$ sudo docker images
REPOSITORY                       TAG       IMAGE ID       CREATED              SIZE
docker-springboot-restful1-app   latest    7af05489783d   About a minute ago   491MB
docker-springboot-app            latest    711128253d24   3 days ago           491MB
<none>                           <none>    b1af2ce19f09   3 days ago           471MB
openjdk                          17        5f94f53bbced   2 years ago          471MB
ubuntu                           latest    ba6acccedd29   2 years ago          72.8MB
hello-world                      latest    feb5d9fea6a5   2 years ago          13.3kB
```

发现新构建的docker-springboot-restful1-app的镜像被成功收录。


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
sudo docker run -d -p 8080:8080 docker-springboot-restful1-app
```

![alt text](image-210.png)

运行结果：

![alt text](image-211.png)

运行成功！

## 使用Postman测试容器镜像：

测试命令：
```bash
http://localhost:8080/getFun1
```
结果：

![alt text](image-212.png)

测试命令：带请求参数的Get请求
```bash
http://localhost:8080/getFun1?name=jinwei
```
结果：
![alt text](image-213.png)

测试成功！

## Docker查看正在运行的容器

使用命令：
```bash
sudo docker ps
```

![alt text](image-214.png)

具体信息：
```bash
(base) jy@jy:~/CODE/DockerBC/RESTful1$ sudo docker ps
CONTAINER ID   IMAGE                            COMMAND               CREATED         STATUS         PORTS                                       NAMES
f5d3d3513d38   docker-springboot-restful1-app   "java -jar app.jar"   9 minutes ago   Up 9 minutes   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   mystifying_dewdney
```

## 停止容器

```命令格式
sudo docker stop {容器名/容器ID}
```
![alt text](image-215.png)

一般来说，使用容器ID会更加准确。如果使用容器名无法关闭，则使用容器ID.


## 以命令终端控制的方式启动容器

首先使用命令查看当前运行的容器：
```bash
sudo docker ps
```
![alt text](image-216.png)

发现当前没有运行任何容器。

格式：
```bash
docker run -it -p {端口：端口} 镜像名称
```
-d: 表示后台运行。

实测命令：
```bash
sudo docker run -it -p 8080:8080 docker-springboot-restful1-app
```
![alt text](image-217.png)

![alt text](image-218.png)

运行成功！

## 使用Postman测试终端交互式容器镜像：

测试命令：
```bash
http://localhost:8080/getFun1
```
结果：

![alt text](image-219.png)

测试命令：带请求参数的Get请求
```bash
http://localhost:8080/getFun1?name=jinwei
```
结果：
![alt text](image-220.png)

测试成功！
