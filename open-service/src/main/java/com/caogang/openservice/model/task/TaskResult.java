package com.caogang.openservice.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.tablesaw.api.Table;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResult<T> implements Serializable {

    private String taskId;

    private T result;

    private List<T> summaryResult;

    private boolean success = false;
    public static TaskResult<Table> of(String taskId, Table table, List<Table> summaryResult, boolean success){
        return new TaskResult<Table>(taskId, table, summaryResult, success);
    }

    public static TaskResult<Table> emptyOf(){
        return new TaskResult<Table>();
    }

    public static TaskResult<Table> forceCastTable(TaskResult<?> taskResult){
        TaskResult<Table> result = new TaskResult<>();
        result.setResult((Table) taskResult.getResult());
        result.setTaskId(taskResult.getTaskId());
        result.setSummaryResult((List<Table>) taskResult.getSummaryResult());
        result.setSuccess(taskResult.isSuccess());
        return result;
    }
}
