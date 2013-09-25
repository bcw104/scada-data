package com.ht.db.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维数组映射的匹配器
 */
public class MappingMatcher implements Matcher {

    private Map<String, String> _map = null;

    public MappingMatcher(String[][] mapping) {
        if (mapping == null)
            throw new IllegalArgumentException();

        _map = new HashMap<String, String>();
        for (int i = 0; i < mapping.length; i++) {
            String columnName = mapping[i][0];
            if (columnName != null)
                _map.put(columnName.toUpperCase(), mapping[i][1]);
        }
    }

    public boolean match(String columnName, String propertyName) {
        if (columnName == null)
            return false;
        String pname = _map.get(columnName.toUpperCase());
        if (pname == null)
            return false;
        else {
            return pname.equals(propertyName);
        }
    }
}