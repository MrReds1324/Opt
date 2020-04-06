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

    public static void printLegionHypersAndFD(JTextArea legion, JTextArea legionBP, double baseCalc, double time, PotVector potVector) {
        int[] hyperStats = potVector.getHypers();
        String[] legionStrings = potVector.legionStrings();
        String topLine = formattedConcat("----Hypers----", String.format("%.3f", (potVector.getCalc() / baseCalc - 1) * 100) + "% Final Damage", 67);
        String finished = formattedConcat(topLine.substring(0, topLine.length()-1), String.format("In %.5f Seconds", time), 110);
        finished += formattedConcat(String.format("%02d Points into Crit Damage", hyperStats[0]), "----Legion----", 65);
        finished += formattedConcat(String.format("%02d Points into Boss Damage", hyperStats[1]), legionStrings[0], 65);
        finished += formattedConcat(String.format("%02d Points into Damage", hyperStats[2]), legionStrings[1], 69);
        finished += formattedConcat(String.format("%02d Points into IED", hyperStats[3]), "", 0);

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
