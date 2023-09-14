package threads;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private List<Object> connections = new ArrayList<>();

    private int count;

    public ConnectionPool(int connectionSize) {
        for (int i=0 ; i < connectionSize ; i++){
            connections.add(new Object());
        }
    }

    public Object getConnection() {

        try {
            synchronized (connections) {
                while (connections.isEmpty()) {
                    connections.wait();
                   // System.out.println("waiting for connection " + Thread.currentThread().getName());
                }
                System.out.println("start getConnection " + Thread.currentThread().getName());
                return connections.remove(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void release(Object connection){
       // System.out.println("start release connection " + Thread.currentThread().getName());
        synchronized (connections){
            connections.add(connection);
            System.out.println("connection released " + Thread.currentThread().getName());
            connections.notifyAll();
        }
    }
}
 class DaoTask implements Runnable {
    private ConnectionPool connectionPool;

    public DaoTask(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    @Override
    public void run() {
      //  System.out.println("current Thread Entered: " + Thread.currentThread().getName());
        Object con = connectionPool.getConnection();
     //   System.out.println("connection acquired: " + Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectionPool.release(con);
    }

    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool(5);
        DaoTask daoTask = new DaoTask(connectionPool);

        for (int i=0; i < 10 ; i++){
            new Thread(daoTask, "thread-"+i).start();
        }
    }
}
