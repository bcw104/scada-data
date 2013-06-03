/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2010, 2013 Oracle and/or its affiliates.  All rights reserved.
 *
 */

package com.ht.scada.data.kv;

import oracle.kv.Key;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.Date;

/**
 * KV数据库Key定义
 * @author 薄成文
 *
 */
public class KeyDefinition {
	public static final String DB_NAME = "db";
	
	/* 实时数据类型  */
	public static final String REAL_BOOL = "real_bool";
	public static final String REAL_NUM = "real_num";
	
	/* 记录类型  */
	public static final String VAR_GROUP_RECORD = "VAR_GROUP";
	public static final String YX_RECORD = "YX";
	public static final String OFF_LIMITS_RECORD = "OFF_LIMITS";
	public static final String FAULT_RECORD = "FAULT";


    public static Key makeVarGroupKey(String code, String varGroup, Date datetime) {
        final String timestamp = LocalDateTime.fromDateFields(datetime).toString();
    	return Key.createKey(Arrays.asList(DB_NAME, VAR_GROUP_RECORD, code), Arrays.asList(varGroup, timestamp));
    }
    
    private static Key createKey(String recordType, String code, String name, Date datetime) {
        final String timestamp = LocalDateTime.fromDateFields(datetime).toString();
    	return Key.createKey(Arrays.asList(DB_NAME, recordType, code, name), timestamp);
    }

    public static Key getVarGroupMajorKey(String code) {
        return Key.createKey(Arrays.asList(DB_NAME, VAR_GROUP_RECORD, code));
    }
    
    public static Key getKey(String recordType, String code) {
    	return Key.createKey(Arrays.asList(DB_NAME, recordType, code));
    }
    
    public static Key getKey(String recordType, String code, String name) {
    	return Key.createKey(Arrays.asList(DB_NAME, recordType, code, name));
    }
    
    public static Key getKey(String recordType) {
    	return Key.createKey(Arrays.asList(DB_NAME, recordType));
    }
    
    public static Key getVarGroupKey(String code, String varGroup) {
    	return Key.createKey(Arrays.asList(DB_NAME, VAR_GROUP_RECORD, code), Arrays.asList(varGroup));
    }
    
}
