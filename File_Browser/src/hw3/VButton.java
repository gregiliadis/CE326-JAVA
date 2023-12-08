/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 *
 * @author uesr1
 */
public class VButton extends JButton {
    private String path;
    private File file;
    private int clicks;
    
    public VButton(File file){
        super(file.getName() );
        this.file = file;
        this.clicks = 0;
        File icons = new File("./icons/");
        
        if( file.isDirectory() ){
            path = "folder";
        }
        else{
            path = file.getName().substring( file.getName().indexOf('.') + 1 );
            int flag = 0;
            for(File f : icons.listFiles()){
                if( f.getName().compareTo(path + ".png") == 0 ){
                    flag++;
                    break;
                }
            }
            if( flag == 0) { path = "question"; }
            
        }
        ImageIcon icon = new ImageIcon("./icons/" + path + ".png");
        super.setIcon( icon );
        super.setVerticalTextPosition(SwingConstants.BOTTOM);
        super.setHorizontalTextPosition(SwingConstants.CENTER);
        super.setBackground(Color.WHITE);
        super.setContentAreaFilled(false);
        super.setVisible(true);
        super.setOpaque(false);
        /////
        super.setPreferredSize(new Dimension(90, 110));
        /////
        if( file.isHidden() ){ 
            super.setEnabled(false);
            super.setVisible(false); 
        }
    }

    public VButton(String path){
        super( path );
        this.file = new File(path);
        this.clicks = 0;
        File icons = new File("./icons/");
        
        if( file.isDirectory() ){
            path = "folder";
        }
        else{
            path = file.getName().substring( file.getName().indexOf('.') + 1 );
            int flag = 0;
            for(File f : icons.listFiles()){
                if( f.getName().compareTo(path + ".png") == 0 ){
                    flag++;
                    break;
                }
            }
            if( flag == 0) { path = "question"; }
            
        }
        ImageIcon icon = new ImageIcon("./icons/" + path + ".png");
        super.setIcon( icon );
        super.setVerticalTextPosition(SwingConstants.CENTER);
        super.setHorizontalTextPosition(SwingConstants.RIGHT);
        super.setBackground(Color.WHITE);
        super.setContentAreaFilled(false);
        super.setVisible(true);
        super.setOpaque(false);
        
        if( file.isHidden() ){ 
            super.setEnabled(false);
            super.setVisible(false); 
        }
    }
    public String getFilePath() { return file.getAbsolutePath(); }
    
    public Path getPath() { return file.toPath(); }
    
    public String getNameOnly() { return file.getName(); }
    
    public File getFile() { return file; }
    
    public void click(){ clicks++; }
    
    public void zero() { clicks = 0; }
    
    public void selected() { clicks = -1; } 
    
    public void setDisabled() { clicks = -2; }
    
    public int getClick() { return clicks; }
}
