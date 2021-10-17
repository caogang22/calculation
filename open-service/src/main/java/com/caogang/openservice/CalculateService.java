package com.caogang.openservice;

import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import com.caogang.openservice.model.task.StringResult;

import java.util.List;

public interface CalculateService {


    boolean cacheTable(String calculateId, String table);

    StringResult calculate(AbstractTaskConfig taskConfig);

    StringResult calculate(List<AbstractTaskConfig> taskConfigs);
}
