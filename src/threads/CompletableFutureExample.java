package threads;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("CompletableFuture runAsync " + Thread.currentThread().getName());

        });

        future.get();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("CompletableFuture supplyAsync " + Thread.currentThread().getName());
            return "Result of the asynchronous computation";
        });

        future1.get();
        future.get();
        //Executor executor = Executors.newFixedThreadPool(3);
        CompletableFuture<String> orderBookingFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("order booking " + Thread.currentThread().getName());

            return "1234 order booking";
        }).thenApply(details -> {

            System.out.println("order booking " + Thread.currentThread().getName());
            return details + " done";
        } );
        System.out.println("doing some other work");
        System.out.println( orderBookingFuture.get());

    }
}
