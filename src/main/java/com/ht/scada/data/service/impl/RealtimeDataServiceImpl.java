package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.RealtimeDataService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.*;

/**
 * @author 薄成文
 *
 */
@Service
public class RealtimeDataServiceImpl implements RealtimeDataService {

    @Inject
    private StringRedisTemplate redisTemplate;
	
	@PostConstruct
	public void init() throws Exception {
    }

    @Override
    public List<String> getEndTagMultiVarValue(String code, List<String> name) {
        return redisTemplate.<String, String>opsForHash().multiGet(code, name);
    }

    @Override
    public Map<String, String> getEndTagAllVarValue(String code) {
        return redisTemplate.<String, String>opsForHash().entries(code);
    }

    @Override
    public Map<String, String> getEndTagVarGroupInfo(String code, String group) {
        String varNameJoins = redisTemplate.<String, String>opsForHash().get(code + ":GROUP", group);
        if (varNameJoins != null) {
            String[] varNameArray = varNameJoins.split(",");
            if (varNameArray.length > 0) {
                List<String> values = redisTemplate.<String, String>opsForHash().multiGet(code, Arrays.asList(varNameArray));
                Map<String, String> map = new HashMap<>(varNameArray.length);
                for (int i = 0; i < varNameArray.length; i++) {
                    String varName = varNameArray[i];
                    String value = values.get(i);
                    if (value != null) {
                        map.put(varName, value);
                    }
                }
                return map;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getEndTagVarInfo(String code, String varName) {
        return redisTemplate.<String, String>opsForHash().get(code, varName);
    }

    @Override
    public Map<String, String> getEndTagVarInfo(List<String> code, String varName) {
        Map<String, String> map = new HashMap<>(code.size());
        for (String s : code) {
            String value = redisTemplate.<String, String>opsForHash().get(s, varName);
            if (value != null) {
                map.put(s, varName);
            }
        }
        return map;
    }

    @Override
    public Object[][] getEndTagVarLineData(String code, String varName) {
        // TODO 应用发布后将删除方法，请参考接口说明
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEndTagVarYcArray(String code, String varName) {

        String value = redisTemplate.<String, String>opsForHash().get(code + ":ARRAY", varName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
//        String[] data = value.split(",");
//        float [] ycArray = new float[data.length];
//        for (int i = 0; i < ycArray.length; i++) {
//            ycArray[i] = Float.parseFloat(data[i]);
//        }
//        return ycArray;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getEndTagVarYcArray(String code, List<String> varNames) {
        List<String> value = redisTemplate.<String, String>opsForHash().multiGet(code + ":ARRAY", varNames);
        if (value == null || value.isEmpty()) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < value.size(); i++) {
            String v = value.get(i);
            if (v != null) {
                map.put(varNames.get(i), v);
//                String[] data = v.split(",");
//                if (data.length > 0) {
//                    float [] ycArray = new float[data.length];
//                    for (int j = 0; j < ycArray.length; j++) {
//                        ycArray[j] = Float.parseFloat(data[i]);
//                    }
//                    map.put(varNames.get(i), ycArray);
//                }
            }
        }
        return map;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @PreDestroy
	public void destroy() {
	}
}
