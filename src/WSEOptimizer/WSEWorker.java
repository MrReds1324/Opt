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
    private static PotType[][] weapon;
    private static PotType[][] secondary;
    private static PotType[][] emblem;
    
    private static PotType[][] weaponBp;
    private static PotType[][] secondaryBp;
    private static PotType[][] emblemBp;
    
    private static PotType[] souls;
    
    private static ArrayList<int[]> hyperStats;
    
    //IED, BOSS
    private static ArrayList<int[]> lcombs;
    
    private static double baseATT;
    private static double baseBOSS;
    private static double baseDMG;
    private static double baseIED;
    private static double baseCRIT;
    private static double PDR;
    private static int options;

    @Override
    protected ArrayList<PotVector> doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
}
