/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.Arrays;
import WSEOptimizer.Constants.ItemType;
import WSEOptimizer.Constants.PotConfig;
import WSEOptimizer.Constants.PotType;

/**
 *
 * @author ryanb
 */
public class Potentials {

    public int[] legpot = new int[3]; //[ATT, IED, BOSS]
    public int[] upot = new int[3]; //[ATT, IED, BOSS]
    private boolean sw_abs = false;
    public boolean bpot = false;

    //Constructor for an item in the WSE (potential types for legendary and unique lines, lvl 150 or 160+ wep)
    Potentials(PotType legendaryLine, PotType uniqueLine1, PotType uniqueLine2, boolean sw) {
        this(legendaryLine, uniqueLine1, uniqueLine2, sw, false);
    }

    //Constructor for an items bpot in the WSE (potential types for legendary and unique lines,, lvl 150 or 160+ wep, and if it is a bpot)
    Potentials(PotType legendaryLine, PotType uniqueLine1, PotType uniqueLine2, boolean sw, boolean bp) {
        switch(legendaryLine) {
            case ATT:
                legpot[0] = 1;
                break;
            case IED:
                legpot[1] = 1;
                break;
            case BOSS:
                legpot[2] = 1;
                break;
          }
        
        switch(uniqueLine1) {
            case ATT:
                upot[0] = 1;
                break;
            case IED:
                upot[1] = 1;
                break;
            case BOSS:
                upot[2] = 1;
                break;
          }
        
        switch(uniqueLine2) {
            case ATT:
                upot[0] += 1;
                break;
            case IED:
                upot[1] += 1;
                break;
            case BOSS:
                upot[2] += 1;
                break;
          }
        //Tracks if the weapon is lvl 160+
        sw_abs = sw;
        bpot = bp;
    }

    //Double values of each potential
    public double lied = 0.40;  //Legendary IED value
    public double uied = 0.30;  //Unique IED value
    public double lboss = 0.40; //Legendary Boss value
    public double uboss = 0.30; //Unique Boss value
    public double latt = 0.12;  //Legendary Att value (will add 0.01 when 160+ wep is used)
    public double uatt = 0.09;  //Unique Att value (will add 0.01 when 160+ wep is used)

    //Double values of bpot
    public double blied = 0.05;  //Legendary IED value
    public double buied = 0.04;  //Unique IED value
    public double blboss = 0.18; //Legendary Boss value
    public double buboss = 0.12; //Unique Boss value

    //Computes and returns the IED added by the given potential of this item
    public double cied() {
        //If bpot is false then treat it as a normal potential
        if (bpot == false) {
            return (Math.pow(1 - lied, legpot[1]) * Math.pow(1 - uied, upot[1]));
        } //Else treat it as a bpot
        else {
            return (Math.pow(1 - blied, legpot[1]) * Math.pow(1 - buied, upot[1]));
        }
    }

    //Computes and returns the Att added by the given potential of this item
    public double catt() {
        int scale = 0;
        if (sw_abs == true) {
            scale = 1;
        }
        return (legpot[0] * (latt + 0.01 * scale) + upot[0] * (uatt + 0.01 * scale));
    }

    //Computes and returns the boss damage added by the given potential of this item
    public double cboss() {
        //If bpot is false then treat it as a normal potential
        if (bpot == false) {
            return (legpot[2] * lboss + upot[2] * uboss);
        } //Else treat it as a bpot
        else {
            return (legpot[2] * blboss + upot[2] * buboss);
        }
    }

    //Returns weather this is the bpot item or not
    public boolean getBpot() {
        return this.bpot;
    }

