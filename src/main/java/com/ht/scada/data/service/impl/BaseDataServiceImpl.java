package com.ht.scada.data.service.impl;

import com.ht.scada.data.service.BaseDataService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BaseDataServiceImpl implements BaseDataService {
	
	
	@Override
	public Map<String, Object> getRealTimeData(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getHistoryData(String code, Date start,
			Date end) {
		// TODO Auto-generated method stub
		return null;
	}

}
