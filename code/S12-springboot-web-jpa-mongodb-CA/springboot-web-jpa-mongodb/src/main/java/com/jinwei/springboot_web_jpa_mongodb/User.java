package com.jinwei.springboot_web_jpa_mongodb;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
//@AllArgsConstructor
public class User {
    @MongoId
    private String id;

    public User(String idName, String idPW) {
    }
}