package com.ices.simulation.controller.util;

import lombok.Data;
import org.springframework.stereotype.Component;

//currentTask和insideTask的时候用到
public class curTaskId {
    private static String taskId;

    public static String getTaskId() {
        return taskId;
    }

    public static void setTaskId(String taskId) {
        curTaskId.taskId = taskId;
    }
}
