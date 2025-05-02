
package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryption;


public class SecureMessagesFrame extends JFrame{

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFileItem;
    private JMenuItem  encryptMsgItem;
    private JMenuItem saveItem;
    private JMenuItem clearItem;
    private JMenuItem exitItem;
    
    private JPanel headingPnl;
    private JLabel headingLbl;
    private JPanel plainTxtPnl;
    private JPanel encryptedTxtPnl;
    private JPanel mainPnl;
    
    private JTextArea plainTxtArea;
    private JTextArea encryptedTxtArea;
    
    private JScrollPane plainScrollPane;
    private JScrollPane encryptedScrollPane;
    
    
    
    
    
    public SecureMessagesFrame() {
        
        setTitle("Secure Messages");
        setSize(150, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
     
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        
        openFileItem = new JMenuItem("Open file...");
        openFileItem.addActionListener(new OpenFileItemListener());
        
        encryptMsgItem = new JMenuItem("Encrypt message...");
        encryptMsgItem.addActionListener(new encryptMsgFileItemListener());
        
        saveItem = new JMenuItem("Save encrypted message...");
        saveItem.addActionListener(new saveEncryptedMsgItemListener());
       
        clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(new clearItemListener());
        
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new exitItemListener());
        
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        headingLbl = new JLabel("Message Encryption");
        headingLbl.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 20));
        headingLbl.setForeground(Color.BLUE);
        headingLbl.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        
        plainTxtPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plainTxtPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        encryptedTxtPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptedTxtPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        mainPnl = new JPanel(new BorderLayout());
        
        plainTxtArea = new JTextArea(10,20);
        plainTxtArea.setEditable(false);
        encryptedTxtArea = new JTextArea(10,20);        
        encryptedTxtArea.setEditable(false);
        
        plainScrollPane = new JScrollPane(plainTxtArea);
        plainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        plainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        encryptedScrollPane = new JScrollPane(encryptedTxtArea);
        encryptedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        encryptedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
        fileMenu.add(openFileItem);
        fileMenu.add(encryptMsgItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        
        headingPnl.add(headingLbl);
        plainTxtPnl.add(plainScrollPane);
        encryptedTxtPnl.add(encryptedScrollPane);
        
        mainPnl.add(headingPnl, BorderLayout.NORTH);
        mainPnl.add(plainTxtPnl, BorderLayout.WEST);
        mainPnl.add(encryptedTxtPnl, BorderLayout.EAST);
        
        setJMenuBar(menuBar);
        add(mainPnl);
        
        pack();
        setVisible(true);
       
    }
    
        private class OpenFileItemListener implements ActionListener {
               
        @Override
        public void actionPerformed(ActionEvent e) {
               
          String data = "", info;
          int optValue;
          File choosenFile;
          JFileChooser fileChooser;
          BufferedReader buffReader;
         
          fileChooser = new JFileChooser();
          optValue = fileChooser.showOpenDialog(SecureMessagesFrame.this);
          if(optValue == JFileChooser.APPROVE_OPTION){
            
              try {
                  choosenFile = fileChooser.getSelectedFile();
                  buffReader = new BufferedReader(new FileReader(choosenFile));
                  
                while((info = buffReader.readLine()) != null){
                      data = data + info + "\n";
                }
                
                 buffReader.close();
                 plainTxtArea.setText(data);
                
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
              }
                          
          }
         
            
        }
        
        
    }

        private class encryptMsgFileItemListener implements ActionListener {
               
        @Override
        public void actionPerformed(ActionEvent e) {
           
        String plainText = plainTxtArea.getText();
        
        if (plainText.isEmpty()) {
           
            JOptionPane.showMessageDialog(null, "Please enter a message to be encrypted!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MessageEncryption encrypt = new MessageEncryption();
        String encryptedText = encrypt.encryptMsg(plainText);

        encryptedTxtArea.setText(encryptedText);
    }
            
            
          }
        
        
        
 
        private class saveEncryptedMsgItemListener implements ActionListener {
               
        @Override
        public void actionPerformed(ActionEvent e) {
            
          String encryptedMsg;
          int optValue;
          File choosenFile;
          JFileChooser fileChooser;
          BufferedWriter buffWriter;
          
          fileChooser = new JFileChooser();
          optValue = fileChooser.showSaveDialog(SecureMessagesFrame.this);
          
          if(optValue == JFileChooser.APPROVE_OPTION){
              
              try {
                  
                  choosenFile = fileChooser.getSelectedFile();
                  buffWriter = new BufferedWriter(new FileWriter(choosenFile));
                  
                  encryptedMsg = encryptedTxtArea.getText();
                  buffWriter.write(encryptedMsg);
                  buffWriter.newLine();
                  buffWriter.close();
                  
              } catch (IOException ex) {
                  Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
              }
              
            }
           
           JOptionPane.showMessageDialog(null, "You have succefully written to a file!");
          }
        
        
    }
 
        private class clearItemListener implements ActionListener {
               
        @Override
        public void actionPerformed(ActionEvent e) {
            
            plainTxtArea.setText("");
            encryptedTxtArea.setText("");
          }
        
        }
 
        private class exitItemListener implements ActionListener {
               
        @Override
        public void actionPerformed(ActionEvent e) { 
            
            System.exit(0); 
          }
        
        }
  
        
        
        


}