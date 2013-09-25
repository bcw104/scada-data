package com.ht.db.util;

public interface Matcher {

    /**
     * 判断字段名与属性名是否匹配
     * 
     * @param columnName 字段名
     * @param propertyName 属性名
     * @return 匹配结果
     */
    boolean match(String columnName, String propertyName);
}