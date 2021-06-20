import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String args[]) throws InterruptedException, IOException {
        HashMap<String, List<String>> dataFromFiles = new HashMap<>();

        for (String fileName : args[0].split(";")) {
            Thread thread = new Thread(new ReadingDataFromCSV(fileName, dataFromFiles));
            thread.start();
            thread.join();
        }
        WritingIntoCSV.writing(dataFromFiles);
    }
}