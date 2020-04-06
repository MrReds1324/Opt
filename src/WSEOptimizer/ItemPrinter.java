/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.PotType;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ryanb
 */
public class ItemPrinter {

    public static void printItem(JTextField outputLeg, JTextField outputUnique1, JTextField outputUnique2, Potentials potentials) {
        outputLeg.setText(potentials.legline());
        String[] uniqueLines = potentials.uline().split(":");
        outputUnique1.setText(uniqueLines[0]);
        outputUnique2.setText(uniqueLines[1]);
    }

    public static void printSoul(JTextField output, PotType soulType) {
        switch (soulType) {
            case ATT:
                output.setText("3% ATT");
                break;
            case BOSS:
                output.setText("7% BOSS");
                break;
            case IED:
                output.setText("7% IED");
                break;
            default:
                break;
        }
    }

    public static void printLegionHypersAndFD(JTextArea legion, JTextArea legionBP, double baseCalc, PotVector potVector) {
        int[] hyperStats = potVector.getHypers();
        String[] legionStrings = potVector.legionStrings();
        String finished = "";
        String critDmg = " Points into Crit Damage";
        finished += formattedConcat(String.format("%.3f", (potVector.getCalc() / baseCalc - 1) * 100) + "% Final Damage", "----Hypers----", 40);
        finished += formattedConcat("----Legion----", hyperStats[0] + " Points into Crit Damage", 45);
        finished += formattedConcat(legionStrings[0], hyperStats[1] + " Points into Boss Damage", 45);
        finished += formattedConcat(legionStrings[1], hyperStats[2] + " Points into Damage", 42);
        finished += formattedConcat("", hyperStats[3] + " Points into IED", 55);

        legion.setText(finished);
        legionBP.setText(finished);
    }
    
    private static String formattedConcat(String beginning, String ending, int length){
        int dif = length - beginning.length();
        for (int i = 0; i <= dif; i++){
            beginning += " ";
        }
        return beginning + ending + "\n";
    }

}
