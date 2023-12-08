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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author uesr1
 */
public class ModalBoxOverWrite {
    Path source, dest;
    
    public ModalBoxOverWrite(Path source, Path dest, JFrame mainFrame, 
                             VisualControl visualControl ) {
        this.source = source;
        this.dest = dest;
  
        JFrame frame = new JFrame("Overite"); 
        frame.setPreferredSize(new Dimension(250, 90));
        
        //Overwrite must be selected
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JButton yes = new JButton("  YES  ");
        yes.setFont(new Font("Courier", Font.BOLD, 15));
        Border buttonBorder = BorderFactory.createRaisedBevelBorder();
        yes.setBorder(buttonBorder);
        yes.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                DeleteFolder(dest);
                
                CopyFolder(source, dest, mainFrame, visualControl);
                
                //refresh
                visualControl.SwitchContext(visualControl.getCurrentPath());
                mainFrame.setEnabled(true);
            }
        });
        
        JButton no = new JButton("  NO  ");
        no.setFont(new Font("Courier", Font.BOLD, 15));
        buttonBorder = BorderFactory.createRaisedBevelBorder();
        no.setBorder(buttonBorder);
        no.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.out.println("NO OVER WRITE");
               frame.dispose();
               //refresh
               visualControl.SwitchContext(visualControl.getCurrentPath());
               mainFrame.setEnabled(true); 
            }
        });
        
        frame.add(new JLabel(" " + dest.toString() ), BorderLayout.NORTH);
            
        JPanel ButtonPanel = new JPanel(new FlowLayout());
        ButtonPanel.add(yes);
        ButtonPanel.add(no);
        frame.add(ButtonPanel, BorderLayout.CENTER);
           
        frame.pack();
        frame.setLocationRelativeTo(null);           
        frame.setVisible(true);
       
    }
    
    private void DeleteFolder(Path source) {
        File temp = new File(source.toString());
        if( temp.isDirectory() ){  
            File list[] = temp.listFiles();
            for(File f : list ){
                DeleteFolder(f.toPath() );
            }
            temp.delete(); 
        }
        else{
            temp.delete();
        }
    } 
    
    private void CopyFolder(Path source, Path dest, JFrame mainFrame, VisualControl visualControl) {
        
        File destFile = new File(dest.toString());
        if (destFile.exists()) {
            ModalBoxOverWrite overwrite = new ModalBoxOverWrite(source, dest, mainFrame, visualControl);
            System.out.println("OverWrite");
        }
        else{
            File temp = new File(source.toString());
            if( temp.isDirectory() ){
                try { 
                    Files.copy(source, dest); 
                }
                catch(IOException ex) { 
                    System.out.println("IOException occured - UNABLE TO COPY FILE"); 
                }
                File list[] = temp.listFiles();
                for(File f : list ){
                    String tempDest = dest.toString() + "/" + f.getName();
                    Path DestPath = Paths.get(tempDest);
                    CopyFolder(f.toPath(), DestPath, mainFrame , visualControl);
                }
            }
            else{
                try { 
                    Files.copy(source, dest); 
                }
                catch(IOException ex) { 
                    System.out.println("IOException occured - UNABLE TO COPY FILE");
                }  
            }
        }
    }
}
