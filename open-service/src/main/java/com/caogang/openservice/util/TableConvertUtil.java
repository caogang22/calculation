package com.caogang.openservice.util;

import com.caogang.openservice.model.task.StringResult;
import com.caogang.openservice.model.task.TaskResult;
import org.springframework.util.CollectionUtils;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.WriteOptions;
import tech.tablesaw.io.json.JsonWriteOptions;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Collectors;

/**
 * @author: Administrator
 * @date: 2021/10/16
 * @description:
 */
public class TableConvertUtil {

    public static String tableCvt2String(Table table){
        for (int i = 0; i < table.columnCount(); i++) {
            Column<?> column = table.column(i);
            if (column instanceof DateColumn){
                DateColumn dateColumn = (DateColumn) column;
                StringColumn replaceColumn = StringColumn.create(dateColumn.name());
                for (int j = 0; j < dateColumn.size(); j++) {
                    replaceColumn.appendObj(dateColumn.getString(j));
                }
                table.replaceColumn(replaceColumn);
            }
        }
        StringWriter writer = new StringWriter();
        try {
            table.write().usingOptions(JsonWriteOptions.builder(writer).asObjects(false).header(true).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static Table stringCvtTable(String table){
        return Table.read().string(table, "json");
    }

    public static StringResult TaskResultCvt2StringResult(TaskResult<Table> taskResult){
        StringResult result = new StringResult();
        result.setTaskId(taskResult.getTaskId());
        result.setResult(tableCvt2String(taskResult.getResult()));
        if (!CollectionUtils.isEmpty(taskResult.getSummaryResult())){
            result.setSummaryResult(taskResult.getSummaryResult().
                    stream().map(TableConvertUtil::tableCvt2String).
                    collect(Collectors.toList()));
        }
        return result;
    }

    public static TaskResult<Table> stringResultCvt2TaskResult(StringResult stringResult){
        TaskResult<Table> result = new TaskResult<>();
        result.setTaskId(stringResult.getTaskId());
        result.setResult(stringCvtTable(stringResult.getResult()));
        if (!CollectionUtils.isEmpty(stringResult.getSummaryResult())){
            result.setSummaryResult(stringResult.getSummaryResult().
                    stream().map(TableConvertUtil::stringCvtTable).
                    collect(Collectors.toList()));
        }
        return result;
    }

}
