package com.ht.scada.data.service;

import com.google.common.collect.Maps;
import com.ht.scada.common.tag.util.VarGroupEnum;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-6-9 上午10:11
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:test-context.xml")
public class RealtimeDataServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    private RealtimeDataService realtimeDataService;
    @Inject
    private StringRedisTemplate redisTemplate;

   // @PostConstruct
    public void init() {
        Map<String, String> inputMap = Maps.newHashMap();
        inputMap.put("test", "true");
        inputMap.put("numberText", "123");
        redisTemplate.opsForHash().putAll("codeTest", inputMap);

        Map<String, String> groupMap = Maps.newHashMap();
        groupMap.put(VarGroupEnum.DIAN_YC.toString(), "i_a,i_b,i_c,test");
        groupMap.put(VarGroupEnum.DIAN_YM.toString(), "numberText");
        redisTemplate.opsForHash().putAll("codeTest:GROUP", groupMap);
    }

    @Test
    public void getEndTagAllVarValueTest() {
        System.out.println("###### getEndTagAllVarValueTest ######");
        Map<String, String> map = realtimeDataService.getEndTagAllVarValue("code_001");
//        assert map != null;
//        assert map.size() == 2;
        for(String s : map.keySet()) {
        	System.out.println(s);
        	System.out.println(map.get(s));
        }
        System.out.println(map.size());
        System.out.println("###### getEndTagAllVarValueTest END ######");
    }
    
    @Test
    public void getEndTagVarGroupInfo() {
    	System.out.println("##############################");
    	Map<String, String> map = realtimeDataService.getEndTagVarGroupInfo("code_001", "DIAN_XB");
    	for(String s : map.keySet()) {
        	System.out.println(s);
        	System.out.println(map.get(s));
        }
    	System.out.println("##############################");
    }
    @Test
    public void getEndTagVarInfo() {
    	System.out.println("##############################");
    	String value = realtimeDataService.getEndTagVarInfo("code_001", "wei_yi_array");
        System.out.println(value);
    	System.out.println("##############################");
    }
    @Test
    public void getEndTagVarYcArray() {
    	System.out.println("##############################");
    	float[] value = realtimeDataService.getEndTagVarYcArray("code_001", "wei_yi_array");
        for(float f : value) {
        	System.out.println(f);
        }
    	System.out.println("##############################");
    	
    }

    

}
