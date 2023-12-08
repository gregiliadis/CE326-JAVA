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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class ModalBoxDelete {
       Path source;
    
    public ModalBoxDelete(Path source, JFrame mainFrame, VisualControl visualControl, Favourites favourites) {
        this.source = source;
  
        JFrame frame = new JFrame("Delte"); 
        frame.setPreferredSize(new Dimension(250, 90));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JButton yes = new JButton("  YES  ");
        yes.setFont(new Font("Courier", Font.BOLD, 15));
        Border buttonBorder = BorderFactory.createRaisedBevelBorder();
        yes.setBorder(buttonBorder);
        yes.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                DeleteFolder(source);
                favourites.rmFile( source.toString() );
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
               System.out.println("NO Delete");
               frame.dispose();
               mainFrame.setEnabled(true);
            }
        });
        
        frame.add(new JLabel(" " + source.toString() ), BorderLayout.NORTH);
            
        JPanel ButtonPanel = new JPanel(new FlowLayout());
        ButtonPanel.add(yes);
        ButtonPanel.add(no);
        frame.add(ButtonPanel, BorderLayout.CENTER);
           
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setEnabled(true);
            }
        });  
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
}
