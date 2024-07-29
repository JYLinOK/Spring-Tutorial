package com.jinwei.S5_dsw_mongodb;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConnectorRepository extends MongoRepository<Connector, String> {

    // 定义通过 cacert 属性查找
    public Connector findByCacert(String cacert);
    // 定义通过 description 属性查找
    public List<Connector> findByDescription(String description);

}


