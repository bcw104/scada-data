package com.ht.scada.data.dao;

import com.ht.scada.data.entity.FaultRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-8-12 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public interface FaultRecordDao extends CrudRepository<FaultRecord, String> {
    List<FaultRecord> findByEndIdAndResumeTimeIsNull(int endId);
    List<FaultRecord> findByEndIdAndResumeTimeIsNotNull(int endId);
}
