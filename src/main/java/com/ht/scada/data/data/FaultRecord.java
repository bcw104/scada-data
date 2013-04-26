package com.ht.scada.data.data;

import oracle.kv.Key;
import oracle.kv.Value;
import org.joda.time.LocalDateTime;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 遥测数据记录
 * @author 薄成文
 *
 */
public class FaultRecord implements IKVRecord {
	
	public static final String RECORD_TYPE = "FAULT";
	
	private String id;	// 唯一主键
	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;// 故障信息
	private boolean value;
	private Date actionTime;
	private Date resumeTime;

	public FaultRecord() {
	}
	
	public FaultRecord(String code, String name, String info, boolean value, Date actionTime) {
		this.code = code;
		this.name = name;
		this.info = info;
		this.value = value;
		this.actionTime = actionTime;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public Date getResumeTime() {
		return resumeTime;
	}

	public void setResumeTime(Date resumeTime) {
		this.resumeTime = resumeTime;
	}
	
	@Override
	public Key makeKey() {
        final String timestamp = LocalDateTime.fromDateFields(actionTime).toString();
    	return Key.createKey(Arrays.asList(DB_NAME, RECORD_TYPE, code), Arrays.asList(name, timestamp));
	}

	@Override
	public void parseKey(Key key) {
        final List<String> majorPath = key.getMajorPath();

        if (!RECORD_TYPE.equals(majorPath.get(1))) {
            throw new IllegalArgumentException("Not a fault record: " + key);
        }

        setCode(majorPath.get(2));
        
        final List<String> minorPath = key.getMinorPath();
        setName(minorPath.get(0));
        setActionTime(LocalDateTime.parse(minorPath.get(1)).toDate());
	}
	
	@Override
	public Value makeValue() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
        	dos.writeUTF(getInfo());
        	dos.writeBoolean(getValue());
        	if (getResumeTime() == null) {
        		dos.writeBoolean(false);
        	} else {
        		dos.writeBoolean(true);
        		dos.writeLong(getResumeTime().getTime());
        	}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(baos.toByteArray());
	}
	
	@Override
	public void parseValue(Value value) {
		ByteArrayInputStream bais = new ByteArrayInputStream(value.getValue());
        DataInputStream dis = new DataInputStream(bais);
        try {
			setInfo(dis.readUTF());
			setValue(dis.readBoolean());
			if (dis.readBoolean()) {
				setResumeTime(new Date(dis.readLong()));
			}
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
	}
}
