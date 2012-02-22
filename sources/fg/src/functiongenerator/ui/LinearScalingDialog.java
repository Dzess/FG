package functiongenerator.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class LinearScalingDialog extends JDialog implements ChangeListener, ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private Boolean result = false;
    private final int POINTS_PER_VARIABLE = 16;
    private Random rand = new Random(); // @jve:decl-index=0:
    private Mode mode = Mode.Real;

    private JPanel jContentPane = null;
    private JLabel labelX0 = null;
    private JFormattedTextField textX0From = null;
    private JLabel labelX0to = null;
    private JFormattedTextField textX0To = null;
    private JCheckBox checkX0FromVariable = null;
    private JCheckBox checkX0ToVariable = null;
    private JLabel labelYFrom = null;
    private JFormattedTextField textYFrom = null;
    private JLabel labelYTo = null;
    private JFormattedTextField textYTo = null;
    private JCheckBox checkYFromVariable = null;
    private JCheckBox checkYToVariable = null;
    private JPanel panelButtons = null;
    private JButton buttonOK = null;
    private JButton buttonCancel = null;

    private JLabel labelInterpretationDesc = null;

    private JLabel labelInterpretation = null;

    /**
     * @param owner
     */
    public LinearScalingDialog(JDialog owner, Mode mode) {
        super(owner);
        setMode(mode);
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(411, 168);
        this.setResizable(false);
        this.setTitle("Linear scaling problem");
        this.setContentPane(getJContentPane());
        this.setModal(true);
    }

    public Boolean getResult() {
        return result;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.gridx = 1;
            gridBagConstraints22.anchor = GridBagConstraints.WEST;
            gridBagConstraints22.gridwidth = 3;
            gridBagConstraints22.gridy = 4;
            labelInterpretation = new JLabel();
            labelInterpretation.setText("x0 in [0..100] => y in [0..1]");
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.anchor = GridBagConstraints.WEST;
            gridBagConstraints12.gridy = 4;
            labelInterpretationDesc = new JLabel();
            labelInterpretationDesc.setText("Interpretation:");
            GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
            gridBagConstraints61.gridx = 3;
            gridBagConstraints61.gridy = 5;
            GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.gridx = 3;
            gridBagConstraints41.anchor = GridBagConstraints.WEST;
            gridBagConstraints41.gridy = 3;
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.gridx = 1;
            gridBagConstraints31.anchor = GridBagConstraints.WEST;
            gridBagConstraints31.gridy = 3;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.ipadx = 480;
            gridBagConstraints21.gridx = 3;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 2;
            gridBagConstraints11.gridy = 2;
            labelYTo = new JLabel();
            labelYTo.setText(" to:");
            labelYTo.setToolTipText("");
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints7.gridy = 2;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.ipadx = 480;
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.anchor = GridBagConstraints.WEST;
            gridBagConstraints6.gridy = 2;
            labelYFrom = new JLabel();
            labelYFrom.setText("y changes from:");
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 3;
            gridBagConstraints5.anchor = GridBagConstraints.WEST;
            gridBagConstraints5.gridy = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.gridy = 1;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints3.gridy = 0;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.ipadx = 480;
            gridBagConstraints3.gridx = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 2;
            gridBagConstraints2.gridy = 0;
            labelX0to = new JLabel();
            labelX0to.setText(" to:");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.ipadx = 480;
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            labelX0 = new JLabel();
            labelX0.setText("x0 changes from:");
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(labelX0, gridBagConstraints);
            jContentPane.add(getTextX0From(), gridBagConstraints1);
            jContentPane.add(labelX0to, gridBagConstraints2);
            jContentPane.add(getTextX0To(), gridBagConstraints3);
            jContentPane.add(getCheckX0FromVariable(), gridBagConstraints4);
            jContentPane.add(getCheckX0ToVariable(), gridBagConstraints5);
            jContentPane.add(labelYFrom, gridBagConstraints6);
            jContentPane.add(getTextYFrom(), gridBagConstraints7);
            jContentPane.add(labelYTo, gridBagConstraints11);
            jContentPane.add(getTextYTo(), gridBagConstraints21);
            jContentPane.add(getCheckYFromVariable(), gridBagConstraints31);
            jContentPane.add(getCheckYToVariable(), gridBagConstraints41);
            jContentPane.add(getPanelButtons(), gridBagConstraints61);
            jContentPane.add(labelInterpretationDesc, gridBagConstraints12);
            jContentPane.add(labelInterpretation, gridBagConstraints22);
        }
        return jContentPane;
    }

    private NumberFormatter getNumberFormatter() {
        NumberFormatter formatter = new NumberFormatter();
        formatter.setCommitsOnValidEdit(true);
        if (mode == Mode.Integer)
            formatter.setFormat(NumberFormat.getIntegerInstance());
        // formatter.setAllowsInvalid(false);
        return formatter;
    }

    /**
     * This method initializes textX0From
     * 
     * @return javax.swing.JTextField
     */
    private JFormattedTextField getTextX0From() {
        if (textX0From == null) {
            textX0From = new JFormattedTextField(getNumberFormatter());
            textX0From.setText("0");
            textX0From.addKeyListener(this);
        }
        return textX0From;
    }

    /**
     * This method initializes textX0To
     * 
     * @return javax.swing.JTextField
     */
    private JFormattedTextField getTextX0To() {
        if (textX0To == null) {
            textX0To = new JFormattedTextField(getNumberFormatter());
            textX0To.setText("100");
            textX0To.addKeyListener(this);
        }
        return textX0To;
    }

    /**
     * This method initializes checkX0FromVariable
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckX0FromVariable() {
        if (checkX0FromVariable == null) {
            checkX0FromVariable = new JCheckBox();
            checkX0FromVariable.setText("Variable");
            checkX0FromVariable.addChangeListener(this);
        }
        return checkX0FromVariable;
    }

    /**
     * This method initializes checkX0ToVariable
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckX0ToVariable() {
        if (checkX0ToVariable == null) {
            checkX0ToVariable = new JCheckBox();
            checkX0ToVariable.setText("Variable");
            checkX0ToVariable.addChangeListener(this);
        }
        return checkX0ToVariable;
    }

    /**
     * This method initializes textYFrom
     * 
     * @return javax.swing.JTextField
     */
    private JFormattedTextField getTextYFrom() {
        if (textYFrom == null) {
            textYFrom = new JFormattedTextField(getNumberFormatter());
            textYFrom.setText("0");
            textYFrom.addKeyListener(this);
        }
        return textYFrom;
    }

    /**
     * This method initializes textYTo
     * 
     * @return javax.swing.JTextField
     */
    private JFormattedTextField getTextYTo() {
        if (textYTo == null) {
            textYTo = new JFormattedTextField(getNumberFormatter());
            textYTo.setText("1");
            textYTo.addKeyListener(this);
        }
        return textYTo;
    }

    /**
     * This method initializes checkYFromVariable
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckYFromVariable() {
        if (checkYFromVariable == null) {
            checkYFromVariable = new JCheckBox();
            checkYFromVariable.setText("Variable");
            checkYFromVariable.addChangeListener(this);
        }
        return checkYFromVariable;
    }

    /**
     * This method initializes checkYToVariable
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckYToVariable() {
        if (checkYToVariable == null) {
            checkYToVariable = new JCheckBox();
            checkYToVariable.setText("Variable");
            checkYToVariable.addChangeListener(this);
        }
        return checkYToVariable;
    }

    /**
     * This method initializes panelButtons
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setVgap(0);
            panelButtons = new JPanel();
            panelButtons.setLayout(flowLayout);
            panelButtons.add(getButtonOK(), null);
            panelButtons.add(getButtonCancel(), null);
        }
        return panelButtons;
    }

    /**
     * This method initializes buttonOK
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonOK() {
        if (buttonOK == null) {
            buttonOK = new JButton();
            buttonOK.setText("OK");
            buttonOK.setActionCommand("OK");
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
            buttonCancel.setText("Cancel");
            buttonCancel.addActionListener(this);
        }
        return buttonCancel;
    }

    private void refreshInterpretation() {
        StringBuilder builder = new StringBuilder("x0 in [");
        int mapping = 1;

        if (checkX0FromVariable.isSelected())
            builder.append("x" + mapping++);
        else
            builder.append(textX0From.getText());

        builder.append("..");

        if (checkX0ToVariable.isSelected())
            builder.append("x" + mapping++);
        else
            builder.append(textX0To.getText());

        builder.append("] => y in [");

        if (checkYFromVariable.isSelected())
            builder.append("x" + mapping++);
        else
            builder.append(textYFrom.getText());

        builder.append("..");

        if (checkYToVariable.isSelected())
            builder.append("x" + mapping++);
        else
            builder.append(textYTo.getText());

        builder.append(']');

        labelInterpretation.setText(builder.toString());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        textX0From.setEnabled(!checkX0FromVariable.isSelected());
        textX0To.setEditable(!checkX0ToVariable.isSelected());
        textYFrom.setEnabled(!checkYFromVariable.isSelected());
        textYTo.setEnabled(!checkYToVariable.isSelected());

        refreshInterpretation();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public PointsTableModel getModel() {
        PointsTableModel model = getModelReal();
        if (mode == Mode.Real)
            return getModelReal();
        return new PointsTableModel(Integer.class, model);
    }

    private PointsTableModel getModelReal() {
        PointsTableModel model = new PointsTableModel(Double.class);
        double x0From = Double.parseDouble(textX0From.getText());
        double x0To = Double.parseDouble(textX0To.getText());
        double yFrom = Double.parseDouble(textYFrom.getText());
        double yTo = Double.parseDouble(textYTo.getText());
        double[][] matrix = new double[6 * POINTS_PER_VARIABLE][6];
        int idx = 0;

        if (checkX0FromVariable.isSelected()) {
            model.addX();
            x0From = Double.NaN;
        }
        if (checkX0ToVariable.isSelected()) {
            model.addX();
            x0To = Double.NaN;
        }
        if (checkYFromVariable.isSelected()) {
            model.addX();
            yFrom = Double.NaN;
        }
        if (checkYToVariable.isSelected()) {
            model.addX();
            yTo = Double.NaN;
        }

        // y
        for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
            matrix[idx][1] = random(x0From);
            matrix[idx][2] = random(x0To);
            matrix[idx][0] = random(matrix[idx][1], matrix[idx][2]);
            matrix[idx][3] = random(yFrom);
            matrix[idx][4] = random(yTo);
            matrix[idx][5] = (matrix[idx][0] - matrix[idx][1]) / (matrix[idx][2] - matrix[idx][1])
                    * (matrix[idx][4] - matrix[idx][3]) + matrix[idx][3];
            ++idx;
            model.addRow();
        }
        // x0
        for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
            matrix[idx][1] = random(x0From);
            matrix[idx][2] = random(x0To);
            matrix[idx][3] = random(yFrom);
            matrix[idx][4] = random(yTo);
            matrix[idx][5] = random(matrix[idx][3], matrix[idx][4]);
            matrix[idx][0] = ((matrix[idx][5] - matrix[idx][3]) * (matrix[idx][2] - matrix[idx][1]))
                    / (matrix[idx][4] - matrix[idx][3]) + matrix[idx][1];
            ++idx;
            model.addRow();
        }
        // x1
        if (Double.isNaN(x0From)) {
            for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
                matrix[idx][2] = random(x0To);
                matrix[idx][0] = random(x0From, matrix[idx][2]);
                matrix[idx][3] = random(yFrom);
                matrix[idx][4] = random(yTo);
                matrix[idx][5] = random(matrix[idx][3], matrix[idx][4]);
                matrix[idx][1] = (-matrix[idx][5] * matrix[idx][2] + matrix[idx][3] * matrix[idx][2] + matrix[idx][0]
                        * matrix[idx][4] - matrix[idx][0] * matrix[idx][3])
                        / (-matrix[idx][5] + matrix[idx][3] + matrix[idx][4] - matrix[idx][3]);
                ++idx;
                model.addRow();
            }
        }
        // x2
        if (Double.isNaN(x0To)) {
            for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
                matrix[idx][1] = random(x0From);
                matrix[idx][0] = random(matrix[idx][1], x0To);
                matrix[idx][3] = random(yFrom);
                matrix[idx][4] = random(yTo);
                matrix[idx][5] = random(matrix[idx][3], matrix[idx][4]);
                matrix[idx][2] = (matrix[idx][0] - matrix[idx][1]) / (matrix[idx][5] - matrix[idx][3])
                        * (matrix[idx][4] - matrix[idx][3]) + matrix[idx][1];
                ++idx;
                model.addRow();
            }
        }
        // x3
        if (Double.isNaN(yFrom)) {
            for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
                matrix[idx][1] = random(x0From);
                matrix[idx][2] = random(x0To);
                matrix[idx][0] = random(matrix[idx][1], matrix[idx][2]);
                matrix[idx][4] = random(yTo);
                matrix[idx][5] = random(yFrom, matrix[idx][4]);
                matrix[idx][3] = (matrix[idx][5] - (matrix[idx][0] - matrix[idx][1])
                        / (matrix[idx][2] - matrix[idx][1]) * matrix[idx][4])
                        / (1 - (matrix[idx][0] - matrix[idx][1]) / (matrix[idx][2] - matrix[idx][1]));
                ++idx;
                model.addRow();
            }
        }
        // x4
        if (Double.isNaN(yTo)) {
            for (int i = 0; i < POINTS_PER_VARIABLE; ++i) {
                matrix[idx][1] = random(x0From);
                matrix[idx][2] = random(x0To);
                matrix[idx][0] = random(matrix[idx][1], matrix[idx][2]);
                matrix[idx][3] = random(yFrom);
                matrix[idx][5] = random(matrix[idx][3], yTo);
                matrix[idx][4] = ((matrix[idx][5] + matrix[idx][3]
                        * ((matrix[idx][0] - matrix[idx][1]) / (matrix[idx][2] - matrix[idx][1]) - 1)) * (matrix[idx][2] - matrix[idx][1]))
                        / (matrix[idx][0] - matrix[idx][1]);
                ++idx;
                model.addRow();
            }
        }

        // verification
        /*
         * for (int i = 0; i < idx; ++i) { if (Math.abs(matrix[i][5] -
         * ((matrix[i][0] - matrix[i][1]) / (matrix[i][2] - matrix[i][1]) *
         * (matrix[i][4] - matrix[i][3]) + matrix[i][3])) >= 0.001) { throw new
         * RuntimeException("error in row " + i + " y=" + matrix[i][5] + "  eq="
         * + ((matrix[i][0] - matrix[i][1]) / (matrix[i][2] - matrix[i][1])
         * (matrix[i][4] - matrix[i][3]) + matrix[i][3])); } }
         */

        int mapping = 1;
        if (Double.isNaN(x0From)) {
            for (int row = 0; row < idx; ++row) {
                model.setValueAt(matrix[row][1], row, mapping);
            }
            ++mapping;
        }
        if (Double.isNaN(x0To)) {
            for (int row = 0; row < idx; ++row) {
                model.setValueAt(matrix[row][2], row, mapping);
            }
            ++mapping;
        }
        if (Double.isNaN(yFrom)) {
            for (int row = 0; row < idx; ++row) {
                model.setValueAt(matrix[row][3], row, mapping);
            }
            ++mapping;
        }
        if (Double.isNaN(yTo)) {
            for (int row = 0; row < idx; ++row) {
                model.setValueAt(matrix[row][4], row, mapping);
            }
            ++mapping;
        }
        for (int row = 0; row < idx; ++row) {
            model.setValueAt(matrix[row][0], row, 0);
            model.setValueAt(matrix[row][5], row, mapping);
        }

        return model;
    }

    private double random(double min, double max) {
        double r = 0;
        assert (Double.NaN > 0.0) == false;
        if (min > max) {
            r = min;
            min = max;
            max = r;
        }
        if (!Double.isNaN(min) && !Double.isNaN(max)) {
            r = rand.nextDouble() * (max - min) + min;
        } else if (!Double.isNaN(min)) {
            r = min + rand.nextDouble() * 100.0;
        } else if (!Double.isNaN(max)) {
            r = max - rand.nextDouble() * 100.0;
        } else {
            r = rand.nextDouble() * 100.0 - 50.0;
        }

        return r;
    }

    /*
     * private int random(int min, int max) { int r = 0; if (min > max) { r =
     * min; min = max; max = r; } if (min > Integer.MIN_VALUE && max <
     * Integer.MAX_VALUE) { r = rand.nextInt(max - min) + min; } else if (min >
     * Integer.MIN_VALUE) { r = min + rand.nextInt(100); } else if (max <
     * Integer.MAX_VALUE) { r = max - rand.nextInt(100); } else { r =
     * rand.nextInt(101) - 50; } return r; }
     */

    private double random(double x) {
        if (Double.isNaN(x))
            return rand.nextDouble() * 100.0 - 50.0;
        return x;
    }

    /*
     * private int random(int x) { if (x == Integer.MAX_VALUE || x ==
     * Integer.MIN_VALUE) return rand.nextInt(101) - 50; return x; }
     */

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("OK")) {
            result = true;
            this.setVisible(false);
        } else if (arg0.getActionCommand().equals("Cancel")) {
            result = false;
            this.setVisible(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        refreshInterpretation();
    }

} // @jve:decl-index=0:visual-constraint="10,10"
