
package EDITOR;
/*
TEXT EDITOR CREATED USING SWING

FUNCTIONALITIES
1. CUT/COPY/PASTE
2. CONVERT TO LOWERCASE/UPPERCASE
3.FIND ALL, REPLACE ALL ETC
*/

import java.util.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.BoxLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.DefaultHighlighter;
import java.awt.Font;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author Rahul
 */

public class textEditor implements ActionListener, KeyListener {

    String text = ""; // used in char word count
    String words[] = {};
    int count = 0;
    ArrayList<String> WordsArr = new ArrayList<>();// used in REPLACE ALL
    String[] fontStyles = { "Arial", "Algerian", "Dialog", "Monospaced", "SansSerif", "Serif" };
    String[] fontSizes = { "5", "8", "10", "12", "15", "18", "20", "22", "24" };
    String cur_Font = "";
    JLabel wordcharCount;
    JComboBox fstyles;
    JComboBox fsize;
    JFrame f;
    JTextPane t1;
    JTextField findField, replaceField;
    JPanel mstPanel, menuPanel, editPanel;
    JPanel findPanel, replacePanel, btPanel;
    JPanel fsub1, fsub2, rsub1, rsub2;
    JButton findAllbtn, findNextbtn, replacebtn, replaceAllbtn;
    JButton countCharBt;
    JMenuBar mb;
    JMenu menu1, menu2, menu3, menu4;
    JMenuItem m1, m2, m3, m4, m5, m6, m7, m8, m9;
    JButton boldBt, italicBt, underlineBt, strikeBt, leftAlign, rightAlign, centerAlign;
    int p0, p1 = 0;

