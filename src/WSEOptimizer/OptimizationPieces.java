/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import static WSEOptimizer.ComboBoxSupport.buildSelectComboBoxMap;
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
    private PotType wepInp1_comboSel = PotType.DEFAULT;
    private PotType wepInp2_comboSel = PotType.DEFAULT;
    private PotType wepInp3_comboSel = PotType.DEFAULT;
    private PotType wepInp5_comboSel = PotType.DEFAULT;
    private PotType secInp1_comboSel = PotType.DEFAULT;
    private PotType secInp2_comboSel = PotType.DEFAULT;
    private PotType secInp3_comboSel = PotType.DEFAULT;
    private PotType wepbpInp1_comboSel = PotType.DEFAULT;
    private PotType wepbpInp2_comboSel = PotType.DEFAULT;
    private PotType wepbpInp3_comboSel = PotType.DEFAULT;
    private PotType secbpInp1_comboSel = PotType.DEFAULT;
    private PotType secbpInp2_comboSel = PotType.DEFAULT;
    private PotType secbpInp3_comboSel = PotType.DEFAULT;
    private PotType embInp1_comboSel = PotType.DEFAULT;
    private PotType embInp2_comboSel = PotType.DEFAULT;
    private PotType embInp3_comboSel = PotType.DEFAULT;
    private PotType embbpInp1_comboSel = PotType.DEFAULT;
    private PotType embbpInp2_comboSel = PotType.DEFAULT;
    private PotType embbpInp3_comboSel = PotType.DEFAULT;
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
    Map<String, PotType> potSelectComboBoxMap = buildSelectComboBoxMap();
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
        wep1ComboBox = new javax.swing.JComboBox<>();
        wepInp2 = new javax.swing.JTextField();
        wep2ComboBox = new javax.swing.JComboBox<>();
        wepInp3 = new javax.swing.JTextField();
        wep3ComboBox = new javax.swing.JComboBox<>();
        secSelect = new javax.swing.JToggleButton();
        secInp1 = new javax.swing.JTextField();
        sec1ComboBox = new javax.swing.JComboBox<>();
        secInp2 = new javax.swing.JTextField();
        sec2ComboBox = new javax.swing.JComboBox<>();
        secInp3 = new javax.swing.JTextField();
        sec3ComboBox = new javax.swing.JComboBox<>();
        embSelect = new javax.swing.JToggleButton();
        embInp1 = new javax.swing.JTextField();
        emb1ComboBox = new javax.swing.JComboBox<>();
        embInp2 = new javax.swing.JTextField();
        emb2ComboBox = new javax.swing.JComboBox<>();
        embInp3 = new javax.swing.JTextField();
        emb3ComboBox = new javax.swing.JComboBox<>();
        soulSelect = new javax.swing.JToggleButton();
        wepInp4 = new javax.swing.JTextField();
        soulComboBox = new javax.swing.JComboBox<>();
        bonusSeperator = new javax.swing.JSeparator();
        wepbpSelect = new javax.swing.JToggleButton();
        wepbpInp1 = new javax.swing.JTextField();
        wepbp1ComboBox = new javax.swing.JComboBox<>();
        wepbpInp2 = new javax.swing.JTextField();
        wepbp2ComboBox = new javax.swing.JComboBox<>();
        wepbpInp3 = new javax.swing.JTextField();
        wepbp3ComboBox = new javax.swing.JComboBox<>();
        secbpSelect = new javax.swing.JToggleButton();
        secbpInp1 = new javax.swing.JTextField();
        secbp1ComboBox = new javax.swing.JComboBox<>();
        secbpInp2 = new javax.swing.JTextField();
        secbp2ComboBox = new javax.swing.JComboBox<>();
        secbpInp3 = new javax.swing.JTextField();
        secbp3ComboBox = new javax.swing.JComboBox<>();
        embbpSelect = new javax.swing.JToggleButton();
        embbpInp1 = new javax.swing.JTextField();
        embbp1ComboBox = new javax.swing.JComboBox<>();
        embbpInp2 = new javax.swing.JTextField();
        embbp2ComboBox = new javax.swing.JComboBox<>();
        embbpInp3 = new javax.swing.JTextField();
        embbp3ComboBox = new javax.swing.JComboBox<>();
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

        inputPanel.setMaximumSize(new java.awt.Dimension(621, 650));
        inputPanel.setMinimumSize(new java.awt.Dimension(621, 650));
        inputPanel.setPreferredSize(new java.awt.Dimension(621, 650));
        java.awt.GridBagLayout inputPanelLayout = new java.awt.GridBagLayout();
        inputPanelLayout.columnWidths = new int[] {69};
        inputPanel.setLayout(inputPanelLayout);

        bp.setText("Bonus Pot");
        bp.setMaximumSize(new java.awt.Dimension(207, 23));
        bp.setMinimumSize(new java.awt.Dimension(207, 23));
        bp.setPreferredSize(new java.awt.Dimension(207, 23));
        bp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(bp, gridBagConstraints);

        numOptions.setText("10");
        numOptions.setMaximumSize(new java.awt.Dimension(50, 23));
        numOptions.setMinimumSize(new java.awt.Dimension(50, 23));
        numOptions.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(numOptions, gridBagConstraints);

        optionsLabel.setText("Additional Options:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
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
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wseOptions, gridBagConstraints);

        calculate.setText("Calculate");
        calculate.setMaximumSize(new java.awt.Dimension(310, 23));
        calculate.setMinimumSize(new java.awt.Dimension(310, 23));
        calculate.setPreferredSize(new java.awt.Dimension(310, 23));
        calculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(calculate, gridBagConstraints);

        no_3lbp.setText("No 3L Att BP");
        no_3lbp.setEnabled(false);
        no_3lbp.setMaximumSize(new java.awt.Dimension(207, 23));
        no_3lbp.setMinimumSize(new java.awt.Dimension(207, 23));
        no_3lbp.setPreferredSize(new java.awt.Dimension(207, 23));
        no_3lbp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lbpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(no_3lbp, gridBagConstraints);

        no_3l.setText("No 3L Att");
        no_3l.setMaximumSize(new java.awt.Dimension(207, 23));
        no_3l.setMinimumSize(new java.awt.Dimension(207, 23));
        no_3l.setPreferredSize(new java.awt.Dimension(207, 23));
        no_3l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_3lActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(no_3l, gridBagConstraints);

        clearInp.setText("Reset/Cancel");
        clearInp.setMaximumSize(new java.awt.Dimension(310, 23));
        clearInp.setMinimumSize(new java.awt.Dimension(310, 23));
        clearInp.setPreferredSize(new java.awt.Dimension(310, 23));
        clearInp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearInpActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(clearInp, gridBagConstraints);

        inputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wepSelect, gridBagConstraints);

        wepInp1.setEnabled(false);
        wepInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp1, gridBagConstraints);

        wep1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wep1ComboBox.setEnabled(false);
        wep1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wep1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wep1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wep1ComboBox, gridBagConstraints);

        wepInp2.setEnabled(false);
        wepInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp2, gridBagConstraints);

        wep2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wep2ComboBox.setEnabled(false);
        wep2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wep2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wep2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wep2ComboBox, gridBagConstraints);

        wepInp3.setEnabled(false);
        wepInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp3, gridBagConstraints);

        wep3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wep3ComboBox.setEnabled(false);
        wep3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wep3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wep3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wep3ComboBox, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(secSelect, gridBagConstraints);

        secInp1.setEnabled(false);
        secInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp1, gridBagConstraints);

        sec1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        sec1ComboBox.setEnabled(false);
        sec1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        sec1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sec1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(sec1ComboBox, gridBagConstraints);

        secInp2.setEnabled(false);
        secInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp2, gridBagConstraints);

        sec2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        sec2ComboBox.setEnabled(false);
        sec2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        sec2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sec2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(sec2ComboBox, gridBagConstraints);

        secInp3.setEnabled(false);
        secInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp3, gridBagConstraints);

        sec3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        sec3ComboBox.setEnabled(false);
        sec3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        sec3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sec3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(sec3ComboBox, gridBagConstraints);

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
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        inputPanel.add(embSelect, gridBagConstraints);

        embInp1.setEnabled(false);
        embInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        embInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        embInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp1, gridBagConstraints);

        emb1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        emb1ComboBox.setEnabled(false);
        emb1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        emb1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                emb1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(emb1ComboBox, gridBagConstraints);

        embInp2.setEnabled(false);
        embInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        embInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        embInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp2, gridBagConstraints);

        emb2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        emb2ComboBox.setEnabled(false);
        emb2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        emb2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                emb2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(emb2ComboBox, gridBagConstraints);

        embInp3.setEnabled(false);
        embInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        embInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        embInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp3, gridBagConstraints);

        emb3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        emb3ComboBox.setEnabled(false);
        emb3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        emb3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                emb3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(emb3ComboBox, gridBagConstraints);

        soulSelect.setText("Soul");
        soulSelect.setMaximumSize(new java.awt.Dimension(207, 23));
        soulSelect.setMinimumSize(new java.awt.Dimension(207, 23));
        soulSelect.setPreferredSize(new java.awt.Dimension(207, 23));
        soulSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soulSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(soulSelect, gridBagConstraints);

        wepInp4.setEnabled(false);
        wepInp4.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp4.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp4.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wepInp4, gridBagConstraints);

        soulComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        soulComboBox.setEnabled(false);
        soulComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        soulComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        soulComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        soulComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                soulComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(soulComboBox, gridBagConstraints);

        bonusSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 9;
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wepbpSelect, gridBagConstraints);

        wepbpInp1.setEnabled(false);
        wepbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp1, gridBagConstraints);

        wepbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wepbp1ComboBox.setEnabled(false);
        wepbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wepbp1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wepbp1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbp1ComboBox, gridBagConstraints);

        wepbpInp2.setEnabled(false);
        wepbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp2, gridBagConstraints);

        wepbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wepbp2ComboBox.setEnabled(false);
        wepbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wepbp2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wepbp2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbp2ComboBox, gridBagConstraints);

        wepbpInp3.setEnabled(false);
        wepbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp3, gridBagConstraints);

        wepbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        wepbp3ComboBox.setEnabled(false);
        wepbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        wepbp3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wepbp3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbp3ComboBox, gridBagConstraints);

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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(secbpSelect, gridBagConstraints);

        secbpInp1.setEnabled(false);
        secbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp1, gridBagConstraints);

        secbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        secbp1ComboBox.setEnabled(false);
        secbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        secbp1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                secbp1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbp1ComboBox, gridBagConstraints);

        secbpInp2.setEnabled(false);
        secbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp2, gridBagConstraints);

        secbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        secbp2ComboBox.setEnabled(false);
        secbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        secbp2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                secbp2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbp2ComboBox, gridBagConstraints);

        secbpInp3.setEnabled(false);
        secbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp3, gridBagConstraints);

        secbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED", "BOSS" }));
        secbp3ComboBox.setEnabled(false);
        secbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        secbp3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                secbp3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbp3ComboBox, gridBagConstraints);

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
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(embbpSelect, gridBagConstraints);

        embbpInp1.setEnabled(false);
        embbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp1, gridBagConstraints);

        embbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        embbp1ComboBox.setEnabled(false);
        embbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        embbp1ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                embbp1ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp1ComboBox, gridBagConstraints);

        embbpInp2.setEnabled(false);
        embbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp2, gridBagConstraints);

        embbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        embbp2ComboBox.setEnabled(false);
        embbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        embbp2ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                embbp2ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp2ComboBox, gridBagConstraints);

        embbpInp3.setEnabled(false);
        embbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp3, gridBagConstraints);

        embbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATT", "IED" }));
        embbp3ComboBox.setEnabled(false);
        embbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        embbp3ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                embbp3ComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp3ComboBox, gridBagConstraints);

        outputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(outputSeperator, gridBagConstraints);

        fd_LegionBP.setColumns(20);
        fd_LegionBP.setRows(5);
        fd_LegionBP.setEnabled(false);
        fd_LegionBP.setMinimumSize(new java.awt.Dimension(621, 150));
        fd_LegionBP.setPreferredSize(new java.awt.Dimension(621, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
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

        inputOutputPane.addTab("Base Stats and Buffs", baseStatsPanel);

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
                    if (!wepInp1.getText().equals("") && wepInp1_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp1.getText()) / 100;
                        switch (wepInp1_comboSel) {
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
                    if (!wepInp2.getText().equals("") && wepInp2_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp2.getText()) / 100;
                        switch (wepInp2_comboSel) {
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
                    if (!wepInp3.getText().equals("") && wepInp3_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp3.getText()) / 100;
                        switch (wepInp3_comboSel) {
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
                    if (!secInp1.getText().equals("") && secInp1_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp1.getText()) / 100;
                        switch (secInp1_comboSel) {
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
                    if (!secInp2.getText().equals("") && secInp2_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp2.getText()) / 100;
                        switch (secInp2_comboSel) {
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
                    if (!secInp3.getText().equals("") && secInp3_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp3.getText()) / 100;
                        switch (secInp3_comboSel) {
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
                    if (!embInp1.getText().equals("") && embInp1_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp1.getText()) / 100;
                        switch (embInp1_comboSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embInp2.getText().equals("") && embInp2_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp2.getText()) / 100;
                        switch (embInp2_comboSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embInp3.getText().equals("") && embInp3_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp3.getText()) / 100;
                        switch (embInp3_comboSel){
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
                    if (!wepbpInp1.getText().equals("") && wepbpInp1_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp1.getText()) / 100;
                        switch (wepbpInp1_comboSel) {
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
                    if (!wepbpInp2.getText().equals("") && wepbpInp2_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp2.getText()) / 100;
                        switch (wepbpInp2_comboSel) {
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
                    if (!wepbpInp3.getText().equals("") && wepbpInp3_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp3.getText()) / 100;
                        switch (wepbpInp3_comboSel) {
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
                    if (!secbpInp1.getText().equals("") && secbpInp1_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp1.getText()) / 100;
                        switch (secbpInp1_comboSel) {
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
                    if (!secbpInp2.getText().equals("") && secbpInp2_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp2.getText()) / 100;
                        switch (secbpInp2_comboSel) {
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
                    if (!secbpInp3.getText().equals("") && secbpInp3_comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp3.getText()) / 100;
                        switch (secbpInp3_comboSel) {
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
                    if (!embbpInp1.getText().equals("") && embbpInp1_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp1.getText()) / 100;
                        switch (embbpInp1_comboSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embbpInp2.getText().equals("") && embbpInp2_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp2.getText()) / 100;
                        switch (embbpInp2_comboSel){
                            case ATT:
                                this.att_base += embInp;
                                break;
                            case IED:
                                this.ied_base = (1 - ((1 - this.ied_base) * (1 - embInp)));
                                break;
                        }
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    if (!embbpInp3.getText().equals("") && embbpInp3_comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp3.getText()) / 100;
                        switch (embbpInp3_comboSel){
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
                    if (!wepInp4.getText().equals("") && wepInp5_comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp4.getText()) / 100;
                        switch (wepInp5_comboSel) {
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
                WSEHelpers.generateFamiliars(3, FamiliarTier.EPIC);
                
                
                worker = new WSEWorker(this.dmg_base, this.boss_base, this.att_base, this.ied_base, this.crit_base, this.pdr, this.classType, this.wep_lvl, this.sec_lvl,  numberOfOptions, this.server);
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

    private void wseOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wseOptionsItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            outputPotVector(this.comboBoxMap.get(wseOptions.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_wseOptionsItemStateChanged

    private void wep1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wep1ComboBoxItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            System.out.println(this.potSelectComboBoxMap.get(wep1ComboBox.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_wep1ComboBoxItemStateChanged

    private void wep2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wep2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wep2ComboBoxItemStateChanged

    private void wep3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wep3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wep3ComboBoxItemStateChanged

    private void sec1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sec1ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sec1ComboBoxItemStateChanged

    private void sec2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sec2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sec2ComboBoxItemStateChanged

    private void sec3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sec3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sec3ComboBoxItemStateChanged

    private void emb1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_emb1ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_emb1ComboBoxItemStateChanged

    private void emb2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_emb2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_emb2ComboBoxItemStateChanged

    private void emb3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_emb3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_emb3ComboBoxItemStateChanged

    private void wepbp1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wepbp1ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wepbp1ComboBoxItemStateChanged

    private void wepbp2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wepbp2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wepbp2ComboBoxItemStateChanged

    private void wepbp3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wepbp3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_wepbp3ComboBoxItemStateChanged

    private void secbp1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_secbp1ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_secbp1ComboBoxItemStateChanged

    private void secbp2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_secbp2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_secbp2ComboBoxItemStateChanged

    private void secbp3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_secbp3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_secbp3ComboBoxItemStateChanged

    private void embbp1ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_embbp1ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_embbp1ComboBoxItemStateChanged

    private void embbp2ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_embbp2ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_embbp2ComboBoxItemStateChanged

    private void embbp3ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_embbp3ComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_embbp3ComboBoxItemStateChanged

    private void soulComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_soulComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_soulComboBoxItemStateChanged
    
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
        emb1ComboBox.setEnabled(b);
        emb2ComboBox.setEnabled(b);
        emb3ComboBox.setEnabled(b);
    }

    public void setEmblemBPEnabled(boolean b) {
        embbpInp1.setEnabled(b);
        embbpInp2.setEnabled(b);
        embbpInp3.setEnabled(b);
        embbp1ComboBox.setEnabled(b);
        embbp2ComboBox.setEnabled(b);
        embbp3ComboBox.setEnabled(b);
    }

    public void setSecondaryEnabled(boolean b) {
        secInp1.setEnabled(b);
        secInp2.setEnabled(b);
        secInp3.setEnabled(b);
        sec1ComboBox.setEnabled(b);
        sec2ComboBox.setEnabled(b);
        sec3ComboBox.setEnabled(b);
    }

    public void setSecondaryBPEnabled(boolean b) {
        secbpInp1.setEnabled(b);
        secbpInp2.setEnabled(b);
        secbpInp3.setEnabled(b);
        secbp1ComboBox.setEnabled(b);
        secbp2ComboBox.setEnabled(b);
        secbp3ComboBox.setEnabled(b);
    }

    public void setWeaponEnabled(boolean b) {
        wepInp1.setEnabled(b);
        wepInp2.setEnabled(b);
        wepInp3.setEnabled(b);
        wep1ComboBox.setEnabled(b);
        wep2ComboBox.setEnabled(b);
        wep3ComboBox.setEnabled(b);
    }

    public void setWeaponBPEnabled(boolean b) {
        wepbpInp1.setEnabled(b);
        wepbpInp2.setEnabled(b);
        wepbpInp3.setEnabled(b);
        wepbp1ComboBox.setEnabled(b);
        wepbp2ComboBox.setEnabled(b);
        wepbp3ComboBox.setEnabled(b);
    }

    public void setSoulEnabled(boolean b) {
        wepInp4.setEnabled(b);
        soulComboBox.setEnabled(b);
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
    private javax.swing.JComboBox<String> emb1ComboBox;
    private javax.swing.JComboBox<String> emb2ComboBox;
    private javax.swing.JComboBox<String> emb3ComboBox;
    private javax.swing.JTextField embInp1;
    private javax.swing.JTextField embInp2;
    private javax.swing.JTextField embInp3;
    private javax.swing.JToggleButton embSelect;
    private javax.swing.JComboBox<String> embbp1ComboBox;
    private javax.swing.JComboBox<String> embbp2ComboBox;
    private javax.swing.JComboBox<String> embbp3ComboBox;
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
    private javax.swing.JComboBox<String> sec1ComboBox;
    private javax.swing.JComboBox<String> sec2ComboBox;
    private javax.swing.JComboBox<String> sec3ComboBox;
    private javax.swing.JTextField secInp1;
    private javax.swing.JTextField secInp2;
    private javax.swing.JTextField secInp3;
    private javax.swing.JToggleButton secSelect;
    private javax.swing.JComboBox<String> secbp1ComboBox;
    private javax.swing.JComboBox<String> secbp2ComboBox;
    private javax.swing.JComboBox<String> secbp3ComboBox;
    private javax.swing.JTextField secbpInp1;
    private javax.swing.JTextField secbpInp2;
    private javax.swing.JTextField secbpInp3;
    private javax.swing.JToggleButton secbpSelect;
    private javax.swing.JToggleButton seclvl;
    private javax.swing.JComboBox<String> soulComboBox;
    private javax.swing.JToggleButton soulSelect;
    private javax.swing.JTextField union;
    private javax.swing.JComboBox<String> wep1ComboBox;
    private javax.swing.JComboBox<String> wep2ComboBox;
    private javax.swing.JComboBox<String> wep3ComboBox;
    private javax.swing.JTextField wepInp1;
    private javax.swing.JTextField wepInp2;
    private javax.swing.JTextField wepInp3;
    private javax.swing.JTextField wepInp4;
    private javax.swing.JToggleButton wepSelect;
    private javax.swing.JComboBox<String> wepbp1ComboBox;
    private javax.swing.JComboBox<String> wepbp2ComboBox;
    private javax.swing.JComboBox<String> wepbp3ComboBox;
    private javax.swing.JTextField wepbpInp1;
    private javax.swing.JTextField wepbpInp2;
    private javax.swing.JTextField wepbpInp3;
    private javax.swing.JToggleButton wepbpSelect;
    private javax.swing.JToggleButton weplvl;
    private javax.swing.JComboBox<String> wseOptions;
    private javax.swing.JToggleButton zeroClass;
    // End of variables declaration//GEN-END:variables
}
