package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MyThreadPool {
    private final BlockingDeque<Runnable> taskQueue;
    private final List<Thread> workers;

    public MyThreadPool(int threadSize) {
        this.taskQueue = new LinkedBlockingDeque<>();
        this.workers = new ArrayList<>(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread t = new Thread(new Worker(taskQueue),"worker-" + i);

            t.start();
        }
    }

    public void addTask(Runnable task) throws InterruptedException {
        taskQueue.put(task);
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(4);
        for (int i = 0; i < 20; i++) {

            // Creating 20 tasks and passing them to execute
            myThreadPool.addTask(new MyTask());
        }
    }
}

class Worker implements Runnable {
    private final BlockingDeque<Runnable> taskQueue;

    public Worker(BlockingDeque<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                taskQueue.take().run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyTask implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(
                "Current Thread :-> "
                        + Thread.currentThread().getName());
    }
}