    textEditor() {
        f = new JFrame("TEXT EDITOR");
        mstPanel = new JPanel();
        mstPanel.setLayout(new BoxLayout(mstPanel, BoxLayout.Y_AXIS));

        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        editPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // MENU PANEL UI CODE
        mb = new JMenuBar();
        menu1 = new JMenu("FILE");
        m1 = new JMenuItem("New");
        m2 = new JMenuItem("Save");
        m4 = new JMenuItem("Open");

        m1.addActionListener(this);
        m2.addActionListener(this);
        m4.addActionListener(this);

        m3 = new JMenuItem("CUT");
        m5 = new JMenuItem("COPY");
        m6 = new JMenuItem("PASTE");
        m7 = new JMenuItem("TO UPPERCASE");
        m8 = new JMenuItem("TO LOWERCASE");

        m9 = new JMenuItem("About");
        m3.addActionListener(this);
        m5.addActionListener(this);
        m6.addActionListener(this);
        m7.addActionListener(this);
        m8.addActionListener(this);
        m9.addActionListener(this);

        menu2 = new JMenu("EDIT");
        menu3 = new JMenu("REVIEW");
        menu4 = new JMenu("HELP");

        // FILE MENU CODE
        menu1.add(m1);
        menu1.add(m2);
        menu1.add(m4);
        // FILE MENU CODE ENDS

        // EDIT MENU CODE
        menu2.add(m3);
        menu2.add(m5);
        menu2.add(m6);
        menu2.add(m7);
        menu2.add(m8);
        // EDIT MENU CODE ENDS

        menu4.add(m9);
        mb.add(menu1);
        mb.add(menu2);
        mb.add(menu3);
        mb.add(menu4);

        menuPanel.add(mb);

        Action underline_ACT = new StyledEditorKit.UnderlineAction();
        // EDIT PANEL CODE
        // MENU PANEL ENDS

        // FIND PANEL UI CODE
        findPanel = new JPanel();
        findPanel.setLayout(new BoxLayout(findPanel, BoxLayout.Y_AXIS));
        fsub1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fsub1.add(new JLabel("Find"));
        fsub2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        findField = new JTextField(65);
        fsub2.add(findField);
        findPanel.add(fsub1);
        findPanel.add(fsub2);
        // FIND PANEL UI CODE

        // REPLACE PANEL UI CODE
        replacePanel = new JPanel();
        replacePanel.setLayout(new BoxLayout(replacePanel, BoxLayout.Y_AXIS));
        rsub1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rsub1.add(new JLabel("Replace"));
        rsub2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        replaceField = new JTextField(65);
        rsub2.add(replaceField);
        replacePanel.add(rsub1);
        replacePanel.add(rsub2);
        // REPLACE PANEL UI CODE

        // BT PANEL CODE
        btPanel = new JPanel();
        findAllbtn = new JButton("Find all");
        findAllbtn.addActionListener(this);
        findNextbtn = new JButton("Find Next");
        findNextbtn.addActionListener(this);
        replacebtn = new JButton("Replace");
        replacebtn.addActionListener(this);
        replaceAllbtn = new JButton("Replace all");
        replaceAllbtn.addActionListener(this);

        countCharBt = new JButton("Count Characters(Selected Text)");
        countCharBt.addActionListener(this);
        btPanel.add(findAllbtn);
        btPanel.add(findNextbtn);
        btPanel.add(replacebtn);
        btPanel.add(replaceAllbtn);
        btPanel.add(countCharBt);
        // BT PANEL CODE

        t1 = new JTextPane();
        t1.addKeyListener(this);
        StyledEditorKit edit = new StyledEditorKit();
        t1.setEditorKit(edit);

        // FONT FEATURE PANEL CODE
        String lAln = "L";
        String rAln = "R";
        String cAln = "C";

        Font f1 = new Font("SansSerif", Font.BOLD, 12);
        Font f2 = new Font("SansSerif", Font.BOLD + Font.ITALIC, 12);
        boldBt = new JButton("B");
        boldBt.setFont(f1);
        italicBt = new JButton("I");
        italicBt.setFont(f2);
        underlineBt = new JButton("U");
        strikeBt = new JButton("S");

        leftAlign = new JButton(lAln);
        rightAlign = new JButton(rAln);
        centerAlign = new JButton(cAln);

        boldBt.addActionListener(this);
        italicBt.addActionListener(this);
        underlineBt.addActionListener(underline_ACT);
        strikeBt.addActionListener(this);
        leftAlign.addActionListener(this);
        rightAlign.addActionListener(this);
        centerAlign.addActionListener(this);

        fstyles = new JComboBox(fontStyles);
        fstyles.setSelectedIndex(1);
        fstyles.addActionListener(this);
        fsize = new JComboBox(fontSizes);
        fsize.setSelectedIndex(0);
        fsize.addActionListener(this);

        editPanel.add(boldBt);
        editPanel.add(italicBt);
        editPanel.add(underlineBt);
        editPanel.add(strikeBt);

        editPanel.add(new JLabel(" "));
        editPanel.add(leftAlign);
        editPanel.add(centerAlign);
        editPanel.add(rightAlign);

        editPanel.add(new JLabel(" "));
        editPanel.add(fstyles);
        editPanel.add(fsize);
        // EDIT PANEL CODE ENDS

        wordcharCount = new JLabel("Words: " + "    Characters: ");
        // MASTER PANEL
        mstPanel.add(menuPanel);
        mstPanel.add(editPanel);
        mstPanel.add(findPanel);
        mstPanel.add(replacePanel);
        mstPanel.add(btPanel);
        // MASTER PANEL CODE ENDS
        f.add(mstPanel, BorderLayout.NORTH);
        f.add(t1, BorderLayout.CENTER);
        f.add(wordcharCount, BorderLayout.SOUTH);
        f.setSize(700, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("New")) {
            t1.setText("");
        }
        // TASK 1. TYPING A DOCUMENT AND SAVING IT AS FILE OF CERTAIN TYPE
        else if (cmd.equals("Save")) {
            JFileChooser j = new JFileChooser();
            int saveD = j.showSaveDialog(null);
            if (saveD == JFileChooser.APPROVE_OPTION) {
                File obj = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    FileWriter writer = new FileWriter(obj, false);

                    writer.write(t1.getText());
                    writer.flush();
                    writer.close();
                } catch (Exception f) {
                    System.out.println("Jchooser error");
                }

            }
        }
        // TASK 2. LOADING THE DOCUMENT AGAIN FROM THE LOCATION WHERE IT WAS SAVED
        else if (cmd.equals("Open")) {
            JFileChooser j1 = new JFileChooser();
            int openD = j1.showOpenDialog(null);

            if (openD == JFileChooser.APPROVE_OPTION) {
                File openObj = new File(j1.getSelectedFile().getAbsolutePath());
                try {
                    Scanner fileRead = new Scanner(openObj);

                    String toSet = "";
                    while (fileRead.hasNextLine()) {
                        toSet += fileRead.nextLine() + "\n";
                    }
                    t1.setText(toSet);

                } catch (FileNotFoundException g) {
                    System.out.println("file open error");
                }

            }

        }
        // 3.(a) CUT COPY AND PASTE
        else if (cmd.equals("CUT")) {
            t1.cut();
        } else if (cmd.equals("COPY")) {
            t1.copy();
        } else if (cmd.equals("PASTE")) {
            t1.paste();

        }
        // 3.(b) CHANGE FONT SIZE, STYLE, SIZE AND CASE FOR SELECTED TEXT
        else if (e.getSource() == fstyles) {
            String selecF = fstyles.getSelectedItem().toString();

            cur_Font = selecF;

            Font ser = new Font(selecF, Font.PLAIN, t1.getFont().getSize());

            String s11 = t1.getText();
            int sStart = t1.getSelectionStart();
            int sEnd = t1.getSelectionEnd();

            if (sEnd == sStart) {
                t1.setFont(ser);
            } else {

                int siz = Integer.parseInt(fsize.getSelectedItem().toString());
                Action fontAction = new StyledEditorKit.FontFamilyAction(String
                        .valueOf(siz), fstyles.getSelectedItem().toString());
                fontAction.actionPerformed(e);
            }

        } else if (e.getSource() == fsize) {

            String selecSize = fsize.getSelectedItem().toString();

            int sizee = Integer.parseInt(selecSize);
            Font si = new Font(cur_Font, t1.getFont().getStyle(), sizee);

            t1.setFont(si);
        } else if (e.getSource() == boldBt) {
            Font si = new Font(cur_Font, Font.BOLD, t1.getFont().getSize());
            t1.setFont(si);
        } else if (e.getSource() == italicBt) {
            Font si = new Font(cur_Font, Font.ITALIC, t1.getFont().getSize());
            t1.setFont(si);
        }

