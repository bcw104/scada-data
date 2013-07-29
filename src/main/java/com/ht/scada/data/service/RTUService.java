package com.ht.scada.data.service;

import java.util.Map;

/**
 * 实现对RTU的遥控遥调操作,目前支持JAX-RS, JAX-WS(即将支持)
 * @author 薄成文
 *
 */
public interface RTUService {
	
	/**
	 * 遥控、遥调操作消息应答中的boolean属性名称：<br>
	 * replyMessage.setBooleanProperty("success", true);<br>
	 * sucess=true表示操作成功
	 */
	public static final String OP_REPLY_KEY = "success";
	
	/**
	 * 当前操作的有效时间，如果消息失效，则放弃操作
	 */
	public static final String TIME_MILLIS = "timeMillis";
	
	/**
	 * 遥控操作
	 * @param code 末端编码
	 * @param varName 变量名称
	 * @param status 操作值，true:合,false:分
	 * @return true表示操作成功
	 * @throws Exception 
	 */
	boolean yk(String code, String varName, boolean status) throws Exception;
	
	/**
     * 遥调操作
	 * @param code
	 * @param varName
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	boolean yt(String code, String varName, int value) throws Exception;

    /**
     *
     * @param code
     * @param value
     * @return
     * @throws Exception
     * @deprecated 未实现该接口，请勿使用
     */
    @Deprecated
    boolean yt(String code, Map<String, Float> value) throws Exception;

    /**
     * 判断功图数据是否召唤完成, 用于手工召唤功图数据
     * @param code
     * @return
     */
    boolean isGTComplete(String code);
}
