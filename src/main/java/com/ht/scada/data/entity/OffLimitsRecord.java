package com.ht.scada.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * 遥测越限记录
 * @author 薄成文
 *
 */
@Entity
@Table(name="T_OffLimits_Record")
public class OffLimitsRecord  implements Persistable<String> {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    @Column(length = 32)
    private String id;

	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;// 报警信息
	private double value;// 动作值
	private double threshold;// 阈值
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean type;// 越限类型 true:越上限，false:越下限
    @Column(name = "action_time")
    @Temporal(TemporalType.TIMESTAMP)
	private Date actionTime;

    @Column(name = "resume_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
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

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
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
	
	/**
	 * 生成报警记录用
	 * @return
	 */
	public String getReMark() {
		return "动作值为：" + String.valueOf(value) + "；阈值为:"+String.valueOf(threshold);
	}

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#getId()
     */
    public String getId() {

        return id;
    }

    /**
     * Sets the id of the entity.
     *
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }
    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    public boolean isNew() {

        return null == getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        OffLimitsRecord that = (OffLimitsRecord) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
