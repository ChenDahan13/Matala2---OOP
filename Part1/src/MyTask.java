import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;


public class MyTask implements Callable<Integer> {
    String nameOfFile;
    // Constructor
    public MyTask(String fileName) {
        this.nameOfFile = fileName;
    }

    @Override
    public Integer call() throws Exception {
        int countLine = 0;
        try {
            // Open the file for reading
            BufferedReader reader = new BufferedReader(new FileReader(this.nameOfFile));
            // Read the file line by line
            String s = reader.readLine();
            while(s!=null) {
                countLine++;
                s = reader.readLine();
            }
            reader.close(); // Close the reader
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return countLine;
    }
}
