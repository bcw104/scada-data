package com.ht.scada.data.service;

import com.ht.scada.data.entity.FaultRecord;
import com.ht.scada.data.entity.OffLimitsRecord;

import java.util.List;

/**
 * 报警服务接口<br>
 * 用于注册监听实时报警，查询报警记录等操作, 控制器可以直接调用该服务。
 * @author 薄成文
 *
 */
public interface AlarmService {
	
	/**
	 * 返回当前的故障报警记录
	 * @param endId
	 * @return
	 */
	public List<FaultRecord> getUnresolvedFaultRecord(int endId);

    public List<FaultRecord> getResolvedFaultRecord(int endId);

	/**
	 * 返回当前的遥测越限报警记录
	 * @param endId
	 * @return
	 */
	public List<OffLimitsRecord> getUnresolvedOffLimitsRecord(int endId);
    public List<OffLimitsRecord> getResolvedOffLimitsRecord(int endId);

}
