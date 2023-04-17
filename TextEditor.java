import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner  fontSizeSpinner;
    JLabel fontLabel,backgroundColorChangeLabel;
    JButton fontColorButton,backgroundColorButton,modeButton,boldTextButton;
    JComboBox fontbox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem,saveItem,exitItem;

    TextEditor(){
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.addWindowStateListener (new WindowStateListener () {
            @Override
            public void windowStateChanged (WindowEvent e) {
                // minimized
                if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED){
                    scrollPane.setPreferredSize (new Dimension (480,480));
                    //textArea.setPreferredSize (new Dimension (480,480));
                   // scrollPane.requestFocus ();
                }
                // maximized
                else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
                    //textArea.setPreferredSize (new Dimension (1450,700));
                    scrollPane.setPreferredSize (new Dimension (1450,700));
                }
            }
        });
        this.getContentPane ().setBackground (new Color (0xEDEDEE));
        this.setTitle ("Simpe Text Editor");
        this.setSize (600,600);
        this.setLayout (new FlowLayout ());
        this.setLocationRelativeTo (null);

        //text are style and code starts here ----------------------------//

        textArea = new JTextArea ();
        textArea.setLineWrap (true);
        textArea.setWrapStyleWord (true);
        textArea.setFont (new Font ("Arial Narrow",Font.PLAIN,20));
        textArea.setBackground (new Color (250, 250, 250));
        textArea.setText ("write something.......");
        textArea.setMargin (new Insets(10,10,10,10));
        textArea.getText ();

        //Scroll pane code------------------------------------------//
        scrollPane = new JScrollPane (textArea);
        scrollPane.setPreferredSize (new Dimension (480,480));
        scrollPane.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel ("Font: ");

        fontSizeSpinner = new JSpinner ();
        fontSizeSpinner.setPreferredSize (new Dimension (50,25));
        fontSizeSpinner.setValue (20);
        //code to change font size
        fontSizeSpinner.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged (ChangeEvent e) {
                int fontSize = (int)fontSizeSpinner.getValue ();
                if(fontSize <5){
                    fontSize = 5;
                    fontSizeSpinner.setValue (5);
                }

                if(fontSize >50){
                    fontSize = 50;
                    fontSizeSpinner.setValue (50);
                }
                textArea.setFont (new Font (textArea.getFont ().getFamily (),textArea.getFont ().getStyle (),fontSize));
            }
        });
        //font colour button
        fontColorButton = new JButton ("Color");
        fontColorButton.addActionListener (this);

        //text bold button----------------------------------------------
        boldTextButton = new JButton ("Bold Text");
        boldTextButton.addActionListener (this);

        //font box
        //this will take all fonts in java store it in fonts array
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment ().getAvailableFontFamilyNames ();

        fontbox = new JComboBox (fonts);
        fontbox.addActionListener (this);
        fontbox.setSelectedItem ("Arial");

        //-----------------background color change option-----------------//
        backgroundColorChangeLabel = new JLabel ("Change Background");
        backgroundColorButton = new JButton ("White");
        backgroundColorButton.addActionListener (this);
        //code to get toggle between night mode
        modeButton = new JButton ("Night Mode");
        modeButton.addActionListener (this);
        //---------MenuBar------------------------------------//

        menuBar = new JMenuBar ();
        fileMenu = new JMenu ("File");
        openItem = new JMenuItem ("Open File ");
        saveItem = new JMenuItem ("Save ");
        exitItem = new JMenuItem ("Exit ");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        openItem.addActionListener (this);
        saveItem.addActionListener (this);
        exitItem.addActionListener (this);

        //---------MenuBar------------------------------------//

        this.setJMenuBar (menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add (fontbox);
        this.add(boldTextButton);
        this.add(backgroundColorChangeLabel);
        this.add (backgroundColorButton);
        this.add(modeButton);
        this.add(scrollPane);
        this.setVisible (true);

    }
    @Override
    public void actionPerformed (ActionEvent e) {
     if(e.getSource () == fontColorButton){
         JColorChooser colorChooser = new JColorChooser ();
         Color color = colorChooser.showDialog(null,"Choose a color",Color.black);
         textArea.setForeground (color);
     }
     if (e.getSource () == fontbox){
         textArea.setFont (new Font ((String) fontbox.getSelectedItem (),textArea.getFont ().getStyle (),textArea.getFont ().getSize ()));
     }
     //code to change background color
     if(e.getSource () == backgroundColorButton){
         JColorChooser colorChooser = new JColorChooser ();
         Color color = colorChooser.showDialog(null,"Choose a color",Color.white);
         textArea.setBackground (color);
         backgroundColorButton.setText ("Color");
     }
        //code to get toggle between night mode
     if(e.getSource () == modeButton){
         if(modeButton.getText () == "Night Mode"){
             textArea.setBackground (new Color (160, 162, 162));
             textArea.setForeground (Color.white);
             this.getContentPane ().setBackground (new Color (0x464648));
             modeButton.setText ("Normal Mode");
             fontLabel.setForeground (Color.white);
             backgroundColorChangeLabel.setForeground (Color.white);
             this.getContentPane ().setForeground (Color.white);
         }
         else{
             textArea.setBackground (Color.white);
             this.getContentPane ().setBackground (new Color (0xEDEDEE));
             textArea.setForeground (Color.black);
             fontLabel.setForeground (Color.black);
             backgroundColorChangeLabel.setForeground (Color.black);
             modeButton.setText ("Night Mode");
         }
     }
     if(e.getSource () == boldTextButton){
         if(boldTextButton.getText () == "Bold Text") {
             textArea.setFont (new Font (textArea.getFont ().getFamily (), Font.BOLD, textArea.getFont ().getSize ()));
             boldTextButton.setText ("Unbold");
         }
         else{
             textArea.setFont (new Font (textArea.getFont ().getFamily (), Font.PLAIN,textArea.getFont ().getSize ()));
             boldTextButton.setText ("Bold Text");
         }
     }
        if(e.getSource () == openItem){
            JFileChooser fileChooser = new JFileChooser ();
            fileChooser.setCurrentDirectory (new File ("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter ("Text Files",".txt");
            fileChooser.setFileFilter (filter);

            int response = fileChooser.showOpenDialog (null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File (fileChooser.getSelectedFile ().getAbsolutePath ());
                Scanner fileIn = null;
                try{
                    fileIn = new Scanner (file);
                    if(file.isFile ()){
                        while (fileIn.hasNextLine ()){
                            String line = fileIn.nextLine ()+"\n";
                            textArea.setText ("");
                            textArea.append (line);
                        }
                    }
                }
                catch (FileNotFoundException e1){
                    e1.printStackTrace ();
                }
                finally {
                    fileIn.close ();
                }
            }
        }
        if(e.getSource () == saveItem){
            JFileChooser fileChooser = new JFileChooser ();
            fileChooser.setCurrentDirectory (new File ("."));
            int response = fileChooser.showSaveDialog (null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;

                file = new File (fileChooser.getSelectedFile ().getAbsolutePath ());
                try {
                    fileOut = new PrintWriter (file);
                    fileOut.println (textArea.getText ());
                }
                catch (FileNotFoundException e1){
                    e1.printStackTrace ();
                }
                finally {
                    fileOut.close ();
                }
            }
        }
        if(e.getSource () == exitItem){
         System.exit (0);
        }
    }
 public static void main (String[] args) {
        new TextEditor ();
    }
}
