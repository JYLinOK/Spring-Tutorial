package com.jinwei.S5_dsw_mongodb;
import org.springframework.data.annotation.Id;

public class Connector {

    // 定义实体类对象的ID
    @Id
    public String id;
    // 定义实体类的其他具体属性
    public String cacert;  // CA 证书字符串
    public String description;  // Connector属性描述

    public Connector() {}

    public Connector(String cacert, String description) {
        // 属性本地化
        this.cacert = cacert;
        this.description = description;
    }

    // 重写toString函数，定义标准的属性获取
    @Override
    public String toString() {
        return String.format(
                "Connector[id=%s, cacert='%s', description='%s']",
                id, cacert, description);
    }

}