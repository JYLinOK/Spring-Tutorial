package com.jinwei.S8_mongotemplate;

public class JWTConfig
{
    String secret;
    String iss;
    String subject;

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("JWTConfig{");
        sb.append("secret='").append(secret).append('\'');
        sb.append(", iss=").append(iss);
        sb.append(", subject=").append(subject);
        sb.append('}');
        return sb.toString();
    }
}

