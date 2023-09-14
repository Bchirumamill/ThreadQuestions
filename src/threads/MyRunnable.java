package threads;

public class MyRunnable implements Runnable{


    @Override
    public void run() {
        for (int i=0; i < 10 ; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" "+i);

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThreads = new Thread(new MyRunnable(), "thread1");
        Thread myThreads2 = new Thread(new MyRunnable(), "thread2");
        Thread myThreads3 = new Thread(new MyRunnable(), "thread3");
    //    myThreads.join(50000);
        myThreads.start();
//      myThreads.join(500);
        myThreads2.start();
      //  System.out.println(Thread.currentThread().getName());
        myThreads.join(50000);
        myThreads3.start();


    }
}
