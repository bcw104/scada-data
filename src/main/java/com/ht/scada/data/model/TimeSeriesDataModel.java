package com.ht.scada.data.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-5-27 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public class TimeSeriesDataModel {
    private Date date;
    private float value;

    public TimeSeriesDataModel(Date date, float value) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
