package functiongenerator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import functiongenerator.ui.printing.JavaContext;
import functiongenerator.ui.printing.JavaEditorKit;
import functiongenerator.ui.printing.Token;

public class ResultsDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton buttonCreateClass = null;
    private JButton buttonCopy = null;
    private JPanel panelButtons = null;
    private JPanel panelNaming = null;
    private JLabel labelClassName = null;
    private JEditorPane editor = null;
    private String template;
    private JFormattedTextField textClassName = null;
    private JScrollPane paneEditor = null;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
        editor.setText(getClassCode());
    }

    public String getClassCode() {
        String _class = getTextClassName().getText();
        String _package = "functiongenerator";

        int periodIdx = _class.lastIndexOf('.');
        if (periodIdx > 0) {
            _package = _class.substring(0, periodIdx);
            _class = _class.substring(periodIdx + 1);
        }

        String code = template.replaceAll("\\/\\*package\\*\\/", _package);
        code = code.replaceAll("\\/\\*className\\*\\/", _class);

        return code;
    }

    /**
     * @param owner
     */
    public ResultsDialog(Frame owner) {
        super(owner, ModalityType.TOOLKIT_MODAL);
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(564, 387);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Results");
        this.setContentPane(getJContentPane());
        this.setModal(true);
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
            GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(1);
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getPanelNaming(), BorderLayout.NORTH);
            jContentPane.add(getPanelButtons(), BorderLayout.SOUTH);
            jContentPane.add(getPaneEditor(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes buttonCreateClass
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonCreateClass() {
        if (buttonCreateClass == null) {
            buttonCreateClass = new JButton();
            buttonCreateClass.setText("Create class");
            buttonCreateClass.setVisible(false);
        }
        return buttonCreateClass;
    }

    /**
     * This method initializes buttonCopy
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonCopy() {
        if (buttonCopy == null) {
            buttonCopy = new JButton();
            buttonCopy.setText("Copy to clipboard");
            buttonCopy.setActionCommand("copyToClipboard");
            buttonCopy.addActionListener(this);
        }
        return buttonCopy;
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
            panelButtons.setPreferredSize(new Dimension(253, 35));
            panelButtons.add(getButtonCreateClass(), null);
            panelButtons.add(getButtonCopy(), null);
        }
        return panelButtons;
    }

    /**
     * This method initializes panelNaming
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelNaming() {
        if (panelNaming == null) {
            labelClassName = new JLabel();
            labelClassName.setText("Class name:");
            panelNaming = new JPanel();
            panelNaming.setLayout(new BoxLayout(getPanelNaming(), BoxLayout.X_AXIS));
            panelNaming.setPreferredSize(new Dimension(563, 20));
            panelNaming.add(labelClassName, null);
            panelNaming.add(getTextClassName(), null);
        }
        return panelNaming;
    }

    /**
     * This method initializes editor
     * 
     * @return javax.swing.JEditorPane
     */
    private JEditorPane getEditor() {
        if (editor == null) {
            JavaEditorKit kit = new JavaEditorKit();

            JavaContext styles = kit.getStylePreferences();
            Style s;
            s = styles.getStyleForScanValue(Token.COMMENT.getScanValue());
            StyleConstants.setForeground(s, new Color(102, 153, 153));
            s = styles.getStyleForScanValue(Token.STRINGVAL.getScanValue());
            StyleConstants.setForeground(s, new Color(102, 153, 102));
            Color keyword = new Color(102, 102, 255);
            for (int code = 70; code <= 130; code++) {
                s = styles.getStyleForScanValue(code);
                if (s != null) {
                    StyleConstants.setForeground(s, keyword);
                }
            }

            editor = new JEditorPane();
            editor.setEditable(false);
            editor.setPreferredSize(new Dimension(563, 350));
            editor.setFont(new Font("Courier New", Font.PLAIN, 12));
            editor.setEditorKitForContentType("text/java", kit);
            editor.setContentType("text/java");
        }
        return editor;
    }

    /**
     * This method initializes textClassName
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTextClassName() {
        if (textClassName == null) {
            RegexFormatter formatter = new RegexFormatter("^([A-Za-z_][A-Za-z_0-9]*\\.)*[A-Za-z_][A-Za-z_0-9]*$");
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(false);
            textClassName = new JFormattedTextField(formatter);
            textClassName.setText("functiongenerator.Function");
            textClassName.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent arg0) {
                }

                @Override
                public void keyReleased(KeyEvent arg0) {
                    editor.setText(getClassCode());
                }

                @Override
                public void keyTyped(KeyEvent arg0) {

                }
            });
        }
        return textClassName;
    }

    /**
     * This method initializes paneEditor
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getPaneEditor() {
        if (paneEditor == null) {
            paneEditor = new JScrollPane();
            paneEditor.setViewportView(getEditor());
        }
        return paneEditor;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("copyToClipboard")) {
            String code = getClassCode();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(code), null);
        }
    }

}
