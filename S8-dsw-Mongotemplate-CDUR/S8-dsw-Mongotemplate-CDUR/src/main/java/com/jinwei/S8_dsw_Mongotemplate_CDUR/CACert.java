package com.jinwei.S8_dsw_Mongotemplate_CDUR;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data  // lombok-set-get
@ToString  // lombok-tostring
@Accessors(chain = true)  // lombok-链式编程开启
public class CACert {
    private String content;
    private String description;

    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date registerDay;
}