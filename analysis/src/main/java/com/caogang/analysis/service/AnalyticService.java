package com.caogang.analysis.service;

import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import tech.tablesaw.api.Table;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public interface AnalyticService {

    boolean cacheTable(Table table, String calculateId);

    TaskResult<Table> analyze(AbstractTaskConfig config);

    TaskResult<Table> analyze(List<AbstractTaskConfig> configs);
}
