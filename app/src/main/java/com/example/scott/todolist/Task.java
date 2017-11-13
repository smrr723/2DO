package com.example.scott.todolist;

/**
 * Created by scott on 12/11/2017.
 */

public class Task {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private boolean completeStatus;

    public Task(Integer id, String name, String category, String description, boolean completeStatus) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.completeStatus = completeStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleteStatus() {
        return completeStatus;
    }
}

