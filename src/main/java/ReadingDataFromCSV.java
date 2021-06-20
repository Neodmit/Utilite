import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ReadingDataFromCSV implements Runnable {

    private static final Object Lock1 = new Object();

    private HashMap<String, List<String>> dataNewFiles;
    private final String fileName;

    ReadingDataFromCSV(String fileName, HashMap<String, List<String>> dataNewFiles) {
        this.fileName = fileName;
        this.dataNewFiles = dataNewFiles;
    }

    public void run() {
        try {
            synchronized (Lock1) {
                List<String> lines = Files.readAllLines(new File(fileName).toPath(), Charset.forName("Windows-1251"));
                String[] heads = lines.get(0).split(";");
                lines.remove(lines.get(0));
                for (int i = 0; i < heads.length; i++) {
                    List<String> values = new ArrayList<>();

                    for (String line : lines) {
                        String value = line.split(";")[i];
                        if (!values.contains(value)) values.add(value);
                    }
                    dataNewFiles.put(heads[i], values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}