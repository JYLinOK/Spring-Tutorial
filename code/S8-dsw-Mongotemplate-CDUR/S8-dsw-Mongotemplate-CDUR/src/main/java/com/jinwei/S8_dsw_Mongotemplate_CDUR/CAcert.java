package com.jinwei.S8_dsw_Mongotemplate_CDUR;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class CAcert {
    private String caContent;
    private String caType;
    private String caDescription;
    @JsonFormat( pattern ="yyyy-MM-dd", timezone ="GMT+8")
    private Date caRegisterDay;
}