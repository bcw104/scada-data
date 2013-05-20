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
 * 遥测数据记录
 * @author 薄成文
 *
 */
public class YXData implements IKVRecord {
	
	private static final String RECORD_TYPE = "YX";
	
	private String id;	// 唯一主键
	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;
	private boolean value;
	private Date datetime;

	public YXData() {
	}

	public YXData(String code, String name, String info, boolean value, Date datetime) {
		this.code = code;
		this.name = name;
		this.info = info;
		this.value = value;
		this.datetime = datetime;
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

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	@Override
	public Key makeKey() {
        final String timestamp = LocalDateTime.fromDateFields(datetime).toString();
    	return Key.createKey(Arrays.asList(DB_NAME, RECORD_TYPE, code), Arrays.asList(name, timestamp));
	}

	@Override
	public void parseKey(Key key) {
        final List<String> majorPath = key.getMajorPath();

        if (!RECORD_TYPE.equals(majorPath.get(1))) {
            throw new IllegalArgumentException("Not a yx com.ht.scada.data.com.ht.scada.data.kv: " + key);
        }

        setCode(majorPath.get(2));
        
        final List<String> minorPath = key.getMinorPath();
        setName(minorPath.get(0));
        setDatetime(LocalDateTime.parse(minorPath.get(1)).toDate());
	}

	@Override
	public Value makeValue() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
        	dos.writeUTF(getInfo());
        	dos.writeBoolean(getValue());
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
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
		
	}
}
