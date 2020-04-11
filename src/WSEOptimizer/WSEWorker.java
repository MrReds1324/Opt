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
        ArrayList<WSEOptimizationThread> threads = new ArrayList();
        ArrayList<PotVector> potVectorList = new ArrayList();
        //Carries out the optimization beginning with Emblem to find the perfect configuration
        for (int[] hyper: hyperStats){
            threads.add(new WSEOptimizationThread(hyper, legion, weapon, secondary, emblem, souls, classType, sw_abs, sec160, Server.REBOOT));
//            for (PotType soul : souls){
//                for (PotType[] emb : emblem) {
//                    //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
//                    Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
//                    for (PotType[] wep : weapon) {
//                        //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
//                        Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
//                        switch (classType) {
//                            case ZERO:
//                                potVectorList = legionAndAddReduce(potVectorList, wtemp, wtemp, etemp, null, null, null, hyper, soul);
//                                break;
//                            case KANNA:
//                                //Secondary fan only recognizes Magic Att%
//                                Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);
//                                potVectorList = legionAndAddReduce(potVectorList, wtemp, stemp, etemp, null, null, null, hyper, soul);
//                                break;
//                            default:
//                                for (PotType[] sec : secondary) {
//                                    //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
//                                    stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
//                                    potVectorList = legionAndAddReduce(potVectorList, wtemp, stemp, etemp, null, null, null, hyper, soul);
//                                }
//                                break;
//                        }
//                    }
//                }
//            }
//            System.out.println(String.format("%.3f%% Completed", (double)counter/totalGenerationSpace * 100));
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
    }
       
}
