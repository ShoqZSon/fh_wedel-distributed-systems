public class Simple {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Print_Word("Hallo"));
        Thread thread2 = new Thread(new Print_Word("Welt"));

        thread1.start();
        thread2.start();
    }
}