    //Checks the configuration of pots is possible
    //If x == 0 then treat it as a wep or secondary, else if x == 1 then treat it as an emblem
    public boolean feasible(ItemType itemType, PotConfig potConfig) {
        //No piece should have more than 2 lines of boss or ied
        if (itemType == ItemType.WEPSEC && potConfig == PotConfig.DEFAULT && (legpot[1] + upot[1] == 3 || legpot[2] + upot[2] == 3)) {
            return false;
        } //Emblem should not have any boss lines
        else if (itemType == ItemType.EMB && potConfig == PotConfig.DEFAULT && ((legpot[2] > 0 || upot[2] > 0))) {
            return false;
        } //No 3 lines option for bpots (they can get really expensive)
        else if (itemType == ItemType.WEPSEC && potConfig == PotConfig.NO3LINE && (legpot[0] + upot[0] == 3 || legpot[1] + upot[1] == 3 || legpot[2] + upot[2] == 3)) {
            return false;
        } //No 3 lines option for bpots on emblem
        else if (itemType == ItemType.EMB && potConfig == PotConfig.NO3LINE && ((legpot[2] > 0 || upot[2] > 0) || legpot[0] + upot[0] == 3 || legpot[1] + upot[1] == 3)) {
            return false;
        } //else return true
        else {
            return true;
        }
    }

    //Equals method for this class
    //If each entry within legpot and upot arrays are the same for both Potential Objects then they are equal
    //Note that you will have to be careful if you compare weapons to secondaries as it will not compare the lvl 150/160+ variable
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Potentials)) {
            return false;
        } else {
            Potentials p = (Potentials) o;
            return (this.legpot[0] == p.legpot[0]) && (this.legpot[1] == p.legpot[1]) && (this.legpot[2] == p.legpot[2]) && (this.upot[0] == p.upot[0]) && (this.upot[1] == p.upot[1]) && (this.upot[2] == p.upot[2]);
        }
    }

    //The hashCode for this class, uses the 3 variables we assigned values to (legpot, upot and sw)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Arrays.hashCode(this.legpot);
        hash = 71 * hash + Arrays.hashCode(this.upot);
        if (this.sw_abs == true) {
            hash = 71 * hash + 1;
        }
        if (this.bpot == true) {
            hash = 71 * hash + 1;
        }
        return hash;
    }

    public String legline() {
        int scale = 0;
        if (sw_abs == true) {
            scale = 1;
        }

        String s = "";
        if (this.bpot == false) {
            if (legpot[0] == 1) {
                s += "" + (this.latt + (0.01 * scale)) * 100 + "% ATT\n";
            } else if (legpot[1] == 1) {
                s += "" + this.lied * 100 + "% IED\n";
            } else {
                s += "" + this.lboss * 100 + "% BOSS\n";
            }
        } else {
            if (legpot[0] == 1) {
                s += "" + (this.latt + (0.01 * scale)) * 100 + "% ATT\n";
            } else if (legpot[1] == 1) {
                s += "" + this.blied * 100 + "% IED\n";
            } else {
                s += "" + this.blboss * 100 + "% BOSS\n";
            }
        }
        return s;
    }

    public String uline() {
        int scale = 0;
        if (sw_abs == true) {
            scale = 1;
        }

        String s = "";
        for (int i = 0; i < upot.length; i++) {
            for (int j = 0; j < upot[i]; j++) {
                if (this.bpot == false) {
                    if (i == 0) {
                        s += "" + (this.uatt + (0.01 * scale)) * 100 + "% ATT:";
                    } else if (i == 1) {
                        s += "" + this.uied * 100 + "% IED:";
                    } else {
                        s += "" + this.uboss * 100 + "% BOSS:";
                    }
                } else {
                    if (i == 0) {
                        s += "" + (this.uatt + (0.01 * scale)) * 100 + "% ATT:";
                    } else if (i == 1) {
                        s += "" + this.buied * 100 + "% IED:";
                    } else {
                        s += "" + this.buboss * 100 + "% BOSS:";
                    }
                }
            }
        }
        return s;
    }

    //Creates the text of the potentials on this piece of gear
    @Override
    public String toString() {
        //Begins with legendary potential lines
        String s = "Legendary line of ";

        if (legpot[0] == 1) {
            s += "ATT\n";
        } else if (legpot[1] == 1) {
            s += "IED\n";
        } else {
            s += "BOSS\n";
        }
        //Ends with unique potential lines
        if (upot[0] >= 1) {
            s += upot[0] + " Unique line ATT\n";
        }
        if (upot[1] >= 1) {
            s += upot[1] + " Unique line IED\n";
        }
        if (upot[2] >= 1) {
            s += upot[2] + " Unique line BOSS\n";
        }
        //Returns the completed String
        return s;
    }
}
