package com.Ben.framework.util;

import java.util.ArrayList;

/**
 * Created by Klaue on 6/8/2015.
 */
public class TaskList {

    private static ArrayList<Task> _list;
    private static ArrayList<Task> _toAdd;
    private static ArrayList<Task> _toDelete;

    public TaskList() {
        _list = new ArrayList<Task>();
        _toAdd = new ArrayList<Task>();
        _toDelete = new ArrayList<Task>();
    }

    //Adds a previously created task to the tasklist.
    public static void addTask(Task t) {
        _toAdd.add(t);
    }

    public static void updateAll(long deltaTime, Painter g) {
        for (Task t : _list) {
            if (t.getState() == Task.TASK_READY) { t.update(deltaTime, g); }
            if (t.getState() == Task.TASK_DONE) { _toDelete.add(t); }
        }
        cleanup();
    }

    private static void cleanup() {
        for (Task t : _toDelete) { _list.remove(t); }
        for (Task t : _toAdd) { _list.add(t); }
        _toDelete.clear();
        _toAdd.clear();
    }

}
