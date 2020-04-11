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
    
    }

    @Override
    protected ArrayList<PotVector> doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
