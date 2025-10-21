public class Synchronization {
    public static void main(String[] args) {
        Counter counter = new Counter();
        int threadCount = 20;
        int increments = 1000;
        int decrements = 200;

        Thread[] incrementThreads = new Thread[threadCount];
        Thread[] decrementThreads = new Thread[threadCount];

        try {
            for (int i = 0; i < threadCount; i++) {
                incrementThreads[i] = new IncrementThread(counter, increments);
                decrementThreads[i] = new DecrementThread(counter, decrements);
            }

            for (int i = 0; i < threadCount; i++) {
                incrementThreads[i].start();
                decrementThreads[i].start();
            }

            for (int i = 0; i < threadCount; i++) {
                incrementThreads[i].join();
                decrementThreads[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Expected Value: " + threadCount * (increments - decrements) + ", Final Value: " + counter.value());
    }
}
