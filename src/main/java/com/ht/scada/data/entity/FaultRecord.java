package com.ht.scada.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * 遥测数据记录
 * @author 薄成文
 *
 */
@Entity
@Table(name="T_Fault_Record")
public class FaultRecord implements Persistable<String> {

    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    @Column(length = 32)
    private String id;

    @Column(name = "endId")
    private Integer endId;
    @Column(name = "endName")
    private String endName;// 监控对象名称(回路名、井名)
    @Column(name = "tagName")
    private String tagName;
	private String code;// 计量点编号(回路号、井号等)
	private String name;// 变量名称
	private String info;// 故障信息
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean value;
    @Column(name = "action_time")
    @Temporal(TemporalType.TIMESTAMP)
	private Date actionTime;
    @Column(name = "resume_time")
    @Temporal(TemporalType.TIMESTAMP)
	private Date resumeTime;

	public FaultRecord() {
	}
	
	public FaultRecord(int endId, String endName, String code,
                       String name, String tagName, String info, boolean value, Date actionTime) {
        this.endId = endId;
        this.endName= endName;
		this.code = code;

        this.tagName = tagName;
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

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

        FaultRecord that = (FaultRecord) obj;

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
