import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

public class WritingIntoCSV {
    public static void writing(HashMap<String, List<String>> dataIntoFiles) throws IOException {
        for (String key : dataIntoFiles.keySet()) {
            String newFileName = "src/main/resources/outputs/" + key;
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFileName + ".csv"), "windows-1251"));
            for (String value : dataIntoFiles.get(key)) {
                br.write(value + ";");
            }
            br.close();
        }
    }
}
