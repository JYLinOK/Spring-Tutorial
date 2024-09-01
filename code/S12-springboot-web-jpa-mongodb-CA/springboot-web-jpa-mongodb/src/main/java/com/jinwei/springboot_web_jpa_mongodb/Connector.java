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
    private String idPW;
    private String caClass;
    private String addDoc;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date date1;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date date2;

    public Connector(String idName, String idPW, String caClass, Date date1, Date date2, String addDoc) {
        this.idName = idName;
        this.idPW = idPW;
        this.caClass = caClass;
        this.date1 = date1;
        this.date2 = date2;
        this.addDoc = addDoc;
    }
}