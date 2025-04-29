
/**
 * CsvLoader is a utility class that reads a CSV file
 * and loads its contents into a list of string arrays
 */


package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvLoader {

    /**
     * Loads a CSV file into a list of string arrays
     * Each row in the file becomes a String[] in the list
     *
     *  filePath The path to the CSV file
     *  List of rows, where each row is an array of string values
     */
    public static List<String[]> loadCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by commas into an array
                String[] values = line.split(",");
                data.add(values);
            }

        } catch (IOException e) {
            // Print any IO error that occurs during reading
            e.printStackTrace();
        }

        return data;
    }
}
