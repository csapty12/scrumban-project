package com.scrumban.model;

public enum Status {

    BACKLOG("BACKLOG"),
    TO_DO("To Do"),
    IN_PROGRESS("IN PROGRESS"),
    IN_TEST("TESTING"),
    DONE("DONE");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String valueOf() {
        return this.status;
    }
}
