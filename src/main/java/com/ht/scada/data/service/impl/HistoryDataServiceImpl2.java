package com.ht.scada.data.service.impl;

import com.google.common.base.Joiner;
import com.ht.db.util.DbUtilsTemplate;
import com.ht.scada.common.tag.util.VarGroupEnum;
import com.ht.scada.data.kv.VarGroupData;
import com.ht.scada.data.model.TimeSeriesDataModel;
import com.ht.scada.data.service.HistoryDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-5-27 上午10:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HistoryDataServiceImpl2 implements HistoryDataService {
    private static final Logger log = LoggerFactory.getLogger(HistoryDataServiceImpl2.class);
    private static final String VAR_GROUP_TABLE_PREFIX = "T_Group_";

    private DbUtilsTemplate dbUtilsTemplate;
    @Inject
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        dbUtilsTemplate = new DbUtilsTemplate(dataSource);
    }

    @PreDestroy
    private void destroy() {
    }

    @Override
    public List<TimeSeriesDataModel> getVarTimeSeriesData(String code, VarGroupEnum varGroup, String varName, Date start, Date end) {
        List<TimeSeriesDataModel> list = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        String tableName = VAR_GROUP_TABLE_PREFIX + varGroup.toString();
        sqlBuilder.append("select ").append(varName).append(" from ")
                .append(tableName)
                .append(" where code=? and datetime>=? and datetime<?")
                .append(" order by datetime asc");

        List<Map<String, Object>> dataList = dbUtilsTemplate.find(sqlBuilder.toString(), code,
                new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        for (Map<String, Object> dataMap : dataList) {
            //list.add()
            Object obj = dataMap.get("datetime");
            if (obj != null && obj instanceof Timestamp) {
                Date datetime = new Date(((Timestamp) obj).getTime());
                obj = dataMap.get(varName);
                if (obj != null && obj instanceof Float) {
                    Float f = (Float) obj;
                    list.add(new TimeSeriesDataModel(datetime, f));
                }
            }
        }

        return list;
    }

    @Override
    public Map<String, List<TimeSeriesDataModel>> getVarTimeSeriesData(String code, VarGroupEnum varGroup, List<String> varNames, Date start, Date end) {
        Map<String, List<TimeSeriesDataModel>> map = new HashMap<>(varNames.size());
        for (String varName : varNames) {
            List<TimeSeriesDataModel> list = new ArrayList<>();
            map.put(varName, list);
        }

        StringBuilder sqlBuilder = new StringBuilder();
        String tableName = VAR_GROUP_TABLE_PREFIX + varGroup.toString();
        sqlBuilder.append("select ");
        Joiner.on(",").appendTo(sqlBuilder, varNames);
        sqlBuilder.append(" from ")
                .append(tableName)
                .append(" where code=? and datetime>=? and datetime<?")
                .append(" order by datetime asc");

        List<Map<String, Object>> dataList = dbUtilsTemplate.find(sqlBuilder.toString(), code,
                new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        for (Map<String, Object> dataMap : dataList) {
            //list.add()
            Object obj = dataMap.get("datetime");
            if (obj != null && obj instanceof Timestamp) {
                Date datetime = new Date(((Timestamp) obj).getTime());
                for (String varName : varNames) {
                    obj = dataMap.get(varName);
                    if (obj != null && obj instanceof Float) {
                        Float f = (Float) obj;
                        List<TimeSeriesDataModel> list = map.get(varName);
                        list.add(new TimeSeriesDataModel(datetime, f));
                    }
                }
            }
        }
        return map;
    }

    @Override
    public List<VarGroupData> getVarGroupData(String code, VarGroupEnum varGroup, Date start, Date end, int limit) {
        List<VarGroupData> list = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        String tableName = VAR_GROUP_TABLE_PREFIX + varGroup.toString();
        sqlBuilder.append("select * from ")
                .append(tableName)
                .append(" where code=? and datetime>=? and datetime<?")
                .append(" order by datetime asc ")
                .append("limit ").append(limit);
        List<Map<String, Object>> dataList = dbUtilsTemplate.find(sqlBuilder.toString(), code,
                new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        for (Map<String, Object> map : dataList) {
            VarGroupData data = new VarGroupData();
            data.setCode(code);
            data.setGroup(varGroup);

            Object obj = map.get("datetime");
            if (obj != null && obj instanceof Date) {
                data.setDatetime((Date) obj);
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                obj = entry.getValue();
                if (obj != null && !entry.getKey().equals("code")) {
                    if (obj instanceof Float) {
                        data.getYcValueMap().put(entry.getKey(), (Float)obj);
                    } else if (obj instanceof Double) {
                        data.getYmValueMap().put(entry.getKey(), (Double)obj);
                    } else if (obj instanceof Integer) {
                        data.getYxValueMap().put(entry.getKey(), ((Integer)obj).intValue() == 1);
                    } else if (obj instanceof String) {
                        String[] array = ((String)obj).split(",");
                        float[] floatArray = new float[array.length];
                        for (int i = 0; i < floatArray.length; i++) {
                            floatArray[i] = Float.parseFloat(array[i]);
                        }
                        data.getArrayValueMap().put(entry.getKey(), floatArray);
                    }
                }
            }
            list.add(data);
        }
        return list;
    }

    @Override
    public VarGroupData getVarGroupData(String code, VarGroupEnum varGroup, Date start) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
