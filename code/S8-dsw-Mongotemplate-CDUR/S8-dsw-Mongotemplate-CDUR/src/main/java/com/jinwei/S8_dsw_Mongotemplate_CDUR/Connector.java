package com.jinwei.S8_dsw_Mongotemplate_CDUR;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.Date;

@Data  // lombok-setter-getter-toString等自动构建
@ToString  // lombok-ToString自动构建
@Accessors(chain = true)  // 启动lombok的链式编程模式
public class Connector {

    @MongoId
    private String id; // 使用 @MongoID 注解可以更清晰的指定 _id主键
    private String description;
    private String type;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date registerDay;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date loginDay;
    private CAcert cacert;

}

