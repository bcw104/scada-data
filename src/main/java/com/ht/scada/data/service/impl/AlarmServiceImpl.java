package com.ht.scada.data.service.impl;

import com.ht.scada.data.dao.FaultRecordDao;
import com.ht.scada.data.dao.OffLimitsRecordDao;
import com.ht.scada.data.entity.FaultRecord;
import com.ht.scada.data.entity.OffLimitsRecord;
import com.ht.scada.data.service.AlarmService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * 作者: "薄成文"
 * 日期: 13-5-16 下午1:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AlarmServiceImpl implements AlarmService {

    @Inject
    private FaultRecordDao faultRecordDao;
    @Inject
    private OffLimitsRecordDao offLimitsRecordDao;

    @Override
    public List<FaultRecord> getUnresolvedFaultRecord(int endId) {
        return faultRecordDao.findByEndIdAndResumeTimeIsNull(endId);
    }

    @Override
    public List<FaultRecord> getResolvedFaultRecord(int endId) {
        return faultRecordDao.findByEndIdAndResumeTimeIsNotNull(endId);
    }

    @Override
    public List<OffLimitsRecord> getUnresolvedOffLimitsRecord(int endId) {
        return offLimitsRecordDao.findByEndIdAndResumeTimeIsNull(endId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<OffLimitsRecord> getResolvedOffLimitsRecord(int endId) {
        return offLimitsRecordDao.findByEndIdAndResumeTimeIsNotNull(endId);  //To change body of implemented methods use File | Settings | File Templates.
    }

}
