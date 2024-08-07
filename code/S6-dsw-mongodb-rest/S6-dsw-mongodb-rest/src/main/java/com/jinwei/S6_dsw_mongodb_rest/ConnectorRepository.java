package com.jinwei.S6_dsw_mongodb_rest;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "cadata", path = "cadata")
public interface ConnectorRepository extends MongoRepository<Connector, String> {

    List<Connector> findByCacert(@Param("cacert") String cacert);

}