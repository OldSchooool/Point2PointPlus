package com.example.spingus.pointtopointplus;

/**
 * Created by Spingus on 29/07/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {
    InputStream inputStream;
   public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String[] read(int size){
        String[] wordy = new String[size];
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            int i = 0;
            while ((csvLine = reader.readLine()) != null) {
                wordy[i] = csvLine;
                i++;
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return wordy;
    }
}