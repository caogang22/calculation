package com.caogang.analysis.analytic;

import com.caogang.analysis.util.CacheUtil;
import com.caogang.openservice.model.analytic.AnalyticParentTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public class AnalyticParentTask extends AnalyticTask<AnalyticParentTaskConfig> {

    public AnalyticParentTask(AnalyticParentTaskConfig config){
        super(config);
    }
    @Override
    public TaskResult<Table> analyze(List<TaskResult<Table>> inputTables) {
        return TaskResult.of(config.getTaskId(), CacheUtil.getTable(config.getCalculateId()),
                new ArrayList<>(), true);
    }

    @Override
    public void setTaskName() {
        this.taskName = "analyticParentTask";
    }
}
