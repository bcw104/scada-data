package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.RealtimeDataService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> getMultiValue(List<String> key) {
        return  redisTemplate.opsForValue().multiGet(key);
    }

    @Override
    public List<String> getEndTagMultiVarValue(String code, List<String> name) {
        List<String> keyList = new ArrayList<>(name.size());
        for (String n : name) {
            keyList.add(code + "/" + n);
        }
        return  getMultiValue(keyList);
    }

    @Override
    public Map<String, String> getEndTagVarGroupInfo(String code, String group) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEndTagVarInfo(String code, String varName) {
        return getValue(code + "/" + varName);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getEndTagVarInfo(List<String> code, String varName) {
        List<String> keyList = new ArrayList<>(code.size());
        for (String s : code) {
            keyList.add(s + "/" + varName);
        }
        List<String> value = getMultiValue(keyList);

        Map<String, String> map = new HashMap<>(value.size());
        for (int i = 0; i < value.size(); i++) {
            String k = code.get(i);
            String v =  value.get(i);
            map.put(k, v);
        }
        return map;
    }

    @Override
    public Object[][] getEndTagVarLineData(String code, String varName) {
        return new Object[0][];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float[] getEndTagVarYcArray(String code, String varName) {
        String value = getValue(code + "/" + varName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] data = value.split(",");
        float [] ycArray = new float[data.length];
        for (int i = 0; i < ycArray.length; i++) {
            ycArray[i] = Float.parseFloat(data[i]);
        }
        return ycArray;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, float[]> getEndTagVarYcArray(String code, List<String> varNames) {
        List<String> keyList = new ArrayList<>(varNames.size());
        for (String varName : varNames) {
            keyList.add(code + "/" + varName);
        }
        List<String> value = getMultiValue(keyList);
        if (value == null || value.isEmpty()) {
            return null;
        }

        Map<String, float[]> map = new HashMap<>();
        for (int i = 0; i < value.size(); i++) {
            String[] data = value.get(i).split(",");
            float [] ycArray = new float[data.length];
            for (int j = 0; j < ycArray.length; j++) {
                ycArray[j] = Float.parseFloat(data[i]);
            }
            map.put(varNames.get(i), ycArray);
        }
        return map;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @PreDestroy
	public void destroy() {
	}
}
