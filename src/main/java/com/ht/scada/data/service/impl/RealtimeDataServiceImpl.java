package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.RealtimeDataService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 薄成文
 *
 */
@Service
public class RealtimeDataServiceImpl implements RealtimeDataService {
	private ShardedJedisPool pool;
	
	@PostConstruct
	public void init() throws Exception {
		JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置
		config.setMaxActive(20);// 最大活动的对象个数
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
		config.setMaxWait(1000 * 10);// 获取对象时最大等待时间
		//config.setTestOnBorrow(true);
	          
//		pool = new JedisPool(config, "localhost");
		
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);

		String hostA = "127.0.0.1";
		int portA = 6379;
		JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
		// infoA.setPassword("redis.360buy");
		jdsInfoList.add(infoA);

		String hostB = "192.168.1.80";
		int portB = 6380;
		JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
		// infoB.setPassword("redis.360buy");
		//jdsInfoList.add(infoB);

		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,

		Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

    @Override
    public String[] getBatchValue(String code, String[] name) {
        ShardedJedis jedis = pool.getResource();

        String[] value = new String[name.length];
        try {
            ShardedJedisPipeline pipeline = jedis.pipelined();
            Response<String>[] response = new Response[name.length];
            for (int i = 0; i < name.length; i++) {
                response[i] = pipeline.get(code + "/" + name[i]);
            }
            pipeline.sync();

            for (int i = 0; i < name.length; i++) {
                Response<String> resp = response[i];
                String s = resp.get();
                if ( s != null) {
                    value[i] = s;
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override
    public void putBatchValue(Map<String, String> kvMap) {
        ShardedJedis jedis = pool.getResource();
        try {
            ShardedJedisPipeline pipeline = jedis.pipelined();
            int i = 0;
            for (Entry<String, String> entry : kvMap.entrySet()) {
                pipeline.set(entry.getKey(), entry.getValue());
                if (i++ % 100 == 0) {
                    pipeline.sync();
                }
            }
            pipeline.sync();
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void putValue(String key, String value) {
        ShardedJedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public String getValue(String key) {
        ShardedJedis jedis = pool.getResource();
        String value = null;
        try {
            value = jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override
    public Map<String, String> getEndTagVarGroupInfo(String code, String group) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEndTagVarInfo(String code, String group, String varName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getEndTagVarInfo(List<String> code, String group, String varName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object[][] getEndTagVarLineData(String code, String group, String varType) {
        return new Object[0][];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @PreDestroy
	public void destroy() {
		pool.destroy();
	}
}
