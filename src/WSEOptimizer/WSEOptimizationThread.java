package WSEOptimizer;

import static WSEOptimizer.WSEBuilder.legionAndAddReduce;
import java.util.ArrayList;
import WSEOptimizer.Constants.*;

/**
 *
 * @author ryan
 */
public class WSEOptimizationThread  extends Thread {
    private int[] hyper;
    private PotType[][] weapon;
    private PotType[][] secondary;
    private PotType[][] emblem;
    
    private PotType[] souls;
    private boolean sec160;
    private boolean sw_abs;
    private ClassType classType;
    private Server server;
    
    private ArrayList<PotVector> mainPots;
    private ArrayList<PotVector> bonusPots;
    
    public ArrayList<PotVector> reducedOptimize;
    
    public WSEOptimizationThread(int[] hyper, PotType[][] weapon, PotType[][] secondary, PotType[][] emblem, PotType[] souls, ClassType classType, boolean sw_abs, boolean sec160, Server server){
        this.hyper = hyper;
        this.weapon = weapon;
        this.secondary = secondary;
        this.emblem = emblem;
        this.souls = souls;
        this.classType = classType;
        this.sw_abs = sw_abs;
        this.sec160 = sec160;
        this.server = server;
    }
    
    public WSEOptimizationThread(int[] hyper, ArrayList<PotVector> mainPots, ArrayList<PotVector> bonusPots, PotType[] souls, Server server){
        this.hyper = hyper;
        this.mainPots = mainPots;
        this.bonusPots = bonusPots;
        this.souls = souls;
        this.server = server;
    }
    
    @Override
    public void run() { 
        try {
            reducedOptimize = new ArrayList();
            System.out.println("STARTING THREAD");
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
                                        reducedOptimize = legionAndAddReduce(reducedOptimize, wtemp, wtemp, etemp, null, null, null, hyper, soul);
                                        break;
                                    case KANNA:
                                        //Secondary fan only recognizes Magic Att%
                                        Potentials stemp = new Potentials(secondary[0][0], secondary[0][1], secondary[0][2], sec160);
                                        reducedOptimize = legionAndAddReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, soul);
                                        break;
                                    default:
                                        for (PotType[] sec : secondary) {
                                            //Saves the potentials and then checks if they are feasible, If they are calculate the multiplier, else go to the next potential combination
                                            stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                            reducedOptimize = legionAndAddReduce(reducedOptimize, wtemp, stemp, etemp, null, null, null, hyper, soul);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                case NONREBOOT:
                    for (PotType soul : souls){
                        for (PotVector mpot : this.mainPots) {
                            for (PotVector bpot : this.bonusPots) {
                                reducedOptimize = legionAndAddReduce(reducedOptimize, mpot.getWep(), mpot.getSec(), mpot.getEmb(), bpot.getWep(), bpot.getSec(), bpot.getEmb(), hyper, soul);
                            }
                        }  
                    }
                }
        }   
        catch (Exception e) { 
            // Throwing an exception 
            System.out.println (e.toString()); 
        } 
    }
    
    public ArrayList<PotVector> getPotVectors(){
        return this.reducedOptimize;
    }
} 
