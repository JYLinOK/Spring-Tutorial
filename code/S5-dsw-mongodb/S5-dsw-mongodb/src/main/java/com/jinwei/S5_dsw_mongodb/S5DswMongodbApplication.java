package com.jinwei.S5_dsw_mongodb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class S5DswMongodbApplication implements CommandLineRunner {  // 主类拓展接口 CommandLineRunner

	@Autowired
	private ConnectorRepository  repository;  // 新建仓库类的对象

	public static void main(String[] args) {
		SpringApplication.run(S5DswMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {  // 具体定义运行函数
		// 首先清空仓库类
		repository.deleteAll();

		// 新建并保存Connector对象
		repository.save(new Connector("cacert ca0001 class1 0001", "This is a connector description class1 0001"));
		repository.save(new Connector("cacert ca0001 class2 0002", "This is a connector description class1 0002"));

		// 通过findAll():打印输出仓库所有的信息
		System.out.println("库所有的信息: with findAll():");
		System.out.println("-------------------------------");
		for (Connector connector : repository.findAll()) {
			System.out.println(connector);
		}
		System.out.println();

		// 通过自定义函数:打印对应的信息
		System.out.println("找出CA证书是:cacert ca0001 class1 0001的Connector:");
		System.out.println("--------------------------------");
		System.out.println(repository.findByCacert("cacert ca0001 class1 0001"));

		// 通过自定义函数:打印对应的信息
		System.out.println("找出描述是:This is a connector description class1 0002的Connector:");
		System.out.println("--------------------------------");
		for (Connector connector : repository.findByDescription("This is a connector description class1 0002")) {
			System.out.println(connector);
		}

	}

}