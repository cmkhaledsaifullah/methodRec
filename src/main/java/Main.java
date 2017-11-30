/*
Author: C M Khaled Saifullah
Software Research Lab
University of Saskatchewan, Canada
Email: khaled.saifullah@usask.ca

Project Description: This is the deelearning project for recommending argument of API usages using recurrent neural network.
The project use deeplearning4j tools developed by skymind group for reccomending top 1,3,5 and 10 arguments for a function call.
I have considered all the methods of javax.swing package and uses 6 sample project of github that uses javax.swing package.



*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{

    private static List<String> nIn ;       //number of input for RNN
    private static List<String> nOut;       //Number of Output(Number of label) for RNN
    private static List<String> trainfiles; // List of training files
    private static List<String> testfiles;  //List of testing files

    public static void main(String[] args) throws IOException, InterruptedException
    {
        //Value Function will get the nIn and nOut for each file and the Run function will run the RNN for each training file
        // and store the result in a specific output file in output folder

        //The following command for JHotDraw dataset
        value("dataset/jhotdrawDatasetInfo.txt");
        Run("JHotDraw");

        // The following command is for Random Dataset
        value("dataset/randomDatasetInfo.txt");
        Run("Random");


        //The following command is for JEdit dataset
        value("dataset/jeditDatasetInfo.txt");
        Run("JEdit");

    }


    private static void Run(String dataset) throws IOException, InterruptedException
    {
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
            //Classification.LSTMBasedRNN(arg);
            LargeRNN.LSTMBasedRNN(arg);
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
