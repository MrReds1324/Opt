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
    private PotType[][] weapon;
    private PotType[][] secondary;
    private PotType[][] emblem;
    
    private PotType[][] weaponBp;
    private PotType[][] secondaryBp;
    private PotType[][] emblemBp;
    
    private PotType[] souls;
    
    private ArrayList<int[]> hyperStats;
    
    //IED, BOSS
    private ArrayList<int[]> legion;
    
    private double baseATT;
    private double baseBOSS;
    private double baseDMG;
    private double baseIED;
    private double baseCRIT;
    private double PDR;
    private ClassType classType;
    private boolean sec160;
    private boolean sw_abs;
    private int options;
    private Server server;

    public WSEWorker(double baseDamage, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, int hyperPoints, int legionPoints, 
            PotConfig mainConfig, PotConfig bpConfig, ClassType classType, boolean sw_abs, boolean sec160, boolean embSelected, boolean wepSelected, 
            boolean secSelected, boolean embbpSelected, boolean wepbpSelected, boolean secbpSelected, PotType soulSelected, int numberOfOptions, Server server){
        this.baseDMG = baseDamage;
        this.baseBOSS = baseBoss;
        this.baseATT = baseAtt;
        this.baseIED = baseIed;
        this.baseCRIT = baseCrit;
        this.PDR = pdr;
        this.hyperStats = WSEHelpers.generateHyperStats(hyperPoints);
        this.legion = WSEHelpers.generateLegion(legionPoints);
        this.weapon = WSEHelpers.setupWeaponGenerationSpace(wepSelected, mainConfig);
        this.secondary = WSEHelpers.setupSecondaryGenerationSpace(secSelected, mainConfig, classType);
        this.emblem = WSEHelpers.setupEmblemGenerationSpace(embSelected, mainConfig);
        this.weaponBp = WSEHelpers.setupWeaponGenerationSpace(wepbpSelected, bpConfig);
        this.secondary = WSEHelpers.setupSecondaryGenerationSpace(secbpSelected, bpConfig, classType);
        this.emblem = WSEHelpers.setupEmblemGenerationSpace(embbpSelected, bpConfig);
        this.souls = WSEHelpers.setupSoulsGenerationSpace(soulSelected);
        this.options = numberOfOptions;
        this.server = server;
        this.classType = classType;
        this.sec160 = sec160;
        this.sw_abs = sw_abs;
    }

    @Override
    protected ArrayList<PotVector> doInBackground() throws Exception {
        switch(server){
            case REBOOT:
                ArrayList<WSEOptimizationThread> threads = new ArrayList();
                ArrayList<PotVector> potVectorList = new ArrayList();
                //Carries out the optimization beginning with Emblem to find the perfect configuration
                for (int[] hyper: hyperStats){
                    threads.add(new WSEOptimizationThread(hyper, legion, weapon, secondary, emblem, souls, classType, baseDMG, baseBOSS, baseATT, baseIED, baseCRIT, PDR, sw_abs, sec160, options, Server.REBOOT));
                }
                System.out.println();
                for (WSEOptimizationThread thread : threads){
                    thread.start();
                }
                for (WSEOptimizationThread thread : threads){
                    try {
                        thread.join();
                        potVectorList.addAll(thread.getPotVectors());
                    } catch (InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                }
                setProgress(100);
                return WSEHelpers.reduce(potVectorList, options);
            case NONREBOOT:
                ArrayList<PotVector> main_temp = new ArrayList();
                ArrayList<PotVector> bonus_temp = new ArrayList();
                potVectorList = new ArrayList();

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
                                main_temp.add(new PotVector(wtemp, wtemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, PotType.DEFAULT));
                                break;
                            case KANNA:
                                //Secondary fan only recognizes Magic Att%
                                Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);
                                main_temp.add(new PotVector(wtemp, stemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, PotType.DEFAULT));
                                break;
                            default:
                                for (PotType[] sec : secondary) {
                                    //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                    stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                    main_temp.add(new PotVector(wtemp, stemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, PotType.DEFAULT));
                                }
                                break;
                        }
                    }
                }

                threads = new ArrayList();
                long totalGenerationSpace = hyperStats.size() * souls.length * emblem.length * weapon.length * secondary.length * emblemBp.length * weaponBp.length * secondaryBp.length * lcombs.size();
                //Combines both main and bonus pots to generate all combinations of the two
                for (int[] hyper : hyperStats){
                    threads.add(new WSEOptimizationThread(hyper, legion, main_temp, bonus_temp, souls, baseDMG, baseBOSS, baseATT, baseIED, baseCRIT, PDR, options, Server.NONREBOOT));
                }
                for (WSEOptimizationThread thread : threads){
                    thread.start();
                }
                for (WSEOptimizationThread thread : threads){
                    try {
                        thread.join();
                        potVectorList.addAll(thread.getPotVectors());
                    } catch (InterruptedException ex) {
                        System.out.println(ex.toString());
                    }
                }
                return WSEHelpers.reduce(potVectorList, options);
            }
         return null;
        }
    }
