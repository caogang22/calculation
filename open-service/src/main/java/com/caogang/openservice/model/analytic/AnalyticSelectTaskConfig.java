package com.caogang.openservice.model.analytic;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalyticSelectTaskConfig extends AbstractTaskConfig{

    private List<String> selectColumns;

    public static AnalyticSelectTaskConfig of(String taskId, String calculateId, List<String> cols){
        AnalyticSelectTaskConfig analyticSelectTaskConfig = new AnalyticSelectTaskConfig();
        analyticSelectTaskConfig.setCalculateId(calculateId);
        analyticSelectTaskConfig.setSelectColumns(cols);
        analyticSelectTaskConfig.setTaskId(taskId);
        return analyticSelectTaskConfig;
    }
}
