/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author uesr1
 */
public class ModalBoxProp {
    JCheckBox read, write, execute;
    File file;
    
    public ModalBoxProp(File file, JFrame mainFrame){
        this.file = file;
        JFrame frame = new JFrame("Properties"); 
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create the check boxes.
        boolean ret;
        ////////////////////////
        read = new JCheckBox("Read");
        ret =  file.canRead(); 
        if( ret )  
            read.setSelected(true);
        else 
            read.setSelected(false);
        
        if( file.setReadable(ret) )
            read.setEnabled(true);
        else
            read.setEnabled(false);
        /////////////////////////
        write = new JCheckBox("Write");
        ret =  file.canWrite(); 
        if( ret )  
            write.setSelected(true);
        else 
            write.setSelected(false);
        
        if( file.setWritable(ret) )
            write.setEnabled(true);
        else
            write.setEnabled(false);
        ///////////////////////////
        execute = new JCheckBox("Execute");
        ret =  file.canExecute(); 
        if( ret )  
            execute.setSelected(true);
        else 
            execute.setSelected(false);
        
        if( file.setExecutable(ret) )
            execute.setEnabled(true);
        else
            execute.setEnabled(false);
  
        //Register a listener for the check boxes.
        read.addItemListener(itemListener);
        write.addItemListener(itemListener);
        execute.addItemListener(itemListener);
 
        //Put the check boxes in a column in a panel
        JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
        
        checkBoxPanel.add(new JLabel(" Name: " + file.getName()) );
        checkBoxPanel.add(new JLabel(" Path: " + file.getAbsolutePath()) );
        checkBoxPanel.add(new JLabel(" Size: " + findSize(file, 0) + " bytes") );
        checkBoxPanel.add(new JLabel(" Properties: ") );
        
        checkBoxPanel.add(read);
        checkBoxPanel.add(write);
        checkBoxPanel.add(execute);
    
        frame.add(checkBoxPanel, BorderLayout.CENTER);
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
 
    ItemListener itemListener = new ItemListener() {
        /* Listens to the check boxes. */
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();

            if (source == read) {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                    file.setReadable(false);
                else
                    file.setReadable(true);
            }
            
            if (source == write) {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                    file.setWritable(false);
                else
                    file.setWritable(true);
            }
           
            if (source == execute) {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                    file.setExecutable(false);
                else
                    file.setExecutable(true);
            }
        }
    };
    
    //recursive function that calculates the size of file.
    //If file is Directory returns the total size of it's contents.
    public long findSize(File file, long size){
        if( Files.isSymbolicLink( file.toPath()) ) {
            return size;
        }
        else{
            if( file.isDirectory()){
                File list[] = file.listFiles();
                if( list != null){
                    for(File f : list){
                        size = findSize(f, size);
                    }
                }
            }
            return file.length() + size;
        }
    }
 
}
