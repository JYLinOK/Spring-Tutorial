package com.jinwei.springboot_web_jpa_mongodb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
//@AllArgsConstructor
public class Connector {
    @MongoId
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 采用数据库ID自增长的方式实现增加被注解的主键字段-ID字段
    private String id;

    private String idName;
    private String DAT;


    public Connector(String idName, String DAT) {
        this.idName = idName;
        this.DAT = DAT;
    }
}