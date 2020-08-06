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
    private Server server = Server.REBOOT;
    //Variables for tracking execution time for the worker
    private double time;
    private long startTime;
    private long endTime;
    //Variables for trakcing the final output
    private PotVector selectedPotVector;
    private ArrayList<PotVector> generatedWSE;
    //Variables related to the worker and listening to its progress and state
    private WSEWorker worker;
    private PropertyChangeListener listener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals("state")){
                switch (worker.getState()) {
                    case DONE:
                        calculate.setEnabled(true);
                        if (!worker.isCancelled()){
                            try {
                                generatedWSE = worker.get();
                                } catch (InterruptedException ex) {
                                    System.out.println(ex.toString());
                                } catch (ExecutionException ex) {
                                    System.out.println(ex.toString());
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
                        }
                        else{
                            fd_LegionBP.setText("Optimization cancelled");
                        }
                        break;
                }
            }
            if (event.getPropertyName().equals("progress")) {
                if (!worker.isCancelled()){
                    fd_LegionBP.setText(String.format("Optimizing... %d%% completed", worker.getProgress()));
                }
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
        java.awt.GridBagConstraints gridBagConstraints;

        inputOutputPane = new javax.swing.JTabbedPane();
        inputPanel = new javax.swing.JPanel();
        bp = new javax.swing.JToggleButton();
        numOptions = new javax.swing.JTextField();
        optionsLabel = new javax.swing.JLabel();
        wseOptions = new javax.swing.JComboBox<>();
        calculate = new javax.swing.JToggleButton();
        no_3lbp = new javax.swing.JToggleButton();
        no_3l = new javax.swing.JToggleButton();
        clearInp = new javax.swing.JToggleButton();
        inputSeperator = new javax.swing.JSeparator();
        wepSelect = new javax.swing.JToggleButton();
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
        secSelect = new javax.swing.JToggleButton();
        secInp1 = new javax.swing.JTextField();
        secAtt1 = new javax.swing.JToggleButton();
        secBoss1 = new javax.swing.JToggleButton();
        secIed1 = new javax.swing.JToggleButton();
        secInp2 = new javax.swing.JTextField();
        secAtt2 = new javax.swing.JToggleButton();
        secBoss2 = new javax.swing.JToggleButton();
        secIed2 = new javax.swing.JToggleButton();
        secInp3 = new javax.swing.JTextField();
        secAtt3 = new javax.swing.JToggleButton();
        secBoss3 = new javax.swing.JToggleButton();
        secIed3 = new javax.swing.JToggleButton();
        embSelect = new javax.swing.JToggleButton();
        embInp1 = new javax.swing.JTextField();
        embAtt1 = new javax.swing.JToggleButton();
        embIed1 = new javax.swing.JToggleButton();
        embInp2 = new javax.swing.JTextField();
        embAtt2 = new javax.swing.JToggleButton();
        embIed2 = new javax.swing.JToggleButton();
        embInp3 = new javax.swing.JTextField();
        embAtt3 = new javax.swing.JToggleButton();
        embIed3 = new javax.swing.JToggleButton();
        soulSelect = new javax.swing.JToggleButton();
        wepInp4 = new javax.swing.JTextField();
        wepAtt4 = new javax.swing.JToggleButton();
        wepBoss4 = new javax.swing.JToggleButton();
        wepIed4 = new javax.swing.JToggleButton();
        bonusSeperator = new javax.swing.JSeparator();
        wepbpSelect = new javax.swing.JToggleButton();
        wepbpInp1 = new javax.swing.JTextField();
        wepbpAtt1 = new javax.swing.JToggleButton();
        wepbpBoss1 = new javax.swing.JToggleButton();
        wepbpIed1 = new javax.swing.JToggleButton();
        wepbpInp2 = new javax.swing.JTextField();
        wepbpAtt2 = new javax.swing.JToggleButton();
        wepbpBoss2 = new javax.swing.JToggleButton();
        wepbpIed2 = new javax.swing.JToggleButton();
        wepbpInp3 = new javax.swing.JTextField();
        wepbpAtt3 = new javax.swing.JToggleButton();
        wepbpBoss3 = new javax.swing.JToggleButton();
        wepbpIed3 = new javax.swing.JToggleButton();
        secbpSelect = new javax.swing.JToggleButton();
        secbpInp1 = new javax.swing.JTextField();
        secbpAtt1 = new javax.swing.JToggleButton();
        secbpBoss1 = new javax.swing.JToggleButton();
        secbpIed1 = new javax.swing.JToggleButton();
        secbpInp2 = new javax.swing.JTextField();
        secbpAtt2 = new javax.swing.JToggleButton();
        secbpBoss2 = new javax.swing.JToggleButton();
        secbpIed2 = new javax.swing.JToggleButton();
        secbpInp3 = new javax.swing.JTextField();
        secbpAtt3 = new javax.swing.JToggleButton();
        secbpBoss3 = new javax.swing.JToggleButton();
        secbpIed3 = new javax.swing.JToggleButton();
        embbpSelect = new javax.swing.JToggleButton();
        embbpInp1 = new javax.swing.JTextField();
        embbpAtt1 = new javax.swing.JToggleButton();
        embbpIed1 = new javax.swing.JToggleButton();
        embbpInp2 = new javax.swing.JTextField();
        embbpAtt2 = new javax.swing.JToggleButton();
        embbpIed2 = new javax.swing.JToggleButton();
        embbpInp3 = new javax.swing.JTextField();
        embbpAtt3 = new javax.swing.JToggleButton();
        embbpIed3 = new javax.swing.JToggleButton();
        outputSeperator = new javax.swing.JSeparator();
        fd_LegionBP = new javax.swing.JTextArea();
        baseStatsPanel = new javax.swing.JPanel();
        dmg = new javax.swing.JTextField();
        dmgLabel = new javax.swing.JLabel();
        boss = new javax.swing.JTextField();
        bossLabel = new javax.swing.JLabel();
        att = new javax.swing.JTextField();
        attLabel = new javax.swing.JLabel();
        critDmgInp = new javax.swing.JTextField();
        ied = new javax.swing.JTextField();
        critdmgLabel = new javax.swing.JLabel();
        iedLabel = new javax.swing.JLabel();
        weplvl = new javax.swing.JToggleButton();
        seclvl = new javax.swing.JToggleButton();
        kannaClass = new javax.swing.JToggleButton();
        zeroClass = new javax.swing.JToggleButton();
        union = new javax.swing.JTextField();
        legionLabel = new javax.swing.JLabel();
        hyperStatsInp = new javax.swing.JTextField();
        hyperLabel = new javax.swing.JLabel();
        monDef = new javax.swing.JTextField();
        pdrLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WSE Optimization");
        setMaximumSize(new java.awt.Dimension(640, 780));
        setMinimumSize(new java.awt.Dimension(640, 780));
        setPreferredSize(new java.awt.Dimension(640, 780));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        inputPanel.setLayout(new java.awt.GridBagLayout());

        bp.setText("Bonus Pot");
        bp.setMaximumSize(new java.awt.Dimension(103, 23));
        bp.setMinimumSize(new java.awt.Dimension(103, 23));
        bp.setPreferredSize(new java.awt.Dimension(103, 23));
        bp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(bp, gridBagConstraints);

        numOptions.setText("10");
        numOptions.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        numOptions.setMinimumSize(new java.awt.Dimension(6, 23));
        numOptions.setPreferredSize(new java.awt.Dimension(24, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(numOptions, gridBagConstraints);

        optionsLabel.setText("Additional Options");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(optionsLabel, gridBagConstraints);

        wseOptions.setModel(new DefaultComboBoxModel());
        wseOptions.setEnabled(false);
        wseOptions.setMaximumSize(new java.awt.Dimension(28, 20));
        wseOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wseOptionsItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wseOptions, gridBagConstraints);

        calculate.setText("Calculate");
        calculate.setMaximumSize(new java.awt.Dimension(207, 23));
        calculate.setMinimumSize(new java.awt.Dimension(207, 23));
        calculate.setPreferredSize(new java.awt.Dimension(207, 23));
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(calculate, gridBagConstraints);

        no_3lbp.setText("No 3L Att BP");
        no_3lbp.setEnabled(false);
        no_3lbp.setMaximumSize(new java.awt.Dimension(103, 23));
        no_3lbp.setMinimumSize(new java.awt.Dimension(103, 23));
        no_3lbp.setPreferredSize(new java.awt.Dimension(103, 23));
        no_3lbp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lbpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(no_3lbp, gridBagConstraints);

        no_3l.setText("No 3L Att");
        no_3l.setMaximumSize(new java.awt.Dimension(103, 23));
        no_3l.setMinimumSize(new java.awt.Dimension(103, 23));
        no_3l.setPreferredSize(new java.awt.Dimension(103, 23));
        no_3l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(no_3l, gridBagConstraints);

        clearInp.setText("Reset/Cancel");
        clearInp.setMaximumSize(new java.awt.Dimension(207, 23));
        clearInp.setMinimumSize(new java.awt.Dimension(207, 23));
        clearInp.setPreferredSize(new java.awt.Dimension(207, 23));
        clearInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearInpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(clearInp, gridBagConstraints);

        inputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(inputSeperator, gridBagConstraints);

        wepSelect.setText("Weapon");
        wepSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        wepSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        wepSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        wepSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepSelect, gridBagConstraints);

        wepInp1.setEnabled(false);
        wepInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        wepInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        wepInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepInp1, gridBagConstraints);

        wepAtt1.setText("ATT");
        wepAtt1.setEnabled(false);
        wepAtt1.setMaximumSize(new java.awt.Dimension(64, 23));
        wepAtt1.setMinimumSize(new java.awt.Dimension(64, 23));
        wepAtt1.setPreferredSize(new java.awt.Dimension(64, 23));
        wepAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepAtt1, gridBagConstraints);

        wepBoss1.setText("BOSS");
        wepBoss1.setEnabled(false);
        wepBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepBoss1, gridBagConstraints);

        wepIed1.setText("IED");
        wepIed1.setEnabled(false);
        wepIed1.setMaximumSize(new java.awt.Dimension(64, 23));
        wepIed1.setMinimumSize(new java.awt.Dimension(64, 23));
        wepIed1.setPreferredSize(new java.awt.Dimension(64, 23));
        wepIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepIed1, gridBagConstraints);

        wepInp2.setEnabled(false);
        wepInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        wepInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        wepInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepInp2, gridBagConstraints);

        wepAtt2.setText("ATT");
        wepAtt2.setEnabled(false);
        wepAtt2.setMaximumSize(new java.awt.Dimension(64, 23));
        wepAtt2.setMinimumSize(new java.awt.Dimension(64, 23));
        wepAtt2.setPreferredSize(new java.awt.Dimension(64, 23));
        wepAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepAtt2, gridBagConstraints);

        wepBoss2.setText("BOSS");
        wepBoss2.setEnabled(false);
        wepBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepBoss2, gridBagConstraints);

        wepIed2.setText("IED");
        wepIed2.setEnabled(false);
        wepIed2.setMaximumSize(new java.awt.Dimension(64, 23));
        wepIed2.setMinimumSize(new java.awt.Dimension(64, 23));
        wepIed2.setPreferredSize(new java.awt.Dimension(64, 23));
        wepIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepIed2, gridBagConstraints);

        wepInp3.setEnabled(false);
        wepInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        wepInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        wepInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepInp3, gridBagConstraints);

        wepAtt3.setText("ATT");
        wepAtt3.setEnabled(false);
        wepAtt3.setMaximumSize(new java.awt.Dimension(64, 23));
        wepAtt3.setMinimumSize(new java.awt.Dimension(64, 23));
        wepAtt3.setPreferredSize(new java.awt.Dimension(64, 23));
        wepAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepAtt3, gridBagConstraints);

        wepBoss3.setText("BOSS");
        wepBoss3.setEnabled(false);
        wepBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepBoss3, gridBagConstraints);

        wepIed3.setText("IED");
        wepIed3.setEnabled(false);
        wepIed3.setMaximumSize(new java.awt.Dimension(64, 23));
        wepIed3.setMinimumSize(new java.awt.Dimension(64, 23));
        wepIed3.setPreferredSize(new java.awt.Dimension(64, 23));
        wepIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepIed3, gridBagConstraints);

        secSelect.setText("Secondary");
        secSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        secSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        secSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        secSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secSelect, gridBagConstraints);

        secInp1.setEnabled(false);
        secInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        secInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        secInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secInp1, gridBagConstraints);

        secAtt1.setText("ATT");
        secAtt1.setEnabled(false);
        secAtt1.setMaximumSize(new java.awt.Dimension(64, 23));
        secAtt1.setMinimumSize(new java.awt.Dimension(64, 23));
        secAtt1.setPreferredSize(new java.awt.Dimension(64, 23));
        secAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secAtt1, gridBagConstraints);

        secBoss1.setText("BOSS");
        secBoss1.setEnabled(false);
        secBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secBoss1, gridBagConstraints);

        secIed1.setText("IED");
        secIed1.setEnabled(false);
        secIed1.setMaximumSize(new java.awt.Dimension(64, 23));
        secIed1.setMinimumSize(new java.awt.Dimension(64, 23));
        secIed1.setPreferredSize(new java.awt.Dimension(64, 23));
        secIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secIed1, gridBagConstraints);

        secInp2.setEnabled(false);
        secInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        secInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        secInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secInp2, gridBagConstraints);

        secAtt2.setText("ATT");
        secAtt2.setEnabled(false);
        secAtt2.setMaximumSize(new java.awt.Dimension(64, 23));
        secAtt2.setMinimumSize(new java.awt.Dimension(64, 23));
        secAtt2.setPreferredSize(new java.awt.Dimension(64, 23));
        secAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secAtt2, gridBagConstraints);

        secBoss2.setText("BOSS");
        secBoss2.setEnabled(false);
        secBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secBoss2, gridBagConstraints);

        secIed2.setText("IED");
        secIed2.setEnabled(false);
        secIed2.setMaximumSize(new java.awt.Dimension(64, 23));
        secIed2.setMinimumSize(new java.awt.Dimension(64, 23));
        secIed2.setPreferredSize(new java.awt.Dimension(64, 23));
        secIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secIed2, gridBagConstraints);

        secInp3.setEnabled(false);
        secInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        secInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        secInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secInp3, gridBagConstraints);

        secAtt3.setText("ATT");
        secAtt3.setEnabled(false);
        secAtt3.setMaximumSize(new java.awt.Dimension(64, 23));
        secAtt3.setMinimumSize(new java.awt.Dimension(64, 23));
        secAtt3.setPreferredSize(new java.awt.Dimension(64, 23));
        secAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secAtt3, gridBagConstraints);

        secBoss3.setText("BOSS");
        secBoss3.setEnabled(false);
        secBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secBoss3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secBoss3, gridBagConstraints);

        secIed3.setText("IED");
        secIed3.setEnabled(false);
        secIed3.setMaximumSize(new java.awt.Dimension(64, 23));
        secIed3.setMinimumSize(new java.awt.Dimension(64, 23));
        secIed3.setPreferredSize(new java.awt.Dimension(64, 23));
        secIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secIed3, gridBagConstraints);

        embSelect.setText("Emblem");
        embSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        embSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        embSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        embSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 2.0;
        inputPanel.add(embSelect, gridBagConstraints);

        embInp1.setEnabled(false);
        embInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        embInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        embInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embInp1, gridBagConstraints);

        embAtt1.setText("ATT");
        embAtt1.setEnabled(false);
        embAtt1.setMaximumSize(new java.awt.Dimension(101, 23));
        embAtt1.setMinimumSize(new java.awt.Dimension(101, 23));
        embAtt1.setPreferredSize(new java.awt.Dimension(101, 23));
        embAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embAtt1, gridBagConstraints);

        embIed1.setText("IED");
        embIed1.setEnabled(false);
        embIed1.setMaximumSize(new java.awt.Dimension(101, 23));
        embIed1.setMinimumSize(new java.awt.Dimension(101, 23));
        embIed1.setPreferredSize(new java.awt.Dimension(101, 23));
        embIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embIed1, gridBagConstraints);

        embInp2.setEnabled(false);
        embInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        embInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        embInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embInp2, gridBagConstraints);

        embAtt2.setText("ATT");
        embAtt2.setEnabled(false);
        embAtt2.setMaximumSize(new java.awt.Dimension(101, 23));
        embAtt2.setMinimumSize(new java.awt.Dimension(101, 23));
        embAtt2.setPreferredSize(new java.awt.Dimension(101, 23));
        embAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embAtt2, gridBagConstraints);

        embIed2.setText("IED");
        embIed2.setEnabled(false);
        embIed2.setMaximumSize(new java.awt.Dimension(101, 23));
        embIed2.setMinimumSize(new java.awt.Dimension(101, 23));
        embIed2.setPreferredSize(new java.awt.Dimension(101, 23));
        embIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embIed2, gridBagConstraints);

        embInp3.setEnabled(false);
        embInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        embInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        embInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embInp3, gridBagConstraints);

        embAtt3.setText("Att");
        embAtt3.setEnabled(false);
        embAtt3.setMaximumSize(new java.awt.Dimension(101, 23));
        embAtt3.setMinimumSize(new java.awt.Dimension(101, 23));
        embAtt3.setPreferredSize(new java.awt.Dimension(101, 23));
        embAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embAtt3, gridBagConstraints);

        embIed3.setText("IED");
        embIed3.setEnabled(false);
        embIed3.setMaximumSize(new java.awt.Dimension(101, 23));
        embIed3.setMinimumSize(new java.awt.Dimension(101, 23));
        embIed3.setPreferredSize(new java.awt.Dimension(101, 23));
        embIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embIed3, gridBagConstraints);

        soulSelect.setText("Soul");
        soulSelect.setMaximumSize(new java.awt.Dimension(621, 23));
        soulSelect.setMinimumSize(new java.awt.Dimension(621, 23));
        soulSelect.setPreferredSize(new java.awt.Dimension(621, 23));
        soulSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soulSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 10;
        inputPanel.add(soulSelect, gridBagConstraints);

        wepInp4.setEnabled(false);
        wepInp4.setMinimumSize(new java.awt.Dimension(621, 23));
        wepInp4.setPreferredSize(new java.awt.Dimension(621, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 10;
        inputPanel.add(wepInp4, gridBagConstraints);

        wepAtt4.setText("Att");
        wepAtt4.setEnabled(false);
        wepAtt4.setMaximumSize(new java.awt.Dimension(207, 23));
        wepAtt4.setMinimumSize(new java.awt.Dimension(207, 23));
        wepAtt4.setPreferredSize(new java.awt.Dimension(207, 23));
        wepAtt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepAtt4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepAtt4, gridBagConstraints);

        wepBoss4.setText("BOSS");
        wepBoss4.setEnabled(false);
        wepBoss4.setMaximumSize(new java.awt.Dimension(207, 23));
        wepBoss4.setMinimumSize(new java.awt.Dimension(207, 23));
        wepBoss4.setPreferredSize(new java.awt.Dimension(207, 23));
        wepBoss4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepBoss4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepBoss4, gridBagConstraints);

        wepIed4.setText("IED");
        wepIed4.setEnabled(false);
        wepIed4.setMaximumSize(new java.awt.Dimension(207, 23));
        wepIed4.setMinimumSize(new java.awt.Dimension(207, 23));
        wepIed4.setPreferredSize(new java.awt.Dimension(207, 23));
        wepIed4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepIed4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepIed4, gridBagConstraints);

        bonusSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(bonusSeperator, gridBagConstraints);

        wepbpSelect.setText("Weapon Bonus Potential");
        wepbpSelect.setEnabled(false);
        wepbpSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        wepbpSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        wepbpSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        wepbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpSelect, gridBagConstraints);

        wepbpInp1.setEnabled(false);
        wepbpInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        wepbpInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        wepbpInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpInp1, gridBagConstraints);

        wepbpAtt1.setText("ATT");
        wepbpAtt1.setEnabled(false);
        wepbpAtt1.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpAtt1.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpAtt1.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpAtt1, gridBagConstraints);

        wepbpBoss1.setText("BOSS");
        wepbpBoss1.setEnabled(false);
        wepbpBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpBoss1, gridBagConstraints);

        wepbpIed1.setText("IED");
        wepbpIed1.setEnabled(false);
        wepbpIed1.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpIed1.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpIed1.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbpIed1, gridBagConstraints);

        wepbpInp2.setEnabled(false);
        wepbpInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        wepbpInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        wepbpInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpInp2, gridBagConstraints);

        wepbpAtt2.setText("ATT");
        wepbpAtt2.setEnabled(false);
        wepbpAtt2.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpAtt2.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpAtt2.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpAtt2, gridBagConstraints);

        wepbpBoss2.setText("BOSS");
        wepbpBoss2.setEnabled(false);
        wepbpBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpBoss2, gridBagConstraints);

        wepbpIed2.setText("IED");
        wepbpIed2.setEnabled(false);
        wepbpIed2.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpIed2.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpIed2.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbpIed2, gridBagConstraints);

        wepbpInp3.setEnabled(false);
        wepbpInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        wepbpInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        wepbpInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpInp3, gridBagConstraints);

        wepbpAtt3.setText("ATT");
        wepbpAtt3.setEnabled(false);
        wepbpAtt3.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpAtt3.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpAtt3.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpAtt3, gridBagConstraints);

        wepbpBoss3.setText("BOSS");
        wepbpBoss3.setEnabled(false);
        wepbpBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpBoss3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(wepbpBoss3, gridBagConstraints);

        wepbpIed3.setText("IED");
        wepbpIed3.setEnabled(false);
        wepbpIed3.setMaximumSize(new java.awt.Dimension(64, 23));
        wepbpIed3.setMinimumSize(new java.awt.Dimension(64, 23));
        wepbpIed3.setPreferredSize(new java.awt.Dimension(64, 23));
        wepbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wepbpIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbpIed3, gridBagConstraints);

        secbpSelect.setText("Secondary Bonus Potential");
        secbpSelect.setEnabled(false);
        secbpSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        secbpSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        secbpSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        secbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpSelect, gridBagConstraints);

        secbpInp1.setEnabled(false);
        secbpInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        secbpInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        secbpInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpInp1, gridBagConstraints);

        secbpAtt1.setText("ATT");
        secbpAtt1.setEnabled(false);
        secbpAtt1.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpAtt1.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpAtt1.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpAtt1, gridBagConstraints);

        secbpBoss1.setText("BOSS");
        secbpBoss1.setEnabled(false);
        secbpBoss1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpBoss1, gridBagConstraints);

        secbpIed1.setText("IED");
        secbpIed1.setEnabled(false);
        secbpIed1.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpIed1.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpIed1.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbpIed1, gridBagConstraints);

        secbpInp2.setEnabled(false);
        secbpInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        secbpInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        secbpInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpInp2, gridBagConstraints);

        secbpAtt2.setText("ATT");
        secbpAtt2.setEnabled(false);
        secbpAtt2.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpAtt2.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpAtt2.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpAtt2, gridBagConstraints);

        secbpBoss2.setText("BOSS");
        secbpBoss2.setEnabled(false);
        secbpBoss2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpBoss2, gridBagConstraints);

        secbpIed2.setText("IED");
        secbpIed2.setEnabled(false);
        secbpIed2.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpIed2.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpIed2.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbpIed2, gridBagConstraints);

        secbpInp3.setEnabled(false);
        secbpInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        secbpInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        secbpInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpInp3, gridBagConstraints);

        secbpAtt3.setText("ATT");
        secbpAtt3.setEnabled(false);
        secbpAtt3.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpAtt3.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpAtt3.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpAtt3, gridBagConstraints);

        secbpBoss3.setText("BOSS");
        secbpBoss3.setEnabled(false);
        secbpBoss3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpBoss3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(secbpBoss3, gridBagConstraints);

        secbpIed3.setText("IED");
        secbpIed3.setEnabled(false);
        secbpIed3.setMaximumSize(new java.awt.Dimension(64, 23));
        secbpIed3.setMinimumSize(new java.awt.Dimension(64, 23));
        secbpIed3.setPreferredSize(new java.awt.Dimension(64, 23));
        secbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secbpIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbpIed3, gridBagConstraints);

        embbpSelect.setText("Emblem Bonus Potential");
        embbpSelect.setEnabled(false);
        embbpSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        embbpSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        embbpSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        embbpSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embbpSelect, gridBagConstraints);

        embbpInp1.setEnabled(false);
        embbpInp1.setMaximumSize(new java.awt.Dimension(207, 23));
        embbpInp1.setMinimumSize(new java.awt.Dimension(207, 23));
        embbpInp1.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embbpInp1, gridBagConstraints);

        embbpAtt1.setText("ATT");
        embbpAtt1.setEnabled(false);
        embbpAtt1.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpAtt1.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpAtt1.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpAtt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpAtt1, gridBagConstraints);

        embbpIed1.setText("IED");
        embbpIed1.setEnabled(false);
        embbpIed1.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpIed1.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpIed1.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpIed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbpIed1, gridBagConstraints);

        embbpInp2.setEnabled(false);
        embbpInp2.setMaximumSize(new java.awt.Dimension(207, 23));
        embbpInp2.setMinimumSize(new java.awt.Dimension(207, 23));
        embbpInp2.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(embbpInp2, gridBagConstraints);

        embbpAtt2.setText("ATT");
        embbpAtt2.setEnabled(false);
        embbpAtt2.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpAtt2.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpAtt2.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpAtt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpAtt2, gridBagConstraints);

        embbpIed2.setText("IED");
        embbpIed2.setEnabled(false);
        embbpIed2.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpIed2.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpIed2.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpIed2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbpIed2, gridBagConstraints);

        embbpInp3.setEnabled(false);
        embbpInp3.setMaximumSize(new java.awt.Dimension(207, 23));
        embbpInp3.setMinimumSize(new java.awt.Dimension(207, 23));
        embbpInp3.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        inputPanel.add(embbpInp3, gridBagConstraints);

        embbpAtt3.setText("ATT");
        embbpAtt3.setEnabled(false);
        embbpAtt3.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpAtt3.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpAtt3.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpAtt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpAtt3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpAtt3, gridBagConstraints);

        embbpIed3.setText("IED");
        embbpIed3.setEnabled(false);
        embbpIed3.setMaximumSize(new java.awt.Dimension(101, 23));
        embbpIed3.setMinimumSize(new java.awt.Dimension(101, 23));
        embbpIed3.setPreferredSize(new java.awt.Dimension(101, 23));
        embbpIed3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embbpIed3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbpIed3, gridBagConstraints);

        outputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(outputSeperator, gridBagConstraints);

        fd_LegionBP.setColumns(20);
        fd_LegionBP.setRows(5);
        fd_LegionBP.setEnabled(false);
        fd_LegionBP.setMinimumSize(new java.awt.Dimension(621, 150));
        fd_LegionBP.setPreferredSize(new java.awt.Dimension(621, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(fd_LegionBP, gridBagConstraints);

        inputOutputPane.addTab("Main", inputPanel);

        baseStatsPanel.setLayout(new java.awt.GridBagLayout());

        dmg.setText("214");
        dmg.setMaximumSize(new java.awt.Dimension(60, 23));
        dmg.setMinimumSize(new java.awt.Dimension(60, 23));
        dmg.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        baseStatsPanel.add(dmg, gridBagConstraints);

        dmgLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dmgLabel.setText("DMG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(dmgLabel, gridBagConstraints);

        boss.setText("170");
        boss.setMaximumSize(new java.awt.Dimension(60, 23));
        boss.setMinimumSize(new java.awt.Dimension(60, 23));
        boss.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        baseStatsPanel.add(boss, gridBagConstraints);

        bossLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bossLabel.setText("BOSS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(bossLabel, gridBagConstraints);

        att.setText("42");
        att.setMaximumSize(new java.awt.Dimension(60, 23));
        att.setMinimumSize(new java.awt.Dimension(60, 23));
        att.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        baseStatsPanel.add(att, gridBagConstraints);

        attLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        attLabel.setText("ATT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(attLabel, gridBagConstraints);

        critDmgInp.setText("90");
        critDmgInp.setMaximumSize(new java.awt.Dimension(60, 23));
        critDmgInp.setMinimumSize(new java.awt.Dimension(60, 23));
        critDmgInp.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        baseStatsPanel.add(critDmgInp, gridBagConstraints);

        ied.setText("90.07");
        ied.setMaximumSize(new java.awt.Dimension(60, 23));
        ied.setMinimumSize(new java.awt.Dimension(60, 23));
        ied.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        baseStatsPanel.add(ied, gridBagConstraints);

        critdmgLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        critdmgLabel.setLabelFor(critDmgInp);
        critdmgLabel.setText("CRIT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(critdmgLabel, gridBagConstraints);

        iedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        iedLabel.setText("IED");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(iedLabel, gridBagConstraints);

        weplvl.setText("lvl 160+ Wep");
        weplvl.setMaximumSize(new java.awt.Dimension(103, 23));
        weplvl.setMinimumSize(new java.awt.Dimension(103, 23));
        weplvl.setPreferredSize(new java.awt.Dimension(103, 23));
        weplvl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weplvlActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(weplvl, gridBagConstraints);

        seclvl.setText("lvl 160+ Sec");
        seclvl.setMaximumSize(new java.awt.Dimension(103, 23));
        seclvl.setMinimumSize(new java.awt.Dimension(103, 23));
        seclvl.setPreferredSize(new java.awt.Dimension(103, 23));
        seclvl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seclvlActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(seclvl, gridBagConstraints);

        kannaClass.setText("Kanna Class");
        kannaClass.setMaximumSize(new java.awt.Dimension(103, 23));
        kannaClass.setMinimumSize(new java.awt.Dimension(103, 23));
        kannaClass.setPreferredSize(new java.awt.Dimension(103, 23));
        kannaClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kannaClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(kannaClass, gridBagConstraints);

        zeroClass.setText("Zero Class");
        zeroClass.setMaximumSize(new java.awt.Dimension(103, 23));
        zeroClass.setMinimumSize(new java.awt.Dimension(103, 23));
        zeroClass.setPreferredSize(new java.awt.Dimension(103, 23));
        zeroClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroClassActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(zeroClass, gridBagConstraints);

        union.setText("120");
        union.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        union.setMinimumSize(new java.awt.Dimension(6, 23));
        union.setPreferredSize(new java.awt.Dimension(24, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(union, gridBagConstraints);

        legionLabel.setText("Legion Points for Boss, IED, Crit DMG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(legionLabel, gridBagConstraints);

        hyperStatsInp.setText("1266");
        hyperStatsInp.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        hyperStatsInp.setMinimumSize(new java.awt.Dimension(6, 23));
        hyperStatsInp.setPreferredSize(new java.awt.Dimension(24, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(hyperStatsInp, gridBagConstraints);

        hyperLabel.setLabelFor(hyperStatsInp);
        hyperLabel.setText("Hyper Points for Boss, DMG, IED, Crit DMG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(hyperLabel, gridBagConstraints);

        monDef.setText("300");
        monDef.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        monDef.setMinimumSize(new java.awt.Dimension(6, 23));
        monDef.setPreferredSize(new java.awt.Dimension(24, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(monDef, gridBagConstraints);

        pdrLabel.setText("Monster Defense");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(pdrLabel, gridBagConstraints);

        inputOutputPane.addTab("Base Stats", baseStatsPanel);

        getContentPane().add(inputOutputPane, new java.awt.GridBagConstraints());

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
            this.server = Server.NONREBOOT;
            wepbpSelect.setEnabled(true);
            if (classType != ClassType.ZERO) {
                secbpSelect.setEnabled(true);
            }
            embbpSelect.setEnabled(true);
            no_3lbp.setEnabled(true);
        } else {
            this.server = Server.REBOOT;
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
                fd_LegionBP.setText("Optimizing...");
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
                if (!this.bp.isSelected()){
                    clearInputs(false, true);
                }
                //Start time of the method
                this.startTime = System.nanoTime();
                
                //Set up generation spaces for each variable
                WSEHelpers.generateHyperStats(hyperPoints);
                WSEHelpers.generateLegion(legionVal);
                WSEHelpers.setupWeaponGenerationSpace(wepSelect.isSelected(), no_3lAtt, PotType.MAIN);
                WSEHelpers.setupSecondaryGenerationSpace(secSelect.isSelected(), no_3lAtt, classType, PotType.MAIN);
                WSEHelpers.setupEmblemGenerationSpace(embSelect.isSelected(), no_3lAtt, PotType.MAIN);
                WSEHelpers.setupWeaponGenerationSpace(wepbpSelect.isSelected(), no_3lbpAtt, PotType.BONUS);
                WSEHelpers.setupSecondaryGenerationSpace(secbpSelect.isSelected(), no_3lbpAtt, classType, PotType.BONUS);
                WSEHelpers.setupEmblemGenerationSpace(embbpSelect.isSelected(), no_3lbpAtt, PotType.BONUS);
                WSEHelpers.setupSoulsGenerationSpace(soulSelect.isSelected());
                
                
                worker = new WSEWorker(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.hyperPoints, this.legionVal, this.no_3lAtt, this.no_3lbpAtt, this.classType, this.wep_lvl, this.sec_lvl, this.embSelect.isSelected(), this.wepSelect.isSelected(), this.secSelect.isSelected(), this.embbpSelect.isSelected(), this.wepbpSelect.isSelected(), this.secbpSelect.isSelected(), this.soulSelect.isSelected(), numberOfOptions, this.server);
                worker.addPropertyChangeListener(listener);
                worker.execute();
            } catch (Exception e) {
                e.printStackTrace();
                fd_LegionBP.setText("ERROR OCCURED: REDO INPUTS");
                this.calculate.setSelected(false);
            }
        }
        calculate.setEnabled(false);
        calculate.setSelected(false);
    }//GEN-LAST:event_calculateActionPerformed

    private void clearInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearInpActionPerformed
        if (clearInp.isSelected() && (worker.isDone() || worker.isCancelled())) {
            clearInputs(true, true);
        }
        else if (!worker.isDone()){
            worker.cancel(true);
        }
        clearInp.setSelected(false);
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
    
    public void clearInputs(boolean main, boolean bonus){
        if (main){
            wepInp1.setText("");
            wepInp2.setText("");
            wepInp3.setText("");

            secInp1.setText("");
            secInp2.setText("");
            secInp3.setText("");

            embInp1.setText("");
            embInp2.setText("");
            embInp3.setText("");
            
            wepInp4.setText("");
        }
        if (bonus){
            wepbpInp1.setText("");
            wepbpInp2.setText("");
            wepbpInp3.setText("");
            
            secbpInp1.setText("");
            secbpInp2.setText("");
            secbpInp3.setText("");
            
            embbpInp1.setText("");
            embbpInp2.setText("");
            embbpInp3.setText("");
        }      
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
        ItemPrinter.printLegionHypersAndFD(fd_LegionBP, calcBase, this.time, potVector);
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
    private javax.swing.JLabel attLabel;
    private javax.swing.JPanel baseStatsPanel;
    private javax.swing.JSeparator bonusSeperator;
    private javax.swing.JTextField boss;
    private javax.swing.JLabel bossLabel;
    private javax.swing.JToggleButton bp;
    private javax.swing.JToggleButton calculate;
    private javax.swing.JToggleButton clearInp;
    private javax.swing.JTextField critDmgInp;
    private javax.swing.JLabel critdmgLabel;
    private javax.swing.JTextField dmg;
    private javax.swing.JLabel dmgLabel;
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
    private javax.swing.JTextArea fd_LegionBP;
    private javax.swing.JLabel hyperLabel;
    private javax.swing.JTextField hyperStatsInp;
    private javax.swing.JTextField ied;
    private javax.swing.JLabel iedLabel;
    private javax.swing.JTabbedPane inputOutputPane;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JSeparator inputSeperator;
    private javax.swing.JToggleButton kannaClass;
    private javax.swing.JLabel legionLabel;
    private javax.swing.JTextField monDef;
    private javax.swing.JToggleButton no_3l;
    private javax.swing.JToggleButton no_3lbp;
    private javax.swing.JTextField numOptions;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JSeparator outputSeperator;
    private javax.swing.JLabel pdrLabel;
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
