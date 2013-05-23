package com.ht.scada.data;

import com.ht.scada.data.entity.FaultRecord;
import com.ht.scada.data.entity.OffLimitsRecord;
import com.ht.scada.data.entity.YxRecord;

/**
 * 实时数据监听器，实现故障报警、遥测越限、遥信变位消息的监听
 * @author: 薄成文 13-5-22 下午4:40
 */
public interface RealtimeMessageListener {

    /**
     * 发生故障报警
     * @param record
     */
    void faultOccured(FaultRecord record);

    /**
     * 故障报警恢复
     * @param record
     */
    void faultResumed(FaultRecord record);

    /**
     * 发生遥测越限
     * @param record
     */
    void offLimitsOccured(OffLimitsRecord record);

    /**
     * 遥测越限恢复
     * @param record
     */
    void offLimitsResumed(OffLimitsRecord record);

    /**
     * 发生遥测变位
     * @param record
     */
    void yxChanged(YxRecord record);
}
