package nl.cds.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class to read individual lines of a file
 */
public class DataReader {
    private final Scanner sc;
    private final String separator;

    public DataReader(String filePath, String separator) throws FileNotFoundException {
        sc = new Scanner(new File(filePath));
        this.separator = separator;
        sc.useDelimiter("\r\n");
    }

    public boolean hasNext(){
        return sc.hasNext();
    }

    public String[] readNextLine(){
        String line = sc.next();
        assert (line.contains(separator));
        return line.split(separator);
    }

    public void close(){
        sc.close();
    }
}
