import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.*;


public class Ex2_1 {

    /**
     * Function creates number of text files
     *
     * @param n
     * @param seed
     * @param bound
     * @return array with the names of the text files
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        // Array of names
        String[] textsNames = new String[n];
        // The line in the text files
        String s = "Chen is the king of the world!";

        // Create random object
        Random rand = new Random(seed);

        try {
            for (int i = 0; i < n; i++) {
                // Generate the file name
                textsNames[i] = "file_" + (i + 1) + ".txt";

                // Create the file object
                File file = new File(textsNames[i]);

                // Create a PrintWriter object to the file
                PrintWriter pw = new PrintWriter(file);

                // Create random number of lines
                int numLines = rand.nextInt(bound);
                int lineCount = 0;

                // Print the line s number of times in the text file
                while (lineCount < numLines) {
                    pw.println(s);
                    lineCount++;
                }
                pw.close();
            }
            System.out.println("Array created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textsNames;
    }

    /**
     * Function gets an array with names of files
     *
     * @param fileNames
     * @return number of lines in all the files together
     */
    public static int getNumOfLines(String[] fileNames) {

        int lineCount = 0;

        try {
            // Declare on BufferedReader object
            BufferedReader reader = null;

            for (int i = 0; i < fileNames.length; i++) {
                // Open the file for reading
                reader = new BufferedReader(new FileReader(fileNames[i]));

                // Read the file line by line
                String s = reader.readLine();
                while (s != null) {
                    lineCount++;
                    s = reader.readLine();
                }
            }
            reader.close(); // close the reader
            System.out.println("Count ended successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    /**
     * Function gets an array with names of files
     * Calculate the number of lines with Threads
     *
     * @param filesNames
     * @return number of lines in all the files together
     */
    public static int getNumOfLinesThreads(String[] filesNames) {
        int len = filesNames.length;
        MyThread[] mt = new MyThread[len];

        // Create amount of Thread
        for (int i = 0; i < len; i++) {
            mt[i] = new MyThread(filesNames[i]);
            mt[i].start();
        }

        // Calculate the sum of all text files lines
        int countLines = 0;
        for (int i = 0; i < len; i++) {
            try {
                mt[i].join();
                countLines = countLines + mt[i].getCounter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return countLines;
    }

    /**
     * Function gets an array with names of files
     * Calculate with ThreadPool
     *
     * @param fileNames
     * @return number of lines in all the files together
     */
    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int len = fileNames.length;
        int countLine = 0;
        // Create Thread pool
        ExecutorService executor = Executors.newFixedThreadPool(len);

        // Create an array of Future
        Future<Integer>[] future = new Future[len];

        // Do the tasks and put the result in an array of Future
        for (int i = 0; i < len; i++) {
            future[i] = executor.submit(new MyTask(fileNames[i]));
        }

        // Calculate the whole lines
        for (int i = 0; i < len; i++) {
            try {
                countLine = countLine + future[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        executor.shutdown();
        return countLine;
    }

    /**
     * Function deletes all the files that were created
     * @param filesNames
     */
    public static void deleteFiles(String[] filesNames) {
        for (String fileName : filesNames) {
            File file = new File(fileName);
            file.delete();
        }
    }
    public static void main(String[] args) {
        long startTime, endTime;
        int numberOfFiles = 30;
        int maxNumberOfLines = 100;
        String fileNames[] = createTextFiles(numberOfFiles, 100, maxNumberOfLines);
        System.out.println("Checking " + numberOfFiles + " files with " + maxNumberOfLines + " maximum lines each:");

        System.out.println("--------------------------------------------");
        startTime = System.currentTimeMillis();
        System.out.println("Num of lines in all files: " + getNumOfLines(fileNames));
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("--------------------------------------------");

        startTime = System.currentTimeMillis();
        System.out.println("Num of lines in all files using threads: " + getNumOfLinesThreads(fileNames));
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("--------------------------------------------");

        startTime = System.currentTimeMillis();
        System.out.println("Num of lines in all files using threadPool: " + getNumOfLinesThreadPool(fileNames));
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("--------------------------------------------");

        deleteFiles(fileNames);
    }
}
