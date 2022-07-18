package ua.garmash.module4.model;

public enum DetailStatus {
    FINISHED("finished"),
    UNFINISHED("unfinished");

    private final String name;

    DetailStatus(String name) {
        this.name = name;
    }
}
