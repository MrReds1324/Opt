/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSEOptimizer;

import static WSEOptimizer.ComboBoxSupport.buildComboBoxFamiliars;
import static WSEOptimizer.ComboBoxSupport.buildFamiliarLinesSelectComboBoxMap;
import static WSEOptimizer.ComboBoxSupport.buildFamiliarSelectComboBoxMap;
import static WSEOptimizer.ComboBoxSupport.buildSelectComboBoxMap;
import static WSEOptimizer.ComboBoxSupport.lvlStrToInt;
import WSEOptimizer.Constants.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ryanb
 */
public class OptimizationPieces extends javax.swing.JFrame {

    private ClassType classType = ClassType.NOCLASS;  //Keeps track of the class type
    //These fields keep track of the values inserted into the input text fields
    private double crit_base;
    private double att_base;
    private double ied_base;
    private double dmg_base;
    private double boss_base;
    private double pdr;
    private int legionVal;
    //Variables for saving the base stats for final damage calculation
    private double crit_baseS;
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
    private int hyperPoints;
    private Server server = Server.REBOOT;
    //Variables for tracking familiar related
    private int numTopLines = 0;
    private int numBotLines = 0;
    private Map<String, FamiliarTier> familiarTierComboBoxMap = buildFamiliarSelectComboBoxMap();
    private Map<String, Integer> familiarLinesComboBoxMap = buildFamiliarLinesSelectComboBoxMap();
    //Variables for tracking execution time for the worker
    private double time;
    private long startTime;
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

        mainPane = new javax.swing.JTabbedPane();
        inputPanel = new javax.swing.JPanel();
        optimization = new javax.swing.JLabel();
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
        soulInp = new javax.swing.JTextField();
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
        familiarSeperator = new javax.swing.JSeparator();
        familiarLinesComboBox = new javax.swing.JComboBox<>();
        familiarTierComboBox = new javax.swing.JComboBox<>();
        familiar1Select = new javax.swing.JToggleButton();
        familiar2Select = new javax.swing.JToggleButton();
        familiar3Select = new javax.swing.JToggleButton();
        familiar1Inp1 = new javax.swing.JTextField();
        familiar1Inp2 = new javax.swing.JTextField();
        familiar2Inp1 = new javax.swing.JTextField();
        familiar2Inp2 = new javax.swing.JTextField();
        familiar3Inp1 = new javax.swing.JTextField();
        familiar3Inp2 = new javax.swing.JTextField();
        familiar1ComboBox1 = new javax.swing.JComboBox<>();
        familiar1ComboBox2 = new javax.swing.JComboBox<>();
        familiar2ComboBox1 = new javax.swing.JComboBox<>();
        familiar2ComboBox2 = new javax.swing.JComboBox<>();
        familiar3ComboBox1 = new javax.swing.JComboBox<>();
        familiar3ComboBox2 = new javax.swing.JComboBox<>();
        outputSeperator = new javax.swing.JSeparator();
        fd_LegionBP = new javax.swing.JTextArea();
        mainFiller = new javax.swing.Box.Filler(new java.awt.Dimension(1, 40), new java.awt.Dimension(1, 40), new java.awt.Dimension(1, 40));
        baseStatsPanel = new javax.swing.JPanel();
        baseStats = new javax.swing.JLabel();
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
        guildSeperator = new javax.swing.JSeparator();
        guildPanel = new javax.swing.JPanel();
        guildSkills = new javax.swing.JLabel();
        guildSkillBoss = new javax.swing.JLabel();
        guildSkillIED = new javax.swing.JLabel();
        guildSkillDmg = new javax.swing.JLabel();
        guildSkillCrit = new javax.swing.JLabel();
        guildBossComboBox = new javax.swing.JComboBox<>();
        guildCritComboBox = new javax.swing.JComboBox<>();
        guildIEDComboBox = new javax.swing.JComboBox<>();
        guildDmgComboBox = new javax.swing.JComboBox<>();
        linkSeperator = new javax.swing.JSeparator();
        linkSkillsPanel = new javax.swing.JPanel();
        zeroLinkSkill = new javax.swing.JLabel();
        linkSkills = new javax.swing.JLabel();
        zeroLinkComboBox = new javax.swing.JComboBox<>();
        cadenaLinkSkill = new javax.swing.JLabel();
        cadenaLinkComboBox = new javax.swing.JComboBox<>();
        abLinkSkill = new javax.swing.JLabel();
        abLinkComboBox = new javax.swing.JComboBox<>();
        luminousLinkSkill = new javax.swing.JLabel();
        luminousLinkComboBox = new javax.swing.JComboBox<>();
        adeleLinkSkill = new javax.swing.JLabel();
        adeleLinkComboBox = new javax.swing.JComboBox<>();
        adelePartyComboBox = new javax.swing.JComboBox<>();
        dsLinkSkill = new javax.swing.JLabel();
        dsLinkComboBox = new javax.swing.JComboBox<>();
        btLinkSkill = new javax.swing.JLabel();
        btLinkComboBox = new javax.swing.JComboBox<>();
        kinesisLinkSkill = new javax.swing.JLabel();
        kinesisLinkComboBox = new javax.swing.JComboBox<>();
        illiumLinkSkill = new javax.swing.JLabel();
        illiumLinkComboBox = new javax.swing.JComboBox<>();
        illiumStacksComboBox = new javax.swing.JComboBox<>();
        arkLinkSkill = new javax.swing.JLabel();
        arkLinkComboBox = new javax.swing.JComboBox<>();
        arkStacksComboBox = new javax.swing.JComboBox<>();
        daLinkSkill = new javax.swing.JLabel();
        daLinkComboBox = new javax.swing.JComboBox<>();
        kannaLinkSkill = new javax.swing.JLabel();
        kannaLinkComboBox = new javax.swing.JComboBox<>();
        mageLinkSkill = new javax.swing.JLabel();
        mageLinkComboBox = new javax.swing.JComboBox<>();
        mageStacksComboBox = new javax.swing.JComboBox<>();
        thiefLinkSkill = new javax.swing.JLabel();
        thiefLinkComboBox = new javax.swing.JComboBox<>();
        statsFiller = new javax.swing.Box.Filler(new java.awt.Dimension(1, 250), new java.awt.Dimension(1, 250), new java.awt.Dimension(1, 250));
        buffsPanel = new javax.swing.JPanel();
        buffsDebuffs = new javax.swing.JLabel();
        mercedesSpikes = new javax.swing.JRadioButton();
        mercedesUnicorn = new javax.swing.JRadioButton();
        bamDarkAura1 = new javax.swing.JRadioButton();
        bamDarkAura2 = new javax.swing.JRadioButton();
        bamWeakeningAura = new javax.swing.JRadioButton();
        daNetherSlice = new javax.swing.JRadioButton();
        dsDemonCry = new javax.swing.JRadioButton();
        mechHEX = new javax.swing.JRadioButton();
        whCall = new javax.swing.JRadioButton();
        sharpEyes = new javax.swing.JRadioButton();
        xenonOrbital = new javax.swing.JRadioButton();
        xenonHypogram = new javax.swing.JRadioButton();
        cadenaDaggers = new javax.swing.JRadioButton();
        abRibbon = new javax.swing.JRadioButton();
        arkAbyssal = new javax.swing.JRadioButton();
        illiumBrand = new javax.swing.JRadioButton();
        hayatoStrike = new javax.swing.JRadioButton();
        kannaBubble = new javax.swing.JRadioButton();
        kinesisQuake1 = new javax.swing.JRadioButton();
        kinesisQuake2 = new javax.swing.JRadioButton();
        kinesisShot = new javax.swing.JRadioButton();
        nlCurse = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WSE Optimization");
        setMaximumSize(new java.awt.Dimension(640, 650));
        setMinimumSize(new java.awt.Dimension(640, 650));
        setPreferredSize(new java.awt.Dimension(640, 650));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        mainPane.setMaximumSize(new java.awt.Dimension(626, 678));

