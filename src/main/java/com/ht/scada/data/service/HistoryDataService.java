package com.ht.scada.data.service;

import com.ht.scada.common.tag.util.VarGroupEnum;
import com.ht.scada.data.kv.VarGroupData;
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

    /*
     * 查询多个变量在指定时间范围内的历史数据, 可用于绘制曲线图
     * @param code
     * @param varGroup
     * @param varName
     * @param start
     * @param end
     * @return
     */
    Map<String, List<TimeSeriesDataModel>> getVarTimeSeriesData(String code, VarGroupEnum varGroup, List<String> varName, Date start, Date end);

    /**
     * 查询某监控对象在指定时间范围内的分组历史数据
     * @param code 监控对象编辑
     * @param varGroup 变量分组
     * @param start 起始时间
     * @param end 结束时间
     * @param limit 返回的最大数量; 当limit<=0或limit>5000时最多返回5000项历史记录; 当limit>0&&limit<=5000时,最多返回limit项历史记录.
     * @return
     */
    List<VarGroupData> getVarGroupData(String code, VarGroupEnum varGroup, Date start, Date end, int limit);

    /**
     * 查询该时间点最新的一条数据记录
     * @param code
     * @param varGroup
     * @param start
     * @return 大于起始时间的第一条数据记录, 返回结果可以为空
     */
    VarGroupData getVarGroupData(String code, VarGroupEnum varGroup, Date start);
}
