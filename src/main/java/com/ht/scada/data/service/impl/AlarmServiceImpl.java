package com.ht.scada.data.service.impl;

import com.ht.scada.data.kv.FaultRecord;
import com.ht.scada.data.kv.OffLimitsRecord;
import com.ht.scada.data.kv.YXData;
import com.ht.scada.data.service.AlarmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * 作者: "薄成文"
 * 日期: 13-5-16 下午1:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    @Override
    public List<FaultRecord> getCurrentFaultRecord(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<OffLimitsRecord> getCurrentOffLimitsRecord(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<YXData> getCurrentYXData(String code) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}