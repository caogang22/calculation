package com.caogang.analysis.service.impl;

import com.caogang.analysis.service.AnalyticService;
import com.caogang.openservice.CalculateService;
import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import com.caogang.openservice.model.task.StringResult;
import com.caogang.openservice.model.task.TaskResult;
import com.caogang.openservice.util.TableConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Autowired
    private AnalyticService analyticService;

    @Override
    public boolean cacheTable(String calculateId, String table) {
        return analyticService.cacheTable(TableConvertUtil.stringCvtTable(table), calculateId);
    }

    @Override
    public StringResult calculate(AbstractTaskConfig taskConfig) {
        TaskResult<Table> analyze = analyticService.analyze(taskConfig);
        return TableConvertUtil.TaskResultCvt2StringResult(analyze);
    }

    @Override
    public StringResult calculate(List<AbstractTaskConfig> taskConfigs) {
        TaskResult<Table> analyze = analyticService.analyze(taskConfigs);
        return TableConvertUtil.TaskResultCvt2StringResult(analyze);
    }
}
