/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import java.util.Objects;
import WSEOptimizer.Constants.*;

/**
 *
 * @author ryanb
 */
public class PotVector implements Comparable {

    //Declares our public variables for use in our PotVector
    //The Potential Objects for each item in WSE
    public Potentials wep, sec, emb, wepb, secb, embb;
    //The Nebs_U Object holding the nebulite and union information 
    public Union union = new Union(0, 0);
    //The total attack, boss damage, ignore enemy defense, and the value from the calculation on these stats
    public double att, boss, ied, calc;
    public PotType soul;

    //Constructor to create PotVector without Bonus Potential
    PotVector(Potentials wep, Potentials sec, Potentials emb, Union union, PotType soul) {
        this(wep, sec, emb, null, null, null, union, soul);
    }

    //Constructor to create PotVectors with Bonus Potential 
    PotVector(Potentials wep, Potentials sec, Potentials emb, Potentials wepb, Potentials secb, Potentials embb, Union union, PotType soul) {
        this.wep = wep;
        this.sec = sec;
        this.emb = emb;
        this.wepb = wepb;
        this.secb = secb;
        this.embb = embb;
        this.union = union;
        this.soul = soul;
    }

    //Returns the Nebs_U Object stored in this Object
    public Union getUnion() {
        return this.union;
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

    //Returns the total boss damage this configuration would have
    public double getBoss() {
        return this.boss;
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
    
    public double calculcateMultiplier(double baseATT, double baseDMG, double baseBOSS, double baseIED, double pdr){
        // wep, sec, emb, wepb, secb, embb
        //Calculate new IED
        double iedt;
        if (soul == PotType.IED){
            iedt = (1 - ((1 - baseIED) * emb.cied() * sec.cied() * wep.cied() * union.cied() * Constants.SIED));
        }
        else{
            iedt = (1 - ((1 - baseIED) * emb.cied() * sec.cied() * wep.cied() * union.cied()));
        }
        //Calculate new ATT
        double attt = 1 + baseATT + emb.catt() + sec.catt() + wep.catt();
        if (soul == PotType.ATT){
            attt += Constants.SATT;
        }
        //Calculate new BOSS
        double bosst = 1 + baseDMG + baseBOSS + emb.cboss() + sec.cboss() + wep.cboss() + union.cboss();
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
        this.att = attt - 1;
        this.boss = bosst - baseDMG - 1;
        this.ied = iedt;
        //Calculates the multiplier
        this.calc = (attt * bosst * (1 - (pdr * (1 - iedt))));
        return this.calc;
    }

    //The ToString of this class (prints the att, boss, ied and then the configuration of the WSE items along with the nebulite and union configuration)
    @Override
    public String toString() {
        String x = String.format("ATT: %.0f%% BOSS: %.0f%% IED: %.2f%%\n", this.getAtt() * 100, this.getBoss() * 100, this.getIed() * 100);
        x += "Wep:\n" + this.getWep().toString() + "Sec:\n" + this.getSec().toString() + "Emb:\n" + this.getEmb().toString() + "\n";
        if ((wepb != null && secb != null && embb != null) && wepb.getBpot() == true && secb.getBpot() == true && embb.getBpot() == true) {
            x += "--Bonus Potential--\n";
            x += "Wep:\n" + this.getWepb().toString() + "Sec:\n" + this.getSecb().toString() + "Emb:\n" + this.getEmbb().toString() + "\n";
        }
        x += this.getUnion().toString();
        return x;
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
        hash = 33 * hash + Objects.hashCode(this.union);
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
                return wep.equals(comp.getWep()) && sec.equals(comp.getSec()) && emb.equals(comp.getEmb()) && union.equals(comp.getUnion()) && wepb.equals(comp.getWepb()) && secb.equals(comp.getSecb()) && embb.equals(comp.getEmbb()) && soul == comp.getSoul();
            } //Else do not as you will get IndexOutOfBounds exception
            else {
                return wep.equals(comp.getWep()) && sec.equals(comp.getSec()) && emb.equals(comp.getEmb()) && union.equals(comp.getUnion()) && soul == comp.getSoul();
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
