package com.ht.scada.data.service;

import com.ht.scada.common.tag.util.VarGroupEnum;
import com.ht.scada.data.model.TimeSeriesDataModel;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-6-26 上午11:18
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:test-context.xml")
public class HistoryDataServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HistoryDataService historyDataService;

    @Test
    public void getVarTimeSeriesDataTest() {
        System.out.println("###### getVarTimeSeriesDataTest ######");

        List<TimeSeriesDataModel> list = historyDataService.getVarTimeSeriesData("code_001", VarGroupEnum.DIAN_YC, "i_a", LocalDate.parse("2013-4-23").toDate(), LocalDate.parse("2013-4-24").toDate());
        assert list != null;
        assert !list.isEmpty();

        System.out.println("###### getVarTimeSeriesDataTest end ######");
    }
}
