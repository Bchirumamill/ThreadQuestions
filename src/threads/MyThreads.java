package threads;

public class MyThreads extends Thread{
    @Override
    public void run() {
      for (int i=0; i < 10 ; i++){
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          System.out.println(i);

      }
    }

    public static void main(String[] args) {
        MyThreads myThreads = new MyThreads();
        MyThreads myThreads2 = new MyThreads();
        myThreads.start();
        myThreads2.start();
    }
}
