package com.ht.scada.data.redis;

import com.ht.scada.data.entity.FaultRecord;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-5-22 下午1:09
 * To change this template use File | Settings | File Templates.
 */
public class FaultRecordSerializer extends JacksonJsonRedisSerializer<FaultRecord> {
    public FaultRecordSerializer() {
        super(FaultRecord.class);
    }
}
