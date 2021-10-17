package com.caogang.analysis.util;

import com.caogang.openservice.model.task.TaskResult;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import tech.tablesaw.api.Table;

/**
 * @author: Administrator
 * @date: 2021/10/17
 * @description:
 */
public class CacheUtil {

    private static Cache<String, TaskResult<Table>> resultCache = CacheBuilder.newBuilder()
            .build();

    private static Cache<String, Table> tableCache = CacheBuilder.newBuilder()
            .build();

    public static TaskResult<Table> getResult(String key){
        return resultCache.getIfPresent(key);
    }

    public static void putResult(String key, TaskResult<Table> taskResult){
        resultCache.put(key, taskResult);
    }

    public static Table getTable(String key){
        return tableCache.getIfPresent(key);
    }

    public static void putTable(String key, Table table){
        tableCache.put(key, table);
    }
}
