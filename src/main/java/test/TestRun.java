package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRun
{
    private static List<String> nIn ;
    private static List<String> nOut;
    private static List<String> trainfiles;
    private static List<String> testfiles;
    public static void main(String[] args) throws IOException, InterruptedException
    {

        Scanner sc= new Scanner(System.in);
        System.out.println("Choose Your Dataset:");
        System.out.println("1. Random Dataset");
        System.out.println("2. JHotDraw");
        System.out.println("3. JEdit");
        int option = sc.nextInt();
        if(option == 1)
        {
            value("dataset/randomDatasetInfo.txt");
            Run("Random");
        }
        if(option == 2)
        {
            value("dataset/jhotdrawDatasetInfo.txt");
            Run("JHotDraw");
        }
        if(option == 3)
        {
            value("dataset/jeditDatasetInfo.txt");
            Run("JEdit");
        }
    }


    private static void Run(String dataset) throws IOException, InterruptedException {
        trainfiles = new ArrayList<String>();
        testfiles = new ArrayList<String>();
        File folder = new File("dataset/"+dataset);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                if(listOfFiles[i].getName().contains("train"))
                    trainfiles.add("dataset/"+dataset+"/" + listOfFiles[i].getName());
                else if(listOfFiles[i].getName().contains("test"))
                    testfiles.add("dataset/"+dataset+"/" + listOfFiles[i].getName());
            }
        }

        for (int i=0;i<trainfiles.size();i++)
        {
            String[] token = trainfiles.get(i).split("\\.");
            String [] nestedToken = token[0].split("_");
            int fileNumber = Integer.parseInt(nestedToken[nestedToken.length-1]);
            //System.out.println(fileNumber+" "+nIn.get(fileNumber)+" "+ nOut.get(fileNumber));
            String testFile = nestedToken[0]+"_test_"+nestedToken[2]+"_"+fileNumber+".csv";
            String[] arg = new String[5];
            arg[0] = "dataset/Output/"+dataset+"_output.txt";
            arg[1] = nIn.get(fileNumber);
            arg[2] = nOut.get(fileNumber);
            arg[3] = trainfiles.get(i);
            arg[4] = testFile;
            //System.out.println(arg[0]+" "+arg[1]);
            TestMain.main(arg);
        }
    }


    private static void value(String inputFile) throws IOException
    {
        nIn = new ArrayList<String>();
        nOut = new ArrayList<String>();
        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);
        String sCurrentLine;
        List<String> lines = new ArrayList<String>();
        while((sCurrentLine=br.readLine())!=null)
        {
            lines.add(sCurrentLine);
            if(sCurrentLine.contains("Maximum Size of an observation"))
            {
                String[] token = sCurrentLine.split(" ");
                nIn.add(token[token.length-1]);
            }
            else if(sCurrentLine.contains("Number of Unique Method Call"))
            {
                String[] token = sCurrentLine.split(" ");
                nOut.add(token[token.length-1]);
            }
        }
    }
}
