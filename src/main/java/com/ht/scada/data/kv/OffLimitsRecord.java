package com.ht.scada.data.kv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import oracle.kv.Key;
import oracle.kv.Value;

import org.joda.time.LocalDateTime;

/**
 * 遥测越限记录
 * @author 薄成文
 *
 */
public class OffLimitsRecord implements IKVRecord {
	public static final String RECORD_TYPE = "OFF_LIMITS";
	
	private String id;	// 唯一主键
	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;// 报警信息
	private double value;// 动作值
	private double threshold;// 阈值
	private boolean type;// 越限类型 true:越上限，false:越下限 
	private Date actionTime;
	private Date resumeTime;

	public OffLimitsRecord() {
	}

	public OffLimitsRecord(String code, String name, String info, double value,
			double threshold, boolean type, Date actionTime) {
		this.code = code;
		this.name = name;
		this.info = info;
		this.value = value;
		this.threshold = threshold;
		this.type = type;
		this.actionTime = actionTime;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public boolean getType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
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
            throw new IllegalArgumentException("Not a offlimits record: " + key);
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
        	dos.writeDouble(getValue());
        	dos.writeDouble(getThreshold());
        	dos.writeBoolean(getType());
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
			setValue(dis.readDouble());
			setThreshold(dis.readDouble());
			setType(dis.readBoolean());
			if (dis.readBoolean()) {
				setResumeTime(new Date(dis.readLong()));
			}
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
	}
}
