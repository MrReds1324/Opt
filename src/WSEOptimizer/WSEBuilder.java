/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.ItemType;
import WSEOptimizer.Constants.PotConfig;
import WSEOptimizer.Constants.ClassType;

/**
 *
 * @author ryanb
 */
public class WSEBuilder {
    
    //Sets up the matrices for the potentials, nebs and legion
    //Att, IED, BOSS
    public static int[][] legcombs = new int[][]{
        {0, 0, 1},
        {0, 1, 0},
        {1, 0, 0}};
    //Att, IED, BOSS
    public static int[][] ucombs = new int[][]{
        {2, 0, 0},
        {0, 2, 0},
        {0, 0, 2},
        {1, 1, 0},
        {1, 0, 1},
        {0, 1, 1}};
    //IED, BOSS
    public static int[][] lcombs = new int[][]{
        {0, 0},
        {0, 0}};
    
    public static PotVector reb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIED, double pdr, PotConfig potConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected) {
        PotVector pt = null;
        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (int[] legcomb : legcombs) {
            for (int[] ucomb : ucombs) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials etemp;
                if (embSelected) {
                    etemp = new Potentials(0, 0, 0, 0, 0, 0, false);
                } else {
                    etemp = new Potentials(legcomb[0], legcomb[1], legcomb[2], ucomb[0], ucomb[1], ucomb[2], false);
                }
                if (etemp.feasible(ItemType.EMB, potConfig)) {
                    for (int[] legcomb1 : legcombs) {
                        for (int[] ucomb1 : ucombs) {
                            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                            Potentials wtemp;
                            if (wepSelected) {
                                wtemp = new Potentials(0, 0, 0, 0, 0, 0, sw_abs);
                            } else {
                                wtemp = new Potentials(legcomb1[0], legcomb1[1], legcomb1[2], ucomb1[0], ucomb1[1], ucomb1[2], sw_abs);
                            }
                            if (wtemp.feasible(ItemType.WEPSEC, potConfig)) {
                                //If the Zero class is selected skip the Secondary weapon as the weapon counts for both
                                if (classType == ClassType.ZERO) {
                                    Potentials stemp = wtemp;

                                    for (int[] union1 : lcombs) {
                                        Union nebu = new Union(union1[0], union1[1]);
                                        //Calculate new IED
                                        double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                        //Calculate new ATT
                                        double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                        //Calculate new BOSS
                                        double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                        //Calculates the multiplier
                                        double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                        //If the max potential vector is null then we make this one the max vector
                                        if (pt == null) {
                                            pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        } //If the current vector is better than the max vector replace it
                                        else if (calct >= pt.getCalc()) {
                                            pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        }
                                    }

                                } //If Kanna is Selected
                                else if (classType == ClassType.KANNA) {
                                    //Secondary fan only recognizes Magic Att%
                                    Potentials stemp = new Potentials(1, 0, 0, 2, 0, 0, sec160);
                                    if (secSelected) {
                                        stemp = new Potentials(0, 0, 0, 0, 0, 0, sec160);
                                    }

                                    for (int[] union1 : lcombs) {
                                        Union nebu = new Union(union1[0], union1[1]);
                                        //Calculate new IED
                                        double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                        //Calculate new ATT
                                        double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                        //Calculate new BOSS
                                        double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                        //Calculates the multiplier
                                        double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                        //Make a PotVector to contain our new configuration
                                        //If the max potential vector is null then we make this one the max vector
                                        if (pt == null) {
                                            pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        } //If the current vector is better than the max vector replace it
                                        else if (calct >= pt.getCalc()) {
                                            pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        }
                                    }

                                } //Else do the Secondary Weapon
                                else {
                                    for (int[] legcomb2 : legcombs) {
                                        for (int[] ucomb2 : ucombs) {
                                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                            Potentials stemp;
                                            if (secSelected) {
                                                stemp = new Potentials(0, 0, 0, 0, 0, 0, false);
                                            } else {
                                                stemp = new Potentials(legcomb2[0], legcomb2[1], legcomb2[2], ucomb2[0], ucomb2[1], ucomb2[2], sec160);
                                            }
                                            if (stemp.feasible(ItemType.WEPSEC, potConfig)) {

                                                for (int[] union1 : lcombs) {
                                                    Union nebu = new Union(union1[0], union1[1]);
                                                    //Calculate new IED
                                                    double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                                    //Calculate new ATT
                                                    double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                                    //Calculate new BOSS
                                                    double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                                    //Calculates the multiplier
                                                    double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                                    //Make a PotVector to contain our new configuration
                                                    //If the max potential vector is null then we make this one the max vector
                                                    if (pt == null) {
                                                        pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                                    } //If the current vector is better than the max vector replace it
                                                    else if (calct >= pt.getCalc()) {
                                                        pt = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return pt;
    }

    public static PotVector nreb_opt(double baseDamage, double baseBoss, double baseAtt, double baseIED, double pdr, PotConfig mainConfig, PotConfig bpConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, boolean secSelected, boolean embbpSelected, boolean wepbpSelected, boolean secbpSelected) {
        //If changed is true (the input values have changed) then delete the old Potvector and recalculate the configurations
        ArrayList<PotVector> main_temp = new ArrayList<>();
        ArrayList<PotVector> bonus_temp = new ArrayList<>();
        PotVector pt = null;

        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (int[] legcomb : legcombs) {
            for (int[] ucomb : ucombs) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials etemp;
                if (embSelected) {
                    etemp = new Potentials(0, 0, 0, 0, 0, 0, false);
                } else {
                    etemp = new Potentials(legcomb[0], legcomb[1], legcomb[2], ucomb[0], ucomb[1], ucomb[2], false);
                }
                if (etemp.feasible(ItemType.EMB, mainConfig)) {
                    for (int[] legcomb1 : legcombs) {
                        for (int[] ucomb1 : ucombs) {
                            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                            Potentials wtemp;
                            if (wepSelected) {
                                wtemp = new Potentials(0, 0, 0, 0, 0, 0, sw_abs);
                            } else {
                                wtemp = new Potentials(legcomb1[0], legcomb1[1], legcomb1[2], ucomb1[0], ucomb1[1], ucomb1[2], sw_abs);
                            }
                            if (wtemp.feasible(ItemType.WEPSEC, mainConfig)) {
                                //If the Zero class is selected skip the Secondary weapon as the weapon counts for both
                                if (classType == ClassType.ZERO) {
                                    Potentials stemp = wtemp;

                                    for (int[] union1 : lcombs) {
                                        Union nebu = new Union(union1[0], union1[1]);
                                        //Calculate new IED
                                        double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                        //Calculate new ATT
                                        double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                        //Calculate new BOSS
                                        double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                        //Calculates the multiplier
                                        double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                        //Make a PotVector to contain our new configuration
                                        PotVector ptm = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        //Add the configuration to the WSE array if it does not exist
                                        if (!main_temp.contains(ptm)) {
                                            main_temp.add(ptm);
                                        }
                                    }

                                } //If the Kanna class is selected
                                else if (classType == ClassType.KANNA) {
                                    //Secondary fan only recognizes Magic Att%
                                    Potentials stemp = new Potentials(1, 0, 0, 2, 0, 0, sec160);
                                    if (secSelected) {
                                        stemp = new Potentials(0, 0, 0, 0, 0, 0, sec160);
                                    }

                                    for (int[] union1 : lcombs) {
                                        Union nebu = new Union(union1[0], union1[1]);
                                        //Calculate new IED
                                        double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                        //Calculate new ATT
                                        double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                        //Calculate new BOSS
                                        double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                        //Calculates the multiplier
                                        double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                        //Make a PotVector to contain our new configuration
                                        PotVector ptm = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                        //Add the configuration to the WSE array if it does not exist
                                        if (!main_temp.contains(ptm)) {
                                            main_temp.add(ptm);
                                        }
                                    }

                                } //Else do the Secondary Weapon
                                else {
                                    for (int[] legcomb2 : legcombs) {
                                        for (int[] ucomb2 : ucombs) {
                                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                            Potentials stemp;
                                            if (secSelected) {
                                                stemp = new Potentials(0, 0, 0, 0, 0, 0, false);
                                            } else {
                                                stemp = new Potentials(legcomb2[0], legcomb2[1], legcomb2[2], ucomb2[0], ucomb2[1], ucomb2[2], sec160);
                                            }
                                            if (stemp.feasible(ItemType.WEPSEC, mainConfig)) {

                                                for (int[] union1 : lcombs) {
                                                    Union nebu = new Union(union1[0], union1[1]);
                                                    //Calculate new IED
                                                    double iedt = (1 - ((1 - baseIED) * etemp.cied() * stemp.cied() * wtemp.cied() * nebu.cied()));
                                                    //Calculate new ATT
                                                    double attt = 1 + baseAtt + etemp.catt() + stemp.catt() + wtemp.catt();
                                                    //Calculate new BOSS
                                                    double bosst = 1 + baseDamage + baseBoss + etemp.cboss() + stemp.cboss() + wtemp.cboss() + nebu.cboss();
                                                    //Calculates the multiplier
                                                    double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                                    //Make a PotVector to contain our new configuration
                                                    PotVector ptm = new PotVector(wtemp, stemp, etemp, attt - 1, bosst - baseDamage - 1, iedt, calct, nebu);
                                                    //Add the configuration to the WSE array if it does not exist
                                                    if (!main_temp.contains(ptm)) {
                                                        main_temp.add(ptm);
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int[] legcomb : legcombs) {
            for (int[] ucomb : ucombs) {
                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                Potentials etempb;
                if (embbpSelected) {
                    etempb = new Potentials(0, 0, 0, 0, 0, 0, false, true);
                } else {
                    etempb = new Potentials(legcomb[0], legcomb[1], legcomb[2], ucomb[0], ucomb[1], ucomb[2], false, true);
                }
                if (etempb.feasible(ItemType.EMB, bpConfig)) {
                    for (int[] legcomb1 : legcombs) {
                        for (int[] ucomb1 : ucombs) {
                            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                            Potentials wtempb;
                            if (wepbpSelected) {
                                wtempb = new Potentials(0, 0, 0, 0, 0, 0, sw_abs, true);
                            } else {
                                wtempb = new Potentials(legcomb1[0], legcomb1[1], legcomb1[2], ucomb1[0], ucomb1[1], ucomb1[2], sw_abs, true);
                            }
                            if (wtempb.feasible(ItemType.WEPSEC, bpConfig)) {
                                //If the Zero class is selected skip the Secondary weapon weapon bonus pot as the weapon counts for both
                                if (classType == ClassType.ZERO) {
                                    Potentials stempb = wtempb;
                                    //Calculate new IED
                                    double iedt = (1 - ((1 - baseIED) * etempb.cied() * stempb.cied() * wtempb.cied()));
                                    //Calculate new ATT
                                    double attt = 1 + baseAtt + etempb.catt() + stempb.catt() + wtempb.catt();
                                    //Calculate new BOSS
                                    double bosst = 1 + baseDamage + baseBoss + etempb.cboss() + stempb.cboss() + wtempb.cboss();
                                    //Calculates the multiplier
                                    double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                    //Make a PotVector to contain our new configuration
                                    PotVector ptb = new PotVector(wtempb, stempb, etempb, attt - 1, bosst - baseDamage - 1, iedt, calct, new Union(0, 0));
                                    //Add the configuration to the WSE array if it does not exist
                                    if (!bonus_temp.contains(ptb)) {
                                        bonus_temp.add(ptb);
                                    }
                                }
                                //If the Kanna class is selected
                                if (classType == ClassType.KANNA) {
                                    //Secondary fan only recognizes Magic Att%
                                    Potentials stempb = new Potentials(1, 0, 0, 2, 0, 0, sec160, true);
                                    if (secbpSelected) {
                                        stempb = new Potentials(0, 0, 0, 0, 0, 0, sec160, true);
                                    }
                                    //Calculate new IED
                                    double iedt = (1 - ((1 - baseIED) * etempb.cied() * stempb.cied() * wtempb.cied()));
                                    //Calculate new ATT
                                    double attt = 1 + baseAtt + etempb.catt() + stempb.catt() + wtempb.catt();
                                    //Calculate new BOSS
                                    double bosst = 1 + baseDamage + baseBoss + etempb.cboss() + stempb.cboss() + wtempb.cboss();
                                    //Calculates the multiplier
                                    double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                    //Make a PotVector to contain our new configuration
                                    PotVector ptb = new PotVector(wtempb, stempb, etempb, attt - 1, bosst - baseDamage - 1, iedt, calct, new Union(0, 0));
                                    //Add the configuration to the WSE array if it does not exist
                                    if (!bonus_temp.contains(ptb)) {
                                        bonus_temp.add(ptb);
                                    }
                                }//Else do the Secondary Weapon bonus pot
                                else {
                                    for (int[] legcomb2 : legcombs) {
                                        for (int[] ucomb2 : ucombs) {
                                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                            Potentials stempb;
                                            if (secbpSelected) {
                                                stempb = new Potentials(0, 0, 0, 0, 0, 0, false, true);
                                            } else {
                                                stempb = new Potentials(legcomb2[0], legcomb2[1], legcomb2[2], ucomb2[0], ucomb2[1], ucomb2[2], sec160, true);
                                            }
                                            if (stempb.feasible(ItemType.WEPSEC, bpConfig)) {
                                                //Calculate new IED
                                                double iedt = (1 - ((1 - baseIED) * etempb.cied() * stempb.cied() * wtempb.cied()));
                                                //Calculate new ATT
                                                double attt = 1 + baseAtt + etempb.catt() + stempb.catt() + wtempb.catt();
                                                //Calculate new BOSS
                                                double bosst = 1 + baseDamage + baseBoss + etempb.cboss() + stempb.cboss() + wtempb.cboss();
                                                //Calculates the multiplier
                                                double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                                                //Make a PotVector to contain our new configuration
                                                PotVector ptb = new PotVector(wtempb, stempb, etempb, attt - 1, bosst - baseDamage - 1, iedt, calct, new Union(0, 0));
                                                //Add the configuration to the WSE array if it does not exist
                                                if (!bonus_temp.contains(ptb)) {
                                                    bonus_temp.add(ptb);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (PotVector mpot : main_temp) {
                for (PotVector bpot : bonus_temp) {
                    //Calculate new IED
                    double iedt = (1 - ((1 - baseIED) * mpot.getWep().cied() * mpot.getSec().cied() * mpot.getEmb().cied() * mpot.getUnion().cied() * bpot.getWep().cied() * bpot.getSec().cied() * bpot.getEmb().cied()));
                    //Calculate new ATT
                    double attt = 1 + baseAtt + mpot.getWep().catt() + mpot.getSec().catt() + mpot.getEmb().catt() + bpot.getWep().catt() + bpot.getSec().catt() + bpot.getEmb().catt();
                    //Calculate new BOSS
                    double bosst = 1 + baseDamage + baseBoss + mpot.getWep().cboss() + mpot.getSec().cboss() + mpot.getEmb().cboss() + mpot.getUnion().cboss() + bpot.getWep().cboss() + bpot.getSec().cboss() + bpot.getEmb().cboss();
                    //Calculates the multiplier
                    double calct = (attt * bosst * (1 - (pdr * (1 - iedt))));
                    if (pt == null) {
                        pt = new PotVector(mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), attt - 1, bosst - baseDamage - 1, iedt, calct, mpot.getUnion());
                    }
                    if (calct >= pt.getCalc()) {
                        pt = new PotVector(mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), attt - 1, bosst - baseDamage - 1, iedt, calct, mpot.getUnion());
                    }
                }
            }
        }
        return pt;
    }
}
