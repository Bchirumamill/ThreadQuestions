package threads;

import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedExample {
    private AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        SynchronizedExample methodSynchronized = new SynchronizedExample();
        SynchronizedExample methodSynchronized1 = new SynchronizedExample();
        Thread myThreads = new Thread(new ReserveSeat(methodSynchronized, 10), "thread1");
        Thread myThreads2 = new Thread(new CancelSeat(methodSynchronized, 10), "thread2");

        Thread myThreads3 = new Thread(new ReserveSeat(methodSynchronized, 20), "thread3");
        myThreads.start();
        myThreads2.start();
        myThreads3.start();
        for (int i = 0; i < 11; i++) {
            Thread.sleep(100);
            System.out.println("total number of seats reserved: " + methodSynchronized.getCount());

        }

    }

    public void  book(int n) throws InterruptedException {
        System.out.println("book start " + Thread.currentThread().getName());
        for (int i = 0; i < n; i++) {
            Thread.sleep(10);
            count.incrementAndGet();
        }
        System.out.println("book end " + Thread.currentThread().getName());
    }

    public void cancel(int n) throws InterruptedException {
        System.out.println("Cancel start " + Thread.currentThread().getName());
//        synchronized (this) {
            for (int i = 0; i < n; i++) {
                Thread.sleep(10);
                count.decrementAndGet();
            }
//        }
        System.out.println("Cancel end " + Thread.currentThread().getName());
    }

    public int getCount() {
        return count.get();
    }

  /*  public void setCount(int count) {
        this.count = count;
    }*/
}

class CancelSeat implements Runnable {

    private final SynchronizedExample methodSynchronized;
    private final int n;

    public CancelSeat(SynchronizedExample methodSynchronized, int n) {
        this.methodSynchronized = methodSynchronized;
        this.n = n;
    }

    @Override
    public void run() {

        try {
            methodSynchronized.cancel(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}

class ReserveSeat implements Runnable {

    private final SynchronizedExample methodSynchronized;
    private final int n;

    public ReserveSeat(SynchronizedExample methodSynchronized, int n) {
        this.methodSynchronized = methodSynchronized;
        this.n = n;
    }

    @Override
    public void run() {
        try {
            methodSynchronized.book(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
