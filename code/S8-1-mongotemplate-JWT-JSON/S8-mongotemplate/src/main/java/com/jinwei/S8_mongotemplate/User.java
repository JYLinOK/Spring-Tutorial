package com.jinwei.S8_mongotemplate;

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



