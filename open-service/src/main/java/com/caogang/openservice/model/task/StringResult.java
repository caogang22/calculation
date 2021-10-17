package com.caogang.openservice.model.task;

import com.caogang.openservice.util.TableConvertUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import tech.tablesaw.api.Table;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
@Data
public class StringResult {

    private String taskId;

    private String result;

    private List<String> summaryResult;

    private boolean success = false;
}
