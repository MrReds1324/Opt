package WSEOptimizer;

import java.util.Arrays;

/**
 *
 * @author ryanb
 */
public class Union {

     private final int uied;
    private final int ub;
    //Double values of each potential
    public double vals = 0.01; //values used for ied and nebs

    //Constructor for Nebs_U object (val of ied neb, val of boss neb, val or union ied, value of union boss, number of ied nebs, number of boss nebs)
    Union(int uied, int ub) {
        this.uied = uied;      //val or union ied
        this.ub = ub;    //val or union boss
    }

    //Calculates the ied added by the union and nebulites
    public double cied() {
            return (1 - (this.uied * vals));
            }

    //Calculates the boss added by the union and nebulites
    public double cboss() {
        return (this.ub * vals);
    }

    //The Equals Method of this class, if the values of the first 4 entries in each objects nebsu are the same then they are equal
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Union)) {
            return false;
        } else {
            Union p = (Union) o;
            return (this.uied == p.uied) && (this.ub == p.ub);
        }
    }

    //The hashCode for this class, hashs on our nebsu array as this is the only variable that may change
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.uied;
        hash = 97 * hash + this.ub;
        return hash;
    }

   
    public String legionString() {
        String s = this.uied + "% IED\n";
        s += this.ub + "% BOSS";
        return s;
    }

    //ToString method for this class
    @Override
    public String toString() {
        String s = "";
        s += "Legion System:\n";
        s += "IED Points: " + this.uied + "\n";
        s += "BOSS Points: " + this.ub + "\n";
        return s;
    }
}
