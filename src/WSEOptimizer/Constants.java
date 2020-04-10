/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.Arrays;

/**
 *
 * @author ryanb
 */
public class Constants {

    public enum PotConfig {
        NO3LINE, DEFAULT;
    }

    public enum ClassType {
        NOCLASS, ZERO, KANNA;
    }

    public enum ItemType {
        WEPSEC, EMB;
    }

    public enum PotType {
        DEFAULT, ATT, BOSS, IED;
    }
    
    public enum Server {
        REBOOT, NONREBOOT;
    }
    
    //Double values of each potential
    public static final double LIED = 0.40;  //Legendary IED value
    public static final double UIED = 0.30;  //Unique IED value
    public static final double LBOSS = 0.40; //Legendary Boss value
    public static final double UBOSS = 0.30; //Unique Boss value
    // Note that these are also the bonus potential lines as well
    public static final double LATT = 0.12;  //Legendary Att value (will add 0.01 when 160+ wep is used)
    public static final double UATT = 0.09;  //Unique Att value (will add 0.01 when 160+ wep is used)

    //Double values of bpot
    public static final double BLIED = 0.05;  //Legendary IED value
    public static final double BUIED = 0.04;  //Unique IED value
    public static final double BLBOSS = 0.18; //Legendary Boss value
    public static final double BUBOSS = 0.12; //Unique Boss value
    
    //Double values of souls
    public static final double SATT = 0.03;
    public static final double SBOSS = 0.07;
    public static final double SIED = 0.07;
    
    public static final PotType[][] weapon = new PotType[][]{
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
    
    public static final PotType[][] weaponNo3LineAtt = Arrays.copyOfRange(weapon, 1, weapon.length);
    
    public static final PotType[][] secondary = weapon;
    
    public static final PotType[][] secondaryNo3LineAtt = weaponNo3LineAtt;
    
    public static final PotType[][] emblem = new PotType[][]{
        // ATT Legendary line then two Unique lines
        {PotType.ATT, PotType.ATT, PotType.ATT},
        {PotType.ATT, PotType.ATT, PotType.IED},
        {PotType.ATT, PotType.IED, PotType.IED},
        // IED Legendary line then two Unique lines
        {PotType.IED, PotType.IED, PotType.ATT},
        {PotType.IED, PotType.ATT, PotType.ATT}};
    
    public static final PotType[][] emblemNo3LineAtt = Arrays.copyOfRange(emblem, 1, emblem.length);
    
    public static final PotType[] souls = new PotType[]{PotType.ATT, PotType.BOSS, PotType.IED};
    
    // Stats for Hyper Stats
    public static final int[] hyperStatCosts = new int[]{0, 1, 3, 7, 15, 25, 40, 60, 85, 115, 150, 200, 265, 345, 440, 550};
    public static final double[] hyperCritDmg = new double[]{0.0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.10, 0.11, 0.12, 0.13, 0.14, 0.15};
    public static final double[] hyperBossDmg = new double[]{0.0, 0.03, 0.06, 0.09, 0.12, 0.15, 0.19, 0.23, 0.27, 0.31, 0.35, 0.39, 0.43, 0.47, 0.51, 0.55};
    public static final double[] hyperDmg = new double[]{0.0, 0.03, 0.06, 0.09, 0.12, 0.15, 0.18, 0.21, 0.24, 0.27, 0.30, 0.33, 0.36, 0.39, 0.42, 0.45};
    public static final double[] hyperIed = new double[]{0.0, 0.03, 0.06, 0.09, 0.12, 0.15, 0.18, 0.21, 0.24, 0.27, 0.30, 0.33, 0.36, 0.39, 0.42, 0.45};

}
