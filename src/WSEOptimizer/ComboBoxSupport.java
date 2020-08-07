/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.PotType;
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
    
    public static Map<String, PotType> buildSelectComboBoxMap(){
        LinkedHashMap<String, PotType> builderMap = new LinkedHashMap<>();
        builderMap.put("None", PotType.DEFAULT);
        builderMap.put("ATT", PotType.ATT);
        builderMap.put("IED", PotType.IED);
        builderMap.put("BOSS", PotType.BOSS);
        builderMap.put("CRIT", PotType.CRITDAMAGE);
        return builderMap;
    }  
}
