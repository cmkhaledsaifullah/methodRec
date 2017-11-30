package Util;

/*
Author: C M Khaled Saifullah
Software Research Lab
University of Saskatchewan, Canada
Email: khaled.saifullah@usask.ca

Project Description: This class is used to calculate the precision, recall and f1 score for top3, 5 , 10 recommendation.

*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StatCalculation
{
    ArrayList<Double> predictedvalue;
    ArrayList<Integer> labelvalue;
    ArrayList<ArrayList<Integer>> bestN;
    int [][][] confusionmatrix;
    int nOut;

    public StatCalculation(ArrayList<Double> predictedvalue,ArrayList<Integer> labelvalue, int nOut)
    {
        this.predictedvalue = predictedvalue;
        this.labelvalue = labelvalue;
        this.nOut = nOut;
    }

    public String stats(int bestNvalue) throws IOException {
        double accuracy = accuracy(bestNvalue);
        double precision = precision();
        double recall = recall();
        double f1Score = f1Score();
        String report = "";
        report = report+ "Top "+bestNvalue+" Recommendation: "+"\n";
        report = report+"Accuracy: "+ accuracy+"\n";
        report = report+"Precision: "+ precision+"\n";
        report=report+"Recall: "+ recall+"\n";
        report=report+"F1 Score: "+ f1Score+"\n";
        report=report+"\n";
        report=report+"====================================================================="+"\n";
        report=report+ "\n";
        System.out.println(report);
        return report;

    }

    public double accuracy(int bestNvalue)
    {

        bestNCalculation(bestNvalue);

        //Count the number of times our actual label appear in the top-3,5,10 prediction
        int count = 0;

        for(int i=0;i<labelvalue.size();i++)
        {
            ArrayList<Integer> values = bestN.get(i);
            int flag = 0;
            for(int j=0;j<values.size();j++)
            {
                if(labelvalue.get(i) == values.get(j))
                {
                    flag = 1;
                }

            }

            if(flag == 1)
            {
                count ++;
            }
        }

        double accuracy = (double) count/(double) labelvalue.size();

        return accuracy;
    }

    public double precision()
    {
        confusionMatrix();
        double[] precision = new double[nOut+1];
        int count = 0;

        for(int i=1; i<=nOut;i++)
        {
            if(confusionmatrix[i][0][0] > 0)
            {
                int reccomendationMade = confusionmatrix[i][0][0]+confusionmatrix[i][1][0];
                precision[i] = (double) confusionmatrix[i][0][0] / (double) reccomendationMade;
                count++;
            }

        }
        double totalPrecision = sumDoubleArray(precision);
        double avgPrecision = totalPrecision / (double) count;
        return avgPrecision;
    }

    public double recall()
    {
        double[] recall = new double[nOut+1];
        int count = 0;

        for(int i=1; i<=nOut;i++)
        {
            if(confusionmatrix[i][0][0] > 0)
            {
                int reccomendationRequested = confusionmatrix[i][0][0]+confusionmatrix[i][0][1];
                recall[i] = (double) confusionmatrix[i][0][0] / (double) reccomendationRequested;
                count++;
            }

        }
        double totalRecall = sumDoubleArray(recall);
        double avgRecall = totalRecall / (double) count;
        return avgRecall;
    }


    public double f1Score()
    {
        double precison = precision();
        double recall = recall();
        double f1Score = (2*precison*recall) / (recall+precison);

        return f1Score;
    }

    private void bestNCalculation(int bestNvalue)
    {
        bestN = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<predictedvalue.size();i++)
        {
            ArrayList<Double> values = new ArrayList<Double>();

            for(int j=i; j<(i+nOut); j++)
            {
                values.add(predictedvalue.get(j));
            }
            ArrayList<Double> sortedvalues = new ArrayList<Double>(values) ;
            Collections.sort(sortedvalues);
            Collections.reverse(sortedvalues);

            ArrayList<Integer> indexvalue = new ArrayList<Integer>();
            for(int k=0;k<bestNvalue;k++)
            {
                int index = values.indexOf(sortedvalues.get(k));
                indexvalue.add(index);
            }
            bestN.add(indexvalue);
            i=i+nOut-1;
        }
    }

    public int[][][] confusionMatrix()
    {
        confusionmatrix = new int[nOut+1][2][2];

        for(int i=0;i<labelvalue.size();i++)
        {
            ArrayList<Integer> values = bestN.get(i);
            int flag = 0;
            for(int j=0;j<values.size();j++)
            {
                if(labelvalue.get(i) == values.get(j))
                {
                    flag = 1;
                }

            }

            if(flag == 1)
                confusionmatrix[labelvalue.get(i)][0][0]  = confusionmatrix[labelvalue.get(i)][0][0] + 1;
            else
                confusionmatrix[labelvalue.get(i)][1][0] = confusionmatrix[labelvalue.get(i)][1][0] + 1;
        }

        return confusionmatrix;
    }



    private double sumDoubleArray(double[] array)
    {
        double total=0;
        for(int i=0;i<array.length;i++)
        {
            total = total + array[i];
        }
        return total;
    }

}
