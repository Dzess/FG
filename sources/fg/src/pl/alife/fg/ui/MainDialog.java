package pl.alife.fg.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;

import pl.alife.fg.core.ProblemType;
import pl.alife.fg.core.Settings;
import pl.alife.fg.core.gp.IOperationProvider;
import pl.alife.fg.core.gp.IOperationProviderFactory;
import pl.alife.fg.core.gp.providers.RangeRuntimeOperationProvider;
import pl.alife.fg.core.gp.providers.RuntimeOperationProvider;
import pl.alife.fg.core.gp.providers.SimpleOperationProvider;
import pl.alife.fg.core.gp.providers.factories.IntegerOperationProviderFactory;
import pl.alife.fg.core.gp.providers.factories.RealOperationProviderFactory;
import pl.alife.fg.ui.loaders.data.CSVDataLoader;
import pl.alife.fg.ui.loaders.data.IDataLoader;
import pl.alife.fg.ui.loaders.settings.INISettingsLoader;
import pl.alife.fg.ui.loaders.settings.ISettingsLoader;


/**
 * Stores all the logic of GUI.
 * 
 * <p>
 * Makes heavy use of subsystems represented by classes:
 * <ul>
 * <li>{@linkplain Settings}</li>
 * <li>{@linkplain ISettingsLoader}</li>
 * <li>{@linkplain IDataLoader}</li>
 * <li>{@linkplain IOperationProviderFactory}</li>
 * </ul>
 * </p>
 */
@SuppressWarnings("serial")
public class MainDialog extends JDialog implements ActionListener {

    private JPanel jContentPane = null;
    private JPanel panelConstraints = null;
    private JPanel panelSettings = null;
    private JPanel panelOperations = null;
    private JButton buttonOK = null;
    private JButton buttonCancel = null;
    private JPanel panelButtons = null;
    private JScrollPane jScrollPane = null;
    private JTable tablePoints = null;
    private JButton buttonAddPoint = null;
    private JButton buttonRemovePoint = null;
    private JPanel constraintsPanelButtons = null;
    private JLabel labelPopulationSize = null;
    private JLabel labelGenerations = null;
    private JScrollPane jScrollPaneOperations = null;
    private JTable tableOperations = null;
    private JFormattedTextField textPopulationSize = null;
    private JFormattedTextField textGenerations = null;

    private boolean result = false;
    private Settings settings;
    private ISettingsLoader settingsLoader = new INISettingsLoader();

    private JButton buttonAddX = null;
    private JButton buttonRemoveX = null;
    private JLabel labelMaxDepth = null;
    private JFormattedTextField textMaxDepth = null;
    private JButton buttonLinearScaling = null;
    private JPanel panelProblemType = null;
    private JRadioButton radioDouble = null;
    private JRadioButton radioInteger = null;
    private ButtonGroup groupProblemType = null; // @jve:decl-index=0:visual-constraint=""
    private JButton buttonLoad = null;
    private JButton buttonSave = null;
    private IDataLoader loader = new CSVDataLoader();
    private JButton savePreferencesButton;
    private JButton loadPreferencesButton;
    private JPanel panel;

    private IOperationProviderFactory realFactory;
    private IOperationProviderFactory integerFactory;
    private JPanel extensionFunction;
    private JButton addLiteralButton;
    private JButton addLiteralRangeButton;

    /**
     * If true, the dialog was successively closed by OK button.
     * 
     * @return
     */
    public boolean getResult() {
        return result;
    }

    /**
     * @param owner
     * @throws IOException
     *             : when the template for the settings cannot be found.
     */
    public MainDialog(Frame owner) throws IOException {
        super(owner, ModalityType.TOOLKIT_MODAL);

        // TODO: provide some way of injecting the logic here
        settings = new Settings();
        realFactory = new RealOperationProviderFactory();
        integerFactory = new IntegerOperationProviderFactory();

        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(1024, 768);
        this.setMinimumSize(new Dimension(800, 600));
        this.setResizable(true);
        this.setModal(true);
        this.setName("MainDialog");
        this.setTitle("Function Generator");
        this.setContentPane(getJContentPane());
        this.getGroupProblemType();

        this.setWindowAtScreenCenter();
    }

