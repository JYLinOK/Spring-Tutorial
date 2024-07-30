package com.jinwei.S7_dsw_mongodb_rest_2;

import com.jinwei.S7_dsw_mongodb_rest_2.Connector;
import com.jinwei.S7_dsw_mongodb_rest_2.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConnectorController {

    @Autowired
    private ConnectorService bookService;

    @GetMapping("/list")
    public HashMap<String,Object> getBookList() {
        String cacert = "cacert-001";
        HashMap<String, Object> connector=new HashMap<>();
        List<Connector> connectorList=bookService.findByCacert(cacert);
        connector.put("connectors", connectorList);
        return connector;
    }
}

