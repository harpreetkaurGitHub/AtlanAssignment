package com.example.demo.Service;
import au.com.bytecode.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class ReadFileService {

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private volatile CSVReader reader;


    public void runMethod(){
        int counter = 0;
        running = true;
        try {
            Resource resource = new ClassPathResource("Data.csv"); // reading file from classPath resource.
            reader = new CSVReader(new FileReader(resource.getFile()), ','); //creating Object to read Csv file.
            String[] record = null;
            reader.readNext();

            while ((record = reader.readNext()) != null && running) {

                // we have taken lock on dummy object.
                synchronized (pauseLock) {
                    if (paused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException ex) {
                            break;
                        }
                    }
                    // As per requirement we get the data and we can write it in Mysql or MongoDB or any other Database.
                    counter++;     // counter is for counting the processed document.
                    System.out.println(" IYEAR :: " + record[10]);
                    System.out.println(" FMONTH :: " + record[6]);

                    System.out.println("Documents read : " + counter);
                    Thread.sleep(1000);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // to stop the running process of file reading.
    public void stopFile(){
        try {
            running = false;
            resumeFile();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // to pause the running process of file reading.
    public void pauseFile() {
        // you may want to throw an IllegalStateException if !running
           paused = true;

    }

    // to resume the paused file process.
    public void resumeFile() {
        try {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll(); // Unblocks thread
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
};
