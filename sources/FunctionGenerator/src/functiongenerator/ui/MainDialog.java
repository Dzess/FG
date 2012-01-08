package functiongenerator.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.text.NumberFormatter;

import functiongenerator.loaders.CSVLoader;
import functiongenerator.loaders.ILoader;

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
	private ILoader loader = new CSVLoader();

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
	 */
	public MainDialog(Frame owner) {
		super(owner, ModalityType.TOOLKIT_MODAL);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(620, 600);
		this.setMinimumSize(new Dimension(620, 500));
		this.setResizable(true);
		this.setModal(true);
		this.setName("MainDialog");
		this.setTitle("Function Generator");
		this.setContentPane(getJContentPane());
		this.getGroupProblemType();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(),
					BoxLayout.Y_AXIS));
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
			panelConstraints.setLayout(new BoxLayout(getPanelConstraints(),
					BoxLayout.Y_AXIS));
			panelConstraints.setBorder(BorderFactory.createTitledBorder(null,
					"Constraints", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
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
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints22.gridy = 4;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.gridx = 1;
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.ipadx = 480;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridy = 4;
			labelMaxDepth = new JLabel();
			labelMaxDepth.setText("Max depth of tree:");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints21.gridy = 3;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.ipady = 0;
			gridBagConstraints21.ipadx = 480;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.ipadx = 480;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.weightx = 1.0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.ipady = 4;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipady = 4;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 0;
			labelGenerations = new JLabel();
			labelGenerations.setText("Number of generations:");
			labelGenerations.setHorizontalAlignment(SwingConstants.LEFT);
			labelGenerations.setHorizontalTextPosition(SwingConstants.LEFT);
			labelGenerations.setName("labelGenerations");
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
			panelSettings.setBorder(BorderFactory.createTitledBorder(null,
					"Basic settings", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			panelSettings.add(labelPopulationSize, gridBagConstraints2);
			panelSettings.add(getTextPopulationSize(), gridBagConstraints11);
			panelSettings.add(labelGenerations, gridBagConstraints4);
			panelSettings.add(getTextGenerations(), gridBagConstraints21);
			panelSettings.add(labelMaxDepth, gridBagConstraints12);
			panelSettings.add(getTextMaxDepth(), gridBagConstraints22);
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			panelOperations = new JPanel();
			panelOperations.setLayout(new GridBagLayout());
			panelOperations.setBorder(BorderFactory.createTitledBorder(null,
					"Available functions", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			panelOperations
					.add(getJScrollPaneOperations(), gridBagConstraints1);
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
			buttonCancel.setText("Cancel");
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
			tablePoints
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tablePoints.setModel(new PointsTableModel(Double.class));
			tablePoints.putClientProperty("terminateEditOnFocusLost", true);

			tablePoints.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent k) {
				}

				@Override
				public void keyReleased(KeyEvent k) {
				}

				@Override
				public void keyPressed(KeyEvent k) {

					if (k.getKeyCode() == 8) {
						// FIXME: this is workaround about the problem
						// backspace has known bug for swing,
						// this is bugfix for now
						System.out.println("Consuming the : " + k.getKeyCode());
						k.consume();
					}
				}
			});
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
			tableOperations
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tableOperations
					.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			tableOperations.setModel(OperationsTableModel.getReal());

			setTableOperationsColumnWidth();
		}
		return tableOperations;
	}

	private void setTableOperationsColumnWidth() {
		TableColumnModel cModel = tableOperations.getColumnModel();
		cModel.getColumn(0).setPreferredWidth(15);
		cModel.getColumn(1).setPreferredWidth(90);
		cModel.getColumn(2).setPreferredWidth(180);
		cModel.getColumn(3).setPreferredWidth(180);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		String cmd = action.getActionCommand();

		if (cmd.equals("updateType")) {
			PointsTableModel model = (PointsTableModel) tablePoints.getModel();
			if (model.getColumnClass(0).equals(Integer.class)
					&& radioDouble.isSelected()) {
				tablePoints.setModel(new PointsTableModel(Double.class, model));
				tableOperations.setModel(OperationsTableModel.getReal());
			} else if (model.getColumnClass(0).equals(Double.class)
					&& radioInteger.isSelected()) {
				tablePoints
						.setModel(new PointsTableModel(Integer.class, model));
				tableOperations.setModel(OperationsTableModel.getInteger());
			}
			setTableOperationsColumnWidth();
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
				JOptionPane.showMessageDialog(this,
						"More variables is not supported!", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				model.addX();
			}
		} else if (cmd.equals("RemoveX")) {
			try {
				PointsTableModel model = (PointsTableModel) tablePoints
						.getModel();
				model.removeX();
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (cmd.equals("Linear scaling")) {
			LinearScalingDialog dlg = new LinearScalingDialog(this, getMode());
			dlg.setVisible(true);
			if (dlg.getResult()) {
				if (JOptionPane.showConfirmDialog(this,
						"Current values will be overridden. Proceed?",
						"Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					tablePoints.setModel(dlg.getModel());
				}
			}
		} else if (cmd.equals("Evolve")) {
			if (getPoints().size() == 0) {
				JOptionPane.showMessageDialog(this,
						"Please define at least 1 point.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				List<Class<?>> operations = getOperations();
				if (operations.size() == 0) {
					JOptionPane.showMessageDialog(this,
							"Please select at least 1 operation.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			result = true;
			setVisible(false);
		} else if (cmd.equals("Cancel")) {
			result = false;
			setVisible(false);
		} else if (cmd.equals("Load")) {
			try {
				JFileChooser chooser = getFileChooser();
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

					Class<?> fieldType = radioDouble.isSelected() ? Double.class
							: Integer.class;
					File file = chooser.getSelectedFile();
					PointsTableModel model = loader.loadFromFile(file,
							fieldType);
					tablePoints.setModel(model);
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (cmd.equals("Save")) {
			try {
				JFileChooser chooser = getFileChooser();
				if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

					PointsTableModel model = (PointsTableModel) tablePoints
							.getModel();
					File file = chooser.getSelectedFile();
					String path = file.getAbsolutePath();
					if (!path.toLowerCase().endsWith(".csv"))
						file = new File(path + ".csv");

					loader.saveToFile(file, model);
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JFileChooser getFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;

				return f.getAbsolutePath().toLowerCase().endsWith(".csv");
			}

			@Override
			public String getDescription() {
				return "CSV files";
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
			textPopulationSize = new JFormattedTextField(
					getPositiveIntegerFormatter());
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
			textGenerations = new JFormattedTextField(
					getPositiveIntegerFormatter());
			textGenerations.setText("50");
			textGenerations.setPreferredSize(new Dimension(32, 20));
		}
		return textGenerations;
	}

	public int getPopulationSize() {
		return Integer.parseInt(textPopulationSize.getText());
	}

	public int getGenerations() {
		return Integer.parseInt(textGenerations.getText());
	}

	public int getMaxTreeDepth() {
		return Integer.parseInt(textMaxDepth.getText());
	}

	public List<Number[]> getPoints() {
		PointsTableModel model = (PointsTableModel) tablePoints.getModel();
		return model.getRows();
	}

	public List<Class<?>> getOperations() throws ClassNotFoundException {
		List<Class<?>> operations = new ArrayList<Class<?>>();
		OperationsTableModel model = (OperationsTableModel) tableOperations
				.getModel();
		for (String operation : model.getSelectedRows()) {
			operations.add(Class.forName(operation));
		}
		return operations;
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
			textMaxDepth = new JFormattedTextField(
					getPositiveIntegerFormatter());
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
			panelProblemType.setBorder(BorderFactory.createTitledBorder(null,
					"Problem type", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
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
} // @jve:decl-index=0:visual-constraint="10,10"
