package com.jinwei.S6_dsw_mongodb_rest;
import org.springframework.data.annotation.Id;


public class Connector {

    @Id private String id;

    // 定义实例类需要 set-get 的各种属性
    private String cacert;
    private String description;
    private String sellDataID;
    private String buyDataID;

    // get and set: cacert
    public String getCacert() {
        return cacert;
    }

    public void setCacert(String cacert) {
        this.cacert = cacert;
    }

    // get and set: description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // get and set: sellDataID
    public String getSellDataID() {
        return sellDataID;
    }

    public void setSellDataID(String sellDataID) {
        this.sellDataID = sellDataID;
    }

    // get and set: buyDataID
    public String getDuyDataID() {
        return buyDataID;
    }

    public void setDuyDataID(String buyDataID) {
        this.buyDataID = buyDataID;
    }
}


