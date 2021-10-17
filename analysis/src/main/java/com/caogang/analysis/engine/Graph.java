package com.caogang.analysis.engine;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@Data
public class Graph {

    private Set<Task<?>> tasks;

    private Map<Task<?>, Set<Task<?>>>  map;

    private Map<String, Set<String>> edgeMap;

    private String calculateId;

    public Graph(){
        tasks = new HashSet<>();
        map = new HashMap<>();
        edgeMap = new HashMap<>();
    }

    public void addEdges(String taskId, List<String> previousTaskIds){
        if (CollectionUtils.isEmpty(previousTaskIds)){
            throw new IllegalArgumentException();
        }

        Task<?> current = tasks.stream().filter(task -> task.taskId.equals(taskId)).
                findFirst().orElse(null);
        List<Task<?>> previousTasks = tasks.stream().filter(task -> previousTaskIds.contains(task.taskId))
                .collect(Collectors.toList());

        if (current == null || CollectionUtils.isEmpty(previousTasks)){
            throw new IllegalArgumentException();
        }

        previousTasks.forEach(task -> addEdges(current, task));
    }

    private void addEdges(Task<?> current, Task<?> previous){
        if (!tasks.contains(current) || !tasks.contains(previous)){
            throw new IllegalArgumentException();
        }

        checkCirculatingDependency(current, previous);

        Set<Task<?>> previousTasks = map.computeIfAbsent(current, v -> new HashSet<>());
        if (previousTasks.contains(previous)){
            throw new IllegalArgumentException();
        }
        previousTasks.add(previous);
    }

    public void checkCirculatingDependency(Task<?> current, Task<?> previous){
        if (Objects.equals(current, previous)){
            throw  new IllegalArgumentException();
        }

        Set<Task<?>> parentTasks = getParentTasks(current);

        if (CollectionUtils.isEmpty(parentTasks)){
            return;
        }

        if (parentTasks.contains(current)){
            throw new IllegalArgumentException();
        }

    }

    public Set<Task<?>> getParentTasks(Task<?> task){
        Set<Task<?>> preTasks = new HashSet<>();
        getPreTasks(task, preTasks);
        return preTasks;
    }

    private void getPreTasks(Task<?> task, Set<Task<?>> preTasks){
        Set<Task<?>> parentTasks = map.get(task);
        if (CollectionUtils.isEmpty(parentTasks)){
            return;
        }
        preTasks.addAll(parentTasks);
        parentTasks.forEach(parentTask -> getPreTasks(parentTask, preTasks));
    }

    public Task<?> queryTask(String taskId){
        return tasks.stream().filter(task -> Objects.equals(task.taskId, taskId)).
                findFirst().orElse(null);
    }

    public Set<Task<?>> queryTasks(List<String> taskIds){
        return tasks.stream().filter(task -> taskIds.contains(task.taskId)).
                collect(Collectors.toSet());
    }

    public void addTask(Task<?> task){
        if (tasks.contains(task)){
            throw new IllegalArgumentException();
        }
        tasks.add(task);
    }
}
