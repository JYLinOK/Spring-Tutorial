package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

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