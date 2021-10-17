package com.caogang.analysis.analytic.test;

import com.caogang.analysis.analytic.AnalyticTask;
import com.caogang.openservice.model.analytic.AnalyticSelectTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public class AnalyticSelectTask extends AnalyticTask<AnalyticSelectTaskConfig> {

    public AnalyticSelectTask(AnalyticSelectTaskConfig config){
        super(config);
    }
    public AnalyticSelectTask(String taskId, String calculateId, List<String> cols){
        super(AnalyticSelectTaskConfig.of(taskId, calculateId, cols));
    }
    @Override
    public TaskResult<Table> analyze(List<TaskResult<Table>> inputTables) {
        Table table = inputTables.get(0).getResult();
        table = table.select(config.getSelectColumns().toArray(new String[0]));
        return new TaskResult<>(taskId, table, new ArrayList<>(), true);
    }

    @Override
    public void setTaskName() {
        this.taskName = "selectTask";
    }
}
