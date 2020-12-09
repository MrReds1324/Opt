/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.FamiliarTier;
import WSEOptimizer.Constants.PotType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ryanb
 */
public class ComboBoxSupport {

    public static Map<String, PotVector> buildComboBoxMap(List<PotVector> wseList1) {
        LinkedHashMap<String, PotVector> builderMap = new LinkedHashMap<String,PotVector>();
        PotVector max = wseList1.get(0);
        for (int i = 0; i < wseList1.size(); i++) {
            double percentFD = ((wseList1.get(i).getCalc() / max.getCalc() - 1) * 100);
            String key = String.format("%d: %.4f%% Final Damage", i, percentFD);
            builderMap.put(key, wseList1.get(i));
        }
        return builderMap;
    }
    
    @SuppressWarnings("unchecked")
    public static DefaultComboBoxModel<String> buildComboBoxItems(Map<String, PotVector> itemMapping){
        return new DefaultComboBoxModel(itemMapping.keySet().toArray());
    }
    
    @SuppressWarnings("unchecked")
    public static DefaultComboBoxModel<String> buildComboBoxFamiliars(int num_lines){
        ArrayList<String> newModel = new ArrayList<>();
        for (int i = 0; i <= num_lines; i++){
            newModel.add(String.format("%d Lines", i));
        }
        return new DefaultComboBoxModel(newModel.toArray());
    }
    
    public static Map<String, PotType> buildSelectComboBoxMap(){
        LinkedHashMap<String, PotType> builderMap = new LinkedHashMap<>();
        builderMap.put("None", PotType.DEFAULT);
        builderMap.put("ATT", PotType.ATT);
        builderMap.put("IED", PotType.IED);
        builderMap.put("BOSS", PotType.BOSS);
        builderMap.put("CRIT", PotType.CRITDAMAGE);
        return builderMap;
    }
    
    public static Map<String, FamiliarTier> buildFamiliarSelectComboBoxMap(){
        LinkedHashMap<String, FamiliarTier> builderMap = new LinkedHashMap<>();
        builderMap.put("LEGENDARY", FamiliarTier.LEGENDARY);
        builderMap.put("UNIQUE", FamiliarTier.UNIQUE);
        builderMap.put("EPIC", FamiliarTier.EPIC);
        builderMap.put("RARE", FamiliarTier.RARE);
        builderMap.put("COMMON", FamiliarTier.COMMON);
        return builderMap;
    }
    
    public static Map<String, Integer> buildFamiliarLinesSelectComboBoxMap(){
        LinkedHashMap<String, Integer> builderMap = new LinkedHashMap<>();
        builderMap.put("0 Lines", 0);
        builderMap.put("1 Lines", 1);
        builderMap.put("2 Lines", 2);
        builderMap.put("3 Lines", 3);
        builderMap.put("4 Lines", 4);
        builderMap.put("5 Lines", 5);
        builderMap.put("6 Lines", 6);
        return builderMap;
    }
    
    public static int lvlStrToInt(String lvlString){
        return Integer.parseInt(lvlString.substring(4));
    }
    
    public static int stacksToInt(String stackString) {
        return Integer.parseInt(stackString.substring(0, 1));
    }
}
