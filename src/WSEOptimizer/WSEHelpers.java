/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.*;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 * @author ryanb
 */
public class WSEHelpers {
    
    public static PotType[] soulSpace = new PotType[]{PotType.DEFAULT};
    public static PotType[][] emblemSpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static PotType[][] emblembpSpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static PotType[][] weaponSpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static PotType[][] weaponbpSpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static PotType[][] secondarySpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static PotType[][] secondarybpSpace = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
    public static ArrayList<int[]> hyperStatsSpace = new ArrayList(Arrays.asList(new int[]{0, 0, 0, 0}));
    public static ArrayList<int[]> legionSpace = new ArrayList(Arrays.asList(new int[]{0, 0, 0}));
    public static ArrayList<Familiars> familiarSpace = new ArrayList(Arrays.asList(new Familiars()));
    
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
    
    public static void setupSoulsGenerationSpace(boolean sel){
        if (sel){
            soulSpace = new PotType[]{PotType.DEFAULT};
        }
        else{
            soulSpace = Constants.souls;
        }
    }
    
    public static void setupEmblemGenerationSpace(boolean sel, PotConfig option, PotType mb){
        PotType[][] local_space;
        if (sel){
            local_space = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    local_space = Constants.emblemNo3LineAtt;
                default:
                    local_space = Constants.emblem;
            }
        }
        
        switch(mb){
            case MAIN:
                emblemSpace = local_space;
                break;
            case BONUS:
                emblembpSpace = local_space;
                break;
        }
    }
    
    public static void setupWeaponGenerationSpace(boolean sel, PotConfig option, PotType mb){
        PotType[][] local_space;
        if (sel){
            local_space = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    local_space = Constants.weaponNo3LineAtt;
                default:
                    local_space = Constants.weapon;
            }
        }
        
        switch(mb){
            case MAIN:
                weaponSpace = local_space;
                break;
            case BONUS:
                weaponbpSpace = local_space;
                break;
        }
    }
        
    public static void setupSecondaryGenerationSpace(boolean sel, PotConfig option, ClassType classType, PotType mb){
        PotType[][] local_space;
        if (sel || classType == ClassType.ZERO){
            local_space = new PotType[][]{{PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT}};
        }
        else if (classType == ClassType.KANNA){
            local_space = new PotType[][]{{PotType.ATT, PotType.ATT, PotType.ATT}};
        }
        else{
            switch(option){
                case NO3LINE:
                    local_space = Constants.secondaryNo3LineAtt;
                default:
                    local_space = Constants.secondary;
            }
        }
        
        switch(mb){
            case MAIN:
                secondarySpace = local_space;
                break;
            case BONUS:
                secondarybpSpace = local_space;
                break;
        }
    }
    
    public static void generateHyperStats(int totalAvailablePoints){
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
        hyperStatsSpace = hyperStats;
    }
    
    public static void generateLegion(int legionPoints){
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
        legionSpace = legionCombos;
    }
   
    public static void generateFamiliars(int numFamiliarLines, FamiliarTier top){   
        ArrayList<Familiars> familiarCombos = new ArrayList();
        ArrayList<int[]> familiarTops = new ArrayList();
        ArrayList<int[]> familiarBots = new ArrayList();
        
        int toplines = 0;
        int botlines = 0;

        // Determine the correct number of top and bottom lines - preferencing top lines over bot
        if (numFamiliarLines >= 3){
            toplines = 3;
            botlines = numFamiliarLines - 3;
        }
        else{
            toplines = numFamiliarLines;
            botlines = 0;
        }

        switch(top){
            case DEFAULT:
                familiarTops.add(new int[]{0, 0, 0, 0});
                break;
            case LEGENDARY:
            case UNIQUE:
            case EPIC:
            case RARE:
            case COMMON:
                // Iterate combinations of top lines
                for(int boss = 0; boss <= toplines; boss++){
                    for(int att = 0; att <= toplines; att++){
                        for(int ied = 0; ied <= toplines; ied++){
                            for(int cd = 0; cd <= toplines; cd++){
                                if ((boss + att + ied + cd) == toplines){
                                    familiarTops.add(new int[]{att, ied, boss, cd});
                                    //[ATT, IED, BOSS, Crit Damage]
                                }
                            }
                        }
                    }
                }
                break;
        }
                
        switch(top){
            case DEFAULT:
            case COMMON:
                familiarBots.add(new int[]{0, 0, 0, 0});
                break;
            case LEGENDARY:
            case UNIQUE:
            case EPIC:
            case RARE:
                // Iterate combinations of bot lines
                for(int boss = 0; boss <= botlines; boss++){
                    for(int att = 0; att <= botlines; att++){
                        for(int ied = 0; ied <= botlines; ied++){
                            for(int cd = 0; cd <= botlines; cd++){
                                if ((boss + att + ied + cd) == botlines){
                                    familiarBots.add(new int[]{att, ied, boss, cd});
                                    //[ATT, IED, BOSS, Crit Damage]
                                }
                            }
                        }
                    }
                }
                break;
        }
        
        for(int[] tops: familiarTops){
            for(int[] bots: familiarBots){
                // Attempt to reduce the total number of combinations as well as validate combos based on tier
                //[ATT, IED, BOSS, Crit Damage]
                if (top == FamiliarTier.LEGENDARY && (tops[2] + bots[2]) > 3){
                    // Limit the amount of boss to as close to 120 as possible
                }
                else if (top == FamiliarTier.UNIQUE && (tops[2] + bots[2]) > 4){
                    // Limit the amount of boss to as close to 120 as possible
                }
                else if (top == FamiliarTier.EPIC && (tops[3] > 0 || bots[3] > 0)){
                    // No crit damage on EPIC tier top lines, No crit damage on RARE tier bottom lines
                }
                else if (top == FamiliarTier.RARE && ( tops[3] > 0 || bots[3] > 0)){
                    //No crit damage on RARE tier top lines, no crit damage on COMMON tier bot lines
                }
                else if (top == FamiliarTier.COMMON && tops[0] + tops[2] == toplines){
                    // Common ONLY has ATT and damage lines - will ignore bottom lines
                    familiarCombos.add(new Familiars(tops, bots, top));
                }
                else{
                    familiarCombos.add(new Familiars(tops, bots, top));
                }
            }
        }        
        

        familiarSpace = familiarCombos;
    }
    
}
