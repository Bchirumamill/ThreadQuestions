package threads;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Task task100ms = new Task();
        Task task200ms = new Task();
        task200ms.sleepMs = 2000;

        Task task300ms = new Task();
        task300ms.sleepMs = 3000;

        ExecutorService executorForRunnable = Executors.newFixedThreadPool(3);
       // ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    //    new LinkedBlockingQueue<>());

        executorForRunnable.execute(() -> { task100ms.runAndForget();});
        executorForRunnable.execute(() -> { task200ms.runAndForget();});
        executorForRunnable.execute(() -> { task300ms.runAndForget();});

        ExecutorService executorForCallable = Executors.newFixedThreadPool(3);


        Collection<Callable<String>> callables = new ArrayList<>();

        callables.add(() -> task100ms.runAndGetResult());
        callables.add(() -> task200ms.runAndGetResult());
        callables.add(() -> task300ms.runAndGetResult());

        List<Future<String>> futureList = executorForCallable.invokeAll(callables);

        System.out.println("before getting result");
        for (Future future: futureList) {
            System.out.println(future.get());
        }
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor();

        Future<String> resultFuture =
                executorService.schedule(() -> task300ms.runAndGetResult(), 1, TimeUnit.SECONDS);
        System.out.println(resultFuture.get());

        executorForRunnable.shutdown();
        executorForCallable.shutdown();
        executorService.shutdown();
    }
}

class Task{
    public long sleepMs = 100l;
    public void runAndForget()  {
        System.out.println("start runAndForget " + Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end runAndForget " + Thread.currentThread().getName());

    }

    public String runAndGetResult()  {
        System.out.println("start runAndGetResult " + Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end runAndGetResult " + Thread.currentThread().getName());
        return Thread.currentThread().getName()+" "+ Instant.now();
    }
}
