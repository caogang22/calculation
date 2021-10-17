package com.caogang.analysis.analytic;

import com.caogang.openservice.model.analytic.AbstractTaskConfig;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import sun.reflect.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public class AnalyticTaskFactory {

    private static Set<Class<? extends AnalyticTask>> taskTypes = new HashSet();
    private static Set<Class<? extends AbstractTaskConfig>> taskConfigTypes = new HashSet();
    private static Map<String, Class<? extends AnalyticTask>> taskMap = new HashMap<>();
    private static Map<String, Class<? extends AbstractTaskConfig>> taskConfigMap = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.caogang.analysis.analytic");
        taskTypes = reflections.getSubTypesOf(AnalyticTask.class);
        for (Class<? extends AnalyticTask> taskType : taskTypes) {
            taskMap.put(taskType.getSimpleName(), taskType);
        }
        reflections = new Reflections("com.caogang.openservice.model.analytic");
        taskConfigTypes = reflections.getSubTypesOf(AbstractTaskConfig.class);
        for (Class<? extends AbstractTaskConfig> taskConfigType : taskConfigTypes) {
            taskConfigMap.put(taskConfigType.getSimpleName(), taskConfigType);
        }
    }

    public static AnalyticTask<? extends AbstractTaskConfig> buildTask(AbstractTaskConfig config){

        AnalyticTask<? extends AbstractTaskConfig> analyticTask = null;

        for (Class<? extends AnalyticTask> taskType : taskTypes) {
            if (StringUtils.equals(taskType.getSimpleName() + "Config",
                    config.getClass().getSimpleName())){
                try {
                    analyticTask = taskType.getDeclaredConstructor(config.getClass()).newInstance(config);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return analyticTask;
    }
}
