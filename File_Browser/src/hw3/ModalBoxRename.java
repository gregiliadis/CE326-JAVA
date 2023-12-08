/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author uesr1
 */
public class ModalBoxRename {
    
    public ModalBoxRename(VButton selected, JFrame mainFrame, VisualControl visualControl, Favourites favourites){
        JFrame frame = new JFrame("Rename"); 
        frame.setPreferredSize(new Dimension(320, 80));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JButton button = new JButton("  OK  ");
        button.setFont(new Font("Courier", Font.BOLD, 15));
        Border buttonBorder = BorderFactory.createRaisedBevelBorder();
        button.setBorder(buttonBorder);
            
        frame.add(new JLabel(" " + selected.getFilePath()), BorderLayout.NORTH);
            
        JPanel textFieldPanel = new JPanel(new FlowLayout());
        JTextField RenameTextField = new JTextField("", 20);    
        textFieldPanel.add(RenameTextField);        
        RenameTextField.addKeyListener( new KeyListener() {
            public void keyTyped(KeyEvent e) {        
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ) {       
                    String str = RenameTextField.getText();        
                    try{ 
                        //if( is in favourites)
                        boolean isInFav = favourites.rmFile(selected.getFilePath());
                        File re = renameFile(selected.getFile(), RenameTextField.getText() );
                        if( isInFav ){
                            favourites.addFile(re.getAbsolutePath());
                        }
                    }
                    catch( IOException ex ) { System.out.println("FILE EXISTS-NOP"); }
                    frame.dispose();
                    //refresh
                    visualControl.SwitchContext(visualControl.getCurrentPath());
                    mainFrame.setEnabled(true);
                }
            }
        });   
           
        button.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //NewName = RenameTextField.getText();
                try{ 
                    //if is in favourites
                    boolean isInFav = favourites.rmFile(selected.getFilePath());
                    File re = renameFile(selected.getFile(), RenameTextField.getText() ); 
                    if( isInFav ){
                        favourites.addFile( re.getAbsolutePath());
                    }
                }
                catch( IOException ex ) { System.out.println("FILE EXISTS-NOP"); }
                frame.dispose();
                //refresh
                visualControl.SwitchContext(visualControl.getCurrentPath());
                mainFrame.setEnabled(true);
            }
        });
 
        textFieldPanel.add(button);
        frame.add(textFieldPanel, BorderLayout.CENTER);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setEnabled(true);
                //visualControl.SwitchContext(visualControl.getCurrentPath());
            }
        });  
        
        frame.pack();
        frame.setLocationRelativeTo(null);           
        frame.setVisible(true);
           
    }
    
    private File renameFile(File toBeRenamed, String new_name) throws IOException {
        //need to be in the same path
        File fileWithNewName = new File(toBeRenamed.getParent(), new_name);
        if (fileWithNewName.exists()) {
            throw new IOException("file exists");
        }
        
        // Rename file (or directory)
        boolean success = toBeRenamed.renameTo(fileWithNewName);
        if (!success) {
        // File was not successfully renamed
        }
        
        return fileWithNewName;
    } 
    
}
