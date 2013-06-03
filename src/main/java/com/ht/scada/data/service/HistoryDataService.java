package com.ht.scada.data.service;

import com.ht.scada.common.tag.util.VarGroupEnum;
import com.ht.scada.data.model.TimeSeriesDataModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-5-27 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public interface HistoryDataService {

    /**
     * 查询单个变量在指定时间范围内的历史数据, 可用于绘制曲线图
     * @param code
     * @param varGroup
     * @param varName
     * @param start
     * @param end
     * @return
     */
    List<TimeSeriesDataModel> getVarTimeSeriesData(String code, VarGroupEnum varGroup, String varName, Date start, Date end);

    /**
     * 查询多个变量在指定时间范围内的历史数据, 可用于绘制曲线图
     * @param code
     * @param varGroup
     * @param varName
     * @param start
     * @param end
     * @return
     */
    Map<String, List<TimeSeriesDataModel>> getVarTimeSeriesData(String code, VarGroupEnum varGroup, List<String> varName, Date start, Date end);

}
