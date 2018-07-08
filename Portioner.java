/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portioningapp;

/**
 *
 * @author Willy
 */
public class Portioner {
   //and another change
    private double currentFillet;
    private int targetPortions;
    private double target;
    private double priorityCut = 0;
    private double priorityCut2 = 0;

    private double offcut;
    private int mixGradeCount;
    private double mixGradeAmount;
    private double totalFilletsWeight = 0;
    private double totalVolumeFromGrader = 0;
    private double maxMixGradeSize = 0;
    
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
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public void PrintStats(){
        int gradedCount = narrowGradeSmallCount + narrowGradeBigCount + wideGradeSmallCount + wideGradeBigCount + targetGradeCount;
        double gradedAmount = totalFilletsWeight - mixGradeAmount;
        System.out.println("Total amount processed: " + (int)totalFilletsWeight);
        System.out.println("Total volume from grader: " + (int)totalVolumeFromGrader);
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
        System.out.println("Mix amount: " + (int)mixGradeAmount);
        System.out.println("Mix percentage: " + mixGradeAmount / totalFilletsWeight);
        System.out.println("Average mix grade size: " + mixGradeAmount / mixGradeCount);
        System.out.println("Max mix grade size: " + round(maxMixGradeSize, 5));
        System.out.println(" ");
    }
    
    // ------------ PORTIONER ALGORYTHM SIMULATING REGULAR PORTIONING WITH SELF-BALANCE ------------------------------- //
    // ----------------------------------- WITH ADDED LAST-CUT-GROWTH ------------------------------------------------- //
    // ********************************* AND FULL DOUBLE OFFCUT OPTIMIZATION ****************************************** //
    // ********************** !!!! NEEDS REWORK TO CHECK FILLET SIZE ALWAYS  !!!!!! *********************************** //
    public void process(double fillet){
        
        currentFillet = fillet;
        totalFilletsWeight += currentFillet; 
       
        if (priorityCut > 0){
            grade(priorityCut);
            currentFillet = currentFillet - priorityCut;
        }
        
        if (priorityCut2 > 0){
            grade(priorityCut2);
            currentFillet = currentFillet - priorityCut2;
            priorityCut2 = 0;
        }
        
        targetPortions = (int)(currentFillet / target);
        offcut = currentFillet - (targetPortions * target);
        
        if (offcut <= target * 0.16){
            grade(target + offcut);
            for (int i = 0; i < targetPortions - 1; i++){
                grade(target);
            }
            priorityCut = (target * 2) - (offcut + target);
        } else if (offcut >= wideGradeSmallMin){
            grade(offcut);
            for (int i = 0; i < targetPortions; i++){
                grade(target);
            }
            priorityCut = (target * 2) - offcut;   
        } else {
            if (offcut <= target * 0.32){
                grade(target * 1.16);
                offcut -= target * 0.16;
                grade(target + offcut);
                for (int i = 0; i < targetPortions - 2; i++){
                    grade(target);
                }
                priorityCut = (target * 2) - (target * 1.16);
                priorityCut2 = (target * 2) - (target + offcut);
            } else {
                grade(target * 1.16);
                grade(target * 1.16);
                for (int i = 0; i < targetPortions - 2; i++){
                    grade(target);
                }
                grade(offcut - target * 0.32);
                priorityCut = (target * 2) - (target * 1.16);
                priorityCut2 = (target * 2) - (target * 1.16);
            }
            
        }
   
    }
    
       
    private void grade(double portion){
        totalVolumeFromGrader += portion;
        if (portion >= wideGradeSmallMin && portion < wideGradeSmallMax){
            wideGradeSmallCount += 1;
        } else if (portion >= narrowGradeSmallMin && portion < narrowGradeSmallMax){
            narrowGradeSmallCount += 1;
        } else if (portion >= targetGradeMin && portion < targetGradeMax){
            targetGradeCount += 1;
        } else if (portion >= narrowGradeBigMin && portion < narrowGradeBigMax){
            narrowGradeBigCount += 1;
        } else if (portion >= wideGradeBigMin && portion <= wideGradeBigMax){
            wideGradeBigCount += 1;
        } else if (portion < wideGradeSmallMin){
            mixGradeCount += 1;
            mixGradeAmount += portion;
            if (portion > maxMixGradeSize){
                maxMixGradeSize = portion;
            } 
        } 
    }
    
    
}
