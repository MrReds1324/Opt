/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.FamiliarTier;
import java.util.Arrays;

/**
 *
 * @author ryan
 */
public class Familiars {
    
    private int[] topline = new int[4]; //[ATT, IED, BOSS, Crit Damage]
    private int[] botline = new int[4]; //[ATT, IED, BOSS, Crit Damage]
    private FamiliarTier familiarTier = FamiliarTier.DEFAULT;
    
    private double topAtt = 0.0;
    private double topIED = 0.0;
    private double topBoss = 0.0;
    private double topCrit = 0.0;
    
    private double botAtt = 0.0;
    private double botIED = 0.0;
    private double botBoss = 0.0;
    private double botCrit = 0.0;
    
    public Familiars(int[] topline, int[] botline, FamiliarTier familiarTier){
        this.topline = topline;
        this.botline = botline;
        this.familiarTier = familiarTier;
        initTopBotValues(familiarTier);
    }
    
    private void initTopBotValues(FamiliarTier familiarTier){
        switch(familiarTier){
            case LEGENDARY:
                this.topAtt = Constants.LFATT;
                this.topIED = Constants.LFIED;
                this.topBoss = Constants.LFBOSS;
                this.topCrit = Constants.LFCRIT;
                
                this.botAtt = Constants.UFATT;
                this.botIED = Constants.UFIED;
                this.botBoss = Constants.UFBOSS;
                this.botCrit = Constants.UFCRIT;
                break;
            case UNIQUE:
                this.topAtt = Constants.UFATT;
                this.topIED = Constants.UFIED;
                this.topBoss = Constants.UFBOSS;
                this.topCrit = Constants.UFCRIT;
                
                this.botAtt = Constants.EFATT;
                this.botIED = Constants.EFIED;
                this.botBoss = Constants.EFBOSS;
                break;
            case EPIC:
                this.topAtt = Constants.EFATT;
                this.topIED = Constants.EFIED;
                this.topBoss = Constants.EFBOSS;
                
                this.botAtt = Constants.RFATT;
                this.botIED = Constants.RFIED;
                this.botBoss = Constants.RFBOSS;
                break;
            case RARE:
                this.topAtt = Constants.RFATT;
                this.topIED = Constants.RFIED;
                this.topBoss = Constants.RFBOSS;
                
                this.botAtt = Constants.CFATT;
                this.botIED = Constants.CFIED;
                this.botBoss = Constants.CFBOSS;
                break;
            case COMMON:
                this.topAtt = Constants.CFATT;
                this.topIED = Constants.CFIED;
                this.topBoss = Constants.CFBOSS;
                break;
        }
    }
    
    //Computes and returns the Att added by the familiar combination
    public double catt() {
        return (topline[0] * topAtt + botline[0] * botAtt);
    }
    
    //Computes and returns the IED added by the familiar combination
    public double cied() {
        return (Math.pow(1 - topIED, topline[1]) * Math.pow(1 - botIED, botline[1]));
    }

    //Computes and returns the boss damage added by the familiar combination
    public double cboss() {
        return (topline[2] * topBoss + botline[2] * botBoss);
    }
    
    //Computes and returns the crit damage added by the familiar combination
    public double ccrit() {
        return (topline[3] * topCrit + botline[3] * botCrit);
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Familiars)) {
            return false;
        } else {
            Familiars p = (Familiars) o;
            return (this.familiarTier == p.familiarTier) && (this.topline[0] == p.topline[0]) && (this.topline[1] == p.topline[1]) && (this.topline[2] == p.topline[2]) && (this.topline[3] == p.topline[3]) && (this.botline[0] == p.botline[0]) && (this.botline[1] == p.botline[1]) && (this.botline[2] == p.botline[2]) && (this.botline[3] == p.botline[3]);
        }
    }

    //The hashCode for this class, uses the 3 variables we assigned values to (legpot, upot and sw)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 51 * hash + Arrays.hashCode(this.topline);
        hash = 51 * hash + Arrays.hashCode(this.botline);
        return hash;
    }
    
}
