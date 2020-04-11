/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import javax.swing.SwingWorker;
import WSEOptimizer.Constants.*;

/**
 *
 * @author ryan
 */
public class WSEWorker extends SwingWorker<ArrayList<PotVector>, ArrayList<PotVector>>{
    
    //Sets up the matrices for the potentials, and legion
    private static PotType[][] weapon;
    private static PotType[][] secondary;
    private static PotType[][] emblem;
    
    private static PotType[][] weaponBp;
    private static PotType[][] secondaryBp;
    private static PotType[][] emblemBp;
    
    private static PotType[] souls;
    
    private static ArrayList<int[]> hyperStats;
    
    //IED, BOSS
    private static ArrayList<int[]> lcombs;
    
    private static double baseATT;
    private static double baseBOSS;
    private static double baseDMG;
    private static double baseIED;
    private static double baseCRIT;
    private static double PDR;
    private static int options;

    @Override
    protected ArrayList<PotVector> doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static void setupMainGenerationSpace(PotType[][] weapons, PotType[][] secondaries, PotType[][] emblems, boolean wepSel, boolean secSel, boolean embSel, ClassType classType){
        if (wepSel){
            weapon = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            weapon = weapons; 
        }
        
        if (secSel || classType == ClassType.ZERO){
            secondary = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else if (classType == ClassType.KANNA){
            secondary = new PotType[][]{{PotType.ATT, PotType.ATT, PotType.ATT}};
        }
        else{
            secondary = secondaries; 
        }
        
        if (embSel){
            emblem = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            emblem = emblems; 
        }
    }
    
    public static void setupBonusGenerationSpace(PotType[][] weaponsbp, PotType[][] secondariesbp, PotType[][] emblemsbp, boolean wepbpSel, boolean secbpSel, boolean embbpSel, ClassType classType){
        if (wepbpSel){
            weaponBp = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            weaponBp = weaponsbp; 
        }
        
        if (secbpSel || classType == ClassType.ZERO){
            secondaryBp = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else if (classType == ClassType.KANNA){
            secondaryBp = new PotType[][]{{PotType.ATT, PotType.ATT, PotType.ATT}};
        }
        else{
            secondaryBp = secondariesbp; 
        }
        
        if (embbpSel){
            emblemBp = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            emblemBp = emblemsbp; 
        }
    }
    
    public void setupHyperStats(int totalAvailablePoints){
        hyperStats = new ArrayList();
        totalAvailablePoints = totalAvailablePoints > 1266 ?  1266 : totalAvailablePoints;
        for (int cd = 0; cd <= 15; cd++){
            int cdCost = Constants.hyperStatCosts[cd];
            if (cdCost > totalAvailablePoints){
                continue;
            }
            for (int bd = 0; bd <=15; bd++){
                int bdCost = Constants.hyperStatCosts[bd];
                if (bdCost + cdCost > totalAvailablePoints){
                    continue;
                }
                for (int dd = 0; dd <= 15; dd++){
                    int ddCost = Constants.hyperStatCosts[dd];
                    if (ddCost + bdCost + cdCost > totalAvailablePoints){
                        continue;
                    }
                    for (int ied = 15; ied >= 0; ied--){
                        int iedCost = Constants.hyperStatCosts[ied];
                        if (iedCost + ddCost + bdCost + cdCost > totalAvailablePoints){
                            continue;
                        }
                        // If we can afford more levels then ignore
                        int cur_available = totalAvailablePoints - (cdCost + bdCost + ddCost + iedCost);
                        if ((cd + 1 <= 15 && (Constants.hyperStatCosts[cd + 1] - Constants.hyperStatCosts[cd]) <= cur_available) || (bd + 1 <= 15 && (Constants.hyperStatCosts[bd + 1] - Constants.hyperStatCosts[bd]) <= cur_available) || (dd + 1 <= 15 && (Constants.hyperStatCosts[dd + 1] - Constants.hyperStatCosts[dd]) <= cur_available)){
                            break;
                        }
                        else{
                            hyperStats.add(new int[]{cd, bd, dd, ied});
                            break;
                        }
                    }
                }
            }
        }
        if (hyperStats.isEmpty()){
            hyperStats.add(new int[]{0, 0, 0, 0});
        }
    }
    
    public void setupLegion(int legionPoints){
        lcombs = new ArrayList();
        if (legionPoints <= 0){
            lcombs.add(new int[]{0, 0, 0});
        }
        else if (legionPoints >= 120){
            lcombs.add(new int[]{40, 40, 40});
        }
        else if (legionPoints >= 80){
            int third = legionPoints - 80;
            int second = legionPoints - third - 40;
            int first = legionPoints - second - third;
            for(int i = 0; 0 < 40-third; i++){
                third += 1;
                second -= 1;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-second; i++){
                second += 1;
                first -= 1;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-first; i++){
                third -= 1;
                first += 1;
                lcombs.add(new int[]{first, second, third});
            }
        }
        else if (legionPoints >= 40){
            int first = legionPoints - 40;
            int second = legionPoints - first;
            int third = legionPoints - second - first;
            for(int i = 0; 0 < 40-third; i++){
                third += 1;
                second -= 1;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-first; i++){
                third -= 1;
                first += 1;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-second; i++){
                second += 1;
                first -= 1;
                lcombs.add(new int[]{first, second, third});
            }
        }
        else{
            int first = legionPoints;
            int second = 0;
            int third = 0;
            for(int i = 0; 0 < legionPoints; i++){
                second++;
                first--;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < legionPoints; i++){
                third++;
                second--;
                lcombs.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < legionPoints; i++){
                third--;
                first++;
                lcombs.add(new int[]{first, second, third});
            }
        }
    }
}
