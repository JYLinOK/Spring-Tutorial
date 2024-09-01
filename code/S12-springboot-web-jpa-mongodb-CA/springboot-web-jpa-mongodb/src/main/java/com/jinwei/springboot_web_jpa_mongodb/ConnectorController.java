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
@RequestMapping("api/backConnector")
public class ConnectorController {

    @Autowired
    private MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(), "mgdbCA");
//    private MongoTemplate mongoTemplate;

    // Connector注册-Connector-通过参数-idName-idPW
    @RequestMapping(value="/registerConnector", method = RequestMethod.POST)
    public ResponseEntity<?> registerConnector(String idName, String idPW, String caClass, Long ldate1, Long ldate2, String addDoc) {
//        System.out.println("idName = " + idName);
//        System.out.println("idPW = " + idPW);
//        System.out.println("Connector.class = " + Connector.class);

        Query query = new Query();
        query.addCriteria(Criteria.where("idName").is(idName));
        List<Connector> find_registerConnector = mongoTemplate.find(query, Connector.class);
        System.out.println("find_registerConnector = " + find_registerConnector);

        List<Connector> findAllC = mongoTemplate.findAll(Connector.class);
        System.out.println("findAllC = " + findAllC);

        if (!find_registerConnector.isEmpty()) {
            return ResponseEntity.badRequest().body("Connector名已存在");
        } else {
            System.out.println("find_registerConnector.isEmpty() =============== ");
            System.out.println("ldate1 = " + ldate1);
            System.out.println("ldate2 = " + ldate2);

            Date date1 = new Date(ldate1);
            Date date2 = new Date(ldate2);

            Connector connector = new Connector(idName, idPW, caClass, date1, date2, addDoc);
            System.out.println("idName = " + idName);
            System.out.println("idPW = " + idPW);
            System.out.println("caClass = " + caClass);
            System.out.println("date1 = " + date1);
            System.out.println("date2 = " + date2);
            System.out.println("addDoc = " + addDoc);

            connector.setIdName(idName);
            connector.setIdPW(idPW);
            connector.setCaClass(caClass);
            connector.setDate1(date1);
            connector.setDate2(date2);
            connector.setAddDoc(addDoc);

            System.out.println("connector = " + connector);
            Connector insertConnector = mongoTemplate.insert(connector);
            return ResponseEntity.ok("Connector:"+idName +"注册成功！初始密码为:" + idPW);
        }
    }


    // 增加数据
    @RequestMapping(value="/insertConnector", method = RequestMethod.POST)
    public ResponseEntity<?> insertConnector(Connector connector) {
        Connector insert = mongoTemplate.insert(connector);
        return ResponseEntity.ok("添加成功,添加后的Connectorid为：" + insert.getId());
    }

    // 删除数据
    @RequestMapping(value="/deleteConnectorById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteConnectorById(@PathVariable("id") String id) {
        Connector findAndRemove = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), Connector.class);
        return ResponseEntity.ok("删除成功,删除的数据为：" + findAndRemove);
    }

    // 修改数据
    @RequestMapping(value = "updateConnectorByName", method = RequestMethod.POST)
    public ResponseEntity<?> updateConnectorByName(String name, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("age", age), Connector.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 分页查询
    @RequestMapping(value="/findConnectorPage", method = RequestMethod.GET)
    public Object findConnectorPage(Integer currentPageNo, Integer pageSize) {
        Query limit = new Query().skip((currentPageNo - 1) * pageSize).limit(pageSize);
        List<Connector> findConnector = mongoTemplate.find(limit, Connector.class);
        return findConnector;
    }

    // 查询所有的数据
    @RequestMapping(value="/findAllConnector", method = RequestMethod.POST)
    public ResponseEntity<?> findAllConnector() {
        System.out.println("findAllConnector");
//        List<Connector> findConnector = mongoTemplate.findAll(Connector.class);
        List<Connector> findConnector = mongoTemplate.findAll(Connector.class);
        System.out.println("findConnector = " + findConnector);
//        return ResponseEntity.ok("返回所有数据");
        return new ResponseEntity<>(findConnector, HttpStatus.OK);
    }

}