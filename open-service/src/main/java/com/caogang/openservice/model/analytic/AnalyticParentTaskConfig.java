package com.caogang.openservice.model.analytic;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public class AnalyticParentTaskConfig extends AbstractTaskConfig{

    public static AnalyticParentTaskConfig of(String taskId, String calculateId){
        AnalyticParentTaskConfig analyticParentTaskConfig = new AnalyticParentTaskConfig();
        analyticParentTaskConfig.setCalculateId(calculateId);
        analyticParentTaskConfig.setTaskId(taskId);
        return analyticParentTaskConfig;
    }
}
