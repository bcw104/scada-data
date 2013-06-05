package com.ht.scada.data.entity;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 遥测数据记录
 * @author 薄成文
 *
 */
@Entity
@Table(name="T_Fault_Record")
public class FaultRecord extends AbstractPersistable<String> {

	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;// 故障信息
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean value;
    @Column(name = "action_time")
	private Date actionTime;
    @Column(name = "resume_time")
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
	
	/**
	 * 生成报警记录用
	 * @return
	 */
	public String getReMark() {
		return "报警值为：" + (value==true?"1":"0");
	}

}