    private void setWindowAtScreenCenter() {

        int widthWindow = this.getWidth();
        int heightWindow = this.getHeight();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int X = (screen.width / 2) - (widthWindow / 2); // Center horizontally.
        int Y = (screen.height / 2) - (heightWindow / 2); // Center vertically.

        this.setBounds(X, Y, widthWindow, heightWindow);
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.Y_AXIS));
            jContentPane.add(getPanelProblemType(), null);
            jContentPane.add(getPanelConstraints(), null);
            jContentPane.add(getPanelSettings(), null);
            jContentPane.add(getPanelOperations(), null);
            jContentPane.add(getPanelButtons(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes panelConstraints
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelConstraints() {
        if (panelConstraints == null) {
            panelConstraints = new JPanel();
            panelConstraints.setLayout(new BoxLayout(getPanelConstraints(), BoxLayout.Y_AXIS));
            panelConstraints.setBorder(BorderFactory.createTitledBorder(null, "Constraints",
                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            panelConstraints.add(getJScrollPane(), null);
            panelConstraints.add(getConstraintsPanelButtons(), null);
        }
        return panelConstraints;
    }

    /**
     * This method initializes panelSettings
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelSettings() {
        if (panelSettings == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints11.fill = GridBagConstraints.BOTH;
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.gridy = 0;
            gridBagConstraints11.ipadx = 480;
            gridBagConstraints11.weightx = 1.0;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.ipady = 4;
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints.weightx = 1.0;
            labelPopulationSize = new JLabel();
            labelPopulationSize.setText("Size of population:");
            labelPopulationSize.setHorizontalAlignment(SwingConstants.LEFT);
            labelPopulationSize.setHorizontalTextPosition(SwingConstants.LEFT);
            labelPopulationSize.setName("labelPopulationSize");
            panelSettings = new JPanel();
            panelSettings.setLayout(new GridBagLayout());
            panelSettings.setBorder(BorderFactory.createTitledBorder(null, "Basic settings",
                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            panelSettings.add(labelPopulationSize, gridBagConstraints2);
            panelSettings.add(getTextPopulationSize(), gridBagConstraints11);
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.ipady = 4;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.gridy = 1;
            labelGenerations = new JLabel();
            labelGenerations.setText("Number of generations:");
            labelGenerations.setHorizontalAlignment(SwingConstants.LEFT);
            labelGenerations.setHorizontalTextPosition(SwingConstants.LEFT);
            labelGenerations.setName("labelGenerations");
            panelSettings.add(labelGenerations, gridBagConstraints4);
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints21.fill = GridBagConstraints.BOTH;
            gridBagConstraints21.gridy = 1;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.ipady = 0;
            gridBagConstraints21.ipadx = 480;
            gridBagConstraints21.gridx = 1;
            panelSettings.add(getTextGenerations(), gridBagConstraints21);
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.insets = new Insets(0, 0, 5, 5);
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.anchor = GridBagConstraints.WEST;
            gridBagConstraints12.gridy = 2;
            labelMaxDepth = new JLabel();
            labelMaxDepth.setText("Max depth of tree:");
            panelSettings.add(labelMaxDepth, gridBagConstraints12);
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.insets = new Insets(0, 0, 5, 0);
            gridBagConstraints22.fill = GridBagConstraints.BOTH;
            gridBagConstraints22.gridy = 2;
            gridBagConstraints22.weightx = 1.0;
            gridBagConstraints22.gridx = 1;
            gridBagConstraints22.ipadx = 480;
            panelSettings.add(getTextMaxDepth(), gridBagConstraints22);
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 1;
            gbc_panel.gridy = 3;
            panelSettings.add(getPanel(), gbc_panel);
        }
        return panelSettings;
    }

    /**
     * This method initializes panelOperations
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelOperations() {
        if (panelOperations == null) {
            panelOperations = new JPanel();
            panelOperations.setBorder(BorderFactory.createTitledBorder(null, "Available functions",
                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            panelOperations.setLayout(new BoxLayout(panelOperations, BoxLayout.Y_AXIS));
            panelOperations.add(getJScrollPaneOperations());
            panelOperations.add(getExtensionFunction());
        }
        return panelOperations;
    }

    /**
     * This method initializes buttonOK
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonOK() {
        if (buttonOK == null) {
            buttonOK = new JButton();
            buttonOK.setText("Evolve");
            buttonOK.addActionListener(this);
        }
        return buttonOK;
    }

    /**
     * This method initializes buttonCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonCancel() {
        if (buttonCancel == null) {
            buttonCancel = new JButton();
            buttonCancel.setText("Exit");
            buttonCancel.addActionListener(this);
        }
        return buttonCancel;
    }

    /**
     * This method initializes panelButtons
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setAlignment(FlowLayout.RIGHT);
            panelButtons = new JPanel();
            panelButtons.setLayout(flowLayout);
            panelButtons.add(getButtonOK(), null);
            panelButtons.add(getButtonCancel(), null);
        }
        return panelButtons;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTablePoints());
        }
        return jScrollPane;
    }

    /**
     * This method initializes tablePoints
     * 
     * @return javax.swing.JTable
     */
    private JTable getTablePoints() {
        if (tablePoints == null) {
            tablePoints = new JTable();
            tablePoints.setCellSelectionEnabled(true);
            tablePoints.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tablePoints.setModel(new PointsTableModel(Double.class));
            tablePoints.putClientProperty("terminateEditOnFocusLost", true);

            // this key listener is very ugly work around for swing problems
            tablePoints.addKeyListener(new JTableBackspaceFixListener());
        }
        return tablePoints;
    }

    /**
     * This method initializes buttonAddPoint
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonAddPoint() {
        if (buttonAddPoint == null) {
            buttonAddPoint = new JButton();
            buttonAddPoint.setText("Add point");
            buttonAddPoint.setActionCommand("Add point");
            buttonAddPoint.addActionListener(this);
        }
        return buttonAddPoint;
    }

    /**
     * This method initializes buttonRemovePoint
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonRemovePoint() {
        if (buttonRemovePoint == null) {
            buttonRemovePoint = new JButton();
            buttonRemovePoint.setText("Remove point");
            buttonRemovePoint.setActionCommand("Remove point");
            buttonRemovePoint.addActionListener(this);
        }
        return buttonRemovePoint;
    }

    /**
     * This method initializes constraintsPanelButtons
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getConstraintsPanelButtons() {
        if (constraintsPanelButtons == null) {
            FlowLayout flowLayout1 = new FlowLayout();
            flowLayout1.setAlignment(FlowLayout.RIGHT);
            constraintsPanelButtons = new JPanel();
            constraintsPanelButtons.setLayout(flowLayout1);
            constraintsPanelButtons.add(getButtonLoad(), null);
            constraintsPanelButtons.add(getButtonSave(), null);
            constraintsPanelButtons.add(getButtonLinearScaling(), null);
            constraintsPanelButtons.add(getButtonAddX(), null);
            constraintsPanelButtons.add(getButtonRemoveX(), null);
            constraintsPanelButtons.add(getButtonAddPoint(), null);
            constraintsPanelButtons.add(getButtonRemovePoint(), null);
        }
        return constraintsPanelButtons;
    }

    /**
     * This method initializes jScrollPaneOperations
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPaneOperations() {
        if (jScrollPaneOperations == null) {
            jScrollPaneOperations = new JScrollPane();
            jScrollPaneOperations.setViewportView(getTableOperations());
        }
        return jScrollPaneOperations;
    }

    /**
     * This method initializes tableOperations
     * 
     * @return javax.swing.JTable
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private JTable getTableOperations() {
        if (tableOperations == null) {
            tableOperations = new JTable();
            tableOperations.setCellSelectionEnabled(true);
            tableOperations.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tableOperations.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

            // create the default provider
            tableOperations.setModel(new OperationsTableModel(realFactory));

            // setTableOperationsColumnWidth();
        }
        return tableOperations;
    }

    // private void setTableOperationsColumnWidth() {
    // TableColumnModel cModel = tableOperations.getColumnModel();
    // cModel.getColumn(0).setPreferredWidth(15);
    // cModel.getColumn(1).setPreferredWidth(90);
    // cModel.getColumn(2).setPreferredWidth(180);
    // cModel.getColumn(3).setPreferredWidth(180);
    // }

    @Override
    public void actionPerformed(ActionEvent action) {
        String cmd = action.getActionCommand();

        if (cmd.equals("updateType")) {
            PointsTableModel model = (PointsTableModel) tablePoints.getModel();
            if (model.getColumnClass(0).equals(Integer.class) && radioDouble.isSelected()) {
                tablePoints.setModel(new PointsTableModel(Double.class, model));

                // create here the type of the factory used
                tableOperations.setModel(new OperationsTableModel(realFactory));

            } else if (model.getColumnClass(0).equals(Double.class) && radioInteger.isSelected()) {
                tablePoints.setModel(new PointsTableModel(Integer.class, model));

                // create here the type of the factory
                tableOperations.setModel(new OperationsTableModel(integerFactory));
            }
        } else if (cmd.equals("Add point")) {
            ((PointsTableModel) tablePoints.getModel()).addRow();
        } else if (cmd.equals("Remove point")) {
            PointsTableModel model = (PointsTableModel) tablePoints.getModel();
            int[] selected = tablePoints.getSelectedRows();
            for (int i = selected.length - 1; i >= 0; --i) {
                model.removeRow(selected[i]);
            }
        } else if (cmd.equals("AddX")) {
            PointsTableModel model = (PointsTableModel) tablePoints.getModel();
            if (model.getColumnCount() >= 6) {
                JOptionPane.showMessageDialog(this, "More variables is not supported!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                model.addX();
            }
        } else if (cmd.equals("RemoveX")) {
            try {
                PointsTableModel model = (PointsTableModel) tablePoints.getModel();
                model.removeX();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else if (cmd.equals("Linear scaling")) {
            LinearScalingDialog dlg = new LinearScalingDialog(this, getMode());
            dlg.setVisible(true);
            if (dlg.getResult()) {
                if (JOptionPane.showConfirmDialog(this, "Current values will be overridden. Proceed?", "Confirm",
                        JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    tablePoints.setModel(dlg.getModel());
                }
            }
        } else if (cmd.equals("Evolve")) {
            if (getPoints().size() == 0) {
                JOptionPane.showMessageDialog(this, "Please define at least 1 point.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                List<IOperationProvider> operations = getOperations();
                if (operations.size() == 0) {
                    JOptionPane.showMessageDialog(this, "Please select at least 1 operation.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            result = true;
            setVisible(false);
        } else if (cmd.equals("Exit")) {
            result = false;
            setVisible(false);
        } else if (cmd.equals("Load")) {
            try {
                JFileChooser chooser = getFileChooser(".csv", "CSV files");
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

                    Class<?> fieldType = radioDouble.isSelected() ? Double.class : Integer.class;
                    File file = chooser.getSelectedFile();
                    PointsTableModel model = loader.loadFromFile(file, fieldType);
                    tablePoints.setModel(model);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (cmd.equals("Save")) {
            try {
                JFileChooser chooser = getFileChooser(".csv", "CSV files");
                if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                    PointsTableModel model = (PointsTableModel) tablePoints.getModel();
                    File file = chooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    if (!path.toLowerCase().endsWith(".csv"))
                        file = new File(path + ".csv");

                    loader.saveToFile(file, model);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JFileChooser getFileChooser(final String sufix, final String description) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;

                return f.getAbsolutePath().toLowerCase().endsWith(sufix);
            }

            @Override
            public String getDescription() {
                return description;
            }
        });
        return chooser;
    }

    protected NumberFormatter getPositiveIntegerFormatter() {
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("0"));
        formatter.setMinimum(1);
        formatter.setCommitsOnValidEdit(true);
        // formatter.setAllowsInvalid(false);
        return formatter;
    }

    /**
     * This method initializes textPopulationSize
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTextPopulationSize() {
        if (textPopulationSize == null) {
            textPopulationSize = new JFormattedTextField(getPositiveIntegerFormatter());
            textPopulationSize.setText("25000");
            textPopulationSize.setPreferredSize(new Dimension(32, 20));
        }
        return textPopulationSize;
    }

    /**
     * This method initializes textGenerations
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTextGenerations() {
        if (textGenerations == null) {
            textGenerations = new JFormattedTextField(getPositiveIntegerFormatter());
            textGenerations.setText("50");
            textGenerations.setPreferredSize(new Dimension(32, 20));
        }
        return textGenerations;
    }

    /**
     * Sets the {@linkplain Settings} object for the whole view. Synchronizes
     * what the controls are showing with the passed object
     * 
     * @param settings
     *            : the data that has to be set
     */
    public void setSettings(Settings settings) {

        // set the graphical things
        textPopulationSize.setText(Integer.toString(settings.getPopSize()));
        textGenerations.setText(Integer.toString(settings.getGenerations()));
        textMaxDepth.setText(Integer.toString(settings.getMaxTreeDepth()));

        // set the differential operations using the problem type provided
        // and using the list of already saved operations ;)
        // TODO: this method should be refactored out of this scopes
        List<IOperationProvider> operations = settings.getOperations();
        List<IOperationProvider> alreadyExisting = null;
        if (ProblemType.INTEGER == settings.getProblemType()) {
            radioInteger.setSelected(true);
            radioDouble.setSelected(false);

            alreadyExisting = integerFactory.getAvaliable();
            setOperationsToModel(alreadyExisting, operations);
            tableOperations.setModel(new OperationsTableModel(integerFactory));

        } else if (ProblemType.DOUBLE == settings.getProblemType()) {
            radioInteger.setSelected(false);
            radioDouble.setSelected(true);

            alreadyExisting = realFactory.getAvaliable();
            setOperationsToModel(alreadyExisting, operations);
            tableOperations.setModel(new OperationsTableModel(realFactory));
        }

        this.settings = settings;
    }

    private void setOperationsToModel(List<IOperationProvider> alreadyExisting, List<IOperationProvider> operations) {
        // remove all the elements that are already existing and are not
        // simple operation
        // providers
        List<IOperationProvider> toRemove = new LinkedList<IOperationProvider>();
        for (IOperationProvider existingProvider : alreadyExisting) {
            if (existingProvider.getClass() == RangeRuntimeOperationProvider.class) {
                toRemove.add(existingProvider);
            } else if (existingProvider.getClass() == RuntimeOperationProvider.class) {
                toRemove.add(existingProvider);
            }
        }

        alreadyExisting.removeAll(toRemove);

        for (IOperationProvider provider : operations) {

            if (provider.getClass() == SimpleOperationProvider.class) {

                SimpleOperationProvider simpleProvider = (SimpleOperationProvider) provider;
                // change the already existing elements (need to find such
                // class)
                for (IOperationProvider existingProvider : alreadyExisting) {
                    if (existingProvider.getClass() == SimpleOperationProvider.class) {
                        // check if it is the about the same node function
                        SimpleOperationProvider sProvider = (SimpleOperationProvider) existingProvider;
                        if (sProvider.getNodeClass() == simpleProvider.getNodeClass()) {
                            // found the exact same element, so break
                            // but before perform all changes (selection)
                            sProvider.setEnabled(true);
                            break;
                        }
                    }
                }

            } else {
                // with other classes there is not so much f
                // just replace the element in already existing
                provider.setEnabled(true);
                alreadyExisting.add(provider);
            }
        }
    }

    /**
     * Synchronizes the current state of controls with the backend
     * {@linkplain Settings} object.
     * 
     * @return up to date {@linkplain Settings} object
     * @throws Exception
     */
    public Settings getSettings() throws Exception {

        settings.setGenerations(getGenerations());
        settings.setMaxTreeDepth(getMaxTreeDepth());
        settings.setPopulationSize(getPopulationSize());

        if (radioDouble.isSelected()) {
            settings.setProblemType(ProblemType.DOUBLE);
        } else if (radioInteger.isSelected()) {
            settings.setProblemType(ProblemType.INTEGER);
        }
        settings.setOperations(getOperations());

        return settings;
    }

    private int getPopulationSize() {
        return Integer.parseInt(textPopulationSize.getText());
    }

    private int getGenerations() {
        return Integer.parseInt(textGenerations.getText());
    }

    private int getMaxTreeDepth() {
        return Integer.parseInt(textMaxDepth.getText());
    }

    private List<IOperationProvider> getOperations() throws ClassNotFoundException {

        List<IOperationProvider> operations = new ArrayList<IOperationProvider>();
        OperationsTableModel model = (OperationsTableModel) tableOperations.getModel();

        // do not copy those operations
        for (IOperationProvider operation : model.getSelectedOperations()) {
            operations.add(operation);
        }
        return operations;
    }

    public List<Number[]> getPoints() {
        PointsTableModel model = (PointsTableModel) tablePoints.getModel();
        return model.getRows();
    }

    /**
     * This method initializes buttonAddX
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonAddX() {
        if (buttonAddX == null) {
            buttonAddX = new JButton();
            buttonAddX.setText("Add X");
            buttonAddX.setActionCommand("AddX");
            buttonAddX.addActionListener(this);
        }
        return buttonAddX;
    }

    /**
     * This method initializes buttonRemoveX
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonRemoveX() {
        if (buttonRemoveX == null) {
            buttonRemoveX = new JButton();
            buttonRemoveX.setText("Remove last X");
            buttonRemoveX.setActionCommand("RemoveX");
            buttonRemoveX.addActionListener(this);
        }
        return buttonRemoveX;
    }

    /**
     * This method initializes textMaxDepth
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTextMaxDepth() {
        if (textMaxDepth == null) {
            textMaxDepth = new JFormattedTextField(getPositiveIntegerFormatter());
            textMaxDepth.setPreferredSize(new Dimension(32, 20));
            textMaxDepth.setText("6");
        }
        return textMaxDepth;
    }

    /**
     * This method initializes buttonLinearScaling
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonLinearScaling() {
        if (buttonLinearScaling == null) {
            buttonLinearScaling = new JButton();
            buttonLinearScaling.setText("Linear scaling");
            buttonLinearScaling.addActionListener(this);
        }
        return buttonLinearScaling;
    }

    /**
     * This method initializes panelProblemType
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelProblemType() {
        if (panelProblemType == null) {
            panelProblemType = new JPanel();
            panelProblemType.setLayout(new GridBagLayout());
            panelProblemType.setBorder(BorderFactory.createTitledBorder(null, "Problem type",
                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
            panelProblemType.add(getRadioDouble(), new GridBagConstraints());
            panelProblemType.add(getRadioInteger(), new GridBagConstraints());
        }
        return panelProblemType;
    }

    /**
     * This method initializes radioDouble
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRadioDouble() {
        if (radioDouble == null) {
            radioDouble = new JRadioButton();
            radioDouble.setText("Real (floating-point)");
            radioDouble.setActionCommand("updateType");
            radioDouble.setSelected(true);
            radioDouble.addActionListener(this);
        }
        return radioDouble;
    }

    /**
     * This method initializes radioInteger
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRadioInteger() {
        if (radioInteger == null) {
            radioInteger = new JRadioButton();
            radioInteger.setText("Integer");
            radioInteger.setActionCommand("updateType");
            radioInteger.addActionListener(this);
        }
        return radioInteger;
    }

    private Mode getMode() {
        if (radioDouble.isSelected())
            return Mode.Real;
        else
            return Mode.Integer;
    }

    /**
     * This method initializes groupProblemType
     * 
     * @return javax.swing.ButtonGroup
     */
    private ButtonGroup getGroupProblemType() {
        if (groupProblemType == null) {
            groupProblemType = new ButtonGroup();
            groupProblemType.add(getRadioDouble());
            groupProblemType.add(getRadioInteger());
        }
        return groupProblemType;
    }

    /**
     * This method initializes buttonLoad
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonLoad() {
        if (buttonLoad == null) {
            buttonLoad = new JButton();
            buttonLoad.setText("Load");
            buttonLoad.addActionListener(this);
        }
        return buttonLoad;
    }

    /**
     * This method initializes buttonSave
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonSave() {
        if (buttonSave == null) {
            buttonSave = new JButton();
            buttonSave.setText("Save");
            buttonSave.addActionListener(this);
        }
        return buttonSave;
    }

    /**
     * Resets the internal state of the object to make it usable more than once.
     */
    public void resetState() {
        result = false;
    }

    private JButton getSavePreferencesButton() {
        if (savePreferencesButton == null) {
            savePreferencesButton = new JButton("Save");
            savePreferencesButton.setToolTipText("Saves the basic settings and functions into file");
            savePreferencesButton.setHorizontalAlignment(SwingConstants.RIGHT);

            // trick to get the pseudo java closure right
            final JDialog parent = this;
            savePreferencesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try {
                        String sufix = ".ini";
                        JFileChooser chooser = getFileChooser(sufix, "INI files");
                        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

                            File file = chooser.getSelectedFile();
                            file = getFormattedFile(file, sufix);

                            settingsLoader.saveToFile(file, getSettings());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            });
        }
        return savePreferencesButton;
    }

    /**
     * Adds the suffix to the filepath if file has no such suffix.
     */
    private File getFormattedFile(File inputFile, String sufix) {

        String path = inputFile.getAbsolutePath();
        if (!path.toLowerCase().endsWith(sufix))
            inputFile = new File(path + sufix);

        return inputFile;
    }

    private JButton getLoadPreferencesButton() {
        if (loadPreferencesButton == null) {
            loadPreferencesButton = new JButton("Load");

            // trick to get the pseudo java closure right
            final JDialog parent = this;
            loadPreferencesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try {
                        String sufix = ".ini";
                        JFileChooser chooser = getFileChooser(sufix, "INI files");
                        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {

                            File file = chooser.getSelectedFile();
                            file = getFormattedFile(file, sufix);

                            Settings s = settingsLoader.loadFromFile(file);

                            // update the graphical elements
                            setSettings(s);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            });

            loadPreferencesButton.setToolTipText("Loads the basic settings and functions from file");
            loadPreferencesButton.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return loadPreferencesButton;
    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
            panel.add(getLoadPreferencesButton());
            panel.add(getSavePreferencesButton());
        }
        return panel;
    }

    private JPanel getExtensionFunction() {
        if (extensionFunction == null) {
            extensionFunction = new JPanel();
            FlowLayout flowLayout = (FlowLayout) extensionFunction.getLayout();
            flowLayout.setAlignment(FlowLayout.LEFT);
            extensionFunction.add(getAddLiteralButton());
            extensionFunction.add(getAddLiteralRangeButton());
        }
        return extensionFunction;
    }

    private JButton getAddLiteralButton() {
        if (addLiteralButton == null) {
            addLiteralButton = new JButton("Add Literal");

            // TODO: maybe the control of how many literal things should be
            // available
            addLiteralButton.setVisible(false);
            addLiteralButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {

                }
            });
        }
        return addLiteralButton;
    }

    private JButton getAddLiteralRangeButton() {
        if (addLiteralRangeButton == null) {
            addLiteralRangeButton = new JButton("Add Literal Range");

            // TODO: maybe the control of how many literal things should be
            // available
            addLiteralRangeButton.setVisible(false);
            addLiteralRangeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
        }
        return addLiteralRangeButton;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
