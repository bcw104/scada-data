package com.ht.scada.data.service;

import java.util.List;
import java.util.Map;

/**
 * 实时数据库服务类<br>
 *     key 的格式为[endTagCode]/[varGroup]/[varName] <br/>
 * 存储双浮点型的数据时采用Double.toString(v)的方式<br/>
 * 存储单浮点型的数据时采用Float.toString(v)的方式<br/>
 * 存储浮点型数组的数据时采用","连接成字符串的方式<br/>
 * 存储布尔型的数据时采用Boolean.toString(b)的方式<br/>
 * @author 薄成文
 *
 */
public interface RealtimeDataService {


//    Map<String, Float> getBatchFloatValue(String code, String[] name);
//	Map<String, Double> getBatchDoubleValue(String code, String[] name);
//	Map<String, Boolean> getBatchBoolValue(String code, String[] name);
    String[] getBatchValue(String code, String[] name);

    /**
     * 批量保存数据
     * @param kvMap
     */
	void putBatchValue(Map<String, String> kvMap);

    void putValue(String key, String value);
    String getValue(String key);

    /**
     * 获取末端指定变量组的所有变量值
     * @param code
     * @param group
     * @return
     */
    Map<String, String> getEndTagVarGroupInfo(String code, String group);

    /**
     * 获取末端指定变量的值
     * @param group
     * @param code
     * @param varName
     * @return
     */
    String getEndTagVarInfo(String code, String group, String varName);

    /**
     * 批量获取末端指定变量的值
     * @param group
     * @param code
     * @param varName
     * @return
     */
    Map<String, String> getEndTagVarInfo(List<String> code, String group, String varName);

    /**
     * 获取曲线数据
     * @param code
     * @param group
     * @param varName
     * @return
     */
    Object[][] getEndTagVarLineData(String code, String group, String varName);

}
