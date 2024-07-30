package com.jinwei.S7_dsw_mongodb_rest_2;

import com.jinwei.S7_dsw_mongodb_rest_2.ConnectorRepository;
import com.jinwei.S7_dsw_mongodb_rest_2.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConnectorService {
    private final ConnectorRepository connectorRepository;

    @Autowired
    public ConnectorService(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    public List<Connector> findByCacert(String cacert) {
        return connectorRepository.findByCacert(cacert);
    }

    public List<Connector> findByDescription(String description) {
        return connectorRepository.findByDescription(description);
    }

}



