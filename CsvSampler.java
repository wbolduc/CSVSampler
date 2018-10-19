/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvsampler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author wbolduc
 */
public class CsvSampler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String bigCSV = args[0];//"E:\\YorkWork\\UrgentResults\\metoo\\RawData\\metoo-merged-2018-01-en.csv";
        int sampleCount = Integer.parseInt(args[1]);
        
        if(FilenameUtils.getExtension(bigCSV).equals("csv") == false)
        {
            System.out.println("Input is not a csv file");
            return;
        }
        if(sampleCount <= 0)
        {
            System.out.println("Sample too small");
            return;
        }
        
        String path = FilenameUtils.getFullPath(bigCSV);
        String name = FilenameUtils.getBaseName(bigCSV);
        String smallCSV = path + name + "_sampleOf" + sampleCount + ".csv";
        
        
        
        
        System.out.println("Selecting " + sampleCount + " records from " + name + ".csv");
        System.out.println("Writing to " + smallCSV);
        
        CSVFormat format = CSVFormat.RFC4180;

        Reader csvData = new FileReader(bigCSV);
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new FileWriter(smallCSV)), format);
        
        List<CSVRecord> rawRecords = format.parse(csvData).getRecords();    //load all the records //is there a way arount this?
        
        printer.printRecord(rawRecords.get(0)); //add header

        int increment = (rawRecords.size()-1)/sampleCount;  //size-1 because first record is header
        for (int samples = 0, i = 1; samples < sampleCount; i += increment, samples++) 
        {
            printer.printRecord(rawRecords.get(i));
        }
        
        csvData.close();
        printer.close();
        System.out.println("Done");
    }
    
}
