package com.Ben.framework.util;

import com.Ben.simpleandroidgdf.BuildConfig;

import java.util.ArrayList;

/**
 * Created by homedesk on 6/8/2015.
 */
public abstract class Task {

    public static final int TASK_NEW = 0;
    public static final int TASK_READY = 1;
    public static final int TASK_DONE = 2;

    private int _state;
    private ArrayList<Task> _children;
    //private TaskList _owner;

    public Task() {
        //_owner = owner;
        _children = new ArrayList<Task>();
        _state = TASK_NEW;
    }

    //Most usually called from within a specific task's initialization function.
    //The initialization function will not be made abstract as a part of the interface,
    //  because the signature should probably be left up to the specific task.
    public void makeRunnable()
    {
        if (!(_state == TASK_NEW)) { throw new AssertionError("Task was already made runnable."); }
        _state = TASK_READY;
    }

    //If the task has a child task, then that task will be promoted up.
    public void finishTask()
    {
        if (!(_state == TASK_READY)) { throw new AssertionError("Task was not running."); }
        for(Task child : _children) {
            TaskList.addTask(child);
            child.makeRunnable();
        }
        _state = TASK_DONE;
    }

    //TODO: This must be overridden. This is what makes a specific instance of a Task unique.
    public abstract void update(long deltaTime, Painter g);

    public void attachChild(Task child) { _children.add(child); }

    public void detachChild() {}

    public int getState() {
        return _state;
    }
}
