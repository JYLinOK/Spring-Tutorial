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

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 用户登录-User-通过参数-idName-idPW
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(String idName, String idPW) throws IOException {
        List<User> find_loginUser = mongoTemplate.find(Query.query(Criteria.where("idName").is(idName).and("idPW").is(idPW)), User.class);
        System.out.println("loginResult = " + find_loginUser);
        if (find_loginUser != null) {

            JWTUtil jwtUtil = new JWTUtil();
		    String jwtoken = jwtUtil.genJWTToken(idName);
            System.out.println("jwtoken = " + jwtoken);

            return ResponseEntity.ok("用户"+idName +"登录成功！");
        } else {
            return ResponseEntity.badRequest().body("用户不存在");
        }
    }

    // 用户注册-User-通过参数-idName-idPW
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(String idName, String idPW) {
        List<User> find_registerUser = mongoTemplate.find(Query.query(Criteria.where("idName")), User.class);
        System.out.println("find_registerUser = " + find_registerUser);
        if (find_registerUser != null) {
            return ResponseEntity.badRequest().body("用户名已存在");
        } else {
            User user = new User(idName, idPW);
            System.out.println("idName = " + idName);
            System.out.println("idPW = " + idPW);
            User insertUser = mongoTemplate.insert(user);
            return ResponseEntity.ok("用户:"+idName +"注册成功！登录密码为:" + idPW);
        }
    }


    // 增加数据
    @RequestMapping(value="/insertUser", method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(User person) {
        User insert = mongoTemplate.insert(person);
        return ResponseEntity.ok("添加成功,添加后的用户id为：" + insert.getId());
    }

    // 删除数据
    @RequestMapping(value="/deleteUserById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        User findAndRemove = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), User.class);
        return ResponseEntity.ok("删除成功,删除的数据为：" + findAndRemove);
    }

    // 修改数据
    @RequestMapping(value = "updateUserByName", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserByName(String name, Integer age) {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("age", age), User.class);
        long modifiedCount = updateResult.getModifiedCount();
        /* mongoTemplate.updateMulti(query, update, entityClass) */
        return ResponseEntity.ok("修改成功,修改数量：" + modifiedCount);
    }

    // 分页查询
    @RequestMapping(value="/findUserPage", method = RequestMethod.GET)
    public Object findUserPage(Integer currentPageNo, Integer pageSize) {
        Query limit = new Query().skip((currentPageNo - 1) * pageSize).limit(pageSize);
        List<User> findUser = mongoTemplate.find(limit, User.class);
        return findUser;
    }

    // 查询所有的数据
    @RequestMapping(value="/findAllUser", method = RequestMethod.GET)
    public Object findAllUser() {
        List<User> findUser = mongoTemplate.findAll(User.class);
        return findUser;
    }


}



