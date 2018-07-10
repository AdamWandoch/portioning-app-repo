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
public class Portioner 
{
 
    private double currentFillet;
    private int targetPortions = 0;
    private double target;
    private double priorityCut = 0;
    private double priorityCut2 = 0;

    private double offcut;
    private int mixGradeCount;
    private double mixGradeAmount = 0;
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
    
    
    public Portioner(double targetSize)
    {
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
    
    public static double round(double value, int places) 
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public void PrintStats()
    {
        int gradedCount = narrowGradeSmallCount + narrowGradeBigCount + wideGradeSmallCount + wideGradeBigCount + targetGradeCount;
        double gradedAmount = totalFilletsWeight - mixGradeAmount;
        System.out.println("Total amount processed: " + (int)totalFilletsWeight);
        System.out.println("Total volume from grader: " + (int)totalVolumeFromGrader);
        System.out.println("Total graded count: " + gradedCount);
        System.out.println("Average graded portion size: " + (gradedAmount / gradedCount));
        System.out.println("Product yield: " + (gradedAmount / totalFilletsWeight));
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
    // ******************* !!!! GRADER is still called too many times during process  !!!!! *************************** //
    public void process(double fillet)
    {
        
        currentFillet = fillet;
        totalFilletsWeight += currentFillet;  // update the total amount of fillets processed
        
        // if current fillet is big enough to give us priorityCut
        // and if needed apply the priority cut first       
        
        if (priorityCut <= currentFillet && priorityCut > 0) 
        {
            grade(priorityCut);
            currentFillet -= priorityCut; // take the priority cut off current fillet
        }
        priorityCut = 0;
        
        // if current fillet is big enough to give us second priority cut
        // then if needed apply the priority cut2
        
        if (priorityCut2 <= currentFillet && priorityCut2 > 0) 
        {
            grade(priorityCut2);
            currentFillet -= priorityCut2; // take the second priority cut off current fillet
        }
        priorityCut2 = 0;
        
        targetPortions = (int)(currentFillet / target); // calculate how many taget cuts can you cut from current fillet
        offcut = currentFillet - (targetPortions * target); // calculate the size of the offcut after cutting off target portions
        
        if (currentFillet >= wideGradeSmallMin)
        {
            if (offcut <= target * 0.16) // if the offcut is smaller or equal to biggest accepted growth size then grow the last cut and cut it off
            {
                grade(target + offcut);
                for (int i = 0; i < targetPortions - 1; i++) // and cut the rest for target sized portions
                {
                    grade(target);
                }
                priorityCut = (target * 2) - (offcut + target); // then update priority cut to match with the grown cut
            } else if (offcut >= wideGradeSmallMin) // if the offcut is bigger or equal to smallest gradable portion then grade it on its own
            {
                grade(offcut);
                for (int i = 0; i < targetPortions; i++) // then cut the rest for target portions
                {
                    grade(target); 
                }
                priorityCut = (target * 2) - offcut;  // and update the priority cut to match the offcut portion
            } else // otherwise (if offcut too big to grow the portion but too small to be graded on its own)
            {
                if (offcut <= target * 0.32) // if offcut is big enough to grow two porions  (2 * 0.16 or less) then
                {
                    grade(target * 1.16); // grow one to the max
                    offcut -= target * 0.16; // find out how much offcut is left
                    grade(target + offcut); // grow the second one with the rest of the offcut
                    for (int i = 0; i < targetPortions - 2; i++) // grade the rest for target sized cuts
                    {
                        grade(target);
                    }
                    priorityCut = (target * 2) - (target * 1.16);
                    priorityCut2 = (target * 2) - (target + offcut);
                } else // if offcut is bigger than doubled max growth size so > 2 * 0.16
                {
                    grade(target * 1.16); // it is needed twice for sure!!
                    grade(target * 1.16); // grade two grown portions 
                    for (int i = 0; i < targetPortions - 2; i++) // grade the rest for target sized cuts
                    {
                        grade(target);
                    }
                    grade(offcut - (target * 0.32)); // grade the rest of offcut (for mix, wasted)
                    priorityCut = (target * 2) - (target * 1.16);
                    priorityCut2 = (target * 2) - (target * 1.16);
                }

            }
        } else
        {
            grade(currentFillet);
        }   
    }
    
       
    private void grade(double portion)
    {
        totalVolumeFromGrader += portion;
        if (portion >= wideGradeSmallMin && portion < wideGradeSmallMax)
        {
            wideGradeSmallCount += 1;
        } else if (portion >= narrowGradeSmallMin && portion < narrowGradeSmallMax)
        {
            narrowGradeSmallCount += 1;
        } else if (portion >= targetGradeMin && portion < targetGradeMax)
        {
            targetGradeCount += 1;
        } else if (portion >= narrowGradeBigMin && portion < narrowGradeBigMax)
        {
            narrowGradeBigCount += 1;
        } else if (portion >= wideGradeBigMin && portion <= wideGradeBigMax)
        {
            wideGradeBigCount += 1;
        } else if (portion < wideGradeSmallMin)
        {
            mixGradeCount += 1;
            mixGradeAmount += portion;
            if (portion > maxMixGradeSize)
            {
                maxMixGradeSize = portion;
            } 
        } 
    }   
}
