// Die Klasse, die ein Wort speichert und Runnable ist
public class Print_Word implements Runnable {
    private String word;

    public Print_Word(String word) {
        this.word = word;
    }

    // Konstruktor zum Übergeben des Wortes
    public void PrintWord(String word) {
        this.word = word;
    }

    @Override
    public void run() {
        System.out.println(word);
    }
}
