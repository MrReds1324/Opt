package opt.wseoptimizer;

import java.util.ArrayList;
import opt.wseoptimizer.Constants.*;
import java.util.concurrent.Callable;

/**
 *
 * @author ryan
 */
public class WSEOptimizationThread implements Callable<ArrayList<PotVector>> {
    private int[] hyper;
    
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
    private Familiars familiars;
    
    private ArrayList<PotVector> mainPots;
    private ArrayList<PotVector> bonusPots;
    
    public ArrayList<PotVector> reducedOptimize;
    
    public WSEOptimizationThread(int[] hyper, ClassType classType, double baseDMG, double baseBoss, double baseAtt, 
            double baseIed, double baseCrit, double pdr, boolean sw_abs, boolean sec160, int options, Server server){
        this.hyper = hyper;
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
    
    public WSEOptimizationThread(int[] hyper, Familiars familiars, ArrayList<PotVector> mainPots, ArrayList<PotVector> bonusPots, double baseDMG, double baseBoss,
            double baseAtt, double baseIed, double baseCrit, double pdr, int options, Server server){
        this.hyper = hyper;
        this.familiars = familiars;
        this.mainPots = mainPots;
        this.bonusPots = bonusPots;
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
                    for (Familiars familiarCombo : WSEHelpers.familiarSpace){
                        for (PotType soul : WSEHelpers.soulSpace){
                            for (PotType[] emb : WSEHelpers.emblemSpace) {
                                Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
                                for (PotType[] wep : WSEHelpers.weaponSpace) {
                                    Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                                    switch (classType) {
                                        case ZERO:
                                            reducedOptimize = legionAndReduce(reducedOptimize, wtemp, wtemp, etemp, null, null, null, hyper, familiarCombo, soul);
                                            break;
                                        case KANNA:
                                            //Secondary fan only recognizes Magic Att%
                                            Potentials stemp = new Potentials(WSEHelpers.secondarySpace[0][0], WSEHelpers.secondarySpace[0][1], WSEHelpers.secondarySpace[0][2], sec160);
                                            reducedOptimize = legionAndReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, familiarCombo, soul);
                                            break;
                                        default:
                                            for (PotType[] sec : WSEHelpers.secondarySpace) {
                                                stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                                reducedOptimize = legionAndReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, familiarCombo, soul);
                                            }
                                            break;
                                        }
                                    }
                                }
                                //Makes the thread interruptable
                                Thread.sleep(1);
                        }
                    }
                    break;
                case NONREBOOT:
                    for (PotType soul : WSEHelpers.soulSpace){
                        for (PotVector mpot : this.mainPots) {
                            for (PotVector bpot : this.bonusPots) {
                                reducedOptimize = legionAndReduce(reducedOptimize, mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), hyper, familiars, soul);
                            }
                            //Makes the thread interruptable
                            if (Thread.currentThread().isInterrupted()){
                                return reducedOptimize;
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
    
    private ArrayList legionAndReduce(ArrayList potContainer, Potentials wepTemp, Potentials secTemp, Potentials embTemp, Potentials wepbpTemp, Potentials secbpTemp, Potentials embbpTemp, int[] hyperStats, Familiars familiars, PotType soul){
        // If we have put a number 120 or greater for Legion then we only need the first combination of BOSS + IED + Crit Damage
        if (WSEHelpers.legionSpace.size() == 1){
            //Add the potVector to the list
            PotVector temp = new PotVector(wepTemp, secTemp, embTemp, wepbpTemp, secbpTemp, embbpTemp, WSEHelpers.legionSpace.get(0), hyperStats, familiars, soul);
            temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
            potContainer.add(temp);
        }
        else{
            for (int[] legion : WSEHelpers.legionSpace){
                //Add the potVector to the list
                PotVector temp = new PotVector(wepTemp, secTemp, embTemp, wepbpTemp, secbpTemp, embbpTemp, legion, hyperStats, familiars, soul);
                temp.calculcateMultiplier(baseATT, baseBOSS, baseDMG, baseIED, baseCRIT, PDR);
                potContainer.add(temp);
            }
        }
        return WSEHelpers.reduce(potContainer, options);
    }
} 
