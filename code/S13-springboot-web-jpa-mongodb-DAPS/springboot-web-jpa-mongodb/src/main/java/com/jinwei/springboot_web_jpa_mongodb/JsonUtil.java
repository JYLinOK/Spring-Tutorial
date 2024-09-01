package com.jinwei.springboot_web_jpa_mongodb;

import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;

public class JsonUtil {

    public String readJSON(String jsonFile) throws IOException {
//        File file0 = new File("");
//        String filePath = file0.getCanonicalPath();
//        System.out.println("filePath = " + filePath);

//        String oriPath = this.getClass().getResource("").getPath();
//        System.out.println("oriPath = " + oriPath);

        File file01 = new File("");
        String filePath01 = file01.getAbsolutePath();
//        System.out.println("filePath01 = " + filePath01);

        String dirPath = filePath01 + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + jsonFile;
//        System.out.println("dirPath = " + dirPath);

        File file = new File(dirPath);
        System.out.println("file = " + file);
        FileReader fileReader = new FileReader(file);

        Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
//        System.out.println("jsonStr = " + jsonStr);
//        System.out.println("JSON.parseObject(jsonStr) = " + JSON.parseObject(jsonStr));
        return jsonStr;
    }
}