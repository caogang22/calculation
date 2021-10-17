package com.caogang.analysis.engine;

import com.caogang.openservice.model.task.TaskResult;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@Data
public abstract class Task<OUT> implements Executor {

    protected String taskId;

    protected String taskName;

    private int status = 0;

    private TaskResult<OUT> taskResult;

    public Task() {

    }

    public Task(String taskId) {
        this.taskId = taskId;
        this.taskName = getTaskName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task<?> task = (Task<?>) o;
        return taskId.equals(task.taskId) &&
                taskName.equals(task.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName);
    }

    public abstract void setTaskName();

    public abstract TaskResult<OUT> process(List<TaskResult<?>> previousResults);

    @Override
    public boolean execute(Set<Task<?>> previousTask) {
        List<TaskResult<?>> taskResults = new ArrayList<>();
        if (!CollectionUtils.isEmpty(previousTask)){
            taskResults = previousTask.stream().map(task -> ((TaskResult<?>)task.taskResult))
                    .collect(Collectors.toList());
        }
        taskResult = process(taskResults);
        status = 1;
        return true;
    }

    public boolean hasNotExecuted(){
        return status != 1;
    }

    public TaskResult<OUT> getTaskResult(){
        if (hasNotExecuted()){
            return null;
        }
        return taskResult;
    }
}
