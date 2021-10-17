package com.caogang.openservice.model.analytic;

import lombok.Data;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@Data
public abstract class AbstractTaskConfig {

    private String calculateId;

    private String taskId;

    private List<String> parentTaskIds;

    private boolean isSecondAnalysis = true;
}
