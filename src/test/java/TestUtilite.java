import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtilite {
    String input = "src/main/resources/inputs/input1.csv;src/main/resources/inputs/input2.csv";
    HashMap<String, List<String>> dataFromFiles = new HashMap<>();

    @Test
    public void testGetDataFromCSV() throws InterruptedException {
        for (String fileName : input.split(";")) {
            Thread t = new Thread(new ReadingDataFromCSV(fileName, dataFromFiles));
            t.start();
            t.join();
        }
        List<String> id = Arrays.asList("0", "1", "2", "3");
        List<String> name = Arrays.asList("ричард", "жорж", "мария", "пьер");
        List<String> path = Arrays.asList("/hello/уточка", "/hello/лошадка", "/hello/собачка");
        List<String> sex = Arrays.asList("м", "ж");
        List<String> version = Arrays.asList("1", "2");

        assertTrue(dataFromFiles.containsKey("id"));
        assertTrue(dataFromFiles.containsKey("name"));
        assertTrue(dataFromFiles.containsKey("path"));
        assertTrue(dataFromFiles.containsKey("sex"));
        assertTrue(dataFromFiles.containsKey("version"));

        assertTrue(dataFromFiles.containsValue(id));
        assertTrue(dataFromFiles.containsValue(name));
        assertTrue(dataFromFiles.containsValue(path));
        assertTrue(dataFromFiles.containsValue(sex));
        assertTrue(dataFromFiles.containsValue(version));
    }

    @Test
    public void testCheckNameFromNewCSV() throws InterruptedException, IOException {
        for (String fileName : input.split(";")) {
            Thread t = new Thread(new ReadingDataFromCSV(fileName, dataFromFiles));
            t.start();
            t.join();
        }
        WritingIntoCSV.writing(dataFromFiles);

        File dir = new File("src/main/resources/outputs/");
        File[] arrFiles = dir.listFiles();

        for (File file : arrFiles) {
            assertTrue(dataFromFiles.containsKey(file.getName().replace(".csv", "")));
        }
    }

    @Test
    public void testCheckDataFromNewCSV() throws InterruptedException, IOException {
        for (String fileName : input.split(";")) {
            Thread t = new Thread(new ReadingDataFromCSV(fileName, dataFromFiles));
            t.start();
            t.join();
        }
        WritingIntoCSV.writing(dataFromFiles);

        File dir = new File("src/main/resources/outputs/");
        File[] arrFiles = dir.listFiles();

        for (File file : arrFiles) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1251"));
            List listDataFromNew = new ArrayList();
            for (String str : br.readLine().split(";")) {
                listDataFromNew.add(str);
            }
            assertTrue(dataFromFiles.containsValue(listDataFromNew));
        }
    }

    @Test
    public void testGetExceptionFile1() {
        Thread t = new Thread(new ReadingDataFromCSV(null, dataFromFiles));
        assertEquals(new HashMap<>(), dataFromFiles);
    }

    @Test
    public void testGetExceptionFile2() {
        Thread t = new Thread(new ReadingDataFromCSV(input, null));
        assertEquals(new HashMap<>(), dataFromFiles);
    }

    @Test
    public void testGetExceptionFile3() {
        Thread t = new Thread(new ReadingDataFromCSV("", dataFromFiles));
        assertEquals(new HashMap<>(), dataFromFiles);
    }
}
