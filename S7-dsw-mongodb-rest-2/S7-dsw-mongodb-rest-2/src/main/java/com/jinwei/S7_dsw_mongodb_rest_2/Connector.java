package com.jinwei.S7_dsw_mongodb_rest_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "connector")
@Data  // lombok-setter-getter-toString等
@NoArgsConstructor   // lombok-无参构造
@AllArgsConstructor  //lombok-全参构造
public class Connector {
    @Id
    private String id;  // 自动赋值属性

    // 自定义属性
    private String cacert;
    private String description;

}

