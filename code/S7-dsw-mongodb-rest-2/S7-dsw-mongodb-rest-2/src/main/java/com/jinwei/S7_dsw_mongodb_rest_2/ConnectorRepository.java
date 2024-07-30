package com.jinwei.S7_dsw_mongodb_rest_2;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ConnectorRepository extends MongoRepository<Connector, String> {
    // 自定义查询方法
    List<Connector> findByCacert(String cacert);
    List<Connector> findByDescription(String description);
}