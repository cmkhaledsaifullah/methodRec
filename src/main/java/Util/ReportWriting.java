package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriting
{
    private static String outputFile;

    public ReportWriting(String outputFile)
    {
        this.outputFile = outputFile;
    }

    public void showReport(String report, String fileName) throws IOException
    {
        File file = new File(outputFile);
        int flag=0;
        if (!file.exists())
        {
            flag=1;
            file.createNewFile();
        }
        FileWriter fw = null;

        if(flag == 0)
        {
            fw = new FileWriter(file,true);
        }
        else
        {
            fw = new FileWriter(file);
        }


        BufferedWriter bw = new BufferedWriter(fw);


        bw.write("File Name: "+fileName);
        bw.newLine();
        bw.write("===========================================================");
        bw.write(report);
        bw.newLine();


        bw.close();

    }

}
