/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.*;
import java.util.Collections;
/**
 *
 * @author ryanb
 */
public class WSEHelpers {
    
    public static ArrayList reduce(ArrayList potContainer, int options){
        //Sorts then shrinks the list to reduce memory overhead
        Collections.sort(potContainer);
        if(options >= 0 && potContainer.size() >= options + 1){
            return new ArrayList<>(potContainer.subList(0, options + 1));
        }
        else if(potContainer.size() >= 25){
            return new ArrayList<>(potContainer.subList(0, 25));
        }
        return potContainer;
    }
    
    public static PotType[] setupSoulsGenerationSpace(boolean sel){
        if (sel){
            return new PotType[]{PotType.DEFAULT};
        }
        else{
            return Constants.souls;
        }
    }
    
    public static PotType[][] setupEmblemGenerationSpace(boolean sel, PotConfig option){
        if (sel){
            return new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    return Constants.emblemNo3LineAtt;
                default:
                    return Constants.emblem;
            }
        }
    }
    
    public static PotType[][] setupWeaponGenerationSpace(boolean sel, PotConfig option){
        if (sel){
            return new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    return Constants.weaponNo3LineAtt;
                default:
                    return Constants.weapon;
            }
        }
    }
        
    public static PotType[][] setupSecondaryGenerationSpace(boolean sel, PotConfig option, ClassType classType){
        if (sel || classType == ClassType.ZERO){
            return new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else if (classType == ClassType.KANNA){
            return new PotType[][]{{PotType.ATT, PotType.ATT, PotType.ATT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    return Constants.secondaryNo3LineAtt;
                default:
                    return Constants.secondary;
            }
        }
    }
    
    public static ArrayList<int[]> generateHyperStats(int totalAvailablePoints){
        ArrayList<int[]> hyperStats = new ArrayList();
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
        return hyperStats;
    }
    
    public static ArrayList<int[]> generateLegion(int legionPoints){
        ArrayList<int[]> legionCombos = new ArrayList();
        if (legionPoints <= 0){
            legionCombos.add(new int[]{0, 0, 0});
        }
        else if (legionPoints >= 120){
            legionCombos.add(new int[]{40, 40, 40});
        }
        else if (legionPoints >= 80){
            int third = legionPoints - 80;
            int second = legionPoints - third - 40;
            int first = legionPoints - second - third;
            for(int i = 0; 0 < 40-third; i++){
                third += 1;
                second -= 1;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-second; i++){
                second += 1;
                first -= 1;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-first; i++){
                third -= 1;
                first += 1;
                legionCombos.add(new int[]{first, second, third});
            }
        }
        else if (legionPoints >= 40){
            int first = legionPoints - 40;
            int second = legionPoints - first;
            int third = legionPoints - second - first;
            for(int i = 0; 0 < 40-third; i++){
                third += 1;
                second -= 1;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-first; i++){
                third -= 1;
                first += 1;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < 40-second; i++){
                second += 1;
                first -= 1;
                legionCombos.add(new int[]{first, second, third});
            }
        }
        else{
            int first = legionPoints;
            int second = 0;
            int third = 0;
            for(int i = 0; 0 < legionPoints; i++){
                second++;
                first--;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < legionPoints; i++){
                third++;
                second--;
                legionCombos.add(new int[]{first, second, third});
            }
            for(int i = 0; 0 < legionPoints; i++){
                third--;
                first++;
                legionCombos.add(new int[]{first, second, third});
            }
        }
        return legionCombos;
    }
   
}
