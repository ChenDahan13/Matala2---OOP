import java.io.*;

public class MyThread extends Thread {
        private String nameOfFile;
        private int counter = 0;
        // Constructor
    public MyThread(String fileName){
        super(fileName);
        this.nameOfFile = fileName;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        try {
            // Open the file for reading
            BufferedReader reader = new BufferedReader(new FileReader(this.getName()));
            // Read the file line by line
            String s = reader.readLine();
            while(s!=null) {
                counter++;
                s = reader.readLine();
            }
            reader.close(); // Close the reader

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
