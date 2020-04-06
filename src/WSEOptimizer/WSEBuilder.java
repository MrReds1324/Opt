/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.*;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ryanb
 */
public class WSEBuilder {
   
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
    public static int[][] lcombs = new int[][]{
        {0, 0},
        {0, 0}};
    
    private static double baseATT;
    private static double baseBOSS;
    private static double baseDMG;
    private static double baseIED;
    private static double baseCRIT;
    private static double PDR;
    private static int options;
    
    @SuppressWarnings("unchecked")
    public static List<PotVector> reb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, int hyperPoints, PotConfig potConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected, PotType soulSelected, int numberOfOptions) {
        baseATT = baseAtt;
        baseBOSS = baseBoss;
        baseDMG = baseDamage;
        baseIED = baseIed;
        baseCRIT = baseCrit;
        PDR = pdr;
        options = numberOfOptions;
        
        setupHyperStats(hyperPoints);
        //Start time of the method
        long startTime = System.nanoTime();
        //Sets up the matrices for the potentials, and legion
        switch (potConfig){
            case NO3LINE:
                setupMainGenerationSpace(Constants.weaponNo3LineAtt, Constants.secondaryNo3LineAtt, Constants.emblemNo3LineAtt, wepSelected, secSelected, embSelected, classType);
                break;
            default:
                setupMainGenerationSpace(Constants.weapon, Constants.secondary, Constants.emblem, wepSelected, secSelected, embSelected, classType);
                break;
        }
        
        switch (soulSelected){
            case DEFAULT:
                souls = Constants.souls;
                break;
            default:
                souls = new PotType[]{soulSelected};
                break;
        }
        
        ArrayList<PotVector> potVectorList = new ArrayList();
        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (int[] hyper: hyperStats){
            for (PotType soul : souls){
                for (PotType[] emb : emblem) {
                    //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                    Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
                    for (PotType[] wep : weapon) {
                        //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                        Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                        switch (classType) {
                            case ZERO:
                                potVectorList = legionAndAddReduce(potVectorList, wtemp, wtemp, etemp, hyper, soul, true);
                                break;
                            case KANNA:
                                //Secondary fan only recognizes Magic Att%
                                Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);
                                potVectorList = legionAndAddReduce(potVectorList, wtemp, stemp, etemp, hyper, soul, true);
                                break;
                            default:
                                for (PotType[] sec : secondary) {
                                    //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                    stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                    potVectorList = legionAndAddReduce(potVectorList, wtemp, stemp, etemp, hyper, soul, true);
                                }
                                break;
                        }
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Execution time in seconds : " + (endTime - startTime) / 1000000000.0);
        return potVectorList;
    }

    @SuppressWarnings("unchecked")
    public static List<PotVector> nreb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, int hyperPoints, PotConfig mainConfig, PotConfig bpConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected, boolean embbpSelected, boolean wepbpSelected, boolean secbpSelected, PotType soulSelected, int numberOfOptions) {
        baseATT = baseAtt;
        baseBOSS = baseBoss;
        baseDMG = baseDamage;
        baseIED = baseIed;
        baseCRIT = baseCrit;
        PDR = pdr;
        options = numberOfOptions;
        
        //Start time of the method
        long startTime = System.nanoTime();
        setupHyperStats(hyperPoints);
        //Sets up the matrices for the potentials, and legion
        switch (mainConfig){
            case NO3LINE:
                setupMainGenerationSpace(Constants.weaponNo3LineAtt, Constants.secondaryNo3LineAtt, Constants.emblemNo3LineAtt, wepSelected, secSelected, embSelected, classType);
                break;
            default:
                setupMainGenerationSpace(Constants.weapon, Constants.secondary, Constants.emblem, wepSelected, secSelected, embSelected, classType);
                break;
        }
        
        switch (bpConfig){
            case NO3LINE:
                setupBonusGenerationSpace(Constants.weaponNo3LineAtt, Constants.secondaryNo3LineAtt, Constants.emblemNo3LineAtt, wepbpSelected, secbpSelected, embbpSelected, classType);
                break;
            default:
                setupBonusGenerationSpace(Constants.weapon, Constants.secondary, Constants.emblem, wepbpSelected, secbpSelected, embbpSelected, classType);
                break;
        }
        
        switch (soulSelected){
            case DEFAULT:
                souls = Constants.souls;
                break;
            default:
                souls = new PotType[]{soulSelected};
                break;
        }
        
        //If changed is true (the input values have changed) then delete the old Potvector and recalculate the configurations
        ArrayList<PotVector> main_temp = new ArrayList();
        ArrayList<PotVector> bonus_temp = new ArrayList();
        ArrayList<PotVector> potVectorList = new ArrayList();

        for (PotType[] emb : emblemBp) {
            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
            Potentials etempb = new Potentials(emb[0], emb[1], emb[2], false, true);
            for (PotType[] wep : weaponBp) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials wtempb = new Potentials(wep[0], wep[1], wep[2], sw_abs, true);
                switch (classType){
                    case ZERO:
                        //Add the potVector to the list
                        PotVector ptb = new PotVector(wtempb, wtempb, etempb, null, null, null);
                        //Add the configuration to the WSE array if it does not exist
                        bonus_temp.add(ptb);
                        break;
                    case KANNA:
                        //Secondary fan only recognizes Magic Att%
                        Potentials stempb = new Potentials(secondaryBp[0][0], secondaryBp[0][1], secondaryBp[0][2], sec160, true);
                        //Add the potVector to the list
                        ptb = new PotVector(wtempb, stempb, etempb, null, null, null);
                        //Add the configuration to the WSE array if it does not exist
                        bonus_temp.add(ptb);
                        break;
                    default:
                        for (PotType[] sec : secondaryBp) {
                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                            stempb = new Potentials(sec[0], sec[1], sec[2], sec160, true);
                            //Add the potVector to the list
                            ptb = new PotVector(wtempb, stempb, etempb, null, null, null);
                            //Add the configuration to the WSE array if it does not exist
                            bonus_temp.add(ptb);
                        }
                        break;
                }
            }
        }
        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (PotType[] emb : emblem) {
            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
            Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
            for (PotType[] wep : weapon) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                switch (classType) {
                    case ZERO:
                        legionAndAddReduce(main_temp, wtemp, wtemp, etemp, new int[]{0, 0, 0, 0}, PotType.DEFAULT, false);
                        break;
                    case KANNA:
                        //Secondary fan only recognizes Magic Att%
                        Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);  
                        legionAndAddReduce(main_temp, wtemp, stemp, etemp, new int[]{0, 0, 0, 0}, PotType.DEFAULT, false);
                        break;
                    default:
                        for (PotType[] sec : secondary) {
                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                            stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                            legionAndAddReduce(main_temp, wtemp, stemp, etemp, new int[]{0, 0, 0, 0}, PotType.DEFAULT, false);
                        }
                        break;
                }
            }
        }
        
        //Combines both main and bonus pots to generate all combinations of the two
        for (int[] hyper : hyperStats){
            for (PotType soul : souls){
                for (PotVector mpot : main_temp) {
                    for (PotVector bpot : bonus_temp) {
                        PotVector temp = new PotVector(mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), mpot.getLegion(), hyper, soul);
                        temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
                        //Adds the potVector to the array list
                        potVectorList.add(temp);
                    }
                    //Sorts then shrinks the list to reduce memory overhead
                    Collections.sort(potVectorList);
                    if(potVectorList.size() >= numberOfOptions + 1){
                        potVectorList = new ArrayList<>(potVectorList.subList(0, options + 1));
                    }
                    else if(potVectorList.size() >= 100){
                        potVectorList = new ArrayList<>(potVectorList.subList(0, 100));
                    }
                }  
            }
        }
            long endTime = System.nanoTime();
            System.out.println("Execution time in seconds : " + (endTime - startTime) / 1000000000.0);
            return potVectorList;
    }
   
    public static ArrayList legionAndAddReduce(ArrayList potContainer, Potentials wepTemp, Potentials secTemp, Potentials embTemp, int[] hyperStats, PotType soul, boolean reduce){
        // If we have put a number 80 or greater for Legion then we only need the first combination of BOSS + IED
        if (lcombs[0][0] == lcombs[0][1]){
            //Add the potVector to the list
            PotVector temp = new PotVector(wepTemp, secTemp, embTemp, lcombs[0], hyperStats, soul);
            temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
            potContainer.add(temp);
        }
        else{
            for (int[] legion : lcombs) {
                //Add the potVector to the list
                PotVector temp = new PotVector(wepTemp, secTemp, embTemp, legion, hyperStats, soul);
                temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
                potContainer.add(temp);
            }
        }
        if (reduce){
            //Sorts then shrinks the list to reduce memory overhead
            Collections.sort(potContainer);
            if(potContainer.size() >= options + 1){
                return new ArrayList<>(potContainer.subList(0, options + 1));
            }
            else if(potContainer.size() >= 100){
                return new ArrayList<>(potContainer.subList(0, 100));
            }
        }
        return potContainer;
    }
    
    public static void setupMainGenerationSpace(PotType[][] weapons, PotType[][] secondaries, PotType[][] emblems, boolean wepSel, boolean secSel, boolean embSel, ClassType classType){
        if (wepSel){
            weapon = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            weapon = weapons; 
        }
        
        if (secSel){
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
        
        if (secbpSel){
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
    
    public static void setupHyperStats(int totalAvailablePoints){
        hyperStats = new ArrayList();
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
   
}
