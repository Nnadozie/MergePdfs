package src.main.java;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Merge extends JPanel implements ActionListener {
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter;
    private JFrame frame;
    private JButton openButton;
    private File file;
    private ArrayList<File> files;
    int returnVal;

    public Merge(){
        files = new ArrayList<>();
        frame = new JFrame("My GUI");
        filter = new FileNameExtensionFilter("Pdf Files", "pdf");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        openButton = new JButton("Select");

        setPreferredSize(new Dimension(278, 179));
        setLayout(null);

        add(openButton);

        openButton.setBounds(84, 145, 100, 25);
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Merge merger = new Merge();
        merger.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        merger.frame.getContentPane().add(merger);
        merger.frame.pack();
        merger.frame.setVisible(true);
        merger.openButton.addActionListener(merger);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                this.mergePdfs(file);
                this.compressPdfs(file);
            }
        }
    }

    public void mergePdfs(File file) {
        PDFMergerUtility ut = new PDFMergerUtility();
        File temp = new File("MergedFile.pdf");
        try {
            ut.addSource(temp);
        } catch (FileNotFoundException ex) {
            //
        }
        try {
            ut.addSource(file);
            ut.setDestinationFileName(temp.toString());
            ut.mergeDocuments(null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    public void compressPdfs(File file) {
        try {
            PdfReader reader = new PdfReader(new FileInputStream(file.toString()));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file.toString()));
            int total = reader.getNumberOfPages() + 1;
            for (int i = 1; i < total; i++) {
                reader.setPageContent(i + 1, reader.getPageContent(i + 1));
            }
            stamper.setFullCompression();
            stamper.close();
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        } catch (com.itextpdf.text.DocumentException ex) {
            //
        }
    }
}