/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import WSEOptimizer.Constants.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

/**
 *
 * @author ryanb
 */
public class OptimizationPieces extends javax.swing.JFrame {

    private PotConfig no_3lAtt = PotConfig.DEFAULT; //Keeps track if we want to calculate with or without 3 lines of attack
    private PotConfig no_3lbpAtt = PotConfig.DEFAULT; //Keeps track if we want to calculate with or without 3 lines of attack for bonus potential
    private boolean sec_lvl; //Keeps track if we want to calculate with a higher level secondary than normal
    private boolean wep_lvl; //Keeps track if we want to calculate with a higher level weapon than normal
    private ClassType classType = ClassType.NOCLASS;  //Keeps track of the class type
    //These keep track of which buttons are selected for which input field
    private PotType wepInp1_butSel = PotType.DEFAULT;
    private PotType wepInp2_butSel = PotType.DEFAULT;
    private PotType wepInp3_butSel = PotType.DEFAULT;
    private PotType wepInp5_butSel = PotType.DEFAULT;
    private PotType secInp1_butSel = PotType.DEFAULT;
    private PotType secInp2_butSel = PotType.DEFAULT;
    private PotType secInp3_butSel = PotType.DEFAULT;
    private PotType wepbpInp1_butSel = PotType.DEFAULT;
    private PotType wepbpInp2_butSel = PotType.DEFAULT;
    private PotType wepbpInp3_butSel = PotType.DEFAULT;
    private PotType secbpInp1_butSel = PotType.DEFAULT;
    private PotType secbpInp2_butSel = PotType.DEFAULT;
    private PotType secbpInp3_butSel = PotType.DEFAULT;
    private PotType embInp1_butSel = PotType.DEFAULT;
    private PotType embInp2_butSel = PotType.DEFAULT;
    private PotType embInp3_butSel = PotType.DEFAULT;
    private PotType embbpInp1_butSel = PotType.DEFAULT;
    private PotType embbpInp2_butSel = PotType.DEFAULT;
    private PotType embbpInp3_butSel = PotType.DEFAULT;
    //These fields keep track of the values inserted into the input text fields
    private double att_base;
    private double ied_base;
    private double dmg_base;
    private double boss_base;
    private double pdr;
    private int legionVal;
    //Variables for saving the base stats for final damage calculation
    private double att_baseS;
    private double boss_baseS;
    private double dmg_baseS;
    private double ied_baseS;
    //Variable for saving number of additional options
    private int numberOfOptions;
    //Keeps track of the map we use to build the list for our combo box
    @SuppressWarnings("unchecked")
    Map<String, PotVector> comboBoxMap = new LinkedHashMap();
    private double crit_base;
    private double crit_baseS;
    private int hyperPoints;
    //Variables for tracking execution time for the worker
    private double time;
    private long startTime;
    private long endTime;
    //Variables for trakcing the final output
    private PotVector selectedPotVector;
    private ArrayList<PotVector> generatedWSE;
    //Variables related to the worker and listening to its progress and state
    private WSEWorker worker;
    private PropertyChangeListener listener = 
                               new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            String eventName = event.getPropertyName();
            switch (eventName) {
                        case "state":
                            switch (worker.getState()) {
                                case DONE:
                                    try {
                                        generatedWSE = worker.get();
                                    } catch (InterruptedException ex) {
                                        System.out.println();
                                    } catch (ExecutionException ex) {
                                        System.out.println();
                                    }
                                    long endTime = System.nanoTime();
                                    time = (endTime - startTime) / 1000000000.0;
                                    System.out.println("Execution time in seconds : " + time);
                                    comboBoxMap = ComboBoxSupport.buildComboBoxMap(generatedWSE);
                                    selectedPotVector = generatedWSE.get(0);
                                    if (selectedPotVector != null) {
                                        outputPotVector(selectedPotVector);
                                        wseOptions.setEnabled(true);
                                        wseOptions.setModel(ComboBoxSupport.buildComboBoxItems(comboBoxMap));
                                    } else {
                                        System.out.println("Something went terribly wrong and the vector was null!");
                                    }
                                    break;

                            }
                            break;
                        case "progress":
                            fd_Legion.setText(String.format("%d%% Done", worker.getProgress()));
                            break;
                    }
        }
    };

    /**
     * Creates new form OptimizationPieces
     */
    public OptimizationPieces() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dmg = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        boss = new javax.swing.JTextField();
        att = new javax.swing.JTextField();
        ied = new javax.swing.JTextField();
        bp = new javax.swing.JToggleButton();
        weplvl = new javax.swing.JToggleButton();
        seclvl = new javax.swing.JToggleButton();
        kannaClass = new javax.swing.JToggleButton();
        zeroClass = new javax.swing.JToggleButton();
        no_3lbp = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        union = new javax.swing.JTextField();
        no_3l = new javax.swing.JToggleButton();
        wepSelect = new javax.swing.JToggleButton();
        secSelect = new javax.swing.JToggleButton();
        embSelect = new javax.swing.JToggleButton();
        wepInp1 = new javax.swing.JTextField();
        wepAtt1 = new javax.swing.JToggleButton();
        wepBoss1 = new javax.swing.JToggleButton();
        wepIed1 = new javax.swing.JToggleButton();
        wepInp2 = new javax.swing.JTextField();
        wepAtt2 = new javax.swing.JToggleButton();
        wepBoss2 = new javax.swing.JToggleButton();
        wepIed2 = new javax.swing.JToggleButton();
        wepInp3 = new javax.swing.JTextField();
        wepAtt3 = new javax.swing.JToggleButton();
        wepBoss3 = new javax.swing.JToggleButton();
        wepIed3 = new javax.swing.JToggleButton();
        secInp2 = new javax.swing.JTextField();
        secAtt2 = new javax.swing.JToggleButton();
        secBoss2 = new javax.swing.JToggleButton();
        secIed2 = new javax.swing.JToggleButton();
        secInp3 = new javax.swing.JTextField();
        secAtt3 = new javax.swing.JToggleButton();
        secInp1 = new javax.swing.JTextField();
        secBoss3 = new javax.swing.JToggleButton();
        secAtt1 = new javax.swing.JToggleButton();
        secIed3 = new javax.swing.JToggleButton();
        secBoss1 = new javax.swing.JToggleButton();
        secIed1 = new javax.swing.JToggleButton();
        embInp2 = new javax.swing.JTextField();
        embAtt2 = new javax.swing.JToggleButton();
        embIed2 = new javax.swing.JToggleButton();
        embInp3 = new javax.swing.JTextField();
        embAtt3 = new javax.swing.JToggleButton();
        embInp1 = new javax.swing.JTextField();
        embAtt1 = new javax.swing.JToggleButton();
        embIed3 = new javax.swing.JToggleButton();
        embIed1 = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        monDef = new javax.swing.JTextField();
        calculate = new javax.swing.JToggleButton();
        clearInp = new javax.swing.JToggleButton();
        wepInp4 = new javax.swing.JTextField();
        soulSelect = new javax.swing.JToggleButton();
        wepAtt4 = new javax.swing.JToggleButton();
        wepBoss4 = new javax.swing.JToggleButton();
        wepIed4 = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        embbpIed2 = new javax.swing.JToggleButton();
        embbpInp3 = new javax.swing.JTextField();
        embbpAtt3 = new javax.swing.JToggleButton();
        embbpInp1 = new javax.swing.JTextField();
        embbpAtt1 = new javax.swing.JToggleButton();
        embbpIed3 = new javax.swing.JToggleButton();
        embbpIed1 = new javax.swing.JToggleButton();
        wepbpSelect = new javax.swing.JToggleButton();
        embbpSelect = new javax.swing.JToggleButton();
        secbpSelect = new javax.swing.JToggleButton();
        wepbpInp2 = new javax.swing.JTextField();
        wepbpAtt2 = new javax.swing.JToggleButton();
        wepbpBoss2 = new javax.swing.JToggleButton();
        wepbpIed2 = new javax.swing.JToggleButton();
        wepbpInp3 = new javax.swing.JTextField();
        wepbpAtt3 = new javax.swing.JToggleButton();
        wepbpInp1 = new javax.swing.JTextField();
        wepbpBoss3 = new javax.swing.JToggleButton();
        wepbpAtt1 = new javax.swing.JToggleButton();
        wepbpIed3 = new javax.swing.JToggleButton();
        wepbpBoss1 = new javax.swing.JToggleButton();
        wepbpIed1 = new javax.swing.JToggleButton();
        secbpInp2 = new javax.swing.JTextField();
        secbpAtt2 = new javax.swing.JToggleButton();
        secbpBoss2 = new javax.swing.JToggleButton();
        secbpIed2 = new javax.swing.JToggleButton();
        secbpInp3 = new javax.swing.JTextField();
        secbpAtt3 = new javax.swing.JToggleButton();
        secbpInp1 = new javax.swing.JTextField();
        secbpBoss3 = new javax.swing.JToggleButton();
        secbpAtt1 = new javax.swing.JToggleButton();
        secbpIed3 = new javax.swing.JToggleButton();
        secbpBoss1 = new javax.swing.JToggleButton();
        secbpIed1 = new javax.swing.JToggleButton();
        embbpInp2 = new javax.swing.JTextField();
        embbpAtt2 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fd_LegionBP = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fd_Legion = new javax.swing.JTextArea();
        wseOptions = new javax.swing.JComboBox<>();
        numOptions = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        critDmgInp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        hyperStatsInp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WSE Optimization");
        setPreferredSize(new java.awt.Dimension(618, 715));
        setResizable(false);

        dmg.setText("214");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("DMG");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("BOSS");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("IED");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("ATT");

        boss.setText("170");

        att.setText("39");

        ied.setText("91.83");

        bp.setText("Bonus Potential");
        bp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpActionPerformed(evt);
            }
        });

        weplvl.setText("lvl 160+ Weapon");
        weplvl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weplvlActionPerformed(evt);
            }
        });

        seclvl.setText("lvl 160+ Secondary");
        seclvl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seclvlActionPerformed(evt);
            }
        });

        kannaClass.setText("Kanna Class");
        kannaClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kannaClassActionPerformed(evt);
            }
        });

        zeroClass.setText("Zero Class");
        zeroClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroClassActionPerformed(evt);
            }
        });

        no_3lbp.setText("No 3 Line Att Bpot");
        no_3lbp.setEnabled(false);
        no_3lbp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lbpActionPerformed(evt);
            }
        });

        jLabel7.setText("Legion Points for Boss, IED, Crit Damage");

        union.setText("120");

        no_3l.setText("No 3 Line Att");
        no_3l.setMaximumSize(new java.awt.Dimension(119, 23));
        no_3l.setMinimumSize(new java.awt.Dimension(119, 23));
        no_3l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lActionPerformed(evt);
            }
        });

        wepSelect.setText("Weapon");
        wepSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepSelectActionPerformed(evt);
            }
        });

        secSelect.setText("Secondary");
        secSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secSelectActionPerformed(evt);
            }
        });

        embSelect.setText("Emblem");
        embSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embSelectActionPerformed(evt);
            }
        });

        wepInp1.setEnabled(false);

        wepAtt1.setText("Att");
        wepAtt1.setEnabled(false);
        wepAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt1ActionPerformed(evt);
            }
        });

        wepBoss1.setText("Boss/Dmg");
        wepBoss1.setEnabled(false);
        wepBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss1ActionPerformed(evt);
            }
        });

        wepIed1.setText("IED");
        wepIed1.setEnabled(false);
        wepIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed1ActionPerformed(evt);
            }
        });

        wepInp2.setEnabled(false);

        wepAtt2.setText("Att");
        wepAtt2.setEnabled(false);
        wepAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt2ActionPerformed(evt);
            }
        });

        wepBoss2.setText("Boss/Dmg");
        wepBoss2.setEnabled(false);
        wepBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss2ActionPerformed(evt);
            }
        });

        wepIed2.setText("IED");
        wepIed2.setEnabled(false);
        wepIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed2ActionPerformed(evt);
            }
        });

        wepInp3.setEnabled(false);

        wepAtt3.setText("Att");
        wepAtt3.setEnabled(false);
        wepAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt3ActionPerformed(evt);
            }
        });

        wepBoss3.setText("Boss/Dmg");
        wepBoss3.setEnabled(false);
        wepBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss3ActionPerformed(evt);
            }
        });

        wepIed3.setText("IED");
        wepIed3.setEnabled(false);
        wepIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed3ActionPerformed(evt);
            }
        });

        secInp2.setEnabled(false);

        secAtt2.setText("Att");
        secAtt2.setEnabled(false);
        secAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt2ActionPerformed(evt);
            }
        });

        secBoss2.setText("Boss/Dmg");
        secBoss2.setEnabled(false);
        secBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss2ActionPerformed(evt);
            }
        });

        secIed2.setText("IED");
        secIed2.setEnabled(false);
        secIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed2ActionPerformed(evt);
            }
        });

        secInp3.setEnabled(false);

        secAtt3.setText("Att");
        secAtt3.setEnabled(false);
        secAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt3ActionPerformed(evt);
            }
        });

        secInp1.setEnabled(false);

        secBoss3.setText("Boss/Dmg");
        secBoss3.setEnabled(false);
        secBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss3ActionPerformed(evt);
            }
        });

        secAtt1.setText("Att");
        secAtt1.setEnabled(false);
        secAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt1ActionPerformed(evt);
            }
        });

        secIed3.setText("IED");
        secIed3.setEnabled(false);
        secIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed3ActionPerformed(evt);
            }
        });

        secBoss1.setText("Boss/Dmg");
        secBoss1.setEnabled(false);
        secBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss1ActionPerformed(evt);
            }
        });

        secIed1.setText("IED");
        secIed1.setEnabled(false);
        secIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed1ActionPerformed(evt);
            }
        });

        embInp2.setEnabled(false);

        embAtt2.setText("Att");
        embAtt2.setEnabled(false);
        embAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt2ActionPerformed(evt);
            }
        });

        embIed2.setText("IED");
        embIed2.setEnabled(false);
        embIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed2ActionPerformed(evt);
            }
        });

        embInp3.setEnabled(false);

        embAtt3.setText("Att");
        embAtt3.setEnabled(false);
        embAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt3ActionPerformed(evt);
            }
        });

        embInp1.setEnabled(false);

        embAtt1.setText("Att");
        embAtt1.setEnabled(false);
        embAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt1ActionPerformed(evt);
            }
        });

        embIed3.setText("IED");
        embIed3.setEnabled(false);
        embIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed3ActionPerformed(evt);
            }
        });

        embIed1.setText("IED");
        embIed1.setEnabled(false);
        embIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Monster Defense");

        monDef.setText("300");

        calculate.setText("Calculate");
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });

        clearInp.setText("Reset");
        clearInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearInpActionPerformed(evt);
            }
        });

        wepInp4.setEnabled(false);

        soulSelect.setText("Soul");
        soulSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soulSelectActionPerformed(evt);
            }
        });

        wepAtt4.setText("Att");
        wepAtt4.setEnabled(false);
        wepAtt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt4ActionPerformed(evt);
            }
        });

        wepBoss4.setText("Boss/Dmg");
        wepBoss4.setEnabled(false);
        wepBoss4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss4ActionPerformed(evt);
            }
        });

        wepIed4.setText("IED");
        wepIed4.setEnabled(false);
        wepIed4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed4ActionPerformed(evt);
            }
        });

        jPanel1.setVisible(false);

        embbpIed2.setText("IED");
        embbpIed2.setEnabled(false);
        embbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed2ActionPerformed(evt);
            }
        });

        embbpInp3.setEnabled(false);

        embbpAtt3.setText("Att");
        embbpAtt3.setEnabled(false);
        embbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt3ActionPerformed(evt);
            }
        });

        embbpInp1.setEnabled(false);

        embbpAtt1.setText("Att");
        embbpAtt1.setEnabled(false);
        embbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt1ActionPerformed(evt);
            }
        });

        embbpIed3.setText("IED");
        embbpIed3.setEnabled(false);
        embbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed3ActionPerformed(evt);
            }
        });

        embbpIed1.setText("IED");
        embbpIed1.setEnabled(false);
        embbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed1ActionPerformed(evt);
            }
        });

        wepbpSelect.setText("Weapon Bonus Potential");
        wepbpSelect.setEnabled(false);
        wepbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpSelectActionPerformed(evt);
            }
        });

        embbpSelect.setText("Emblem Bonus Potential");
        embbpSelect.setEnabled(false);
        embbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpSelectActionPerformed(evt);
            }
        });

        secbpSelect.setText("Secondary Bonus Potential");
        secbpSelect.setEnabled(false);
        secbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpSelectActionPerformed(evt);
            }
        });

        wepbpInp2.setEnabled(false);

        wepbpAtt2.setText("Att");
        wepbpAtt2.setEnabled(false);
        wepbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt2ActionPerformed(evt);
            }
        });

        wepbpBoss2.setText("Boss/Dmg");
        wepbpBoss2.setEnabled(false);
        wepbpBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss2ActionPerformed(evt);
            }
        });

        wepbpIed2.setText("IED");
        wepbpIed2.setEnabled(false);
        wepbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed2ActionPerformed(evt);
            }
        });

        wepbpInp3.setEnabled(false);

        wepbpAtt3.setText("Att");
        wepbpAtt3.setEnabled(false);
        wepbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt3ActionPerformed(evt);
            }
        });

        wepbpInp1.setEnabled(false);

        wepbpBoss3.setText("Boss/Dmg");
        wepbpBoss3.setEnabled(false);
        wepbpBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss3ActionPerformed(evt);
            }
        });

        wepbpAtt1.setText("Att");
        wepbpAtt1.setEnabled(false);
        wepbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt1ActionPerformed(evt);
            }
        });

        wepbpIed3.setText("IED");
        wepbpIed3.setEnabled(false);
        wepbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed3ActionPerformed(evt);
            }
        });

        wepbpBoss1.setText("Boss/Dmg");
        wepbpBoss1.setEnabled(false);
        wepbpBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss1ActionPerformed(evt);
            }
        });

        wepbpIed1.setText("IED");
        wepbpIed1.setEnabled(false);
        wepbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed1ActionPerformed(evt);
            }
        });

        secbpInp2.setEnabled(false);

        secbpAtt2.setText("Att");
        secbpAtt2.setEnabled(false);
        secbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt2ActionPerformed(evt);
            }
        });

        secbpBoss2.setText("Boss/Dmg");
        secbpBoss2.setEnabled(false);
        secbpBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss2ActionPerformed(evt);
            }
        });

        secbpIed2.setText("IED");
        secbpIed2.setEnabled(false);
        secbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed2ActionPerformed(evt);
            }
        });

        secbpInp3.setEnabled(false);

        secbpAtt3.setText("Att");
        secbpAtt3.setEnabled(false);
        secbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt3ActionPerformed(evt);
            }
        });

        secbpInp1.setEnabled(false);

        secbpBoss3.setText("Boss/Dmg");
        secbpBoss3.setEnabled(false);
        secbpBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss3ActionPerformed(evt);
            }
        });

        secbpAtt1.setText("Att");
        secbpAtt1.setEnabled(false);
        secbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt1ActionPerformed(evt);
            }
        });

        secbpIed3.setText("IED");
        secbpIed3.setEnabled(false);
        secbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed3ActionPerformed(evt);
            }
        });

        secbpBoss1.setText("Boss/Dmg");
        secbpBoss1.setEnabled(false);
        secbpBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss1ActionPerformed(evt);
            }
        });

        secbpIed1.setText("IED");
        secbpIed1.setEnabled(false);
        secbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed1ActionPerformed(evt);
            }
        });

        embbpInp2.setEnabled(false);

        embbpAtt2.setText("Att");
        embbpAtt2.setEnabled(false);
        embbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt2ActionPerformed(evt);
            }
        });

        fd_LegionBP.setColumns(20);
        fd_LegionBP.setRows(5);
        fd_LegionBP.setEnabled(false);
        fd_LegionBP.setPreferredSize(new java.awt.Dimension(164, 100));
        jScrollPane1.setViewportView(fd_LegionBP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(wepbpSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(wepbpAtt1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(wepbpInp2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(wepbpAtt2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpBoss2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)
                        .addComponent(wepbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(wepbpAtt3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpBoss3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(wepbpInp3)
                    .addComponent(wepbpInp1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(secbpAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpBoss3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(secbpAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpBoss2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(secbpInp2)
                    .addComponent(secbpInp3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(secbpAtt1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secbpIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(secbpInp1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(secbpSelect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(embbpAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(embbpIed1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(embbpAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(embbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(embbpInp2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(embbpAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(embbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(embbpInp3)
                    .addComponent(embbpSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(embbpInp1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wepbpSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secbpSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(embbpSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(wepbpInp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepbpAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpInp2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepbpAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpBoss2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpBoss2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepbpInp3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepbpAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpBoss3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(secbpInp1)
                        .addGap(44, 44, 44)
                        .addComponent(secbpInp2)
                        .addGap(44, 44, 44)
                        .addComponent(secbpInp3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(secbpBoss3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secbpAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(embbpInp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(embbpAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(embbpIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(embbpInp2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(embbpAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(embbpIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(embbpInp3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(embbpAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(embbpIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setVisible(true);

        fd_Legion.setColumns(20);
        fd_Legion.setRows(5);
        fd_Legion.setEnabled(false);
        fd_Legion.setPreferredSize(new java.awt.Dimension(164, 100));
        jScrollPane2.setViewportView(fd_Legion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 244, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        wseOptions.setModel(new DefaultComboBoxModel());
        wseOptions.setEnabled(false);
        wseOptions.setMaximumSize(new java.awt.Dimension(28, 20));
        wseOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wseOptionsItemStateChanged(evt);
            }
        });

        numOptions.setText("10");

        jLabel5.setText("Additional Options");

        critDmgInp.setText("90");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setLabelFor(critDmgInp);
        jLabel9.setText("Critical Damage");

        hyperStatsInp.setText("1091");

        jLabel11.setLabelFor(hyperStatsInp);
        jLabel11.setText("Hyper Points for Boss, Damage, IED, Crit Damage");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wepInp4)
                    .addComponent(soulSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bp, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(no_3lbp, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(no_3l, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dmg)
                            .addComponent(boss)
                            .addComponent(ied)
                            .addComponent(att, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(weplvl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(seclvl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kannaClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zeroClass, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(numOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                    .addComponent(hyperStatsInp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(10, 10, 10)
                                        .addComponent(wseOptions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(union, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(critDmgInp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(monDef, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calculate, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearInp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(wepAtt1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepBoss1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(wepAtt2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepBoss2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(wepInp2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(wepAtt3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepBoss3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wepIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(wepInp3)
                            .addComponent(wepInp1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(wepSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(secAtt1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secBoss1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(secInp1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(secAtt2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secBoss2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(secInp2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(secAtt3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secBoss3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(secInp3)
                            .addComponent(secSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(embAtt2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(embIed2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(embInp3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(embInp2)
                            .addComponent(embInp1)
                            .addComponent(embSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(embAtt3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(embIed3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(embAtt1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(embIed1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(wepAtt4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepBoss4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepIed4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dmg)
                    .addComponent(weplvl)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(critDmgInp)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(monDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seclvl)
                    .addComponent(boss)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(union)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kannaClass)
                    .addComponent(att)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hyperStatsInp)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zeroClass)
                    .addComponent(ied)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(wseOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(no_3lbp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(no_3l, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearInp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(wepSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(wepInp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepInp2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepBoss2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wepInp3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wepAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepBoss3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(wepIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(embSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(80, 80, 80)
                                .addComponent(embInp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(secSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(secInp1)
                                    .addComponent(embInp1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(secAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(secBoss1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(secIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(embAtt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(embIed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(secInp2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(secAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secBoss2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secIed2)
                            .addComponent(embAtt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(embIed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secInp3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(embInp3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(secAtt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secBoss3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(embAtt3)
                            .addComponent(embIed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soulSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wepInp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wepAtt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wepBoss4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wepIed4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void secSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secSelectActionPerformed
        if (secSelect.isSelected()) {
            setSecondaryEnabled(true);
        } else {
            setSecondaryEnabled(false);
        }
    }//GEN-LAST:event_secSelectActionPerformed

    private void bpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpActionPerformed
        if (bp.isSelected()) {
            setSize(618, 960);
            jPanel1.setVisible(true);
            jPanel2.setVisible(false);
            wepbpSelect.setEnabled(true);
            if (classType != ClassType.ZERO) {
                secbpSelect.setEnabled(true);
            }
            embbpSelect.setEnabled(true);
            no_3lbp.setEnabled(true);
        } else {
            setSize(618, 715);
            jPanel1.setVisible(false);
            jPanel2.setVisible(true);
            wepbpSelect.setEnabled(false);
            secbpSelect.setEnabled(false);
            embbpSelect.setEnabled(false);
            wepbpSelect.setSelected(false);
            secbpSelect.setSelected(false);
            embbpSelect.setSelected(false);
            setEmblemBPEnabled(false);
            setSecondaryBPEnabled(false);
            setWeaponBPEnabled(false);
            no_3lbp.setEnabled(false);
        }
    }//GEN-LAST:event_bpActionPerformed

    private void kannaClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kannaClassActionPerformed
        if (kannaClass.isSelected()) {
            zeroClass.setEnabled(false);
            this.classType = ClassType.KANNA;
        } else {
            this.classType = ClassType.NOCLASS;
            zeroClass.setEnabled(true);
        }
    }//GEN-LAST:event_kannaClassActionPerformed

    private void no_3lActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_3lActionPerformed
        if (no_3l.isSelected()) {
            this.no_3lAtt = PotConfig.NO3LINE;
        } else {
            this.no_3lAtt = PotConfig.DEFAULT;
        }
    }//GEN-LAST:event_no_3lActionPerformed

    private void no_3lbpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_3lbpActionPerformed
        if (no_3lbp.isSelected()) {
            this.no_3lbpAtt = PotConfig.NO3LINE;
        } else {
            this.no_3lbpAtt = PotConfig.DEFAULT;
        }
    }//GEN-LAST:event_no_3lbpActionPerformed

    @SuppressWarnings("unchecked")
    private void calculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateActionPerformed
        if (calculate.isSelected()) {
            //Disable the combobox
            wseOptions.setEnabled(false);
            //Determine the inputs from the text fields
            try {
                this.numberOfOptions = Integer.parseInt(numOptions.getText());
                if (this.numberOfOptions < 0) {
                    this.numberOfOptions = 0;
                }
                this.att_base = Double.parseDouble(att.getText()) / 100;
                this.boss_base = Double.parseDouble(boss.getText()) / 100;
                this.dmg_base = Double.parseDouble(dmg.getText()) / 100;
                this.ied_base = Double.parseDouble(ied.getText()) / 100;
                this.crit_base = Double.parseDouble(critDmgInp.getText()) / 100;
                this.pdr = Double.parseDouble(monDef.getText()) / 100;
                this.hyperPoints = Integer.parseInt(hyperStatsInp.getText());
                saveBase();
                this.legionVal = Integer.parseInt(union.getText());
                //Sets up a scaling value for weapon inputs if the class selected was Zero
                double zero_scale = 1;
                if (this.classType == ClassType.ZERO) {
                    zero_scale = 2;
                }
                //If the weapon is sleceted go through and pull all the inputs and add them to the base values
                if (wepSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepInp1.getText().equals("") && wepInp1_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp1.getText()) / 100;
                        switch (wepInp1_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepInp2.getText().equals("") && wepInp2_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp2.getText()) / 100;
                        switch (wepInp2_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepInp3.getText().equals("") && wepInp3_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp3.getText()) / 100;
                        switch (wepInp3_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                }
                //If the secondary is sleceted go through and pull all the inputs and add them to the base values
                if (secSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secInp1.getText().equals("") && secInp1_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp1.getText()) / 100;
                        switch (secInp1_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secInp2.getText().equals("") && secInp2_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp2.getText()) / 100;
                        switch (secInp2_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secInp3.getText().equals("") && secInp3_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp3.getText()) / 100;
                        switch (secInp3_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                }
                //If the emblem is sleceted go through and pull all the inputs and add them to the base values
                if (embSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embInp1.getText().equals("") && embInp1_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp1.getText()) / 100;
                        switch (embInp1_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embInp2.getText().equals("") && embInp2_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp2.getText()) / 100;
                        switch (embInp2_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embInp3.getText().equals("") && embInp3_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp3.getText()) / 100;
                        switch (embInp3_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                }

                //If the weapon is sleceted go through and pull all the inputs and add them to the base values
                if (wepbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepbpInp1.getText().equals("") && wepbpInp1_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp1.getText()) / 100;
                        switch (wepbpInp1_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepbpInp2.getText().equals("") && wepbpInp2_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp2.getText()) / 100;
                        switch (wepbpInp2_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepbpInp3.getText().equals("") && wepbpInp3_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp3.getText()) / 100;
                        switch (wepbpInp3_butSel) {
                            case ATT:
                                this.att_base += wepInp * zero_scale;
                                break;
                            case BOSS:
                                this.boss_base += wepInp * zero_scale;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                if (zero_scale == 2) {
                                    this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp) * (1 - wepInp)));
                                }
                                break;
                        }
                    }
                }
                //If the secondary is sleceted go through and pull all the inputs and add them to the base values
                if (secbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secbpInp1.getText().equals("") && secbpInp1_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp1.getText()) / 100;
                        switch (secbpInp1_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secbpInp2.getText().equals("") && secbpInp2_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp2.getText()) / 100;
                        switch (secbpInp2_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!secbpInp3.getText().equals("") && secbpInp3_butSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp3.getText()) / 100;
                        switch (secbpInp3_butSel) {
                            case ATT:
                                this.att_base += secInp;
                                break;
                            case BOSS:
                                this.boss_base += secInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - secInp)));
                                break;
                        }
                    }
                }
                //If the emblem is sleceted go through and pull all the inputs and add them to the base values
                if (embbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embbpInp1.getText().equals("") && embbpInp1_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp1.getText()) / 100;
                        switch (embbpInp1_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embbpInp2.getText().equals("") && embbpInp2_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp2.getText()) / 100;
                        switch (embbpInp2_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embbpInp3.getText().equals("") && embbpInp3_butSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp3.getText()) / 100;
                        switch (embbpInp3_butSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                }
                //If the soul is sleceted go through and pull all the inputs and add them to the base values
                if (soulSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!wepInp4.getText().equals("") && wepInp5_butSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp4.getText()) / 100;
                        switch (wepInp5_butSel) {
                            case ATT:
                                this.att_base += wepInp;
                                break;
                            case BOSS:
                                this.boss_base += wepInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - wepInp)));
                                break;
                        }
                    }
                }

                //Start time of the method
                this.startTime = System.nanoTime();
                worker = new WSEWorker(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.no_3lbpAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), this.embbpSelect.isSelected(), this.wepbpSelect.isSelected(), this.secbpSelect.isSelected(), PotType.DEFAULT, numberOfOptions, Server.REBOOT);
                worker.addPropertyChangeListener(listener);
                worker.execute();
//                if (!bp.isSelected()) {
////                    WSEHelpers.progressOutput = fd_Legion;
//                    if (soulSelect.isSelected()) {
//                        simpleWSE = WSEHelpers.reb_opt(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), wepInp5_butSel, numberOfOptions);
//                    } else {
//                        simpleWSE = WSEHelpers.reb_opt(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), PotType.DEFAULT, numberOfOptions);
//                    }
//                } else {
////                    WSEHelpers.progressOutput = fd_LegionBP; 
//                    if (soulSelect.isSelected()) {
//                        simpleWSE = WSEHelpers.nreb_opt(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.no_3lbpAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), this.embbpSelect.isSelected(), this.wepbpSelect.isSelected(), this.secbpSelect.isSelected(), wepInp5_butSel, numberOfOptions);
//                    } else {
//                        simpleWSE = WSEHelpers.nreb_opt(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.no_3lbpAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), this.embbpSelect.isSelected(), this.wepbpSelect.isSelected(), this.secbpSelect.isSelected(), PotType.DEFAULT, numberOfOptions);
//                    }
//                }
//                this.comboBoxMap = ComboBoxSupport.buildComboBoxMap(simpleWSE);
//                pt = simpleWSE.get(0);
//                if (pt != null) {
//                    outputPotVector(pt);
//                    wseOptions.setEnabled(true);
//                    wseOptions.setModel(ComboBoxSupport.buildComboBoxItems(comboBoxMap));
//                } else {
//                    System.out.println("Something went terribly wrong and the vector was null!");
//                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
                fd_Legion.setText("ERROR OCCURED: REDO INPUTS");
                fd_LegionBP.setText("ERROR OCCURED: REDO INPUTS");
            }
        }
        calculate.setSelected(false);
    }//GEN-LAST:event_calculateActionPerformed

    private void clearInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearInpActionPerformed
        if (clearInp.isSelected()) {
            wepInp1.setText("");
            wepInp2.setText("");
            wepInp3.setText("");

            secInp1.setText("");
            secInp2.setText("");
            secInp3.setText("");

            embInp1.setText("");
            embInp2.setText("");
            embInp3.setText("");
            wepbpInp1.setText("");
            wepbpInp2.setText("");
            wepbpInp3.setText("");
            secbpInp1.setText("");
            secbpInp2.setText("");
            secbpInp3.setText("");
            embbpInp1.setText("");
            embbpInp2.setText("");
            embbpInp3.setText("");
            wepInp4.setText("");
            clearInp.setSelected(false);
        }
    }//GEN-LAST:event_clearInpActionPerformed

    private void wepSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepSelectActionPerformed
        if (wepSelect.isSelected()) {
            setWeaponEnabled(true);
        } else {
            setWeaponEnabled(false);
        }
    }//GEN-LAST:event_wepSelectActionPerformed

    private void embSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embSelectActionPerformed
        if (embSelect.isSelected()) {
            setEmblemEnabled(true);
        } else {
            setEmblemEnabled(false);
        }
    }//GEN-LAST:event_embSelectActionPerformed

    private void seclvlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seclvlActionPerformed
        this.sec_lvl = seclvl.isSelected();
    }//GEN-LAST:event_seclvlActionPerformed

    private void wepAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepAtt1ActionPerformed
        this.wepInp1_butSel = buttonSelectAndDisable(wepAtt1, wepBoss1, wepIed1, PotType.ATT);
    }//GEN-LAST:event_wepAtt1ActionPerformed

    private void wepAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepAtt2ActionPerformed
        this.wepInp2_butSel = buttonSelectAndDisable(wepAtt2, wepBoss2, wepIed2, PotType.ATT);
    }//GEN-LAST:event_wepAtt2ActionPerformed

    private void wepAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepAtt3ActionPerformed
        this.wepInp3_butSel = buttonSelectAndDisable(wepAtt3, wepBoss3, wepIed3, PotType.ATT);
    }//GEN-LAST:event_wepAtt3ActionPerformed

    private void secAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secAtt1ActionPerformed
        this.secInp1_butSel = buttonSelectAndDisable(secAtt1, secBoss1, secIed1, PotType.ATT);
    }//GEN-LAST:event_secAtt1ActionPerformed

    private void secAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secAtt2ActionPerformed
        this.secInp2_butSel = buttonSelectAndDisable(secAtt2, secBoss2, secIed2, PotType.ATT);
    }//GEN-LAST:event_secAtt2ActionPerformed

    private void secAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secAtt3ActionPerformed
        this.secInp3_butSel = buttonSelectAndDisable(secAtt3, secBoss3, secIed3, PotType.ATT);
    }//GEN-LAST:event_secAtt3ActionPerformed

    private void zeroClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroClassActionPerformed
        if (zeroClass.isSelected()) {
            kannaClass.setEnabled(false);
            seclvl.setEnabled(false);
            secSelect.setEnabled(false);
            secSelect.setSelected(false);
            secbpSelect.setEnabled(false);
            secbpSelect.setSelected(false);
            setSecondaryEnabled(false);
            setSecondaryBPEnabled(false);
            this.classType = ClassType.ZERO;
        } else {
            kannaClass.setEnabled(true);
            secSelect.setEnabled(true);
            seclvl.setEnabled(true);
            secbpSelect.setEnabled(true);
            this.classType = ClassType.NOCLASS;
        }
    }//GEN-LAST:event_zeroClassActionPerformed

    private void embAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embAtt1ActionPerformed
        this.embInp1_butSel = buttonSelectAndDisable(embAtt1, embIed1, null, PotType.ATT);
    }//GEN-LAST:event_embAtt1ActionPerformed

    private void embAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embAtt2ActionPerformed
        this.embInp2_butSel = buttonSelectAndDisable(embAtt2, embIed2, null, PotType.ATT);
    }//GEN-LAST:event_embAtt2ActionPerformed

    private void embAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embAtt3ActionPerformed
        this.embInp3_butSel = buttonSelectAndDisable(embAtt3, embIed3, null, PotType.ATT);
    }//GEN-LAST:event_embAtt3ActionPerformed

    private void wepBoss1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepBoss1ActionPerformed
        this.wepInp1_butSel = buttonSelectAndDisable(wepBoss1, wepAtt1, wepIed1, PotType.BOSS);
    }//GEN-LAST:event_wepBoss1ActionPerformed

    private void wepBoss2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepBoss2ActionPerformed
        this.wepInp2_butSel = buttonSelectAndDisable(wepBoss2, wepAtt2, wepIed2, PotType.BOSS);
    }//GEN-LAST:event_wepBoss2ActionPerformed

    private void wepBoss3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepBoss3ActionPerformed
        this.wepInp3_butSel = buttonSelectAndDisable(wepBoss3, wepAtt3, wepIed3, PotType.BOSS);
    }//GEN-LAST:event_wepBoss3ActionPerformed

    private void wepIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepIed1ActionPerformed
        this.wepInp1_butSel = buttonSelectAndDisable(wepIed1, wepAtt1, wepBoss1, PotType.IED);
    }//GEN-LAST:event_wepIed1ActionPerformed

    private void wepIed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepIed2ActionPerformed
        this.wepInp2_butSel = buttonSelectAndDisable(wepIed2, wepAtt2, wepBoss2, PotType.IED);
    }//GEN-LAST:event_wepIed2ActionPerformed

    private void wepIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepIed3ActionPerformed
        this.wepInp3_butSel = buttonSelectAndDisable(wepIed3, wepAtt3, wepBoss3, PotType.IED);
    }//GEN-LAST:event_wepIed3ActionPerformed

    private void secBoss1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secBoss1ActionPerformed
        this.secInp1_butSel = buttonSelectAndDisable(secBoss1, secAtt1, secIed1, PotType.BOSS);
    }//GEN-LAST:event_secBoss1ActionPerformed

    private void secBoss2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secBoss2ActionPerformed
        this.secInp2_butSel = buttonSelectAndDisable(secBoss2, secAtt2, secIed2, PotType.BOSS);
    }//GEN-LAST:event_secBoss2ActionPerformed

    private void secBoss3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secBoss3ActionPerformed
        this.secInp3_butSel = buttonSelectAndDisable(secBoss3, secAtt3, secIed3, PotType.BOSS);
    }//GEN-LAST:event_secBoss3ActionPerformed

    private void secIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secIed1ActionPerformed
        this.secInp1_butSel = buttonSelectAndDisable(secIed1, secAtt1, secBoss1, PotType.IED);
    }//GEN-LAST:event_secIed1ActionPerformed

    private void secIed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secIed2ActionPerformed
        this.secInp2_butSel = buttonSelectAndDisable(secIed2, secAtt2, secBoss2, PotType.IED);
    }//GEN-LAST:event_secIed2ActionPerformed

    private void secIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secIed3ActionPerformed
        this.secInp3_butSel = buttonSelectAndDisable(secIed3, secAtt3, secBoss3, PotType.IED);
    }//GEN-LAST:event_secIed3ActionPerformed

    private void embIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embIed1ActionPerformed
        this.embInp1_butSel = buttonSelectAndDisable(embIed1, embAtt1, null, PotType.IED);
    }//GEN-LAST:event_embIed1ActionPerformed

    private void embIed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embIed2ActionPerformed
        this.embInp2_butSel = buttonSelectAndDisable(embIed2, embAtt2, null, PotType.IED);
    }//GEN-LAST:event_embIed2ActionPerformed

    private void embIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embIed3ActionPerformed
        this.embInp3_butSel = buttonSelectAndDisable(embIed3, embAtt3, null, PotType.IED);
    }//GEN-LAST:event_embIed3ActionPerformed

    private void weplvlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weplvlActionPerformed
        this.wep_lvl = weplvl.isSelected();
    }//GEN-LAST:event_weplvlActionPerformed

    private void soulSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soulSelectActionPerformed
        if (soulSelect.isSelected()) {
            setSoulEnabled(true);
        } else {
            setSoulEnabled(false);
        }
    }//GEN-LAST:event_soulSelectActionPerformed

    private void wepAtt4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepAtt4ActionPerformed
        this.wepInp5_butSel = buttonSelectAndDisable(wepAtt4, wepIed4, wepBoss4, PotType.ATT);
    }//GEN-LAST:event_wepAtt4ActionPerformed

    private void wepBoss4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepBoss4ActionPerformed
        this.wepInp5_butSel = buttonSelectAndDisable(wepBoss4, wepAtt4, wepIed4, PotType.BOSS);
    }//GEN-LAST:event_wepBoss4ActionPerformed

    private void wepIed4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepIed4ActionPerformed
        this.wepInp5_butSel = buttonSelectAndDisable(wepIed4, wepBoss4, wepAtt4, PotType.IED);
    }//GEN-LAST:event_wepIed4ActionPerformed

    private void embbpAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpAtt2ActionPerformed
        this.embbpInp2_butSel = buttonSelectAndDisable(embbpAtt2, embbpIed2, null, PotType.ATT);
    }//GEN-LAST:event_embbpAtt2ActionPerformed

    private void secbpIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpIed1ActionPerformed
        this.secbpInp1_butSel = buttonSelectAndDisable(secbpIed1, secbpBoss1, secbpAtt1, PotType.IED);
    }//GEN-LAST:event_secbpIed1ActionPerformed

    private void secbpBoss1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpBoss1ActionPerformed
        this.secbpInp1_butSel = buttonSelectAndDisable(secbpBoss1, secbpAtt1, secbpIed1, PotType.BOSS);
    }//GEN-LAST:event_secbpBoss1ActionPerformed

    private void secbpIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpIed3ActionPerformed
        this.secbpInp3_butSel = buttonSelectAndDisable(secbpIed3, secbpBoss3, secbpAtt3, PotType.IED);
    }//GEN-LAST:event_secbpIed3ActionPerformed

    private void secbpAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpAtt1ActionPerformed
        this.secbpInp1_butSel = buttonSelectAndDisable(secbpAtt1, secbpBoss1, secbpIed1, PotType.ATT);
    }//GEN-LAST:event_secbpAtt1ActionPerformed

    private void secbpBoss3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpBoss3ActionPerformed
        this.secbpInp3_butSel = buttonSelectAndDisable(secbpBoss3, secbpAtt3, secbpIed3, PotType.BOSS);
    }//GEN-LAST:event_secbpBoss3ActionPerformed

    private void secbpAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpAtt3ActionPerformed
        this.secbpInp3_butSel = buttonSelectAndDisable(secbpAtt3, secbpBoss3, secbpIed3, PotType.ATT);
    }//GEN-LAST:event_secbpAtt3ActionPerformed

    private void secbpIed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpIed2ActionPerformed
        this.secbpInp2_butSel = buttonSelectAndDisable(secbpIed2, secbpAtt2, secbpBoss2, PotType.IED);
    }//GEN-LAST:event_secbpIed2ActionPerformed

    private void secbpBoss2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpBoss2ActionPerformed
        this.secbpInp2_butSel = buttonSelectAndDisable(secbpBoss2, secbpAtt2, secbpIed2, PotType.BOSS);
    }//GEN-LAST:event_secbpBoss2ActionPerformed

    private void secbpAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpAtt2ActionPerformed
        this.secbpInp2_butSel = buttonSelectAndDisable(secbpAtt2, secbpBoss2, secbpIed2, PotType.ATT);
    }//GEN-LAST:event_secbpAtt2ActionPerformed

    private void wepbpIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpIed1ActionPerformed
        this.wepbpInp1_butSel = buttonSelectAndDisable(wepbpIed1, wepbpAtt1, wepbpBoss1, PotType.IED);
    }//GEN-LAST:event_wepbpIed1ActionPerformed

    private void wepbpBoss1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpBoss1ActionPerformed
        this.wepbpInp1_butSel = buttonSelectAndDisable(wepbpBoss1, wepbpAtt1, wepbpIed1, PotType.BOSS);
    }//GEN-LAST:event_wepbpBoss1ActionPerformed

    private void wepbpIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpIed3ActionPerformed
        this.wepbpInp3_butSel = buttonSelectAndDisable(wepbpIed3, wepbpAtt3, wepbpBoss3, PotType.IED);
    }//GEN-LAST:event_wepbpIed3ActionPerformed

    private void wepbpAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpAtt1ActionPerformed
        this.wepbpInp1_butSel = buttonSelectAndDisable(wepbpAtt1, wepbpBoss1, wepbpIed1, PotType.ATT);
    }//GEN-LAST:event_wepbpAtt1ActionPerformed

    private void wepbpBoss3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpBoss3ActionPerformed
        this.wepbpInp3_butSel = buttonSelectAndDisable(wepbpBoss3, wepbpAtt3, wepbpIed3, PotType.BOSS);
    }//GEN-LAST:event_wepbpBoss3ActionPerformed

    private void wepbpAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpAtt3ActionPerformed
        this.wepbpInp3_butSel = buttonSelectAndDisable(wepbpAtt3, wepbpBoss3, wepbpIed3, PotType.ATT);
    }//GEN-LAST:event_wepbpAtt3ActionPerformed

    private void wepbpIed2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpIed2ActionPerformed
        this.wepbpInp2_butSel = buttonSelectAndDisable(wepbpIed2, wepbpAtt2, wepbpBoss2, PotType.IED);
    }//GEN-LAST:event_wepbpIed2ActionPerformed

    private void wepbpBoss2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpBoss2ActionPerformed
        this.wepbpInp2_butSel = buttonSelectAndDisable(wepbpBoss2, wepbpAtt2, wepbpIed2, PotType.BOSS);
    }//GEN-LAST:event_wepbpBoss2ActionPerformed

    private void wepbpAtt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpAtt2ActionPerformed
        this.wepbpInp2_butSel = buttonSelectAndDisable(wepbpAtt2, wepbpBoss2, wepbpIed2, PotType.ATT);
    }//GEN-LAST:event_wepbpAtt2ActionPerformed

    private void secbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpSelectActionPerformed
        if (secbpSelect.isSelected()) {
            setSecondaryBPEnabled(true);
        } else {
            setSecondaryBPEnabled(false);
        }
    }//GEN-LAST:event_secbpSelectActionPerformed

    private void embbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpSelectActionPerformed
        if (embbpSelect.isSelected()) {
            setEmblemBPEnabled(true);
        } else {
            setEmblemBPEnabled(false);
        }
    }//GEN-LAST:event_embbpSelectActionPerformed

    private void wepbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpSelectActionPerformed
        if (wepbpSelect.isSelected()) {
            setWeaponBPEnabled(true);
        } else {
            setWeaponBPEnabled(false);
        }
    }//GEN-LAST:event_wepbpSelectActionPerformed

    private void embbpIed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpIed1ActionPerformed
        this.embbpInp1_butSel = buttonSelectAndDisable(embbpIed1, embbpAtt1, null, PotType.IED);
    }//GEN-LAST:event_embbpIed1ActionPerformed

    private void embbpIed3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpIed3ActionPerformed
        this.embbpInp3_butSel = buttonSelectAndDisable(embbpIed3, embbpAtt3, null, PotType.IED);
    }//GEN-LAST:event_embbpIed3ActionPerformed

    private void embbpAtt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpAtt1ActionPerformed
        this.embbpInp1_butSel = buttonSelectAndDisable(embbpAtt1, embbpIed1, null, PotType.ATT);
    }//GEN-LAST:event_embbpAtt1ActionPerformed

    private void embbpAtt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpAtt3ActionPerformed
        this.embbpInp3_butSel = buttonSelectAndDisable(embbpAtt3, embbpIed3, null, PotType.ATT);
    }//GEN-LAST:event_embbpAtt3ActionPerformed

    private void wseOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wseOptionsItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            outputPotVector(this.comboBoxMap.get(wseOptions.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_wseOptionsItemStateChanged

    private void embbpIed2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.embbpInp2_butSel = buttonSelectAndDisable(embbpIed2, embbpAtt2, null, PotType.IED);
    }

    public void setEmblemEnabled(boolean b) {
        embInp1.setEnabled(b);
        embInp2.setEnabled(b);
        embInp3.setEnabled(b);
        embAtt1.setEnabled(b);
        embAtt2.setEnabled(b);
        embAtt3.setEnabled(b);
        embIed1.setEnabled(b);
        embIed2.setEnabled(b);
        embIed3.setEnabled(b);
    }

    public void setEmblemBPEnabled(boolean b) {
        embbpInp1.setEnabled(b);
        embbpInp2.setEnabled(b);
        embbpInp3.setEnabled(b);
        embbpAtt1.setEnabled(b);
        embbpAtt2.setEnabled(b);
        embbpAtt3.setEnabled(b);
        embbpIed1.setEnabled(b);
        embbpIed2.setEnabled(b);
        embbpIed3.setEnabled(b);
    }

    public void setSecondaryEnabled(boolean b) {
        secInp1.setEnabled(b);
        secInp2.setEnabled(b);
        secInp3.setEnabled(b);
        secAtt1.setEnabled(b);
        secAtt2.setEnabled(b);
        secAtt3.setEnabled(b);
        secIed1.setEnabled(b);
        secIed2.setEnabled(b);
        secIed3.setEnabled(b);
        secBoss1.setEnabled(b);
        secBoss2.setEnabled(b);
        secBoss3.setEnabled(b);
    }

    public void setSecondaryBPEnabled(boolean b) {
        secbpInp1.setEnabled(b);
        secbpInp2.setEnabled(b);
        secbpInp3.setEnabled(b);
        secbpAtt1.setEnabled(b);
        secbpAtt2.setEnabled(b);
        secbpAtt3.setEnabled(b);
        secbpIed1.setEnabled(b);
        secbpIed2.setEnabled(b);
        secbpIed3.setEnabled(b);
        secbpBoss1.setEnabled(b);
        secbpBoss2.setEnabled(b);
        secbpBoss3.setEnabled(b);
    }

    public void setWeaponEnabled(boolean b) {
        wepInp1.setEnabled(b);
        wepInp2.setEnabled(b);
        wepInp3.setEnabled(b);
        wepAtt1.setEnabled(b);
        wepAtt2.setEnabled(b);
        wepAtt3.setEnabled(b);
        wepIed1.setEnabled(b);
        wepIed2.setEnabled(b);
        wepIed3.setEnabled(b);
        wepBoss1.setEnabled(b);
        wepBoss2.setEnabled(b);
        wepBoss3.setEnabled(b);
    }

    public void setWeaponBPEnabled(boolean b) {
        wepbpInp1.setEnabled(b);
        wepbpInp2.setEnabled(b);
        wepbpInp3.setEnabled(b);
        wepbpAtt1.setEnabled(b);
        wepbpAtt2.setEnabled(b);
        wepbpAtt3.setEnabled(b);
        wepbpIed1.setEnabled(b);
        wepbpIed2.setEnabled(b);
        wepbpIed3.setEnabled(b);
        wepbpBoss1.setEnabled(b);
        wepbpBoss2.setEnabled(b);
        wepbpBoss3.setEnabled(b);
    }

    public void setSoulEnabled(boolean b) {
        wepInp4.setEnabled(b);
        wepAtt4.setEnabled(b);
        wepBoss4.setEnabled(b);
        wepIed4.setEnabled(b);
    }

    private void saveBase() {
        this.att_baseS = this.att_base;
        this.boss_baseS = this.boss_base;
        this.dmg_baseS = this.dmg_base;
        this.ied_baseS = this.ied_base;
        this.crit_baseS = this.crit_base;
    }

    private void outputPotVector(PotVector potVector) {
        if (!wepSelect.isSelected()) {
            ItemPrinter.printItem(wepInp1, wepInp2, wepInp3, potVector.getWep());
        }
        if (!secSelect.isSelected()) {
            ItemPrinter.printItem(secInp1, secInp2, secInp3, potVector.getSec());
        }
        if (!embSelect.isSelected()) {
            ItemPrinter.printItem(embInp1, embInp2, embInp3, potVector.getEmb());
        }
        if (!wepbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(wepbpInp1, wepbpInp2, wepbpInp3, potVector.getWepb());
        }
        if (!secbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(secbpInp1, secbpInp2, secbpInp3, potVector.getSecb());
        }
        if (!embbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(embbpInp1, embbpInp2, embbpInp3, potVector.getEmbb());
        }
        if (!soulSelect.isSelected()) {
            ItemPrinter.printSoul(wepInp4, potVector.getSoul());
        }
        double calcBase = ((1.3 + this.crit_baseS) * (1 + this.att_baseS) * (1 + this.boss_baseS + this.dmg_baseS) * (1 - (this.pdr * (1 - this.ied_baseS))));
        ItemPrinter.printLegionHypersAndFD(fd_Legion, fd_LegionBP, calcBase, this.time, potVector);
    }

    private PotType buttonSelectAndDisable(JToggleButton selector, JToggleButton disabler1, JToggleButton disabler2, PotType potType) {
        if (selector.isSelected()) {
            if (disabler1 != null) {
                disabler1.setSelected(false);
            }
            if (disabler2 != null) {
                disabler2.setSelected(false);
            }
            return potType;
        } else {
            return PotType.DEFAULT;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OptimizationPieces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OptimizationPieces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OptimizationPieces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OptimizationPieces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OptimizationPieces().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField att;
    private javax.swing.JTextField boss;
    private javax.swing.JToggleButton bp;
    private javax.swing.JToggleButton calculate;
    private javax.swing.JToggleButton clearInp;
    private javax.swing.JTextField critDmgInp;
    private javax.swing.JTextField dmg;
    private javax.swing.JToggleButton embAtt1;
    private javax.swing.JToggleButton embAtt2;
    private javax.swing.JToggleButton embAtt3;
    private javax.swing.JToggleButton embIed1;
    private javax.swing.JToggleButton embIed2;
    private javax.swing.JToggleButton embIed3;
    private javax.swing.JTextField embInp1;
    private javax.swing.JTextField embInp2;
    private javax.swing.JTextField embInp3;
    private javax.swing.JToggleButton embSelect;
    private javax.swing.JToggleButton embbpAtt1;
    private javax.swing.JToggleButton embbpAtt2;
    private javax.swing.JToggleButton embbpAtt3;
    private javax.swing.JToggleButton embbpIed1;
    private javax.swing.JToggleButton embbpIed2;
    private javax.swing.JToggleButton embbpIed3;
    private javax.swing.JTextField embbpInp1;
    private javax.swing.JTextField embbpInp2;
    private javax.swing.JTextField embbpInp3;
    private javax.swing.JToggleButton embbpSelect;
    private javax.swing.JTextArea fd_Legion;
    private javax.swing.JTextArea fd_LegionBP;
    private javax.swing.JTextField hyperStatsInp;
    private javax.swing.JTextField ied;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton kannaClass;
    private javax.swing.JTextField monDef;
    private javax.swing.JToggleButton no_3l;
    private javax.swing.JToggleButton no_3lbp;
    private javax.swing.JTextField numOptions;
    private javax.swing.JToggleButton secAtt1;
    private javax.swing.JToggleButton secAtt2;
    private javax.swing.JToggleButton secAtt3;
    private javax.swing.JToggleButton secBoss1;
    private javax.swing.JToggleButton secBoss2;
    private javax.swing.JToggleButton secBoss3;
    private javax.swing.JToggleButton secIed1;
    private javax.swing.JToggleButton secIed2;
    private javax.swing.JToggleButton secIed3;
    private javax.swing.JTextField secInp1;
    private javax.swing.JTextField secInp2;
    private javax.swing.JTextField secInp3;
    private javax.swing.JToggleButton secSelect;
    private javax.swing.JToggleButton secbpAtt1;
    private javax.swing.JToggleButton secbpAtt2;
    private javax.swing.JToggleButton secbpAtt3;
    private javax.swing.JToggleButton secbpBoss1;
    private javax.swing.JToggleButton secbpBoss2;
    private javax.swing.JToggleButton secbpBoss3;
    private javax.swing.JToggleButton secbpIed1;
    private javax.swing.JToggleButton secbpIed2;
    private javax.swing.JToggleButton secbpIed3;
    private javax.swing.JTextField secbpInp1;
    private javax.swing.JTextField secbpInp2;
    private javax.swing.JTextField secbpInp3;
    private javax.swing.JToggleButton secbpSelect;
    private javax.swing.JToggleButton seclvl;
    private javax.swing.JToggleButton soulSelect;
    private javax.swing.JTextField union;
    private javax.swing.JToggleButton wepAtt1;
    private javax.swing.JToggleButton wepAtt2;
    private javax.swing.JToggleButton wepAtt3;
    private javax.swing.JToggleButton wepAtt4;
    private javax.swing.JToggleButton wepBoss1;
    private javax.swing.JToggleButton wepBoss2;
    private javax.swing.JToggleButton wepBoss3;
    private javax.swing.JToggleButton wepBoss4;
    private javax.swing.JToggleButton wepIed1;
    private javax.swing.JToggleButton wepIed2;
    private javax.swing.JToggleButton wepIed3;
    private javax.swing.JToggleButton wepIed4;
    private javax.swing.JTextField wepInp1;
    private javax.swing.JTextField wepInp2;
    private javax.swing.JTextField wepInp3;
    private javax.swing.JTextField wepInp4;
    private javax.swing.JToggleButton wepSelect;
    private javax.swing.JToggleButton wepbpAtt1;
    private javax.swing.JToggleButton wepbpAtt2;
    private javax.swing.JToggleButton wepbpAtt3;
    private javax.swing.JToggleButton wepbpBoss1;
    private javax.swing.JToggleButton wepbpBoss2;
    private javax.swing.JToggleButton wepbpBoss3;
    private javax.swing.JToggleButton wepbpIed1;
    private javax.swing.JToggleButton wepbpIed2;
    private javax.swing.JToggleButton wepbpIed3;
    private javax.swing.JTextField wepbpInp1;
    private javax.swing.JTextField wepbpInp2;
    private javax.swing.JTextField wepbpInp3;
    private javax.swing.JToggleButton wepbpSelect;
    private javax.swing.JToggleButton weplvl;
    private javax.swing.JComboBox<String> wseOptions;
    private javax.swing.JToggleButton zeroClass;
    // End of variables declaration//GEN-END:variables
}
