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

    List<String> getEndTagMultiVarValue(String code, List<String> name);

    /**
     * 查询监控对象所有变量的实时数据
     * @param code
     * @return
     */
    Map<String, String> getEndTagAllVarValue(String code);

    /**
     * 获取末端指定变量组的所有变量值
     * @param code 监控对象编号
     * @param group 监控对象变量组
     * @return
     */
    Map<String, String> getEndTagVarGroupInfo(String code, String group);

    /**
     * 获取单个监控对象指定变量的值
     * @param code 监控对象编号
     * @param varName 遥测、遥信、遥脉变量（请不要传递其它类型的变量，包括遥测数组）
     * @return
     */
    String getEndTagVarInfo(String code, String varName);

    /**
     * 获取多个监控对象指定变量的值
     * @param code 监控对象编号
     * @param varName 遥测、遥信、遥脉变量（请不要传递其它类型的变量，包括遥测数组）
     * @return
     */
    Map<String, String> getEndTagVarInfo(List<String> code, String varName);

    /**
     * 获取监控对象实时曲线数据, TODO 实时曲线最多记录最近1个小时的数据
     * @param code
     * @param varName
     * @return
     * @deprecated 请使用HistoryDataService中的相应接口代替
     */
    @Deprecated
    Object[][] getEndTagVarLineData(String code, String varName);

    /**
     * 查询遥测数组实时数据, 返回结果可以为Null<br/>
     * 用于获取功图和谐波等数组形式的数组数据<br/>
     * @param code
     * @param varName
     * @return
     */
    String getEndTagVarYcArray(String code, String varName);

    /**
     * 查询多个遥测数组实时数据, 返回结果可以为Null
     * 用于获取功图和谐波等数组形式的数组数据<br/>
     * @param code
     * @param varName
     * @return
     */
    Map<String, String> getEndTagVarYcArray(String code, List<String> varName);

    String getEndTagManualGTDatetime(String code);
}
