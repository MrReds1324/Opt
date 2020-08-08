/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.PotType;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ryanb
 */
public class ItemPrinter {

    public static void printItem(JTextField outputLeg, JComboBox legSelect, JTextField outputUnique1, JComboBox uniqueSelect1, JTextField outputUnique2, JComboBox uniqueSelect2, Potentials potentials) {
        Entry<String, String> legline = potentials.legline();
        outputLeg.setText(legline.getKey());
        legSelect.setSelectedItem(legline.getValue());
        
        List<Entry<String, String>> uniqueLines = potentials.uline();
        outputUnique1.setText(uniqueLines.get(0).getKey());
        uniqueSelect1.setSelectedItem(uniqueLines.get(0).getValue());
        
        outputUnique2.setText(uniqueLines.get(1).getKey());
        uniqueSelect2.setSelectedItem(uniqueLines.get(1).getValue());
    }

    public static void printSoul(JTextField output, JComboBox soulSelect, PotType soulType) {
        switch (soulType) {
            case ATT:
                output.setText("3");
                soulSelect.setSelectedItem(PotType.ATT.toString());
                break;
            case BOSS:
                output.setText("7");
                soulSelect.setSelectedItem(PotType.BOSS.toString());
                break;
            case IED:
                output.setText("7");
                soulSelect.setSelectedItem(PotType.IED.toString());
                break;
            default:
                soulSelect.setSelectedItem("None");
                break;
        }
    }

    public static void printLegionHypersAndFD(JTextArea legionBP, double baseCalc, double time, PotVector potVector) {
        int[] hyperStats = potVector.getHypers();
        String[] legionStrings = potVector.legionStrings();
        String finished = formattedConcat(formattedConcat("----Hypers----", String.format("%.3f%% Final Damage", (potVector.getCalc() / baseCalc - 1) * 100), 67), String.format("In %.5f Seconds\n", time), 110);
        finished += formattedConcat(formattedConcat(String.format("%02d Points into Crit Damage", hyperStats[0]), "----Legion----", 65), String.format("%.0f%% Total Attack\n", 100 * potVector.getAtt()), 109);
        finished += formattedConcat(formattedConcat(String.format("%02d Points into Boss Damage", hyperStats[1]), legionStrings[0], 65), String.format("%.0f%% Total Damage/Boss\n", 100 * potVector.getTotalDMG()), 106);
        finished += formattedConcat(formattedConcat(String.format("%02d Points into Damage", hyperStats[2]), legionStrings[1], 69), String.format("%.3f%% Total IED\n", 100 * potVector.getIed()), 109);
        finished += formattedConcat(formattedConcat(String.format("%02d Points into IED", hyperStats[3]), legionStrings[2], 69), String.format("%.1f%% Total Crit Damage\n", 100 * potVector.getCrit()), 108);

        legionBP.setText(finished);
    }
    
    private static String formattedConcat(String beginning, String ending, int length){
        int dif = length - beginning.length();
        for (int i = 0; i <= dif; i++){
            beginning += " ";
        }
        return beginning + ending;
    }

}