        else if (e.getSource() == strikeBt) {
            DefaultStyledDocument styleDocument = new DefaultStyledDocument();
            StyleContext sc = new StyleContext();
            Style style = sc.addStyle("strikethru", null);
            if (count % 2 == 0) {
                StyleConstants.setStrikeThrough(style, true);
            } else {
                StyleConstants.setStrikeThrough(style, false);
            }

            try {

                styleDocument.insertString(0, t1.getText().toString(), style);
                t1.setStyledDocument(styleDocument);
            } catch (Exception ex) {
                System.out.println("Strikethru error");
            }
            count++;
        }

        else if (e.getSource() == rightAlign) {
            StyledDocument myDoc = t1.getStyledDocument();
            SimpleAttributeSet right = new SimpleAttributeSet();
            StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
            myDoc.setParagraphAttributes(0, myDoc.getLength(), right, false);

        } else if (e.getSource() == leftAlign) {
            StyledDocument myDoc = t1.getStyledDocument();
            SimpleAttributeSet left = new SimpleAttributeSet();
            StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
            myDoc.setParagraphAttributes(0, myDoc.getLength(), left, false);
        } else if (e.getSource() == centerAlign) {
            StyledDocument myDoc = t1.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            myDoc.setParagraphAttributes(0, myDoc.getLength(), center, false);
        } else if (cmd.equals("TO UPPERCASE")) {
            String tot = t1.getText();
            String sel = t1.getSelectedText();
            if (sel != null) {
                String conv = sel.toUpperCase();
                String fin = tot.replace(sel, conv);
                t1.setText(fin);
            }

        } else if (cmd.equals("TO LOWERCASE")) {
            String tot = t1.getText();
            String sel = t1.getSelectedText();
            if (sel != null) {
                String conv = sel.toLowerCase();
                String fin = tot.replace(sel, conv);
                t1.setText(fin);
            }

        }
        // 3.(c) FIND, REPLACE AND REPLACE ALL FOR SELECTED TEXT
        else if (cmd.equals("Find all")) {
            String s2 = t1.getText();

            String fs1 = findField.getText();

            for (int i = 0; i < s2.length(); i++) {
                if (s2.contains(fs1)) {
                    Highlighter highlighter = t1.getHighlighter();

                    HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
                    highlighter.removeAllHighlights();
                    p0 = s2.indexOf(fs1);
                    p1 = p0 + fs1.length();
                    try {

                        while (p0 >= 0) {
                            highlighter.addHighlight(p0, p1, painter);
                            p0 = s2.indexOf(fs1, p0 + fs1.length());
                            p1 = p0 + fs1.length();
                        }
                    } catch (Exception et) {
                        // System.out.println("highlight error");
                    }

                    // System.out.println("occurs");
                }
            }
        }

