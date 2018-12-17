package com.scrumban.model;

public enum Priority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    public String valueOf() {
        return this.priority;
    }
}
