package com.insanity.rs2.engine;

import com.insanity.core.engine.TaskEngine;
import com.insanity.core.engine.task.Task;
import com.insanity.core.event.Event;
import com.insanity.core.event.Events;
import com.insanity.core.tickable.Tickable;
import com.insanity.core.tickable.Tickables;

public class GameEngine {

    private TaskEngine taskEngine = new TaskEngine();

    private Events events = new Events(taskEngine);

    private Tickables tickables = new Tickables();

    /**
     * Submits a new event.
     * @param event The event to submit.
     */
    public void submit(Event event) {
        this.events.submit(event);
    }

    /**
     * Submits a new tickable.
     * @param tickable The tickable to submit.
     */
    public void submit(final Tickable tickable) {
        submit(new Task() {
            @Override
            public void execute(TaskEngine context) {
                GameEngine.this.tickables.submit(tickable);
            }
        });
    }

    /**
     * Submits a new task.
     * @param task The task to submit.
     */
    public void submit(Task task) {
        this.taskEngine.pushTask(task);
    }

    public TaskEngine getTaskEngine() {
        return this.taskEngine;
    }
}
