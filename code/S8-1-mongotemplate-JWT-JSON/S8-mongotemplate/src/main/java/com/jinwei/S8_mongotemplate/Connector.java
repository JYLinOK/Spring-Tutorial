package com.jinwei.S8_mongotemplate;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Connector {

    @MongoId
    private String id;
    private String name;
    private String description;
    private String cacert;
}



