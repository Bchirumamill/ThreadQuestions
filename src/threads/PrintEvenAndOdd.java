package threads;

public class PrintEvenAndOdd {

    private int num;
    private int max;

    public PrintEvenAndOdd(int max) {
        this.max = max;
    }

    public void printOdd(){
        synchronized (this){
            while (num < max){
                while (num % 2 == 0){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(num);
                num ++;
                notify();
            }
        }
    }

    public void printEven(){
        synchronized (this){
            while (num < max){
                while (num % 2 == 1){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(num);
                num ++;
                notify();
            }
        }
    }

    public static void main(String[] args) {
        PrintEvenAndOdd printEvenAndOdd = new PrintEvenAndOdd(10);
        Thread odd = new Thread(() -> printEvenAndOdd.printOdd());
        Thread even = new Thread(() -> printEvenAndOdd.printEven());
        odd.start();
        even.start();
    }
}
