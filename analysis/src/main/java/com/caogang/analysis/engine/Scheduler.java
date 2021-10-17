package com.caogang.analysis.engine;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
public class Scheduler {

    public void schedule(Graph graph){
        while (true){
            List<Task<?>> todo = new ArrayList<>();
            for (Task<?> task : graph.getTasks()) {
                if (task.hasNotExecuted()){
                    Set<Task<?>> previous = graph.getMap().get(task);
                    if (!CollectionUtils.isEmpty(previous)){
                        boolean toAdd = true;
                        for (Task<?> task1 : previous) {
                            if (task1.hasNotExecuted()){
                                toAdd = false;
                                break;
                            }
                        }
                        if (toAdd){
                            todo.add(task);
                        }
                    }else {
                        todo.add(task);
                    }
                }
            }
            if (!todo.isEmpty()){
                for (Task<?> task : todo) {
                    Set<Task<?>> previous = graph.getMap().get(task);
                    if (!task.execute(previous)){
                        throw new RuntimeException();
                    }
                }
            }else {
                break;
            }
        }
    }
}