        inputPanel.setMaximumSize(new java.awt.Dimension(621, 650));
        inputPanel.setMinimumSize(new java.awt.Dimension(621, 650));
        inputPanel.setPreferredSize(new java.awt.Dimension(621, 650));
        java.awt.GridBagLayout inputPanelLayout = new java.awt.GridBagLayout();
        inputPanelLayout.columnWidths = new int[] {69};
        inputPanel.setLayout(inputPanelLayout);

        optimization.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        optimization.setText("Optimization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        inputPanel.add(optimization, gridBagConstraints);

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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(bp, gridBagConstraints);

        numOptions.setText("10");
        numOptions.setMaximumSize(new java.awt.Dimension(50, 23));
        numOptions.setMinimumSize(new java.awt.Dimension(50, 23));
        numOptions.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(numOptions, gridBagConstraints);

        optionsLabel.setText("Additional Options:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(calculate, gridBagConstraints);

        no_3lbp.setText("No 3L Att BP");
        no_3lbp.setEnabled(false);
        no_3lbp.setMaximumSize(new java.awt.Dimension(207, 23));
        no_3lbp.setMinimumSize(new java.awt.Dimension(207, 23));
        no_3lbp.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(no_3lbp, gridBagConstraints);

        no_3l.setText("No 3L Att");
        no_3l.setMaximumSize(new java.awt.Dimension(207, 23));
        no_3l.setMinimumSize(new java.awt.Dimension(207, 23));
        no_3l.setPreferredSize(new java.awt.Dimension(207, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(clearInp, gridBagConstraints);

        inputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wepSelect, gridBagConstraints);

        wepInp1.setEnabled(false);
        wepInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp1, gridBagConstraints);

        wep1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wep1ComboBox.setEnabled(false);
        wep1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wep1ComboBox, gridBagConstraints);

        wepInp2.setEnabled(false);
        wepInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp2, gridBagConstraints);

