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

    @PostConstruct
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
        Map<String, String> map = realtimeDataService.getEndTagAllVarValue("codeTest");
        assert map != null;
        assert map.size() == 2;
        System.out.println(map);
        System.out.println("###### getEndTagAllVarValueTest END ######");
    }

    @Test
    public void getEndTagVarGroupInfoTest() {
        System.out.println("###### getEndTagVarGroupInfoTest ######");

        Map<String, String> map = realtimeDataService.getEndTagVarGroupInfo("codeTest", VarGroupEnum.DIAN_YC.toString());
        assert map != null;
        assert map.size() == 1;
        assert map.get("test").equals("true");
        System.out.println(map);

        map = realtimeDataService.getEndTagVarGroupInfo("codeTest", VarGroupEnum.DIAN_YM.toString());
        assert map != null;
        assert map.size() == 1;
        assert map.get("numberText").equals("123");
        System.out.println(map);

        System.out.println("###### getEndTagVarGroupInfoTest END ######");
    }

}
