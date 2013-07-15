package com.ht.scada.data.service.impl;

import com.ht.scada.common.tag.dao.EndTagDao;
import com.ht.scada.data.Config;
import com.ht.scada.data.rs.IMyResource;
import com.ht.scada.data.service.RTUService;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

@Service
public class RTUServiceImpl implements RTUService {
    public static final Logger log = LoggerFactory.getLogger(RTUServiceImpl.class);


//    private RealtimeDataService realtimeDataService;
    @Autowired
    private EndTagDao endTagDao;
    private IMyResource commRs;

    public RTUServiceImpl() {
    }

    @PostConstruct
    private void init() {

        commRs = JAXRSClientFactory.create(Config.INSTANCE.getCommUrl(), IMyResource.class);
    }
    @PreDestroy
    private void destroy() {
    }

	@Override
	public boolean yk(String code, String varName, boolean value) throws Exception {
        log.info("执行遥控操作:{}-{}", code, varName);
        Integer channelIndex = endTagDao.getChannelIdxByCode(code);
        if (channelIndex == null) {
            throw new Exception("Communication channel not found, the channel index is null!");
        }
        return Boolean.parseBoolean(commRs.yk(channelIndex, code, varName, value));
	}

	@Override
	public boolean yt(String code, String varName, int value) throws Exception {
        log.info("执行遥调操作:{}-{}", code, varName);
        Integer channelIndex = endTagDao.getChannelIdxByCode(code);
        if (channelIndex == null) {
            throw new Exception("Communication channel not found, the channel index is null!");
        }
        return Boolean.parseBoolean(commRs.yt(channelIndex, code, varName, value));
	}

    @Override
    public boolean yt(String code, Map<String, Float> value) throws Exception {
        throw new Exception("this method not implemented!");
    }

}