        wep2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wep2ComboBox.setEnabled(false);
        wep2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wep2ComboBox, gridBagConstraints);

        wepInp3.setEnabled(false);
        wepInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        wepInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        wepInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepInp3, gridBagConstraints);

        wep3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wep3ComboBox.setEnabled(false);
        wep3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wep3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wep3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(secSelect, gridBagConstraints);

        secInp1.setEnabled(false);
        secInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp1, gridBagConstraints);

        sec1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        sec1ComboBox.setEnabled(false);
        sec1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(sec1ComboBox, gridBagConstraints);

        secInp2.setEnabled(false);
        secInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp2, gridBagConstraints);

        sec2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        sec2ComboBox.setEnabled(false);
        sec2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(sec2ComboBox, gridBagConstraints);

        secInp3.setEnabled(false);
        secInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        secInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        secInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secInp3, gridBagConstraints);

        sec3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        sec3ComboBox.setEnabled(false);
        sec3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        sec3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        sec3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp1, gridBagConstraints);

        emb1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        emb1ComboBox.setEnabled(false);
        emb1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(emb1ComboBox, gridBagConstraints);

        embInp2.setEnabled(false);
        embInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        embInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        embInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp2, gridBagConstraints);

        emb2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        emb2ComboBox.setEnabled(false);
        emb2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(emb2ComboBox, gridBagConstraints);

        embInp3.setEnabled(false);
        embInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        embInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        embInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embInp3, gridBagConstraints);

        emb3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        emb3ComboBox.setEnabled(false);
        emb3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        emb3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        emb3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(soulSelect, gridBagConstraints);

        soulInp.setEnabled(false);
        soulInp.setMaximumSize(new java.awt.Dimension(140, 23));
        soulInp.setMinimumSize(new java.awt.Dimension(140, 23));
        soulInp.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(soulInp, gridBagConstraints);

        soulComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        soulComboBox.setEnabled(false);
        soulComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        soulComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        soulComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(soulComboBox, gridBagConstraints);

        bonusSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(wepbpSelect, gridBagConstraints);

        wepbpInp1.setEnabled(false);
        wepbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp1, gridBagConstraints);

        wepbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wepbp1ComboBox.setEnabled(false);
        wepbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbp1ComboBox, gridBagConstraints);

        wepbpInp2.setEnabled(false);
        wepbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp2, gridBagConstraints);

        wepbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wepbp2ComboBox.setEnabled(false);
        wepbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(wepbp2ComboBox, gridBagConstraints);

        wepbpInp3.setEnabled(false);
        wepbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        wepbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        wepbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(wepbpInp3, gridBagConstraints);

        wepbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        wepbp3ComboBox.setEnabled(false);
        wepbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        wepbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        wepbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(secbpSelect, gridBagConstraints);

        secbpInp1.setEnabled(false);
        secbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp1, gridBagConstraints);

        secbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        secbp1ComboBox.setEnabled(false);
        secbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbp1ComboBox, gridBagConstraints);

        secbpInp2.setEnabled(false);
        secbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp2, gridBagConstraints);

        secbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        secbp2ComboBox.setEnabled(false);
        secbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(secbp2ComboBox, gridBagConstraints);

        secbpInp3.setEnabled(false);
        secbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        secbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        secbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(secbpInp3, gridBagConstraints);

        secbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS" }));
        secbp3ComboBox.setEnabled(false);
        secbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        secbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        secbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(embbpSelect, gridBagConstraints);

        embbpInp1.setEnabled(false);
        embbpInp1.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp1.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp1, gridBagConstraints);

        embbp1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        embbp1ComboBox.setEnabled(false);
        embbp1ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp1ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp1ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp1ComboBox, gridBagConstraints);

        embbpInp2.setEnabled(false);
        embbpInp2.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp2.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp2, gridBagConstraints);

        embbp2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        embbp2ComboBox.setEnabled(false);
        embbp2ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp2ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp2ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp2ComboBox, gridBagConstraints);

        embbpInp3.setEnabled(false);
        embbpInp3.setMaximumSize(new java.awt.Dimension(140, 23));
        embbpInp3.setMinimumSize(new java.awt.Dimension(140, 23));
        embbpInp3.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(embbpInp3, gridBagConstraints);

        embbp3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED" }));
        embbp3ComboBox.setEnabled(false);
        embbp3ComboBox.setMaximumSize(new java.awt.Dimension(67, 20));
        embbp3ComboBox.setMinimumSize(new java.awt.Dimension(67, 20));
        embbp3ComboBox.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(embbp3ComboBox, gridBagConstraints);

        familiarSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(familiarSeperator, gridBagConstraints);

        familiarLinesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 Lines", "1 Lines", "2 Lines", "3 Lines", "4 Lines", "5 Lines", "6 Lines" }));
        familiarLinesComboBox.setMaximumSize(new java.awt.Dimension(310, 20));
        familiarLinesComboBox.setMinimumSize(new java.awt.Dimension(310, 20));
        familiarLinesComboBox.setPreferredSize(new java.awt.Dimension(310, 20));
        familiarLinesComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                familiarLinesComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        inputPanel.add(familiarLinesComboBox, gridBagConstraints);

        familiarTierComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LEGENDARY", "UNIQUE", "EPIC", "RARE", "COMMON" }));
        familiarTierComboBox.setMaximumSize(new java.awt.Dimension(310, 20));
        familiarTierComboBox.setMinimumSize(new java.awt.Dimension(310, 20));
        familiarTierComboBox.setPreferredSize(new java.awt.Dimension(310, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        inputPanel.add(familiarTierComboBox, gridBagConstraints);

        familiar1Select.setText("Familiar 1");
        familiar1Select.setMaximumSize(new java.awt.Dimension(207, 23));
        familiar1Select.setMinimumSize(new java.awt.Dimension(207, 23));
        familiar1Select.setPreferredSize(new java.awt.Dimension(207, 23));
        familiar1Select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                familiar1SelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(familiar1Select, gridBagConstraints);

        familiar2Select.setText("Familiar 2");
        familiar2Select.setMaximumSize(new java.awt.Dimension(207, 23));
        familiar2Select.setMinimumSize(new java.awt.Dimension(207, 23));
        familiar2Select.setPreferredSize(new java.awt.Dimension(207, 23));
        familiar2Select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                familiar2SelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(familiar2Select, gridBagConstraints);

        familiar3Select.setText("Familiar 3");
        familiar3Select.setMaximumSize(new java.awt.Dimension(207, 23));
        familiar3Select.setMinimumSize(new java.awt.Dimension(207, 23));
        familiar3Select.setPreferredSize(new java.awt.Dimension(207, 23));
        familiar3Select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                familiar3SelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        inputPanel.add(familiar3Select, gridBagConstraints);

        familiar1Inp1.setEnabled(false);
        familiar1Inp1.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar1Inp1.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar1Inp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar1Inp1, gridBagConstraints);

        familiar1Inp2.setEnabled(false);
        familiar1Inp2.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar1Inp2.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar1Inp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar1Inp2, gridBagConstraints);

        familiar2Inp1.setEnabled(false);
        familiar2Inp1.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar2Inp1.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar2Inp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar2Inp1, gridBagConstraints);

        familiar2Inp2.setEnabled(false);
        familiar2Inp2.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar2Inp2.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar2Inp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar2Inp2, gridBagConstraints);

        familiar3Inp1.setEnabled(false);
        familiar3Inp1.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar3Inp1.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar3Inp1.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar3Inp1, gridBagConstraints);

        familiar3Inp2.setEnabled(false);
        familiar3Inp2.setMaximumSize(new java.awt.Dimension(140, 23));
        familiar3Inp2.setMinimumSize(new java.awt.Dimension(140, 23));
        familiar3Inp2.setPreferredSize(new java.awt.Dimension(140, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 2;
        inputPanel.add(familiar3Inp2, gridBagConstraints);

        familiar1ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar1ComboBox1.setEnabled(false);
        familiar1ComboBox1.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar1ComboBox1.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar1ComboBox1.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        inputPanel.add(familiar1ComboBox1, gridBagConstraints);

        familiar1ComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar1ComboBox2.setEnabled(false);
        familiar1ComboBox2.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar1ComboBox2.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar1ComboBox2.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        inputPanel.add(familiar1ComboBox2, gridBagConstraints);

        familiar2ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar2ComboBox1.setEnabled(false);
        familiar2ComboBox1.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar2ComboBox1.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar2ComboBox1.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 19;
        inputPanel.add(familiar2ComboBox1, gridBagConstraints);

        familiar2ComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar2ComboBox2.setEnabled(false);
        familiar2ComboBox2.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar2ComboBox2.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar2ComboBox2.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 20;
        inputPanel.add(familiar2ComboBox2, gridBagConstraints);

        familiar3ComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar3ComboBox1.setEnabled(false);
        familiar3ComboBox1.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar3ComboBox1.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar3ComboBox1.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 19;
        inputPanel.add(familiar3ComboBox1, gridBagConstraints);

        familiar3ComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "ATT", "IED", "BOSS", "CRIT" }));
        familiar3ComboBox2.setEnabled(false);
        familiar3ComboBox2.setMaximumSize(new java.awt.Dimension(67, 20));
        familiar3ComboBox2.setMinimumSize(new java.awt.Dimension(67, 20));
        familiar3ComboBox2.setPreferredSize(new java.awt.Dimension(67, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 20;
        inputPanel.add(familiar3ComboBox2, gridBagConstraints);

        outputSeperator.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
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
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(fd_LegionBP, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(mainFiller, gridBagConstraints);

        mainPane.addTab("Main", inputPanel);

        baseStatsPanel.setAlignmentX(0.0F);
        baseStatsPanel.setAlignmentY(0.0F);
        baseStatsPanel.setMaximumSize(new java.awt.Dimension(621, 350));
        baseStatsPanel.setMinimumSize(new java.awt.Dimension(621, 350));
        baseStatsPanel.setPreferredSize(new java.awt.Dimension(621, 350));
        baseStatsPanel.setLayout(new java.awt.GridBagLayout());

        baseStats.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        baseStats.setText("Base Stats");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        baseStatsPanel.add(baseStats, gridBagConstraints);

        dmg.setText("215");
        dmg.setMaximumSize(new java.awt.Dimension(60, 23));
        dmg.setMinimumSize(new java.awt.Dimension(60, 23));
        dmg.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        baseStatsPanel.add(dmg, gridBagConstraints);

        dmgLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        dmgLabel.setText("DMG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(dmgLabel, gridBagConstraints);

        boss.setText("210");
        boss.setMaximumSize(new java.awt.Dimension(60, 23));
        boss.setMinimumSize(new java.awt.Dimension(60, 23));
        boss.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        baseStatsPanel.add(boss, gridBagConstraints);

        bossLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bossLabel.setText("BOSS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(bossLabel, gridBagConstraints);

        att.setText("39");
        att.setMaximumSize(new java.awt.Dimension(60, 23));
        att.setMinimumSize(new java.awt.Dimension(60, 23));
        att.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        baseStatsPanel.add(att, gridBagConstraints);

        attLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        attLabel.setText("ATT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(attLabel, gridBagConstraints);

        critDmgInp.setText("96");
        critDmgInp.setMaximumSize(new java.awt.Dimension(60, 23));
        critDmgInp.setMinimumSize(new java.awt.Dimension(60, 23));
        critDmgInp.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        baseStatsPanel.add(critDmgInp, gridBagConstraints);

        ied.setText("89.49");
        ied.setMaximumSize(new java.awt.Dimension(60, 23));
        ied.setMinimumSize(new java.awt.Dimension(60, 23));
        ied.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        baseStatsPanel.add(ied, gridBagConstraints);

        critdmgLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        critdmgLabel.setLabelFor(critDmgInp);
        critdmgLabel.setText("CRIT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(critdmgLabel, gridBagConstraints);

        iedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        iedLabel.setText("IED");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(iedLabel, gridBagConstraints);

        weplvl.setText("lvl 160+ Wep");
        weplvl.setMaximumSize(new java.awt.Dimension(103, 23));
        weplvl.setMinimumSize(new java.awt.Dimension(103, 23));
        weplvl.setPreferredSize(new java.awt.Dimension(103, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(weplvl, gridBagConstraints);

        seclvl.setText("lvl 160+ Sec");
        seclvl.setMaximumSize(new java.awt.Dimension(103, 23));
        seclvl.setMinimumSize(new java.awt.Dimension(103, 23));
        seclvl.setPreferredSize(new java.awt.Dimension(103, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        baseStatsPanel.add(zeroClass, gridBagConstraints);

        union.setText("120");
        union.setMaximumSize(new java.awt.Dimension(60, 23));
        union.setMinimumSize(new java.awt.Dimension(60, 23));
        union.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(union, gridBagConstraints);

        legionLabel.setText("Legion Points for Boss, IED, and Crit Dmage ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(legionLabel, gridBagConstraints);

        hyperStatsInp.setText("1266");
        hyperStatsInp.setMaximumSize(new java.awt.Dimension(60, 23));
        hyperStatsInp.setMinimumSize(new java.awt.Dimension(60, 23));
        hyperStatsInp.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(hyperStatsInp, gridBagConstraints);

        hyperLabel.setLabelFor(hyperStatsInp);
        hyperLabel.setText("Hyper Points for Boss, Damage, IED, and Crit Damage");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(hyperLabel, gridBagConstraints);

        monDef.setText("300");
        monDef.setMaximumSize(new java.awt.Dimension(60, 23));
        monDef.setMinimumSize(new java.awt.Dimension(60, 23));
        monDef.setPreferredSize(new java.awt.Dimension(60, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(monDef, gridBagConstraints);

        pdrLabel.setText("Monster Defense");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        baseStatsPanel.add(pdrLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(guildSeperator, gridBagConstraints);

        guildPanel.setMaximumSize(new java.awt.Dimension(621, 61));
        guildPanel.setMinimumSize(new java.awt.Dimension(621, 61));
        guildPanel.setPreferredSize(new java.awt.Dimension(621, 61));
        guildPanel.setLayout(new java.awt.GridBagLayout());

        guildSkills.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        guildSkills.setText("Nobelesse Guild Skills");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        guildPanel.add(guildSkills, gridBagConstraints);

        guildSkillBoss.setText("Boss Slayers (Boss)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildSkillBoss, gridBagConstraints);

        guildSkillIED.setText("Undeterred (IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildSkillIED, gridBagConstraints);

        guildSkillDmg.setText("For the Guild! (Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildSkillDmg, gridBagConstraints);

        guildSkillCrit.setText("Hard Hitter (Crit Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildSkillCrit, gridBagConstraints);

        guildBossComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6", "LVL 7", "LVL 8", "LVL 9", "LVL 10", "LVL 11", "LVL 12", "LVL 13", "LVL 14", "LVL 15" }));
        guildBossComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        guildBossComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        guildBossComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildBossComboBox, gridBagConstraints);

        guildCritComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6", "LVL 7", "LVL 8", "LVL 9", "LVL 10", "LVL 11", "LVL 12", "LVL 13", "LVL 14", "LVL 15" }));
        guildCritComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        guildCritComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        guildCritComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildCritComboBox, gridBagConstraints);

        guildIEDComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6", "LVL 7", "LVL 8", "LVL 9", "LVL 10", "LVL 11", "LVL 12", "LVL 13", "LVL 14", "LVL 15" }));
        guildIEDComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        guildIEDComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        guildIEDComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildIEDComboBox, gridBagConstraints);

        guildDmgComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6", "LVL 7", "LVL 8", "LVL 9", "LVL 10", "LVL 11", "LVL 12", "LVL 13", "LVL 14", "LVL 15" }));
        guildDmgComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        guildDmgComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        guildDmgComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        guildPanel.add(guildDmgComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 9;
        baseStatsPanel.add(guildPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(linkSeperator, gridBagConstraints);

        linkSkillsPanel.setMaximumSize(new java.awt.Dimension(621, 160));
        linkSkillsPanel.setMinimumSize(new java.awt.Dimension(621, 160));
        linkSkillsPanel.setPreferredSize(new java.awt.Dimension(621, 160));
        linkSkillsPanel.setLayout(new java.awt.GridBagLayout());

        zeroLinkSkill.setText("Zero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(zeroLinkSkill, gridBagConstraints);

        linkSkills.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        linkSkills.setText("Link Skills");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        linkSkillsPanel.add(linkSkills, gridBagConstraints);

        zeroLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5" }));
        zeroLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        zeroLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        zeroLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(zeroLinkComboBox, gridBagConstraints);

        cadenaLinkSkill.setText("Cadena");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(cadenaLinkSkill, gridBagConstraints);

        cadenaLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2" }));
        cadenaLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        cadenaLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        cadenaLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(cadenaLinkComboBox, gridBagConstraints);

        abLinkSkill.setText("Angelic Buster");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(abLinkSkill, gridBagConstraints);

        abLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        abLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        abLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        abLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(abLinkComboBox, gridBagConstraints);

        luminousLinkSkill.setText("Luminous");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(luminousLinkSkill, gridBagConstraints);

        luminousLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        luminousLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        luminousLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        luminousLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(luminousLinkComboBox, gridBagConstraints);

        adeleLinkSkill.setText("Adele");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(adeleLinkSkill, gridBagConstraints);

        adeleLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2" }));
        adeleLinkComboBox.setMaximumSize(new java.awt.Dimension(65, 20));
        adeleLinkComboBox.setMinimumSize(new java.awt.Dimension(65, 20));
        adeleLinkComboBox.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linkSkillsPanel.add(adeleLinkComboBox, gridBagConstraints);

        adelePartyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Stacks", "2 Stacks", "3 Stacks", "4 Stacks" }));
        adelePartyComboBox.setMaximumSize(new java.awt.Dimension(89, 20));
        adelePartyComboBox.setMinimumSize(new java.awt.Dimension(89, 20));
        adelePartyComboBox.setPreferredSize(new java.awt.Dimension(89, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        linkSkillsPanel.add(adelePartyComboBox, gridBagConstraints);

        dsLinkSkill.setText("Demon Slayer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(dsLinkSkill, gridBagConstraints);

        dsLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        dsLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        dsLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        dsLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(dsLinkComboBox, gridBagConstraints);

        btLinkSkill.setText("Beast Tamer ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(btLinkSkill, gridBagConstraints);

        btLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        btLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        btLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        btLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(btLinkComboBox, gridBagConstraints);

        kinesisLinkSkill.setText("Kinesis");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(kinesisLinkSkill, gridBagConstraints);

        kinesisLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2" }));
        kinesisLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        kinesisLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        kinesisLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(kinesisLinkComboBox, gridBagConstraints);

        illiumLinkSkill.setText("Illium");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(illiumLinkSkill, gridBagConstraints);

        illiumLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2" }));
        illiumLinkComboBox.setMaximumSize(new java.awt.Dimension(65, 20));
        illiumLinkComboBox.setMinimumSize(new java.awt.Dimension(65, 20));
        illiumLinkComboBox.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linkSkillsPanel.add(illiumLinkComboBox, gridBagConstraints);

        illiumStacksComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Stacks", "2 Stacks", "3 Stacks" }));
        illiumStacksComboBox.setMaximumSize(new java.awt.Dimension(89, 20));
        illiumStacksComboBox.setMinimumSize(new java.awt.Dimension(89, 20));
        illiumStacksComboBox.setPreferredSize(new java.awt.Dimension(89, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        linkSkillsPanel.add(illiumStacksComboBox, gridBagConstraints);

        arkLinkSkill.setText("Ark");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(arkLinkSkill, gridBagConstraints);

        arkLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        arkLinkComboBox.setMaximumSize(new java.awt.Dimension(65, 20));
        arkLinkComboBox.setMinimumSize(new java.awt.Dimension(65, 20));
        arkLinkComboBox.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linkSkillsPanel.add(arkLinkComboBox, gridBagConstraints);

        arkStacksComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 Stacks", "1 Stacks", "2 Stacks", "3 Stacks", "4 Stacks", "5 Stacks" }));
        arkStacksComboBox.setMaximumSize(new java.awt.Dimension(89, 20));
        arkStacksComboBox.setMinimumSize(new java.awt.Dimension(89, 20));
        arkStacksComboBox.setPreferredSize(new java.awt.Dimension(89, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        linkSkillsPanel.add(arkStacksComboBox, gridBagConstraints);

        daLinkSkill.setText("Demon Avenger");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(daLinkSkill, gridBagConstraints);

        daLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3" }));
        daLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        daLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        daLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(daLinkComboBox, gridBagConstraints);

        kannaLinkSkill.setText("Kanna");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(kannaLinkSkill, gridBagConstraints);

        kannaLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2" }));
        kannaLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        kannaLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        kannaLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(kannaLinkComboBox, gridBagConstraints);

        mageLinkSkill.setText("Explorer Mage");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(mageLinkSkill, gridBagConstraints);

        mageLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6" }));
        mageLinkComboBox.setMaximumSize(new java.awt.Dimension(65, 20));
        mageLinkComboBox.setMinimumSize(new java.awt.Dimension(65, 20));
        mageLinkComboBox.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linkSkillsPanel.add(mageLinkComboBox, gridBagConstraints);

        mageStacksComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 Stacks", "1 Stacks", "2 Stacks", "3 Stacks", "4 Stacks", "5 Stacks", "6 Stacks" }));
        mageStacksComboBox.setMaximumSize(new java.awt.Dimension(89, 20));
        mageStacksComboBox.setMinimumSize(new java.awt.Dimension(89, 20));
        mageStacksComboBox.setPreferredSize(new java.awt.Dimension(89, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        linkSkillsPanel.add(mageStacksComboBox, gridBagConstraints);

        thiefLinkSkill.setText("Explorer Thief");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(thiefLinkSkill, gridBagConstraints);

        thiefLinkComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LVL 0", "LVL 1", "LVL 2", "LVL 3", "LVL 4", "LVL 5", "LVL 6" }));
        thiefLinkComboBox.setMaximumSize(new java.awt.Dimension(155, 20));
        thiefLinkComboBox.setMinimumSize(new java.awt.Dimension(155, 20));
        thiefLinkComboBox.setPreferredSize(new java.awt.Dimension(155, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        linkSkillsPanel.add(thiefLinkComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 9;
        baseStatsPanel.add(linkSkillsPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        baseStatsPanel.add(statsFiller, gridBagConstraints);

        mainPane.addTab("Base Stats, Links, and Guild Skills", baseStatsPanel);

        buffsPanel.setMaximumSize(new java.awt.Dimension(621, 300));
        buffsPanel.setMinimumSize(new java.awt.Dimension(621, 300));
        buffsPanel.setPreferredSize(new java.awt.Dimension(621, 300));
        buffsPanel.setLayout(new java.awt.GridBagLayout());

        buffsDebuffs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buffsDebuffs.setText("Party Buffs and Debuffs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        buffsPanel.add(buffsDebuffs, gridBagConstraints);

        mercedesSpikes.setText("Merc - Spikes Royale (50% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(mercedesSpikes, gridBagConstraints);

        mercedesUnicorn.setText("Merc - Unicorn Spike (30% Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(mercedesUnicorn, gridBagConstraints);

        bamDarkAura1.setText("BaM - Dark Aura (20% Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(bamDarkAura1, gridBagConstraints);

        bamDarkAura2.setText("BaM - Dark Aura (10% Boss)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(bamDarkAura2, gridBagConstraints);

        bamWeakeningAura.setText("BaM - Weakening Aura (20% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(bamWeakeningAura, gridBagConstraints);

        daNetherSlice.setText("DA - Nether Slice (30% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(daNetherSlice, gridBagConstraints);

        dsDemonCry.setText("DS - Demon Cry (15% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(dsDemonCry, gridBagConstraints);

        mechHEX.setText("Mech - Support Unit: H-EX (10% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(mechHEX, gridBagConstraints);

        whCall.setText("WH - Call of the Wild (10% Att)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(whCall, gridBagConstraints);

        sharpEyes.setText("Sharp Eyes - (15% Crit Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(sharpEyes, gridBagConstraints);

        xenonOrbital.setText("Xenon - Orbital Cataclysm (30% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(xenonOrbital, gridBagConstraints);

        xenonHypogram.setText("Xenon - Hypogram Field: Fusion (20% Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(xenonHypogram, gridBagConstraints);

        cadenaDaggers.setText("Cadena - Summon Daggers (30% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(cadenaDaggers, gridBagConstraints);

        abRibbon.setText("AB - Finale Ribbon (20% Damage)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(abRibbon, gridBagConstraints);

        arkAbyssal.setText("Ark - Abyssal Charge Drive (30% Boss, 20% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(arkAbyssal, gridBagConstraints);

        illiumBrand.setText("Illium - Umbral Brand III (12% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(illiumBrand, gridBagConstraints);

        hayatoStrike.setText("Hayato - Sudden Strike (20% Boss)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(hayatoStrike, gridBagConstraints);

        kannaBubble.setText("Kanna - Bellflower Barrier (20% Damage, 25% Boss)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(kannaBubble, gridBagConstraints);

        kinesisQuake1.setText("Kinesis - Mind Quake (13% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(kinesisQuake1, gridBagConstraints);

        kinesisQuake2.setText("Kinesis - Mind Quake Overwhelm (+3% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(kinesisQuake2, gridBagConstraints);

        kinesisShot.setText("Kinesis - Psychic Shot (15% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(kinesisShot, gridBagConstraints);

        nlCurse.setText("Night Lord - Frailty Curse (30% IED)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buffsPanel.add(nlCurse, gridBagConstraints);

        mainPane.addTab("Party Buffs", buffsPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(mainPane, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void secSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secSelectActionPerformed
        setSecondaryEnabled(secSelect.isSelected());
    }//GEN-LAST:event_secSelectActionPerformed

    private void bpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpActionPerformed
        if (bp.isSelected()) {
            server = Server.NONREBOOT;
            wepbpSelect.setEnabled(true);
            if (classType != ClassType.ZERO) {
                secbpSelect.setEnabled(true);
            }
            embbpSelect.setEnabled(true);
            no_3lbp.setEnabled(true);
        } else {
            server = Server.REBOOT;
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
        zeroClass.setEnabled(!kannaClass.isSelected());
        classType = kannaClass.isSelected() ? ClassType.KANNA : ClassType.NOCLASS;
    }//GEN-LAST:event_kannaClassActionPerformed

    @SuppressWarnings("unchecked")
    private void calculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateActionPerformed
        if (calculate.isSelected()) {
            //Disable the combobox
            wseOptions.setEnabled(false);
            //Determine the inputs from the text fields
            try {
                resetOpenFamiliarSlots();
                numberOfOptions = Integer.parseInt(numOptions.getText());
                if (numberOfOptions < 0) {
                    numberOfOptions = 0;
                }
                att_base = Double.parseDouble(att.getText()) / 100;
                boss_base = Double.parseDouble(boss.getText()) / 100;
                dmg_base = Double.parseDouble(dmg.getText()) / 100;
                ied_base = Double.parseDouble(ied.getText()) / 100;
                crit_base = Double.parseDouble(critDmgInp.getText()) / 100;
                pdr = Double.parseDouble(monDef.getText()) / 100;
                hyperPoints = Integer.parseInt(hyperStatsInp.getText());
                
                saveBase();
                legionVal = Integer.parseInt(union.getText());
                getGuildSkills();
                //Sets up a scaling value for weapon inputs if the class selected was Zero
                double zero_scale = classType == ClassType.ZERO ? 2 : 1;
                fd_LegionBP.setText("Optimizing...");
                //If the weapon is sleceted go through and pull all the inputs and add them to the base values
                PotType comboSel = PotType.DEFAULT;
                if (wepSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wep1ComboBox.getSelectedItem().toString());
                    if (!wepInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp1.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wep2ComboBox.getSelectedItem().toString());
                    if (!wepInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp2.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wep3ComboBox.getSelectedItem().toString());
                    if (!wepInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepInp3.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                }
                //If the secondary is sleceted go through and pull all the inputs and add them to the base values
                if (secSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(sec1ComboBox.getSelectedItem().toString());
                    if (!secInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp1.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(sec2ComboBox.getSelectedItem().toString());
                    if (!secInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp2.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(sec3ComboBox.getSelectedItem().toString());
                    if (!secInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secInp3.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                }
                //If the emblem is sleceted go through and pull all the inputs and add them to the base values
                if (embSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(emb1ComboBox.getSelectedItem().toString());
                    if (!embInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp1.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(emb2ComboBox.getSelectedItem().toString());
                    if (!embInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp2.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(emb3ComboBox.getSelectedItem().toString());
                    if (!embInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embInp3.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                }

                //If the weapon is sleceted go through and pull all the inputs and add them to the base values
                if (wepbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wepbp1ComboBox.getSelectedItem().toString());
                    if (!wepbpInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp1.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wepbp2ComboBox.getSelectedItem().toString());
                    if (!wepbpInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp2.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(wepbp3ComboBox.getSelectedItem().toString());
                    if (!wepbpInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(wepbpInp3.getText()) / 100;
                        addInputToBase(wepInp, comboSel, zero_scale);
                    }
                }
                //If the secondary is sleceted go through and pull all the inputs and add them to the base values
                if (secbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(secbp1ComboBox.getSelectedItem().toString());
                    if (!secbpInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp1.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(secbp2ComboBox.getSelectedItem().toString());
                    if (!secbpInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp2.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(secbp3ComboBox.getSelectedItem().toString());
                    if (!secbpInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double secInp = Double.parseDouble(secbpInp3.getText()) / 100;
                        addInputToBase(secInp, comboSel);
                    }
                }
                //If the emblem is sleceted go through and pull all the inputs and add them to the base values
                if (embbpSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(embbp1ComboBox.getSelectedItem().toString());
                    if (!embbpInp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp1.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(embbp2ComboBox.getSelectedItem().toString());
                    if (!embbpInp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp2.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(embbp3ComboBox.getSelectedItem().toString());
                    if (!embbpInp3.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double embInp = Double.parseDouble(embbpInp3.getText()) / 100;
                        addInputToBase(embInp, comboSel);
                    }
                }
                //If the soul is sleceted go through and pull all the inputs and add them to the base values
                if (soulSelect.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(soulComboBox.getSelectedItem().toString());
                    if (!soulInp.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(soulInp.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                }
                if (familiar1Select.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar1ComboBox1.getSelectedItem().toString());
                    if (!familiar1Inp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar1Inp1.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar1ComboBox2.getSelectedItem().toString());
                    if (!familiar1Inp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar1Inp2.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                }
                if (familiar2Select.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar2ComboBox1.getSelectedItem().toString());
                    if (!familiar2Inp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar2Inp1.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar2ComboBox2.getSelectedItem().toString());
                    if (!familiar2Inp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar2Inp2.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                }
                if (familiar3Select.isSelected()) {
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar3ComboBox1.getSelectedItem().toString());
                    if (!familiar3Inp1.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar3Inp1.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                    //If the field is not empty and a button has been selected the grab the inputs and add them to the base values
                    comboSel = potSelectComboBoxMap.get(familiar3ComboBox2.getSelectedItem().toString());
                    if (!familiar3Inp2.getText().equals("") && comboSel != PotType.DEFAULT) {
                        double wepInp = Double.parseDouble(familiar3Inp2.getText()) / 100;
                        addInputToBase(wepInp, comboSel);
                    }
                }
                if (!bp.isSelected()){
                    clearInputs();
                }
                //Start time of the method
                startTime = System.nanoTime();
                
                //Set up generation spaces for each variable
                WSEHelpers.generateHyperStats(hyperPoints);
                WSEHelpers.generateLegion(legionVal);
                
                PotConfig no_3lAtt = no_3l.isSelected() ? PotConfig.NO3LINE : PotConfig.DEFAULT; //Keeps track if we want to calculate with or without 3 lines of attack
                WSEHelpers.setupWeaponGenerationSpace(wepSelect.isSelected(), no_3lAtt, PotType.MAIN);
                WSEHelpers.setupSecondaryGenerationSpace(secSelect.isSelected(), no_3lAtt, classType, PotType.MAIN);
                WSEHelpers.setupEmblemGenerationSpace(embSelect.isSelected(), no_3lAtt, PotType.MAIN);
                
                PotConfig no_3lbpAtt = no_3lbp.isSelected() ? PotConfig.NO3LINE : PotConfig.DEFAULT; //Keeps track if we want to calculate with or without 3 lines of attack for bonus potential
                WSEHelpers.setupWeaponGenerationSpace(wepbpSelect.isSelected(), no_3lbpAtt, PotType.BONUS);
                WSEHelpers.setupSecondaryGenerationSpace(secbpSelect.isSelected(), no_3lbpAtt, classType, PotType.BONUS);
                WSEHelpers.setupEmblemGenerationSpace(embbpSelect.isSelected(), no_3lbpAtt, PotType.BONUS);
                WSEHelpers.setupSoulsGenerationSpace(soulSelect.isSelected());
                WSEHelpers.generateFamiliars(numTopLines, numBotLines, familiarTierComboBoxMap.get(familiarTierComboBox.getSelectedItem().toString()));
                
                
                worker = new WSEWorker(dmg_base, boss_base, att_base, ied_base, crit_base, pdr, classType, weplvl.isSelected(), seclvl.isSelected(),  numberOfOptions, server);
                worker.addPropertyChangeListener(listener);
                worker.execute();
            } catch (Exception e) {
                e.printStackTrace();
                fd_LegionBP.setText("ERROR OCCURED: REDO INPUTS");
                calculate.setSelected(false);
            }
        }
        calculate.setEnabled(false);
        calculate.setSelected(false);
    }//GEN-LAST:event_calculateActionPerformed
    
    private void addInputToBase(double input, PotType selection){
        addInputToBase(input, selection, 1);
    }
    
    private void addInputToBase(double input, PotType selection, double zeroScale){
        switch (selection) {
            case ATT:
                att_base += input * zeroScale;
                break;
            case BOSS:
                boss_base += input * zeroScale;
                break;
            case IED:
                ied_base = (1 - ((1 - ied_base) * (1 - input)));
                if (zeroScale == 2) {
                    ied_base = (1 - ((1 - ied_base) * (1 - input)));
                }
                break;
            case CRITDAMAGE:
                crit_base += input;
                break;
        }
    }
    
    private void clearInpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearInpActionPerformed
        if (clearInp.isSelected() && (worker == null || worker.isDone() || worker.isCancelled())) {
            clearInputs();
        }
        else if (!worker.isDone()){
            worker.cancel(true);
        }
        clearInp.setSelected(false);
    }//GEN-LAST:event_clearInpActionPerformed

    private void wepSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepSelectActionPerformed
        setWeaponEnabled(wepSelect.isSelected());
    }//GEN-LAST:event_wepSelectActionPerformed

    private void embSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embSelectActionPerformed
        setEmblemEnabled(embSelect.isSelected());
    }//GEN-LAST:event_embSelectActionPerformed

    private void zeroClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroClassActionPerformed
        classType = zeroClass.isSelected() ? ClassType.ZERO : ClassType.NOCLASS;
        kannaClass.setEnabled(!zeroClass.isSelected());
        secSelect.setEnabled(!zeroClass.isSelected());
        seclvl.setEnabled(!zeroClass.isSelected());
        secbpSelect.setEnabled(zeroClass.isSelected() ? false : secbpSelect.isSelected());
        secSelect.setSelected(false);
        secbpSelect.setSelected(false);
        setSecondaryEnabled(false);
        setSecondaryBPEnabled(false);
    }//GEN-LAST:event_zeroClassActionPerformed

    private void soulSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soulSelectActionPerformed
        setSoulEnabled(soulSelect.isSelected());
    }//GEN-LAST:event_soulSelectActionPerformed

    private void secbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secbpSelectActionPerformed
        setSecondaryBPEnabled(secbpSelect.isSelected());
    }//GEN-LAST:event_secbpSelectActionPerformed

    private void embbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_embbpSelectActionPerformed
        setEmblemBPEnabled(embbpSelect.isSelected());
    }//GEN-LAST:event_embbpSelectActionPerformed

    private void wepbpSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wepbpSelectActionPerformed
        setWeaponBPEnabled(wepbpSelect.isSelected());
    }//GEN-LAST:event_wepbpSelectActionPerformed

    private void wseOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_wseOptionsItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            outputPotVector(comboBoxMap.get(wseOptions.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_wseOptionsItemStateChanged

    private void familiar1SelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_familiar1SelectActionPerformed
        setFamiliar1Enabled(familiar1Select.isSelected());
        updateFamiliarLineComboBox(familiar1Select.isSelected(), familiar2Select.isSelected(), familiar3Select.isSelected());
    }//GEN-LAST:event_familiar1SelectActionPerformed

    private void familiar2SelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_familiar2SelectActionPerformed
        setFamiliar2Enabled(familiar2Select.isSelected());
        updateFamiliarLineComboBox(familiar1Select.isSelected(), familiar2Select.isSelected(), familiar3Select.isSelected());
    }//GEN-LAST:event_familiar2SelectActionPerformed

    private void familiar3SelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_familiar3SelectActionPerformed
        setFamiliar3Enabled(familiar3Select.isSelected());
        updateFamiliarLineComboBox(familiar1Select.isSelected(), familiar2Select.isSelected(), familiar3Select.isSelected());
    }//GEN-LAST:event_familiar3SelectActionPerformed

    private void familiarLinesComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_familiarLinesComboBoxItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            int total_lines = familiarLinesComboBoxMap.get(familiarLinesComboBox.getSelectedItem().toString());

            boolean f1 = familiar1Select.isSelected();
            boolean f2 = familiar2Select.isSelected();
            boolean f3 = familiar3Select.isSelected();
            if(total_lines >= 5){
                numTopLines = 3;
                numBotLines = total_lines - 3;
            }
            else if (total_lines >= 3){
                if (f1 || f2 || f3){
                    numTopLines = 2;
                    numBotLines = total_lines - 2;
                }
                else{
                    numTopLines = 3;
                    numBotLines = total_lines - 3;
                }
            }
            else if (total_lines >= 1){
                if (f1 && f2 || f2 && f3 || f1 && f3){
                    numTopLines = 1;
                    numBotLines = total_lines - 1;
                }
                else{
                    numTopLines = total_lines;
                    numBotLines = 0;
                }
            }
            else {
                numTopLines = 0;
                numBotLines = 0;
            }
        }
    }//GEN-LAST:event_familiarLinesComboBoxItemStateChanged
    
    public void getGuildSkills(){
        //Add the guild skills to the base of each stat
        crit_base += lvlStrToInt(guildCritComboBox.getSelectedItem().toString()) * 0.02;
        boss_base += lvlStrToInt(guildBossComboBox.getSelectedItem().toString()) * 0.02;
        dmg_base += lvlStrToInt(guildDmgComboBox.getSelectedItem().toString()) * 0.02;
        ied_base = (1 - ((1 - ied_base) * (1 - (lvlStrToInt(guildIEDComboBox.getSelectedItem().toString()) * 0.02))));
    }
    
    public void clearInputs() {
        //Main inputs
        wepInp1.setText("");
        wepInp2.setText("");
        wepInp3.setText("");
        wep1ComboBox.setSelectedItem("None");
        wep2ComboBox.setSelectedItem("None");
        wep3ComboBox.setSelectedItem("None");

        secInp1.setText("");
        secInp2.setText("");
        secInp3.setText("");
        sec1ComboBox.setSelectedItem("None");
        sec2ComboBox.setSelectedItem("None");
        sec3ComboBox.setSelectedItem("None");

        embInp1.setText("");
        embInp2.setText("");
        embInp3.setText("");
        emb1ComboBox.setSelectedItem("None");
        emb2ComboBox.setSelectedItem("None");
        emb3ComboBox.setSelectedItem("None");

        soulInp.setText("");
        soulComboBox.setSelectedItem("None");

        resetFamiliar1();
        resetFamiliar2();
        resetFamiliar3();

        //BP inputs
        wepbpInp1.setText("");
        wepbpInp2.setText("");
        wepbpInp3.setText("");
        wepbp1ComboBox.setSelectedItem("None");
        wepbp2ComboBox.setSelectedItem("None");
        wepbp3ComboBox.setSelectedItem("None");

        secbpInp1.setText("");
        secbpInp2.setText("");
        secbpInp3.setText("");
        secbp1ComboBox.setSelectedItem("None");
        secbp2ComboBox.setSelectedItem("None");
        secbp3ComboBox.setSelectedItem("None");

        embbpInp1.setText("");
        embbpInp2.setText("");
        embbpInp3.setText("");
        embbp1ComboBox.setSelectedItem("None");
        embbp2ComboBox.setSelectedItem("None");
        embbp3ComboBox.setSelectedItem("None");
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
        soulInp.setEnabled(b);
        soulComboBox.setEnabled(b);
    }

    public void setFamiliar1Enabled(boolean b) {
        familiar1Inp1.setEnabled(b);
        familiar1Inp2.setEnabled(b);
        familiar1ComboBox1.setEnabled(b);
        familiar1ComboBox2.setEnabled(b);
    }

    public void setFamiliar2Enabled(boolean b) {
        familiar2Inp1.setEnabled(b);
        familiar2Inp2.setEnabled(b);
        familiar2ComboBox1.setEnabled(b);
        familiar2ComboBox2.setEnabled(b);
    }

    public void setFamiliar3Enabled(boolean b) {
        familiar3Inp1.setEnabled(b);
        familiar3Inp2.setEnabled(b);
        familiar3ComboBox1.setEnabled(b);
        familiar3ComboBox2.setEnabled(b);
    }
    
    public void updateFamiliarLineComboBox(boolean f1, boolean f2, boolean f3){
        if (f1 && f2 && f3){
            familiarLinesComboBox.setModel(buildComboBoxFamiliars(0));
            // Setting selected item was not setting the lines to 0
            numTopLines = 0;
            numBotLines = 0;
        }
        else if (f1 && f2 || f1 && f3 || f2 && f3){
            familiarLinesComboBox.setModel(buildComboBoxFamiliars(2));
            familiarLinesComboBox.setSelectedItem("2 Lines");
        }
        else if (f1 || f2 || f3){
            familiarLinesComboBox.setModel(buildComboBoxFamiliars(4));
            familiarLinesComboBox.setSelectedItem("4 Lines");
        }
        else {
            familiarLinesComboBox.setModel(buildComboBoxFamiliars(6));
            familiarLinesComboBox.setSelectedItem("6 Lines");
        }
    }

    private void saveBase() {
        att_baseS = att_base;
        boss_baseS = boss_base;
        dmg_baseS = dmg_base;
        ied_baseS = ied_base;
        crit_baseS = crit_base;
    }

    private void outputPotVector(PotVector potVector) {
        if (!wepSelect.isSelected()) {
            ItemPrinter.printItem(wepInp1, wep1ComboBox, wepInp2, wep2ComboBox, wepInp3, wep3ComboBox, potVector.getWep());
        }
        if (!secSelect.isSelected()) {
            ItemPrinter.printItem(secInp1, sec1ComboBox, secInp2, sec2ComboBox, secInp3, sec3ComboBox, potVector.getSec());
        }
        if (!embSelect.isSelected()) {
            ItemPrinter.printItem(embInp1, emb1ComboBox, embInp2, emb2ComboBox, embInp3, emb3ComboBox, potVector.getEmb());
        }
        if (!wepbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(wepbpInp1, wepbp1ComboBox, wepbpInp2, wepbp2ComboBox, wepbpInp3, wepbp3ComboBox, potVector.getWepb());
        }
        if (!secbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(secbpInp1, secbp1ComboBox, secbpInp2, secbp2ComboBox, secbpInp3, secbp3ComboBox, potVector.getSecb());
        }
        if (!embbpSelect.isSelected() && bp.isSelected()) {
            ItemPrinter.printItem(embbpInp1, embbp1ComboBox, embbpInp2, embbp2ComboBox, embbpInp3, embbp3ComboBox, potVector.getEmbb());
        }
        if (!soulSelect.isSelected()) {
            ItemPrinter.printSoul(soulInp, soulComboBox, potVector.getSoul());
        }
        ItemPrinter.printFamiliars(familiar1Select.isSelected(), familiar2Select.isSelected(), familiar3Select.isSelected(), familiar1Inp1, familiar1ComboBox1, familiar1Inp2, familiar1ComboBox2,
                familiar2Inp1, familiar2ComboBox1, familiar2Inp2, familiar2ComboBox2, familiar3Inp1, familiar3ComboBox1, familiar3Inp2, familiar3ComboBox2, potVector.getFamiliars());
        
        double calcBase = ((1.35 + crit_baseS) * (1 + att_baseS) * (1 + boss_baseS + dmg_baseS) * (1 - (pdr * (1 - ied_baseS))));
        ItemPrinter.printLegionHypersAndFD(fd_LegionBP, calcBase, time, potVector);
    }

    private void resetFamiliar1() {
        familiar1Inp1.setText("");
        familiar1Inp2.setText("");
        familiar1ComboBox1.setSelectedItem("None");
        familiar1ComboBox2.setSelectedItem("None");
    }

    private void resetFamiliar2() {
        familiar2Inp1.setText("");
        familiar2Inp2.setText("");
        familiar2ComboBox1.setSelectedItem("None");
        familiar2ComboBox2.setSelectedItem("None");
    }

    private void resetFamiliar3() {
        familiar3Inp1.setText("");
        familiar3Inp2.setText("");
        familiar3ComboBox1.setSelectedItem("None");
        familiar3ComboBox2.setSelectedItem("None");
    }
    
    private void resetOpenFamiliarSlots(){
        if (!familiar1Select.isSelected()){
            resetFamiliar1();
        }
        if (!familiar2Select.isSelected()){
            resetFamiliar2();
        }
        if (!familiar3Select.isSelected()){
            resetFamiliar3();
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OptimizationPieces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OptimizationPieces().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> abLinkComboBox;
    private javax.swing.JLabel abLinkSkill;
    private javax.swing.JRadioButton abRibbon;
    private javax.swing.JComboBox<String> adeleLinkComboBox;
    private javax.swing.JLabel adeleLinkSkill;
    private javax.swing.JComboBox<String> adelePartyComboBox;
    private javax.swing.JRadioButton arkAbyssal;
    private javax.swing.JComboBox<String> arkLinkComboBox;
    private javax.swing.JLabel arkLinkSkill;
    private javax.swing.JComboBox<String> arkStacksComboBox;
    private javax.swing.JTextField att;
    private javax.swing.JLabel attLabel;
    private javax.swing.JRadioButton bamDarkAura1;
    private javax.swing.JRadioButton bamDarkAura2;
    private javax.swing.JRadioButton bamWeakeningAura;
    private javax.swing.JLabel baseStats;
    private javax.swing.JPanel baseStatsPanel;
    private javax.swing.JSeparator bonusSeperator;
    private javax.swing.JTextField boss;
    private javax.swing.JLabel bossLabel;
    private javax.swing.JToggleButton bp;
    private javax.swing.JComboBox<String> btLinkComboBox;
    private javax.swing.JLabel btLinkSkill;
    private javax.swing.JLabel buffsDebuffs;
    private javax.swing.JPanel buffsPanel;
    private javax.swing.JRadioButton cadenaDaggers;
    private javax.swing.JComboBox<String> cadenaLinkComboBox;
    private javax.swing.JLabel cadenaLinkSkill;
    private javax.swing.JToggleButton calculate;
    private javax.swing.JToggleButton clearInp;
    private javax.swing.JTextField critDmgInp;
    private javax.swing.JLabel critdmgLabel;
    private javax.swing.JComboBox<String> daLinkComboBox;
    private javax.swing.JLabel daLinkSkill;
    private javax.swing.JRadioButton daNetherSlice;
    private javax.swing.JTextField dmg;
    private javax.swing.JLabel dmgLabel;
    private javax.swing.JRadioButton dsDemonCry;
    private javax.swing.JComboBox<String> dsLinkComboBox;
    private javax.swing.JLabel dsLinkSkill;
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
    private javax.swing.JComboBox<String> familiar1ComboBox1;
    private javax.swing.JComboBox<String> familiar1ComboBox2;
    private javax.swing.JTextField familiar1Inp1;
    private javax.swing.JTextField familiar1Inp2;
    private javax.swing.JToggleButton familiar1Select;
    private javax.swing.JComboBox<String> familiar2ComboBox1;
    private javax.swing.JComboBox<String> familiar2ComboBox2;
    private javax.swing.JTextField familiar2Inp1;
    private javax.swing.JTextField familiar2Inp2;
    private javax.swing.JToggleButton familiar2Select;
    private javax.swing.JComboBox<String> familiar3ComboBox1;
    private javax.swing.JComboBox<String> familiar3ComboBox2;
    private javax.swing.JTextField familiar3Inp1;
    private javax.swing.JTextField familiar3Inp2;
    private javax.swing.JToggleButton familiar3Select;
    private javax.swing.JComboBox<String> familiarLinesComboBox;
    private javax.swing.JSeparator familiarSeperator;
    private javax.swing.JComboBox<String> familiarTierComboBox;
    private javax.swing.JTextArea fd_LegionBP;
    private javax.swing.JComboBox<String> guildBossComboBox;
    private javax.swing.JComboBox<String> guildCritComboBox;
    private javax.swing.JComboBox<String> guildDmgComboBox;
    private javax.swing.JComboBox<String> guildIEDComboBox;
    private javax.swing.JPanel guildPanel;
    private javax.swing.JSeparator guildSeperator;
    private javax.swing.JLabel guildSkillBoss;
    private javax.swing.JLabel guildSkillCrit;
    private javax.swing.JLabel guildSkillDmg;
    private javax.swing.JLabel guildSkillIED;
    private javax.swing.JLabel guildSkills;
    private javax.swing.JRadioButton hayatoStrike;
    private javax.swing.JLabel hyperLabel;
    private javax.swing.JTextField hyperStatsInp;
    private javax.swing.JTextField ied;
    private javax.swing.JLabel iedLabel;
    private javax.swing.JRadioButton illiumBrand;
    private javax.swing.JComboBox<String> illiumLinkComboBox;
    private javax.swing.JLabel illiumLinkSkill;
    private javax.swing.JComboBox<String> illiumStacksComboBox;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JSeparator inputSeperator;
    private javax.swing.JRadioButton kannaBubble;
    private javax.swing.JToggleButton kannaClass;
    private javax.swing.JComboBox<String> kannaLinkComboBox;
    private javax.swing.JLabel kannaLinkSkill;
    private javax.swing.JComboBox<String> kinesisLinkComboBox;
    private javax.swing.JLabel kinesisLinkSkill;
    private javax.swing.JRadioButton kinesisQuake1;
    private javax.swing.JRadioButton kinesisQuake2;
    private javax.swing.JRadioButton kinesisShot;
    private javax.swing.JLabel legionLabel;
    private javax.swing.JSeparator linkSeperator;
    private javax.swing.JLabel linkSkills;
    private javax.swing.JPanel linkSkillsPanel;
    private javax.swing.JComboBox<String> luminousLinkComboBox;
    private javax.swing.JLabel luminousLinkSkill;
    private javax.swing.JComboBox<String> mageLinkComboBox;
    private javax.swing.JLabel mageLinkSkill;
    private javax.swing.JComboBox<String> mageStacksComboBox;
    private javax.swing.Box.Filler mainFiller;
    private javax.swing.JTabbedPane mainPane;
    private javax.swing.JRadioButton mechHEX;
    private javax.swing.JRadioButton mercedesSpikes;
    private javax.swing.JRadioButton mercedesUnicorn;
    private javax.swing.JTextField monDef;
    private javax.swing.JRadioButton nlCurse;
    private javax.swing.JToggleButton no_3l;
    private javax.swing.JToggleButton no_3lbp;
    private javax.swing.JTextField numOptions;
    private javax.swing.JLabel optimization;
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
    private javax.swing.JRadioButton sharpEyes;
    private javax.swing.JComboBox<String> soulComboBox;
    private javax.swing.JTextField soulInp;
    private javax.swing.JToggleButton soulSelect;
    private javax.swing.Box.Filler statsFiller;
    private javax.swing.JComboBox<String> thiefLinkComboBox;
    private javax.swing.JLabel thiefLinkSkill;
    private javax.swing.JTextField union;
    private javax.swing.JComboBox<String> wep1ComboBox;
    private javax.swing.JComboBox<String> wep2ComboBox;
    private javax.swing.JComboBox<String> wep3ComboBox;
    private javax.swing.JTextField wepInp1;
    private javax.swing.JTextField wepInp2;
    private javax.swing.JTextField wepInp3;
    private javax.swing.JToggleButton wepSelect;
    private javax.swing.JComboBox<String> wepbp1ComboBox;
    private javax.swing.JComboBox<String> wepbp2ComboBox;
    private javax.swing.JComboBox<String> wepbp3ComboBox;
    private javax.swing.JTextField wepbpInp1;
    private javax.swing.JTextField wepbpInp2;
    private javax.swing.JTextField wepbpInp3;
    private javax.swing.JToggleButton wepbpSelect;
    private javax.swing.JToggleButton weplvl;
    private javax.swing.JRadioButton whCall;
    private javax.swing.JComboBox<String> wseOptions;
    private javax.swing.JRadioButton xenonHypogram;
    private javax.swing.JRadioButton xenonOrbital;
    private javax.swing.JToggleButton zeroClass;
    private javax.swing.JComboBox<String> zeroLinkComboBox;
    private javax.swing.JLabel zeroLinkSkill;
    // End of variables declaration//GEN-END:variables
}
