package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: SQL公共提供类
 * @author: DHY
 * @created: 2021/02/08 13:39
 */
public class Provider {

    public static String selectByKey(Map<String, Object> para, String sql, String table) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(sql);
        if (para.get("key") != null && para.get("id") != null) {
            if (table.equals("orders")) {
                stringBuffer.append(" and ").append(table).append(".").append(para.get("key")).append(" = ")
                        .append(para.get("id"));
                if (para.get("status") != null && !para.get("key").equals("status"))
                    stringBuffer.append(" and status = ").append(para.get("status"));
            } else {
                stringBuffer.append(" where ").append(table).append(".").append(para.get("key")).append(" = ")
                        .append(para.get("id"));
            }
        }
        return stringBuffer.toString();
    }
}
