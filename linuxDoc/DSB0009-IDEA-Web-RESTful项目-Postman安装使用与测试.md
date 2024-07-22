# DockerSpringBoot009-IDEA-Web-RESTful项目-Postman安装使用与测试
lin-jinwei, FaQianApp

注意，未授权不得擅自以盈利方式转载本博客任何文章。

---

Code: [../code](../code)

## 什么是 Postman?

![alt text](image-166.png)

What is Postman?
Postman is an API platform for building and using APIs. Postman simplifies each step of the API lifecycle and streamlines collaboration so you can create better APIs—faster.

简单地讲，Postman就是一款非常常用的一个APIs测试工具软件。
另外，用来测试Java-SpringBoot项目的RESTful请求也是非常的好用。

## Postman官网

![alt text](image-165.png)

## 下载安装Postman

![alt text](image-167.png)

![alt text](image-168.png)

![alt text](image-169.png)

下载完成后，将下载文件复制到安装目录进行解压安装，也可以直接安装：

![alt text](image-172.png)

![alt text](image-173.png)

命令：
```bash
tar -zxvf postman-linux-x64.tar.gz 
```

![alt text](image-174.png)

解压完成：

![alt text](image-175.png)

进入Postman目录：

![alt text](image-176.png)

点击图标启动运行：

![alt text](image-177.png)

如果没有安装UI桌面，则直接进入Postman的解压目录，运行下面命令即可运行Postman：

命令：
```bash
./Postman
```

![alt text](image-178.png)

完整功能需要登陆才能使用：

![alt text](image-179.png)



## 浏览器访问使用postman
不想本地安装postman的可以直接网上访问地址使用：

[https://www.postman.com/downloads/?utm_source=postman-home](https://www.postman.com/downloads/?utm_source=postman-home)

![alt text](image-171.png)

![alt text](image-170.png)

## 登录注册Postman

登陆后可以使用更加复杂的内容：

![alt text](image-180.png)

## 测试Springboot项目APIs:

首先打开一个已经在运行的SpringBoot项目：

![alt text](image-181.png)

测试命令：
```bash
http://localhost:8080/getFun1
```
结果：
![alt text](image-193.png)

测试命令：带请求参数的Get请求
```bash
http://localhost:8080/getFun1?name=jinwei
```
![alt text](image-195.png)

测试成功！

## 测试历史记录

点击Postman左边竖直栏的History,可以查看测试命令记录以及测试命令的使用记录。

![alt text](image-196.png)