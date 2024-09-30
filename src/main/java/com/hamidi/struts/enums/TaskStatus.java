package com.hamidi.struts.enums;

import com.hamidi.struts.exception.ExceptionError;

public enum TaskStatus {
    PENDING,
    COMPLETED;

    public static TaskStatus fromString(String status){
        for (TaskStatus ts: TaskStatus.values()){
            if (ts.name().equalsIgnoreCase(status)){
                return ts;
            }
        }
        throw new ExceptionError("No enum constant for value: " + status);
    }
}
