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

    public static void printLegionAndFD(JTextArea legion, JTextArea legionBP, double baseCalc, PotVector potVector) {
        String fd = String.format("%.3f", (potVector.getCalc() / baseCalc - 1) * 100);
        String s = "----Legion----\n";
        s += potVector.getUnion().legionString();
        s += "\n----------------------------------------------";
        s += "\n" + fd + "% Final Damage";
        legion.setText(s);
        legionBP.setText(s);
    }

}
