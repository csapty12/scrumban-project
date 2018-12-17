package com.scrumban.model;

public enum Status {

    BACKLOG("Backlog"),
    TO_DO("To Do"),
    IN_PROGRESS("In Progress"),
    IN_TEST("Testing"),
    DONE("Done");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String valueOf() {
        return this.status;
    }
}
