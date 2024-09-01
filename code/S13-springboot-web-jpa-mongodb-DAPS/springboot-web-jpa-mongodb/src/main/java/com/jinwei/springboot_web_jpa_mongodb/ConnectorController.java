package com.jinwei.springboot_web_jpa_mongodb;

import com.google.gson.Gson;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jinwei.springboot_web_jpa_mongodb.JWTUtil.jwtConfig;


@RestController
@RequestMapping("api/backConnectorDAT")
public class ConnectorController {

    @Autowired
    private MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(), "mgdbDAPS");
//    private MongoTemplate mongoTemplate;

    // Connector-DAT-提交-通过参数-idName-DAT
    @RequestMapping(value="/registerConnectorDAPS", method = RequestMethod.POST)
    public ResponseEntity<?> registerDAPS(String idName, String DAT) {
        Connector connector = new Connector(idName, DAT);
        System.out.println("idName = " + idName);
        System.out.println("DAT = " + DAT);

        connector.setIdName(idName);
        connector.setDAT(DAT);

        System.out.println("connector = " + connector);
        Connector insertConnector = mongoTemplate.insert(connector);
        return ResponseEntity.ok("Connector:"+idName +"注册成功！添加DAT为:" + DAT);
    }


    // 增加数据
    @RequestMapping(value="/insertConnectorDAT", method = RequestMethod.POST)
    public ResponseEntity<?> insertConnector(Connector connector) {
        Connector insert = mongoTemplate.insert(connector);
        return ResponseEntity.ok("添加成功,添加后的Connectorid为：" + insert.getId());
    }

    // 删除数据
    @RequestMapping(value="/deleteConnectorDATById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteConnectorById(@PathVariable("id") String id) {
        Connector findAndRemove = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), Connector.class);
        return ResponseEntity.ok("删除成功,删除的数据为：" + findAndRemove);
    }

    // 修改数据
    @RequestMapping(value = "updateConnectorDATByName", method = RequestMethod.POST)
    public ResponseEntity<?> updateConnectorByName(String name, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("age", age), Connector.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 分页查询
    @RequestMapping(value="/findConnectorDATPage", method = RequestMethod.GET)
    public Object findConnectorPage(Integer currentPageNo, Integer pageSize) {
        Query limit = new Query().skip((currentPageNo - 1) * pageSize).limit(pageSize);
        List<Connector> findConnector = mongoTemplate.find(limit, Connector.class);
        return findConnector;
    }

    // 查询所有的数据
    @RequestMapping(value="/findAllConnectorDAT", method = RequestMethod.POST)
    public ResponseEntity<?> findAllConnector() {
        System.out.println("findAllConnectorDAT");
        List<Connector> findConnectorDAT = mongoTemplate.findAll(Connector.class);
        System.out.println("findConnectorDAT = " + findConnectorDAT);
//        return ResponseEntity.ok("返回所有数据");
        return new ResponseEntity<>(findConnectorDAT, HttpStatus.OK);
    }

}