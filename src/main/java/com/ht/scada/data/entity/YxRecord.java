package com.ht.scada.data.entity;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 遥测数据记录
 * @author 薄成文
 *
 */
@Entity
@Table(name="T_YX_Record")
public class YxRecord  extends AbstractPersistable<String> {

	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean value;
	private Date datetime;

	public YxRecord() {
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

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 生成报警记录用
	 * @return
	 */
	public String getReMark() {
		return "变位值为：" + (value==true?"1":"0");
	}

}
