package com.jinwei.S7_dsw_mongodb_rest_2;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "connector")
@Data
public class Connector {
    @Id
    private String id;  // 自动赋值属性

    // 自定义属性
    private String cacert;
    private String description;
}