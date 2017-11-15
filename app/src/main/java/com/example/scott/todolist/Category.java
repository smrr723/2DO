package com.example.scott.todolist;

/**
 * Created by scott on 12/11/2017.
 */

public class Category {

    private Integer id;
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equalsIgnoreCase(getName());
    }
}
