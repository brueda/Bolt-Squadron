package com.Ben.framework.util;

import com.Ben.simpleandroidgdf.BuildConfig;

/**
 * Created by homedesk on 6/8/2015.
 */
public abstract class Task {

    public static final int TASK_NEW = 0;
    public static final int TASK_READY = 1;
    public static final int TASK_DONE = 2;

    private int _state;
    private Task _child;
    //private TaskList _owner;

    public Task() {
        //_owner = owner;
        _state = TASK_NEW;
        _child = null;
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
        if(_child != null) {
            TaskList.addTask(_child);
            _child.makeRunnable();
        }
        _state = TASK_DONE;
    }

    //TODO: This must be overridden. This is what makes a specific instance of a Task unique.
    public abstract void update(long deltaTime, Painter g);

    public void attachChild(Task child) { _child = child; }

    public void detachChild() { _child = null; }

    public int getState() {
        return _state;
    }
}
