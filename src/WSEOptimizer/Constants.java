/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

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
    
}
