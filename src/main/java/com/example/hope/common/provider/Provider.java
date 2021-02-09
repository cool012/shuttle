package com.example.hope.common.provider;

import java.util.Map;

/**
 * @description: SQL公共提供类
 * @author: DHY
 * @created: 2021/02/08 13:39
 */
public class Provider {

    public static String selectByKey(Map<String, Object> para, String sql, String table) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sql);
        if (para.get("key") != null && para.get("id") != null) {
            if (para.get("key").equals("search"))
                stringBuffer.append(" where " + table + ".name like %" + para.get("keyword") + "%");
            else stringBuffer.append(" where " + table + "." + para.get("key") + " = " + para.get("id"));
        }
        return stringBuffer.toString();
    }
}
