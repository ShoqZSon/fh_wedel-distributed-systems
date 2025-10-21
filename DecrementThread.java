public class DecrementThread extends Thread {
    private Counter counter;
    private int times;

    public DecrementThread(Counter counter, int times) {
        this.counter = counter;
        this.times = times;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            counter.decrement();
        }
    }
}