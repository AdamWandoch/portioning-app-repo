/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portioningapp;

/**
 *
 * @author AdamWandoch
 */
public class Portioner {
    
    private double currentFillet;
    private int targetPortions;
    private double target;
    private double priorityCut = 0;
    //private double[] layout;
    private double offcut;
    private int mixGradeCount;
    private double mixGradeAmount;
    private double totalFilletsWeight = 0;
    
    
    private double targetGradeMin;
    private double targetGradeMax;
    private int targetGradeCount;
    
    private double narrowGradeSmallMin;
    private double narrowGradeSmallMax;
    private double narrowGradeBigMin;
    private double narrowGradeBigMax;
    private int narrowGradeSmallCount; 
    private int narrowGradeBigCount;
    
    private double wideGradeSmallMin;
    private double wideGradeSmallMax;
    private double wideGradeBigMin;
    private double wideGradeBigMax;
    private int wideGradeSmallCount;
    private int wideGradeBigCount;
    
    
    public Portioner(double targetSize){
        target = targetSize;
        targetGradeMin = target * 0.96;
        targetGradeMax = target * 1.04;
        narrowGradeSmallMin = target * 0.9;
        narrowGradeSmallMax = target * 0.96;
        narrowGradeBigMin = target * 1.04;
        narrowGradeBigMax = target * 1.1;
        wideGradeSmallMin = target * 0.84;
        wideGradeSmallMax = target * 0.9;
        wideGradeBigMin = target * 1.1;
        wideGradeBigMax = target * 1.16;
    }
    
    public void PrintStats(){
        int gradedCount = narrowGradeSmallCount + narrowGradeBigCount + wideGradeSmallCount + wideGradeBigCount + targetGradeCount;
        double gradedAmount = totalFilletsWeight - mixGradeAmount;
        System.out.println("Total amount processed: " + (int)totalFilletsWeight);
        System.out.println("Total graded count: " + gradedCount);
        System.out.println("Average graded portion size: " + gradedAmount / gradedCount);
        System.out.println("Product yield: " + gradedAmount / totalFilletsWeight);
        System.out.println("Target cuts percentage: " + ((double)targetGradeCount) / ((double)gradedCount));
        System.out.println("target count: " + targetGradeCount);
        System.out.println("narrowSmalls: " + narrowGradeSmallCount);
        System.out.println("narrowBigs: " + narrowGradeBigCount);
        System.out.println("wideSmalls: " + wideGradeSmallCount);
        System.out.println("wideBigs: " + wideGradeBigCount);
        System.out.println("mixGrade: " + mixGradeCount);
        System.out.println(" ");
    }
    
    // ------------ BASIC PORTIONER ALGORYTHM SIMULATING REGULAR PORTIONING WITH SELF-BALANCE ------------------------- //
    // **************************************************************************************************************** //
    public void process(double fillet){
        
        currentFillet = fillet;
        totalFilletsWeight += currentFillet; 
        
        
        if (priorityCut > 0){
            grade(priorityCut);
            currentFillet = currentFillet - priorityCut;
        }
        
        
        targetPortions = (int)(currentFillet / target);
        offcut = currentFillet - (targetPortions * target);
        grade(offcut);
        for (int i = 0; i < targetPortions; i++){
            grade(target);
        }
        
        if (offcut >= wideGradeSmallMin){
            priorityCut = (target * 2) - offcut;   
        } else {
            priorityCut = 0;
        }
    }
    
    
    
    private void grade(double portion){
        if (portion >= wideGradeSmallMin && portion < wideGradeSmallMax){
            wideGradeSmallCount += 1;
        } else if (portion >= narrowGradeSmallMin && portion < narrowGradeSmallMax){
            narrowGradeSmallCount += 1;
        } else if (portion >= targetGradeMin && portion < targetGradeMax){
            targetGradeCount += 1;
        } else if (portion >= narrowGradeBigMin && portion < narrowGradeBigMax){
            narrowGradeBigCount += 1;
        } else if (portion >= wideGradeBigMin && portion < wideGradeBigMax){
            wideGradeBigCount += 1;
        } else if (portion < wideGradeSmallMin){
            mixGradeCount += 1;
            mixGradeAmount += portion;
        } 
    }
    
    
}
