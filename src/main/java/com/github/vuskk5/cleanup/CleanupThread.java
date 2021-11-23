package com.github.vuskk5.cleanup;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@Accessors(fluent = true)
public class CleanupThread extends Thread {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Queue<Stack<Runnable>> tasks = new ConcurrentLinkedQueue<>();

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Queue<Stack<Runnable>> queuedTasks = new ConcurrentLinkedQueue<>();

    /**
     * This flag determines whether or not the {@link CleanupService} will accept new tasks.
     */
    private boolean hadTestsFinished = false;

    CleanupThread() {
        super("Cleanup");
        start();
        log.info("Thread started");
    }

    @Override
    @SneakyThrows
    public void run() {
        while (!hadTestsFinished || !tasks.isEmpty()) {
            if (!tasks.isEmpty()) {
                Stack<Runnable> currentStack = tasks.poll();

                if (currentStack != null) {
                    while (!currentStack.isEmpty()) {
                        try {
                            currentStack.pop().run();
                        } catch (Exception ex) {
                            log.error("An error had occurred, stack trace follows", ex);
                        }
                    }
                }
            }

            if (!queuedTasks.isEmpty()) {
                while (!queuedTasks.isEmpty()) {
                    tasks.offer(queuedTasks.poll());
                }
            }
        }
    }

    @Synchronized
    public void queueTasks(Stack<Runnable> taskStack) {
        queuedTasks.offer(taskStack);
    }
}

