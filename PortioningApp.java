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
public class PortioningApp {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        System.out.println("    // ------------ PORTIONER ALGORYTHM SIMULATING REGULAR PORTIONING WITH SELF-BALANCE ------------------------------- //\n" +
"    // ----------------------------------- WITH ADDED LAST-CUT-GROWTH ------------------------------------------------- //\n" +
"    // ********************************* AND FULL DOUBLE OFFCUT OPTIMIZATION ****************************************** //");
        double portionSize = 125;
        int testSize = 1000000;
        double randomFillet;
        double minFilletSize = 450;
        double maxFilletSize = 900;
        
        System.out.println("Test One: ");
        Portioner machine1 = new Portioner(portionSize);
        
        for (int i = 0; i < testSize; i++){
            randomFillet = Math.random() * (maxFilletSize - minFilletSize) + minFilletSize;
            machine1.process(randomFillet);
        }
        machine1.PrintStats();
        //machine1 = null;
        //////////////////////////////////////////////////////
        
        System.out.println("Test Two: ");
        Portioner machine2 = new Portioner(portionSize);
        
        for (int i = 0; i < testSize; i++){
            randomFillet = Math.random() * (maxFilletSize - minFilletSize) + minFilletSize;
            machine2.process(randomFillet);
        }
        machine2.PrintStats();
        //machine2 = null;
        //////////////////////////////////////////////////////
        
        System.out.println("Test Three: ");
        Portioner machine3 = new Portioner(portionSize);
        
        for (int i = 0; i < testSize; i++){
            randomFillet = Math.random() * (maxFilletSize - minFilletSize) + minFilletSize;
            machine3.process(randomFillet);
        }
        machine3.PrintStats();
        //machine3 = null;
        //////////////////////////////////////////////////////
        
    }
    
}
