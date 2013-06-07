package com.ht.scada.data.service.impl;

import com.ht.scada.common.tag.util.VarGroupEnum;
import com.ht.scada.data.kv.KeyDefinition;
import com.ht.scada.data.kv.VarGroupData;
import com.ht.scada.data.model.TimeSeriesDataModel;
import com.ht.scada.data.service.HistoryDataService;
import oracle.kv.*;
import org.joda.time.LocalDateTime;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-5-27 上午10:30
 * To change this template use File | Settings | File Templates.
 */
public class HistoryDataServiceImpl implements HistoryDataService {

    private KVStore store;

    @Override
    public List<TimeSeriesDataModel> getVarTimeSeriesData(String code, VarGroupEnum varGroup, String varName, Date start, Date end) {
        List<TimeSeriesDataModel> list = new ArrayList<>();

        final String startTimestamp = LocalDateTime.fromDateFields(start).toString();
        final String endTimestamp = LocalDateTime.fromDateFields(end).toString();

        KeyRange keyRange = new KeyRange(startTimestamp /*start*/, true /*startInclusive*/,
                endTimestamp /*end*/, false /*endInclusive*/);

        final Map<Key, ValueVersion> results = store.multiGet(KeyDefinition.getVarGroupKey(code, varGroup.toString()), keyRange, Depth.CHILDREN_ONLY);
        for (Map.Entry<Key, ValueVersion> entry : results.entrySet()) {
            VarGroupData data = new VarGroupData();
            data.parseKey(entry.getKey());
            data.parseValue(entry.getValue().getValue());
            Float f = data.getYcValueMap().get(varName);
            if (f != null) {
                list.add(new TimeSeriesDataModel(data.getDatetime(), f));
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

        final String startTimestamp = LocalDateTime.fromDateFields(start).toString();
        final String endTimestamp = LocalDateTime.fromDateFields(end).toString();

        KeyRange keyRange = new KeyRange(startTimestamp /*start*/, true /*startInclusive*/,
                endTimestamp /*end*/, false /*endInclusive*/);

        final Map<Key, ValueVersion> results = store.multiGet(KeyDefinition.getVarGroupKey(code, varGroup.toString()), keyRange, Depth.CHILDREN_ONLY);
        for (Map.Entry<Key, ValueVersion> entry : results.entrySet()) {
            VarGroupData data = new VarGroupData();
            data.parseKey(entry.getKey());
            data.parseValue(entry.getValue().getValue());
            for (String varName : varNames) {
                Float f = data.getYcValueMap().get(varName);
                if (f != null) {
                    List<TimeSeriesDataModel> list = map.get(varName);
                    list.add(new TimeSeriesDataModel(data.getDatetime(), f));
                }
            }
        }
        return map;
    }

    @Override
    public List<VarGroupData> getVarGroupData(String code, VarGroupEnum varGroup, Date start, Date end, int limit) {
        List<VarGroupData> list = new ArrayList<>();

        if (limit <= 0 || limit > 5000) {
            limit = 5000;
        }

        final String startTimestamp = LocalDateTime.fromDateFields(start).toString();
        final String endTimestamp = LocalDateTime.fromDateFields(end).toString();

        KeyRange keyRange = new KeyRange(startTimestamp /*start*/, true /*startInclusive*/,
                endTimestamp /*end*/, false /*endInclusive*/);

        Key parentKey = KeyDefinition.getVarGroupKey(code, varGroup.toString());
        Iterator<Key> keyIterator = store.multiGetKeysIterator(Direction.FORWARD, 0, parentKey, keyRange, Depth.CHILDREN_ONLY);
        int count = 0;
        while (keyIterator.hasNext()) {
            Key next = keyIterator.next();
            count++;
        }

        int skip = 0;
        if (count > limit) {
           skip = count / count % limit;
        }

        Iterator<KeyValueVersion> keyValueVersionIterator = store.multiGetIterator(Direction.FORWARD, 0, KeyDefinition.getVarGroupKey(code, varGroup.toString()), keyRange, Depth.CHILDREN_ONLY);
        //final Map<Key, ValueVersion> results = store.multiGetIterator(Direction.FORWARD, KeyDefinition.getVarGroupKey(code, varGroup.toString()), keyRange, Depth.CHILDREN_ONLY);
        int i = 1;
        while (keyValueVersionIterator.hasNext()) {
            KeyValueVersion keyValueVersion = keyValueVersionIterator.next();
            if (skip == 0 || i % skip != 0) {
                VarGroupData data = new VarGroupData();
                data.parseKey(keyValueVersion.getKey());
                data.parseValue(keyValueVersion.getValue());
                list.add(data);
                if (list.size() == limit) {
                    break;
                }
            }
            i++;
        }
        return list;
    }
}
