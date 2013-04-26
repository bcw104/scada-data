package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.RTUService;
import org.springframework.stereotype.Service;

@Service("rtuService")
public class RTUServiceImpl implements RTUService {
	@Override
	public boolean yk(String code, String varName, boolean status) throws Exception {
		return true;
	}

	@Override
	public boolean yt(String code, String varName, double value) throws Exception {
		return false;
	}

}
