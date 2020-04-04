/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.PotConfig;
import WSEOptimizer.Constants.ClassType;
import WSEOptimizer.Constants.PotType;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ryanb
 */
public class WSEBuilder {
   
    //Sets up the matrices for the potentials, and legion
    public static PotType[][] weapon = new PotType[][]{
        // ATT Legendary line then two Unique lines
        {PotType.ATT, PotType.ATT, PotType.ATT},
        {PotType.ATT, PotType.ATT, PotType.BOSS},
        {PotType.ATT, PotType.ATT, PotType.IED},
        {PotType.ATT, PotType.BOSS, PotType.BOSS},
        {PotType.ATT, PotType.IED, PotType.IED},
        {PotType.ATT, PotType.IED, PotType.BOSS},
        // BOSS Legendary line then two Unique lines
        {PotType.BOSS, PotType.BOSS, PotType.ATT},
        {PotType.BOSS, PotType.BOSS, PotType.IED},
        {PotType.BOSS, PotType.ATT, PotType.ATT},
        {PotType.BOSS, PotType.IED, PotType.IED},
        {PotType.BOSS, PotType.IED, PotType.ATT},
        // IED Legendary line then two Unique lines
        {PotType.IED, PotType.IED, PotType.BOSS},
        {PotType.IED, PotType.IED, PotType.ATT},
        {PotType.IED, PotType.BOSS, PotType.BOSS},
        {PotType.IED, PotType.ATT, PotType.ATT},
        {PotType.IED, PotType.ATT, PotType.BOSS}};
    
    public static PotType[][] secondary = weapon;
    
    public static PotType[][] emblem = new PotType[][]{
        // ATT Legendary line then two Unique lines
        {PotType.ATT, PotType.ATT, PotType.ATT},
        {PotType.ATT, PotType.ATT, PotType.IED},
        {PotType.ATT, PotType.IED, PotType.IED},
        // IED Legendary line then two Unique lines
        {PotType.IED, PotType.IED, PotType.ATT},
        {PotType.IED, PotType.ATT, PotType.ATT}};
    
    public static PotType[] souls = new PotType[]{PotType.ATT, PotType.BOSS, PotType.IED};

    //IED, BOSS
    public static int[][] lcombs = new int[][]{
        {0, 0},
        {0, 0}};

