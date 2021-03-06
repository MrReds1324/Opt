/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opt.wseoptimizer;

import java.util.Objects;
import opt.wseoptimizer.Constants.*;

/**
 *
 * @author ryanb
 */
public class PotVector implements Comparable {

    //Declares our public variables for use in our PotVector
    //The Potential Objects for each item in WSE
    private Potentials wep, sec, emb, wepb, secb, embb;
    //The Legion array holding BOSS/IED/Crit Damage 
    private int[] legion = new int[]{0, 0, 0};
    //The Hyper Stats array holding Crit Damage/Boss Damage/Damage/IED
    private int[] hyperStats = new int[]{0, 0, 0, 0};
    //The total attack, boss damage, ignore enemy defense, and the value from the calculation on these stats
    private double att, totalDMG, ied, crit, calc;
    private PotType soul;
    Familiars familiars = new Familiars();

    //Constructor to create PotVector without Bonus Potential
    PotVector(Potentials wep, Potentials sec, Potentials emb, int[] legion, int[] hyperStats, Familiars familiars, PotType soul) {
        this(wep, sec, emb, null, null, null, legion, hyperStats, familiars, soul);
    }

    //Constructor to create PotVectors with Bonus Potential 
    PotVector(Potentials wep, Potentials sec, Potentials emb, Potentials wepb, Potentials secb, Potentials embb, int[] legion, int[] hyperStats, Familiars familiars, PotType soul) {
        this.wep = wep;
        this.sec = sec;
        this.emb = emb;
        this.wepb = wepb;
        this.secb = secb;
        this.embb = embb;
        this.legion = legion;
        this.hyperStats = hyperStats;
        this.familiars = familiars;
        this.soul = soul;
    }

    //Returns Legion array stored in this Object
    public int[] getLegion() {
        return this.legion;
    }

    //Returns the Potentials Object of the weapon stored in this Object
    public Potentials getWep() {
        return this.wep;
    }

    //Returns the Potentials Object of the secondary stored in this Object
    public Potentials getSec() {
        return this.sec;
    }

    //Returns the Potentials Object of the emblem stored in this Object
    public Potentials getEmb() {
        return this.emb;
    }

    //Returns the "Bonus" Potentials Object of the weapon stored in this Object
    public Potentials getWepb() {
        return this.wepb;
    }

    //Returns the "Bonus" Potentials Object of the secondary stored in this Object
    public Potentials getSecb() {
        return this.secb;
    }

    //Returns the "Bonus" Potentials Object of the emblem stored in this Object
    public Potentials getEmbb() {
        return this.embb;
    }

    //Returns the total att this configuration would have
    public double getAtt() {
        return this.att;
    }

    //Returns the total boss + damage this configuration would have
    public double getTotalDMG() {
        return this.totalDMG;
    }

    //Returns the total crit damage this configuration would have
    public double getCrit() {
        return this.crit;
    }
    
    //Returns the total ignore enemy defense this configuration would have
    public double getIed() {
        return this.ied;
    }

    //Returns the value of the calculation on these stats
    public double getCalc() {
        return this.calc;
    }

    public PotType getSoul() {
        return this.soul;
    }
    
    public int[] getHypers(){
        return this.hyperStats;
    }
    
    public Familiars getFamiliars(){
        return this.familiars;
    }
    
