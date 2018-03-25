package src.main.java;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.pdfbox.io.MemoryUsageSetting.setupMainMemoryOnly;

public class Merge {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Merge merger = new Merge();
        merger.mergePdfs("C:\\Users\\neoke\\Desktop\\MergePdfs\\src\\main\\java\\pdfs.txt");
    }

    public void mergePdfs(String fileName) {
        // The name of the file to open.
        //String fileName = "temp.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            PDFMergerUtility ut = new PDFMergerUtility();
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                Path path = Paths.get(line);
                System.out.println(Files.exists(path));
                File file = new File(path.normalize().toString());
                ut.addSource(file);
            }
            // Always close files.
            bufferedReader.close();
            ut.setDestinationFileName("MergedFile");
            //MemoryUsageSetting max = setupMainMemoryOnly(2048);
            ut.mergeDocuments(null);
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
}