    @SuppressWarnings("unchecked")
    public static List<PotVector> reb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIED, double pdr, PotConfig potConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected, PotType soulSelected, int numberOfOptions) {
        if (soulSelected != PotType.DEFAULT){
            souls = new PotType[]{soulSelected};
        }
        List<PotVector> potVectorList = new ArrayList();
        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (PotType soul : souls){
            for (PotType[] emb : emblem) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials etemp;
                if (embSelected) {
                    etemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false);
                } else {
                    etemp = new Potentials(emb[0], emb[1], emb[2], false);
                }
                for (PotType[] wep : weapon) {
                    //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                    Potentials wtemp;
                    if (wepSelected) {
                        wtemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sw_abs);
                    } else {
                        wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                    }
                    //If the Zero class is selected skip the Secondary weapon as the weapon counts for both
                    if (classType == ClassType.ZERO) {
                        Potentials stemp = wtemp;
                        for (int[] union1 : lcombs) {
                            Union union = new Union(union1[0], union1[1]);
                            //Add the potVector to the list
                            PotVector temp = new PotVector(wtemp, stemp, etemp, union, soul);
                            temp.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                            if (!potVectorList.contains(temp)) {
                                potVectorList.add(temp);
                            }
                        }
                    } //If Kanna is Selected
                    else if (classType == ClassType.KANNA) {
                        //Secondary fan only recognizes Magic Att%
                        Potentials stemp = new Potentials(PotType.ATT, PotType.ATT, PotType.ATT, sec160);
                        if (secSelected) {
                            stemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sec160);
                        }
                        for (int[] union1 : lcombs) {
                            Union union = new Union(union1[0], union1[1]);
                            //Add the potVector to the list
                            PotVector temp = new PotVector(wtemp, stemp, etemp, union, soul);
                            temp.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                            if (!potVectorList.contains(temp)) {
                                potVectorList.add(temp);
                            }
                        }
                    } //Else do the Secondary Weapon
                    else {
                        for (PotType[] sec : secondary) {
                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                            Potentials stemp;
                            if (secSelected) {
                                stemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false);
                            } else {
                                stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                            }
                            for (int[] union1 : lcombs) {
                                Union union = new Union(union1[0], union1[1]);
                                //Add the potVector to the list
                                PotVector temp = new PotVector(wtemp, stemp, etemp, union, soul);
                                temp.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                                if (!potVectorList.contains(temp)) {
                                    potVectorList.add(temp);
                                }
                            }
                        }
                    }
                }
            }
        }

        //Build the list of the top number of potVectors
        Collections.sort(potVectorList);
        if(potVectorList.size() >= numberOfOptions + 1){
            return potVectorList.subList(0, numberOfOptions + 1);
        }
        else{
            return potVectorList;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<PotVector> nreb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIED, double pdr, PotConfig mainConfig, PotConfig bpConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected, boolean embbpSelected, boolean wepbpSelected, boolean secbpSelected, PotType soulSelected, int numberOfOptions) {
        if (soulSelected != PotType.DEFAULT){
            souls = new PotType[]{soulSelected};
        }
        //If changed is true (the input values have changed) then delete the old Potvector and recalculate the configurations
        ArrayList<PotVector> main_temp = new ArrayList();
        ArrayList<PotVector> bonus_temp = new ArrayList();
        ArrayList<PotVector> potVectorList = new ArrayList();

        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (PotType[] emb : emblem) {
            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
            Potentials etemp;
            if (embSelected) {
                etemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false);
            } else {
                etemp = new Potentials(emb[0], emb[1], emb[2], false);
            }
            for (PotType[] wep : weapon) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials wtemp;
                if (wepSelected) {
                    wtemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sw_abs);
                } else {
                    wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                }
                //If the Zero class is selected skip the Secondary weapon as the weapon counts for both
                if (classType == ClassType.ZERO) {
                    Potentials stemp = wtemp;

                    for (int[] union1 : lcombs) {
                        Union union = new Union(union1[0], union1[1]);
                        //Add the potVector to the list
                        PotVector ptm = new PotVector(wtemp, stemp, etemp, union, null);
                        ptm.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                        //Add the configuration to the WSE array if it does not exist
                        if (!main_temp.contains(ptm)) {
                            main_temp.add(ptm);
                        }
                    }

                } //If the Kanna class is selected
                else if (classType == ClassType.KANNA) {
                    //Secondary fan only recognizes Magic Att%
                    Potentials stemp = new Potentials(PotType.ATT, PotType.ATT, PotType.ATT, sec160);
                    if (secSelected) {
                        stemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sec160);
                    }

                    for (int[] union1 : lcombs) {
                        Union union = new Union(union1[0], union1[1]);
                        //Add the potVector to the list
                        PotVector ptm = new PotVector(wtemp, stemp, etemp, union, null);
                        ptm.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                        //Add the configuration to the WSE array if it does not exist
                        if (!main_temp.contains(ptm)) {
                            main_temp.add(ptm);
                        }
                    }

                } //Else do the Secondary Weapon
                else {
                    for (PotType[] sec : secondary) {
                        //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                        Potentials stemp;
                        if (secSelected) {
                            stemp = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false);
                        } else {
                            stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                        }
                        for (int[] union1 : lcombs) {
                            Union union = new Union(union1[0], union1[1]);
                            //Add the potVector to the list
                            PotVector ptm = new PotVector(wtemp, stemp, etemp, union, null);
                            ptm.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                            //Add the configuration to the WSE array if it does not exist
                            if (!main_temp.contains(ptm)) {
                                main_temp.add(ptm);
                            }
                        }
                    }
                }
            }
        }

        for (PotType[] emb : emblem) {
            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
            Potentials etempb;
            if (embbpSelected) {
                etempb = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false, true);
            } else {
                etempb = new Potentials(emb[0], emb[1], emb[2], false, true);
            }
            for (PotType[] wep : weapon) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials wtempb;
                if (wepbpSelected) {
                    wtempb = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sw_abs, true);
                } else {
                    wtempb = new Potentials(wep[0], wep[1], wep[2], sw_abs, true);
                }
                //If the Zero class is selected skip the Secondary weapon weapon bonus pot as the weapon counts for both
                if (classType == ClassType.ZERO) {
                    Potentials stempb = wtempb;
                    //Add the potVector to the list
                    PotVector ptb = new PotVector(wtempb, stempb, etempb, new Union(0, 0), null);
                    ptb.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                    //Add the configuration to the WSE array if it does not exist
                    if (!bonus_temp.contains(ptb)) {
                        bonus_temp.add(ptb);
                    }
                }
                //If the Kanna class is selected
                if (classType == ClassType.KANNA) {
                    //Secondary fan only recognizes Magic Att%
                    Potentials stempb = new Potentials(PotType.ATT, PotType.ATT, PotType.ATT, sec160, true);
                    if (secbpSelected) {
                        stempb = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, sec160, true);
                    }
                   //Add the potVector to the list
                    PotVector ptb = new PotVector(wtempb, stempb, etempb, new Union(0, 0), null);
                    ptb.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                    //Add the configuration to the WSE array if it does not exist
                    if (!bonus_temp.contains(ptb)) {
                        bonus_temp.add(ptb);
                    }
                }//Else do the Secondary Weapon bonus pot
                else {
                    for (PotType[] sec : secondary) {
                        //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                        Potentials stempb;
                        if (secbpSelected) {
                            stempb = new Potentials(PotType.DEFAULT, PotType.DEFAULT, PotType.DEFAULT, false, true);
                        } else {
                            stempb = new Potentials(sec[0], sec[1], sec[2], sec160, true);
                        }
                        //Add the potVector to the list
                        PotVector ptb = new PotVector(wtempb, stempb, etempb, new Union(0, 0), null);
                        ptb.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                        //Add the configuration to the WSE array if it does not exist
                        if (!bonus_temp.contains(ptb)) {
                            bonus_temp.add(ptb);
                        }
                    }
                }
            }
        }
        //Combines both main and bonus pots to generate all combinations of the two
        for (PotType soul : souls){
            for (PotVector mpot : main_temp) {
                for (PotVector bpot : bonus_temp) {
                    PotVector temp = new PotVector(mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), mpot.getUnion(), soul);
                    temp.calculcateMultiplier(baseAtt, baseBoss, baseDamage, baseIED, pdr);
                    //Adds the potVector to the array list
                    potVectorList.add(temp);
                }
            }
        }
        //Sorts then returns only the values we want
        Collections.sort(potVectorList);
        if(potVectorList.size() >= numberOfOptions + 1){
            return potVectorList.subList(0, numberOfOptions + 1);
        }
        else{
            return potVectorList;
        }
    }
}
