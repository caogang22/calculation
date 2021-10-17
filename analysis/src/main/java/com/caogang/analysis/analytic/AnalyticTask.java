package com.caogang.analysis.analytic;

import com.caogang.analysis.engine.Task;
import com.caogang.analysis.util.CacheUtil;
import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import lombok.Data;
import tech.tablesaw.api.Table;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
@Data
public abstract class AnalyticTask<T extends AbstractTaskConfig> extends Task<Table> {


    protected T config;

    private boolean needProcess = true;

    public AnalyticTask(T config){
        super(config.getTaskId());
        this.config = config;
    }

    @Override
    public TaskResult<Table> process(List<TaskResult<?>> previousResults) {
        if (!needProcess){
            return null;
        }

        TaskResult<Table> result = CacheUtil.getResult(taskId + "_" + config.getCalculateId());
        if (result != null){
            return result;
        }

        List<TaskResult<Table>> inputTables = previousResults.stream().map(TaskResult::forceCastTable).
                collect(Collectors.toList());

        TaskResult<Table> taskResult = analyze(inputTables);
        CacheUtil.putResult(taskId + "_" + config.getCalculateId(), taskResult);
        return taskResult;
    }

    public abstract TaskResult<Table> analyze(List<TaskResult<Table>> inputTables);

    public void setNeedProcess(){
        needProcess = true;
    }

    public void setNotNeedProcess(){
        needProcess = false;
    }
}
