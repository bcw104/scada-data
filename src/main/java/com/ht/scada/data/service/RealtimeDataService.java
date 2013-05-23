package com.ht.scada.data.service;

import java.util.List;
import java.util.Map;

/**
 * 实时数据库服务类<br>
 *     key 的格式为[endTagCode]/[varName] <br/>
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
    String getValue(String key);
    List<String> getMultiValue(List<String> key);

    List<String> getEndTagMultiVarValue(String code, List<String> name);
    /**
     * 获取末端指定变量组的所有变量值
     * @param code
     * @param group
     * @return
     */
    Map<String, String> getEndTagVarGroupInfo(String code, String group);

    /**
     * 获取单个监控对象指定变量的值
     * @param code
     * @param varName
     * @return
     */
    String getEndTagVarInfo(String code, String varName);

    /**
     * 获取多个监控对象指定变量的值
     * @param code
     * @param varName
     * @return
     */
    Map<String, String> getEndTagVarInfo(List<String> code, String varName);

    /**
     * 获取监控对象实时曲线数据, TODO 实时曲线最多记录最近1个小时的数据
     * @param code
     * @param varName
     * @return
     */
    Object[][] getEndTagVarLineData(String code, String varName);

}
