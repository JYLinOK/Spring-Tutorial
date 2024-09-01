package com.jinwei.springboot_web_jpa_mongodb;

public class JWTConfig
{
    String secret;
    String iss;
    String subject;
    String user0;
    String pw0;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("JWTConfig{");
        sb.append("secret='").append(secret).append('\'');
        sb.append(", iss=").append(iss);
        sb.append(", subject=").append(subject);
        sb.append(", user0=").append(user0);
        sb.append(", pw0=").append(pw0);
        sb.append('}');
        return sb.toString();
    }
}