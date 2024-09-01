package com.jinwei.springboot_web_jpa_mongodb;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        //判断当前拦截到的是Controller的方法还是其他资源
        //当前拦截到的不是动态方法，直接放行
        if (handler instanceof HandlerMethod) {

            // 获取JWT配置-对象-GSON
            String jsonFile = "jwt-authorization.json";
            JsonUtil jsonUtil = new JsonUtil();
            String jsonStr;

            try {
                jsonStr = jsonUtil.readJSON(jsonFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Gson gson = new Gson();
            JWTConfig jwtConfig = gson.fromJson(jsonStr, JWTConfig.class);
            System.out.println("jwtConfig.secret = " + jwtConfig.secret);

            Map<String, String> ParameterMap = new HashMap<String, String>(); //map参数
            Map<String, String[]> map=request.getParameterMap(); //请求中的map数组
            for(String key :map.keySet()) { //遍历数组
                ParameterMap.put(key, map.get(key)[0]); //将值key，key对应的的value 赋值到map参数中
            }
            System.out.println("map = " + map);
//            System.out.println("ParameterMap = " + ParameterMap);
//            System.out.println("ParameterMap.get(\"idPW\") = " + ParameterMap.get("idPW"));
//            System.out.println("ParameterMap.get(\"idName\") = " + ParameterMap.get("idName"));
            System.out.println("ParameterMap.get(\"cacert\") = " + ParameterMap.get("cacert"));

            JSONObject cacertJsonObj = JSONObject.parseObject(ParameterMap.get("cacert"));  // 将json字符串转换成对象
            String cacert = cacertJsonObj.get("cacert").toString();
            System.out.println("cacert = " + cacert);

            //2、校验令牌
            try {
                JWTUtil jwtUtil = new JWTUtil();
                Jws<Claims> jwtoken = jwtUtil.parseJWTToken(cacert);
                System.out.println("jwtoken = " + jwtoken);

                Claims payload  = jwtUtil.parsePayload(cacert);
                String payloadStr  = payload.get("payload").toString();
                System.out.println("payloadStr = " + payloadStr);

                String[] strList = payloadStr.split("===");
                System.out.println("strList = " + strList);
                System.out.println("strList[0] = " + strList[0]);
                System.out.println("strList[1] = " + strList[1]);

                if (strList[1].equals(jwtConfig.secret)) {
                    result = true;
                    System.out.println("jwt-正确-不拦截");
                } else {
                    result = false;
                    System.out.println("jwt-不正确-拦截");
                }

            } catch (Exception e) {
                //4、不通过，响应401状态码
                response.setStatus(401);
                result = false;
            }
        }

        System.out.println("======================================================================================");
        return result;
    }
}