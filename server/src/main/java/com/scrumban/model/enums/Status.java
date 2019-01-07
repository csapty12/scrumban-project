package com.scrumban.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    BACKLOG("Backlog"),
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    IN_TEST("Testing"),
    DONE("Done"),
    BLOCKED("Blocked");

    private String statusValue;

     Status(String statusValue) {
        this.statusValue = statusValue;
    }

    private static final Map<String, Status> lookup = new HashMap<>();
    static {
        for (Status d : Status.values()) {
            lookup.put(d.getStatusValue(), d);
        }
    }

    public String getStatusValue() {
        return statusValue;
    }

    public static Status get(String statusValue) {
        return lookup.get(statusValue);
    }
}
