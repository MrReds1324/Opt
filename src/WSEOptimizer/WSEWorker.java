/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.ArrayList;
import javax.swing.SwingWorker;
import WSEOptimizer.Constants.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author ryan
 */
public class WSEWorker extends SwingWorker<ArrayList<PotVector>, ArrayList<PotVector>>{
    
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
    
    private int progress = 0;
    private ExecutorService pool;

    public WSEWorker(double baseDamage, double baseBoss, double baseAtt, double baseIed, double baseCrit, double pdr, ClassType classType, boolean sw_abs, 
            boolean sec160, int numberOfOptions, Server server){
        this.baseDMG = baseDamage;
        this.baseBOSS = baseBoss;
        this.baseATT = baseAtt;
        this.baseIED = baseIed;
        this.baseCRIT = baseCrit;
        this.PDR = pdr;
        this.classType = classType;
        this.sw_abs = sw_abs;
        this.sec160 = sec160;
        this.options = numberOfOptions;
        this.server = server;
    }

    @Override
    protected ArrayList<PotVector> doInBackground() throws Exception {
        List<Future<ArrayList<PotVector>>> allResults = new ArrayList();
        Collection<Callable<ArrayList<PotVector>>> threads = new ArrayList();
        ArrayList<PotVector> potVectorList = new ArrayList();
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println(coreCount + " processor" + (coreCount != 1 ? "s are " : " is ") + "available");
        switch(server){
            case REBOOT:
                //Carries out the optimization beginning with Emblem to find the perfect configuration
                for (int[] hyper: WSEHelpers.hyperStatsSpace){
                    threads.add(new WSEOptimizationThread(hyper, classType, baseDMG, baseBOSS, baseATT, baseIED, baseCRIT, PDR, sw_abs, sec160, options, Server.REBOOT));
                }
                pool = Executors.newFixedThreadPool(coreCount);
                for (Callable<ArrayList<PotVector>> thread : threads){
                    allResults.add(pool.submit(thread));
                }
                pool.shutdown();
                while (!pool.isTerminated()){
                    if(isCancelled()){
                        pool.shutdownNow();
                    }
                    else{
                        int count = 0;
                        for (Future<ArrayList<PotVector>> thread : allResults){
                            if (thread.isDone()){
                                count++;
                            }
                        }
                        int newProgress = (int)Math.round((count * 1.0)/(allResults.size()*1.0) * 100);
                        if (newProgress != progress){
                            setProgress(newProgress);
                            progress = newProgress;
                            Thread.sleep(100);
                        }
                    }
                }
                //All Workers should be finished
                for (Future<ArrayList<PotVector>> threadResult: allResults){
                    potVectorList.addAll(threadResult.get());
                }
                return WSEHelpers.reduce(potVectorList, options);
            case NONREBOOT:
                ArrayList<PotVector> main_temp = new ArrayList();
                ArrayList<PotVector> bonus_temp = new ArrayList();

                for (PotType[] emb : WSEHelpers.emblembpSpace) {
                    Potentials etempb = new Potentials(emb[0], emb[1], emb[2], false, true);
                    for (PotType[] wep : WSEHelpers.weaponbpSpace) {
                        Potentials wtempb = new Potentials(wep[0], wep[1], wep[2], sw_abs, true);
                        switch (classType){
                            case ZERO:
                                //Add the potVector to the list
                                PotVector ptb = new PotVector(wtempb, wtempb, etempb, null, null, null, PotType.DEFAULT);
                                //Add the configuration to the WSE array if it does not exist
                                bonus_temp.add(ptb);
                                break;
                            case KANNA:
                                //Secondary fan only recognizes Magic Att%
                                Potentials stempb = new Potentials(WSEHelpers.secondarybpSpace[0][0], WSEHelpers.secondarybpSpace[0][1], WSEHelpers.secondarybpSpace[0][2], sec160, true);
                                //Add the potVector to the list
                                ptb = new PotVector(wtempb, stempb, etempb, null, null, null, PotType.DEFAULT);
                                //Add the configuration to the WSE array if it does not exist
                                bonus_temp.add(ptb);
                                break;
                            default:
                                for (PotType[] sec : WSEHelpers.secondarybpSpace) {
                                    stempb = new Potentials(sec[0], sec[1], sec[2], sec160, true);
                                    //Add the potVector to the list
                                    ptb = new PotVector(wtempb, stempb, etempb, null, null, null, PotType.DEFAULT);
                                    //Add the configuration to the WSE array if it does not exist
                                    bonus_temp.add(ptb);
                                }
                                break;
                        }
                    }
                }
                //Carries out the optimization beginning with Emblem to find the perfect configuration
                for (PotType[] emb : WSEHelpers.emblemSpace) {
                    Potentials etemp = new Potentials(emb[0], emb[1], emb[2], false);
                    for (PotType[] wep : WSEHelpers.weaponSpace) {
                        Potentials wtemp = new Potentials(wep[0], wep[1], wep[2], sw_abs);
                        switch (classType) {
                            case ZERO:
                                main_temp.add(new PotVector(wtemp, wtemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, new Familiars(), PotType.DEFAULT));
                                break;
                            case KANNA:
                                //Secondary fan only recognizes Magic Att%
                                Potentials stemp = new Potentials(WSEHelpers.secondarySpace[0][0], WSEHelpers.secondarySpace[0][1], WSEHelpers.secondarySpace[0][2], sec160);
                                main_temp.add(new PotVector(wtemp, stemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, new Familiars(), PotType.DEFAULT));
                                break;
                            default:
                                for (PotType[] sec : WSEHelpers.secondarySpace) {
                                    stemp = new Potentials(sec[0], sec[1], sec[2], sec160);
                                    main_temp.add(new PotVector(wtemp, stemp, etemp, new int[]{0, 0, 0}, new int[]{0, 0, 0, 0}, new Familiars(), PotType.DEFAULT));
                                }
                                break;
                        }
                    }
                }

                threads = new ArrayList();
                long totalGenerationSpace = WSEHelpers.hyperStatsSpace.size() * WSEHelpers.soulSpace.length * WSEHelpers.emblemSpace.length * WSEHelpers.weaponSpace.length * WSEHelpers.secondarySpace.length * WSEHelpers.emblembpSpace.length * WSEHelpers.weaponbpSpace.length * WSEHelpers.secondarybpSpace.length * WSEHelpers.legionSpace.size() * WSEHelpers.familiarSpace.size();
                long workerGenerationSpace = WSEHelpers.soulSpace.length * WSEHelpers.emblemSpace.length * WSEHelpers.weaponSpace.length * WSEHelpers.secondarySpace.length * WSEHelpers.emblembpSpace.length * WSEHelpers.weaponbpSpace.length * WSEHelpers.secondarybpSpace.length * WSEHelpers.legionSpace.size();
                //Combines both main and bonus pots to generate all combinations of the two
                for (int[] hyper : WSEHelpers.hyperStatsSpace){
                    for (Familiars familiars : WSEHelpers.familiarSpace){
                        threads.add(new WSEOptimizationThread(hyper, familiars, main_temp, bonus_temp, baseDMG, baseBOSS, baseATT, baseIED, baseCRIT, PDR, options, Server.NONREBOOT));
                    }
                }
                pool = Executors.newFixedThreadPool(coreCount);
                for (Callable<ArrayList<PotVector>> thread : threads){
                    allResults.add(pool.submit(thread));
                }
                pool.shutdown();
                while (!pool.isTerminated()){
                    if(isCancelled()){
                        pool.shutdownNow();
                    }
                    else{
                        int count = 0;
                        for (Future<ArrayList<PotVector>> thread : allResults){
                            if (thread.isDone()){
                                count++;
                            }
                        }
                        int newProgress = (int)Math.round((count * 1.0)/(allResults.size()*1.0) * 100);
                        if (newProgress != progress){
                            setProgress(newProgress);
                            progress = newProgress;
                            Thread.sleep(100);
                        }
                    }
                }
                //All Workers should be finished
                for (Future<ArrayList<PotVector>> threadResult: allResults){
                    potVectorList.addAll(threadResult.get());
                }
                return WSEHelpers.reduce(potVectorList, options);
            }
         return null;
        }
    }