        else if (cmd.equals("Find Next")) {
            String s2 = t1.getText();
            String fs1 = findField.getText();// keyword
            Highlighter highlighter = t1.getHighlighter();
            HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
            highlighter.removeAllHighlights();
            try {
                p0 = s2.indexOf(fs1, p0 + fs1.length());
                p1 = p0 + fs1.length();
                highlighter.addHighlight(p0, p1, painter);
            } catch (Exception et) {
                // System.out.println("highlight error");
            }
        }

        else if (cmd.equals("Replace")) {
            String full1 = t1.getText();
            String rep = t1.getSelectedText();

            String toRep = replaceField.getText();

            int sStart = t1.getSelectionStart();
            int sEnd = t1.getSelectionEnd();

            if (sStart != sEnd) {
                String fin = full1.replace(rep, toRep);
                t1.setText(fin);
            }

        }

        else if (cmd.equals("Replace all")) {
            String fs1 = findField.getText();
            String toRep = replaceField.getText();
            String s2 = t1.getText();

            String[] ls1 = s2.split("[\\s,.-;:]");

            List<String> textWords = Arrays.asList(ls1);

            WordsArr = new ArrayList<>(textWords);
            // System.out.println(WordsArr);

            for (int i = 0; i < WordsArr.size(); i++) {
                if (WordsArr.get(i).equals(fs1)) {
                    WordsArr.set(i, toRep);
                    // System.out.println("occurs");
                }
            }
            // System.out.println(WordsArr);//working
            StringBuffer sbuf = new StringBuffer();

            for (String s : WordsArr) {
                sbuf.append(s);
                sbuf.append(" ");
            }

            String fin = sbuf.toString();
            t1.setText(fin);
        }

        // 3 (d) COUNTING AND PRINTING TOTAL WORDS AND CHARACTERS DYNAMICALLY AND
        // SELECTED TEXT
        else if (cmd.equals("Count Characters(Selected Text)")) {
            String seltext = t1.getSelectedText();
            if (seltext != null) {
                wordcharCount.setText("Words: " + words.length + "     Characters: " + text.length()
                        + "     Characters(Selected Text): " + seltext.length());

            }
        }

        // HELP MENU CODE
        else if (cmd.equals("About")) {
            JOptionPane.showMessageDialog(menuPanel, "CREATED BY: Rahul Jayaram" + "\n" +
                    "EMAIL: rj712@snu.edu.in", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new textEditor();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        text = t1.getText();
        words = text.split("\\s");

        wordcharCount.setText("Words: " + words.length + "     Characters: " + text.length());

    }
}
