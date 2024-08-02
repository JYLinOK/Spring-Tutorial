package com.jinwei.S8_mongotemplate;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;
import java.util.Map;


@RestController
public class ConnectorController {

    @Autowired
    private MongoTemplate mongoTemplate;


    // 增加数据-connector集合
    @RequestMapping(value="/insertConnector", method = RequestMethod.POST)
    public ResponseEntity<?> insertConnector(Connector person) {
        Connector insert = mongoTemplate.insert(person);
        return ResponseEntity.ok("添加成功,添加后的用户id为：" + insert.getId());
    }

    // 删除数据-通过参数-id-connector集合
    @RequestMapping(value="/deleteConnectorById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteConnectorById(@PathVariable("id") String id) {
        Connector findAndRemove = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), Connector.class);
        return ResponseEntity.ok("删除成功,删除的数据为：" + findAndRemove);
    }

    // 修改数据-通过参数-name-connector集合
    @RequestMapping(value = "updateConnectorByName", method = RequestMethod.POST)
    public ResponseEntity<?> updateConnectorByName(String name, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("age", age), Connector.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 修改数据-通过参数-id-connector集合
    @RequestMapping(value = "updateConnectorById", method = RequestMethod.POST)
    public ResponseEntity<?> updateConnectorById(String id, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)),
                Update.update("age", age), Connector.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 分页查询-connector集合
    @RequestMapping(value="/findConnectorPage", method = RequestMethod.GET)
    public Object findConnectorPage(Integer currentPageNo, Integer pageSize) {
        Query limit = new Query().skip((currentPageNo - 1) * pageSize).limit(pageSize);
        List<Connector> findConnector = mongoTemplate.find(limit, Connector.class);
        return findConnector;
    }

    // 查询所有-connector集合
    @RequestMapping(value="/findAllConnector", method = RequestMethod.GET)
    public Object findAllConnector() {
        List<Connector> findConnector = mongoTemplate.findAll(Connector.class);
        return findConnector;
    }



}



