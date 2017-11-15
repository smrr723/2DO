package com.example.scott.todolist;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by scott on 14/11/2017.
 */

public class TaskTest {
    @Test
    public void testTaskCreation() {
        Category category = new Category(1, "Sample category");
        Task task = new Task(1, "task 1", category, "Description", 1, "Low", "23/12/2017");
        Assert.assertEquals(task.getCategory(), category);
        Assert.assertTrue(task.getCompleteStatus() == 1);
        task.setCompleteStatus(0);
        Assert.assertTrue(task.getCompleteStatus() == 0);

    }
}
