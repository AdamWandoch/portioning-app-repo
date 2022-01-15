
package portioningapp;

/**
 * @author AdamWandoch
 */

public class PortioningApp {

    private static int testCount = 0;
    private static double portionSize = 125;
    private static int testSize = 200;
    private static double randomFillet;
    private static double minFilletSize = 450;
    private static double maxFilletSize = 900;

    public static void main(String[] args) {
        System.out.println("    // ------------ PORTIONER ALGORYTHM SIMULATING REGULAR PORTIONING WITH SELF-BALANCE ------------------------------- //\n" +
                "    // ----------------------------------- WITH ADDED LAST-CUT-GROWTH ------------------------------------------------- //\n" +
                "    // ********************************* AND FULL DOUBLE OFFCUT OPTIMIZATION ****************************************** //");

        runTest();
        runTest();
        runTest();
    }

    public static void runTest() {
        testCount++;
        System.out.println("Test " + testCount + ": ");
        Portioner machine = new Portioner(portionSize);
        for (int i = 0; i < testSize; i++) {
            randomFillet = Math.random() * (maxFilletSize - minFilletSize) + minFilletSize;
            machine.process(randomFillet);
        }
        machine.PrintStats();
    }
}
