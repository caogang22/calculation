package com.caogang.analysis.controller;

import com.caogang.analysis.service.AnalyticService;
import com.caogang.openservice.model.analytic.AnalyticSelectTaskConfig;
import com.caogang.openservice.model.task.TaskResult;
import com.caogang.openservice.util.TableConvertUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.tablesaw.api.Table;

import java.io.IOException;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private AnalyticService analyticService;

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "test") String test){
        return "add" + test;
    }

    @RequestMapping("/select")
    public String select(){
        Table select = null;
        try {
            Table table = Table.read().csv(getClass().getClassLoader().getResource("test.csv"));
            boolean success = analyticService.cacheTable(table, "selectTest");
            if (!success){
                System.out.println("cache table fail");
                throw new RuntimeException("cache table fail");
            }
            AnalyticSelectTaskConfig selectConfig = AnalyticSelectTaskConfig.of("selectId",
                    "selectTest", Lists.newArrayList("日期","城市","销售额"));
            selectConfig.setParentTaskIds(Lists.newArrayList("parentId"));
            TaskResult<Table> analyze = analyticService.analyze(selectConfig);
            if (analyze.isSuccess()){
                select = analyze.getResult();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(select.print());
        return TableConvertUtil.tableCvt2String(select);
    }
}
