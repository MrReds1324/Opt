/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opt.wseoptimizer;

import java.util.Arrays;
import opt.wseoptimizer.Constants.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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

    //Computes and returns the IED added by the given potential of this item
    public double cied() {
        //If bpot is false then treat it as a normal potential
        if (bpot == false) {
            return (Math.pow(1 - Constants.LIED, legpot[1]) * Math.pow(1 - Constants.UIED, upot[1]));
        } //Else treat it as a bpot
        else {
            return (Math.pow(1 - Constants.BLIED, legpot[1]) * Math.pow(1 - Constants.BUIED, upot[1]));
        }
    }

    //Computes and returns the Att added by the given potential of this item
    public double catt() {
        int scale = 0;
        if (sw_abs == true) {
            scale = 1;
        }
        return (legpot[0] * (Constants.LATT + 0.01 * scale) + upot[0] * (Constants.UATT + 0.01 * scale));
    }

    //Computes and returns the boss damage added by the given potential of this item
    public double cboss() {
        //If bpot is false then treat it as a normal potential
        if (bpot == false) {
            return (legpot[2] * Constants.LBOSS + upot[2] * Constants.UBOSS);
        } //Else treat it as a bpot
        else {
            return (legpot[2] * Constants.BLBOSS + upot[2] * Constants.BUBOSS);
        }
    }

    //Returns weather this is the bpot item or not
    public boolean getBpot() {
        return this.bpot;
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

    public Entry<String, String> legline() {
        int scale = sw_abs ? 1 : 0;
        
        if (legpot[0] == 1) {
            return new AbstractMap.SimpleEntry<>(String.valueOf((int)((Constants.LATT + (0.01 * scale)) * 100)), PotType.ATT.toString());
        } else if (legpot[1] == 1) {
            return new AbstractMap.SimpleEntry<>(String.valueOf((int)(((bpot ? Constants.BLIED : Constants.LIED)) * 100)), PotType.IED.toString());
        } else {
            return new AbstractMap.SimpleEntry<>(String.valueOf((int)(((bpot ? Constants.BLBOSS : Constants.LBOSS)) * 100)), PotType.BOSS.toString());
        }
    }

    public List<Entry<String, String>> uline() {
        ArrayList<Entry<String, String>> ulines = new ArrayList<>();
        int scale = sw_abs ? 1 : 0;

        for (int i = 0; i < upot.length; i++) {
            for (int j = 0; j < upot[i]; j++) {
                switch (i) {
                    case 0:
                        ulines.add(new AbstractMap.SimpleEntry<>(String.valueOf((int)((Constants.UATT + (0.01 * scale)) * 100)), PotType.ATT.toString()));
                        break;
                    case 1:
                        ulines.add(new AbstractMap.SimpleEntry<>(String.valueOf((int)((bpot ? Constants.BUIED : Constants.UIED) * 100)), PotType.IED.toString()));
                        break;
                    default:
                        ulines.add(new AbstractMap.SimpleEntry<>(String.valueOf((int)((bpot ? Constants.BUBOSS : Constants.UBOSS) * 100)), PotType.BOSS.toString()));
                        break;
                }
            }
        }
        return ulines;
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
