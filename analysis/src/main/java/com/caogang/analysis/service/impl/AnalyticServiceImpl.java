package com.caogang.analysis.service.impl;

import com.caogang.analysis.analytic.AnalyticParentTask;
import com.caogang.analysis.analytic.AnalyticTask;
import com.caogang.analysis.analytic.AnalyticTaskFactory;
import com.caogang.analysis.engine.Graph;
import com.caogang.analysis.engine.Scheduler;
import com.caogang.analysis.service.AnalyticService;
import com.caogang.analysis.util.CacheUtil;
import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import com.caogang.openservice.model.analytic.AnalyticParentTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
@Service
public class AnalyticServiceImpl implements AnalyticService {
    @Override
    public boolean cacheTable(Table table, String calculateId) {
        CacheUtil.putTable(calculateId, table);
        return true;
    }

    @Override
    public TaskResult<Table> analyze(AbstractTaskConfig config) {
        String calculateId = config.getCalculateId();
        String taskId = config.getTaskId();
        List<String> parentTaskIds = config.getParentTaskIds();
        boolean isSecondAnalysis = config.isSecondAnalysis();
        if (StringUtils.isAnyBlank(calculateId, taskId)){
            throw new IllegalArgumentException();
        }

        AnalyticTask<? extends AbstractTaskConfig> analyticTask = AnalyticTaskFactory.buildTask(config);

        Graph graph = getDefaultGraph(calculateId);
        graph.addTask(analyticTask);
        graph.addEdges(taskId, parentTaskIds);

        //do analyze
        TaskResult<Table> result = doAnalyze(analyticTask, graph, isSecondAnalysis);
        return result;
    }

    private static Graph getDefaultGraph(String calculateId){
        Graph graph = new Graph();
        graph.setCalculateId(calculateId);
        AnalyticParentTaskConfig config = AnalyticParentTaskConfig.of("parentId", calculateId);
        AnalyticParentTask analyticParentTask = new AnalyticParentTask(config);
        graph.addTask(analyticParentTask);
        return graph;
    }

    @Override
    public TaskResult<Table> analyze(List<AbstractTaskConfig> configs) {
        List<TaskResult<Table>> taskResults = new ArrayList<>();
        for (AbstractTaskConfig config : configs) {
            taskResults.add(analyze(config));
        }
        //table merge
        return null;
    }

    private TaskResult<Table> doAnalyze(AnalyticTask task, Graph graph, boolean isSecondAnalysis){
        Assert.notNull(task, "current task is null when do analyze");
        if (!isSecondAnalysis){
            //todo
        }
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(graph);
        return task.getTaskResult();
    }
}
