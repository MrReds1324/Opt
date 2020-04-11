package WSEOptimizer;

import java.util.ArrayList;
import WSEOptimizer.Constants.*;
import java.util.concurrent.Callable;

/**
 *
 * @author ryan
 */
public class WSEOptimizationThread implements Callable<ArrayList<PotVector>> {
    private int[] hyper;
    private PotType[][] weapon;
    private PotType[][] secondary;
    private PotType[][] emblem;
    
    private PotType[] souls;
    private ArrayList<int[]> legion;
    
    private boolean sec160;
    private boolean sw_abs;
    private ClassType classType;
    private Server server;
    
    private double baseATT;
    private double baseBOSS;
    private double baseDMG;
    private double baseIED;
    private double baseCRIT;
    private double PDR;
    private int options;
    
    private ArrayList<PotVector> mainPots;
    private ArrayList<PotVector> bonusPots;
    
    public ArrayList<PotVector> reducedOptimize;
    
    public WSEOptimizationThread(int[] hyper, ArrayList<int[]> legion, PotType[][] weapon, PotType[][] secondary, PotType[][] emblem, PotType[] souls, ClassType classType,
            double baseDMG, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, boolean sw_abs, boolean sec160, int options, Server server){
        this.hyper = hyper;
        this.legion = legion;
        this.weapon = weapon;
        this.secondary = secondary;
        this.emblem = emblem;
        this.souls = souls;
        this.classType = classType;
        this.baseDMG = baseDMG;
        this.baseBOSS = baseBoss;
        this.baseATT = baseAtt;
        this.baseIED = baseIed;
        this.baseCRIT = baseCrit;
        this.PDR = pdr;
        this.sw_abs = sw_abs;
        this.sec160 = sec160;
        this.options = options;
        this.server = server;
    }
    
    public WSEOptimizationThread(int[] hyper, ArrayList<int[]> legion, ArrayList<PotVector> mainPots, ArrayList<PotVector> bonusPots, PotType[] souls,
            double baseDMG, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, int options, Server server){
        this.hyper = hyper;
        this.legion = legion;
        this.mainPots = mainPots;
        this.bonusPots = bonusPots;
        this.souls = souls;
        this.baseDMG = baseDMG;
        this.baseBOSS = baseBoss;
        this.baseATT = baseAtt;
        this.baseIED = baseIed;
        this.baseCRIT = baseCrit;
        this.PDR = pdr;
        this.options = options;
        this.server = server;
    }
    
    @Override
    public ArrayList<PotVector> call() { 
        try {
            reducedOptimize = new ArrayList();
            switch(this.server){
                case REBOOT:
                    for (PotType soul : souls){
                        for (PotType[] emb : emblem) {
                            //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                            Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
                            for (PotType[] wep : weapon) {
                                //Saves the potentials and then checks if they are feasible, If they are go to the next piece of gear, else go to the next potential combination
                                Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                                switch (classType) {
                                    case ZERO:
                                        reducedOptimize = legionAndReduce(reducedOptimize, wtemp, wtemp, etemp, null, null, null, hyper, soul);
                                        break;
                                    case KANNA:
                                        //Secondary fan only recognizes Magic Att%
                                        Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);
                                        reducedOptimize = legionAndReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, soul);
                                        break;
                                    default:
                                        for (PotType[] sec : secondary) {
                                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                            stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                            reducedOptimize = legionAndReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, soul);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    break;
                case NONREBOOT:
                    for (PotType soul : souls){
                        for (PotVector mpot : this.mainPots) {
                            for (PotVector bpot : this.bonusPots) {
                                reducedOptimize = legionAndReduce(reducedOptimize, mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), hyper, soul);
                            }
                        }  
                    }
                    break;
                }
        }   
        catch (Exception e) { 
            // Throwing an exception 
            return reducedOptimize;
        }
        return reducedOptimize;
    }
    
    private ArrayList legionAndReduce(ArrayList potContainer, Potentials wepTemp, Potentials secTemp, Potentials embTemp, Potentials wepbpTemp, Potentials secbpTemp, Potentials embbpTemp, int[] hyperStats, PotType soul){
        // If we have put a number 80 or greater for Legion then we only need the first combination of BOSS + IED
        if (legion.size() == 1){
            //Add the potVector to the list
            PotVector temp = new PotVector(wepTemp, secTemp, embTemp, wepbpTemp, secbpTemp, embbpTemp, legion.get(0), hyperStats, soul);
            temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
            potContainer.add(temp);
        }
        else{
            for (int[] legion : legion){
                //Add the potVector to the list
                PotVector temp = new PotVector(wepTemp, secTemp, embTemp, wepbpTemp, secbpTemp, embbpTemp, legion, hyperStats, soul);
                temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
                potContainer.add(temp);
            }
        }
        return WSEHelpers.reduce(potContainer, options);
    }
} 
