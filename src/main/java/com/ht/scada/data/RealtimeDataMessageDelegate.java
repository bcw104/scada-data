package com.ht.scada.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ht.scada.data.entity.FaultRecord;
import com.ht.scada.data.entity.OffLimitsRecord;
import com.ht.scada.data.entity.YxRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 实时数据推送代理, 用于触发故障报警、遥信变位、遥测越限，使用时请注册监听器
 *
 * @author: "薄成文" 13-5-21 下午1:51
 * To change this template use File | Settings | File Templates.
 */
public class RealtimeDataMessageDelegate {
    public static final Logger log = LoggerFactory.getLogger(RealtimeDataMessageDelegate.class);

    private RealtimeMessageListener listener;

    public void setListener(RealtimeMessageListener listener) {
        this.listener = listener;
    }

    /**
     * 收到故障报警信息<br/>
     * 故障记录中的id为字符串类型，客户端收到报警信息时该记录的ID已自动生成，但不保证立即写入数据库。
     * @param message
     */
    public void handleFaultMessage(String message) throws IOException {
        log.warn("收到故障报警:{}", message);
        if (listener != null) {
            FaultRecord record = JSON.parseObject(message, FaultRecord.class);
            if (record.getResumeTime() == null) {
                // 新故障记录
                listener.faultOccured(record);
            } else {
                // 故障解除
                listener.faultResumed(record);
            }
        }

    }

    /**
     * 收到越限报警信息<br/>
     * 报警记录中的id可能为空，请不要用id来区分记录，推荐使用code+name+actiontime
     * @param message
     */
    public void handleOffLimitsMessage(String message) throws IOException {
        log.warn("收到越限报警:{}", message);
        if (listener != null) {
            OffLimitsRecord record = JSONObject.parseObject(message, OffLimitsRecord.class);
            if (record.getResumeTime() == null) {
                // 新越限记录
                listener.offLimitsOccured(record);
            } else {
                // 越限解除
                listener.offLimitsResumed(record);
            }
            System.out.println(record);
        }
    }

    /**
     * 收到遥信变位信息<br/>
     * 报警记录中的id可能为空，请不要用id来区分记录，推荐使用code+name+actiontime
     * @param message
     */
    public void handleYxChangeMessage(String message) throws IOException {
        log.info("收到遥信变位信息:{}", message);
        if (listener != null) {
            YxRecord record = JSONObject.parseObject(message, YxRecord.class);
            System.out.println(record);
        }
    }
}
