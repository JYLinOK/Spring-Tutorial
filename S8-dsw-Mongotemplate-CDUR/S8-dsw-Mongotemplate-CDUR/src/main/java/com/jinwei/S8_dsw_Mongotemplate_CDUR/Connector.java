package com.jinwei.S8_dsw_Mongotemplate_CDUR;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.Date;

@Data  // ombok-自动构建-get、set、equals、hashCode、canEqual、toString等方法
@ToString  // lombok-自动构建-ToString
@Accessors(chain = true)  // lombok-链式编程
public class Connector {
    @MongoId
    private String id;  // 使用注解 MongoID可以更清晰地指定 _id主键
    private String description;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date loginDay;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date registerDay;
    // CA certification
    private CACert caCert;  // 链式编程
}