    public double calculcateMultiplier(double baseATT, double baseBOSS, double baseDMG, double baseIED, double baseCrit, double pdr){
        // wep, sec, emb, wepb, secb, embb
        //Calculate new IED - Hard cap of 100% IED, if familiars go over that then cap it to 1 (100% ied)
        double iedt = (1 - ((1 - baseIED) * emb.cied() * sec.cied() * wep.cied() * familiars.cied() * (1 - Constants.hyperIed[hyperStats[3]]) * (1 - (legion[1] * 0.01))));
        if (soul == PotType.IED){
            iedt = (1 - ((1 - iedt) * (1 - Constants.SIED)));
        }
        //Calculate new ATT
        double attt = 1 + baseATT + emb.catt() + sec.catt() + wep.catt() + familiars.catt();
        if (soul == PotType.ATT){
            attt += Constants.SATT;
        }
        //Calculate new BOSS
        double bosst = 1 + baseDMG + baseBOSS + emb.cboss() + sec.cboss() + wep.cboss() + familiars.cboss() + Constants.hyperBossDmg[hyperStats[1]] + Constants.hyperDmg[hyperStats[2]] + (legion[0] * 0.01);
        if (soul == PotType.BOSS){
            bosst += Constants.SBOSS;
        }
        if (wepb != null && secb != null && embb != null){
            iedt = (1 - ((1 - iedt) * embb.cied() * secb.cied() * wepb.cied()));
            //Calculate new ATT
            attt += embb.catt() + secb.catt() + wepb.catt();
            //Calculate new BOSS
            bosst += embb.cboss() + secb.cboss() + wepb.cboss();
        }
        //Calculate new Crit Damage
        double critt = 1.35 + baseCrit + (legion[2] * 0.005) + Constants.hyperCritDmg[hyperStats[0]];
        this.att = attt - 1;
        this.totalDMG = bosst - 1;
        this.ied = iedt;
        this.crit = critt - 1.3;
        //Calculates the multiplier
        this.calc = (critt * attt * bosst * (1 - (pdr * (1 - iedt))));
        return this.calc;
    }

    //The ToString of this class (prints the att, boss, ied and then the configuration of the WSE items along with the nebulite and union configuration)
    @Override
    public String toString() {
        String x = String.format("ATT: %.0f%% Total Damage: %.0f%% IED: %.2f%% CRIT: %.2f%%\n", this.getAtt() * 100, this.getTotalDMG()* 100, this.getIed() * 100, this.getCrit() * 100);
        x += "Wep:\n" + this.getWep().toString() + "Sec:\n" + this.getSec().toString() + "Emb:\n" + this.getEmb().toString() + "\n";
        if ((wepb != null && secb != null && embb != null) && wepb.getBpot() && secb.getBpot() && embb.getBpot()) {
            x += "--Bonus Potential--\n";
            x += "Wep:\n" + this.getWepb().toString() + "Sec:\n" + this.getSecb().toString() + "Emb:\n" + this.getEmbb().toString() + "\n";
        }
        String[] strs = this.legionStrings();
        x += strs[0] + "\n" + strs[1];
        return x;
    }
    
    public String[] legionStrings() {
        String[] strs = new String[]{legion[1] + "% IED", legion[0] + "% BOSS", (legion[2] * 0.5) + "% Crit Damage"};
        return strs;
    }

    //The hashCode of this class for storage in more complicated data structures
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 33 * hash + Objects.hashCode(this.wep);
        hash = 33 * hash + Objects.hashCode(this.sec);
        hash = 33 * hash + Objects.hashCode(this.emb);
        hash = 33 * hash + Objects.hashCode(this.wepb);
        hash = 33 * hash + Objects.hashCode(this.secb);
        hash = 33 * hash + Objects.hashCode(this.embb);
        hash = 33 * hash + Objects.hashCode(this.legion);
        hash = 33 * hash + Objects.hashCode(this.soul);
        return hash;
    }

    //The equals method of this class (if each item in the WSE and the Nebs_U are equal than they are equal configurations)
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PotVector)) {
            return false;
        } else {
            PotVector comp = (PotVector) o;
            //If bonus pots are not null then use it in equals
            if (wepb != null && secb != null && embb != null) {
                return wep.equals(comp.getWep()) && sec.equals(comp.getSec()) && emb.equals(comp.getEmb()) && legion == comp.getLegion() && wepb.equals(comp.getWepb()) && secb.equals(comp.getSecb()) && embb.equals(comp.getEmbb()) && soul == comp.getSoul();
            } //Else do not as you will get IndexOutOfBounds exception
            else {
                return wep.equals(comp.getWep()) && sec.equals(comp.getSec()) && emb.equals(comp.getEmb()) && legion == comp.getLegion() && soul == comp.getSoul();
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof PotVector)) {
            throw new IllegalArgumentException();
        }
        PotVector pv = (PotVector) o;
        if (this.calc > pv.calc) {
            return -1;
        } else if (this.calc == pv.calc) {
            return 0;
        } else {
            return 1;
        }
    }
}
