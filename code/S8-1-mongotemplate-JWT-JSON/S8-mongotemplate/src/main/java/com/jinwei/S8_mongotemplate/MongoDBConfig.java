package com.jinwei.S8_mongotemplate;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@PropertySource(value = { "mongodb.properties" })
@Configuration
class MongoDBConfig {

    @Value("${mongodb.url}")
    private String mongoUrl;

    @Value("${mongodb.database}")
    private String mongoDateBase;

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUrl), mongoDateBase));
    }

}
