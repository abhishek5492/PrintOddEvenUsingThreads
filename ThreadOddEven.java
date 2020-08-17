public class ThreadOddEven {

    public static void main(String[] args) {
        PrintOddEvenNumber print = new PrintOddEvenNumber();
        Thread odd = new Thread(new OddEven(print,1));
        Thread even = new Thread(new OddEven(print,2));
        odd.start();
        even.start();
    }
}

class OddEven implements Runnable{
    int startpoint;
    PrintOddEvenNumber ptr;
    public OddEven(PrintOddEvenNumber ptr, int startpoint){
        this.startpoint = startpoint;
        this.ptr= ptr;
    }
    @Override
    public void run() {
        while(startpoint <= 20){
            if (startpoint % 2 == 0){
                try {
                    ptr.PrintEven(startpoint);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    ptr.PrintOdd(startpoint);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startpoint = startpoint+2;
        }
    }
}

class PrintOddEvenNumber{
    public volatile boolean oddChance = true;

    public synchronized void PrintOdd(int num) throws InterruptedException {
        if(!oddChance){
            wait();
        }
        System.out.println("Odd Thread :"+ num);
        oddChance = false;
        notifyAll();
    }
    public synchronized void PrintEven(int num) throws InterruptedException {
        if(oddChance){
            wait();
        }
        System.out.println("Even Thread :"+num);
        oddChance = true;
        notifyAll();
    }
}
