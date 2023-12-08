/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.io.File;
import static java.lang.Thread.sleep;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author uesr1
 */
public class BreadCrump {
    String filepath;
    JPanel panel;
    BButton links[];
    JLabel labels[];
    
    public BreadCrump(String filepath, JPanel panel){
      this.filepath = filepath;    
      this.panel = panel;
      this.links = this.initLinks(filepath);
      this.labels = this.initLabels();
      this.visual();
    }
    
    public void visual(){
        for(int i = 0; i < this.links.length; i++){
           this.panel.add(links[i]);
           if( i < labels.length ) { this.panel.add(labels[i]); }
        }
        
        this.panel.setOpaque(true);
        this.panel.revalidate();
        this.panel.repaint();
    }
    private BButton[] initLinks(String filepath){
        String fileSeparator;
        //String temp[] = null;
        BButton array[];
        
        fileSeparator = System.getProperty( "file.separator" );
        ///Debug
        System.out.println("----------> " + fileSeparator + "---->" + filepath);
           
	
        int size = 0, pos = 0;
      
        pos = filepath.indexOf(fileSeparator) + fileSeparator.length();
        if( pos >= fileSeparator.length() ){
            size++;
        }
        while( pos < filepath.length() ){
            if(filepath.substring(pos).indexOf(fileSeparator) == -1){
                size++;
                break;
            }
            else{
                size++; 
                pos = pos + filepath.substring(pos).indexOf(fileSeparator) + fileSeparator.length(); 
            }
        }
        array = new BButton[size];
        System.out.println("Size: " + size);
        //Make buttons
        size = 0;
        int start = 0;
        pos = filepath.indexOf(fileSeparator) + fileSeparator.length();
        if( pos >= fileSeparator.length() ){
            System.out.println("----------> POS:" + filepath.substring(start, pos - fileSeparator.length()) + "Size: " +filepath.substring(0, pos-fileSeparator.length()) );
            if( ( pos - fileSeparator.length()) == 0 ) { 
            	array[size] = new BButton( filepath.substring(start, pos - fileSeparator.length()), "/" );
            	size++;
			}
			else{
				array[size] = new BButton( filepath.substring(start, pos - fileSeparator.length()), filepath.substring(0, pos-fileSeparator.length()) );
            	size++;
			}
        }
        while( pos < filepath.length() ){
            if(filepath.substring(pos).indexOf(fileSeparator) == -1){
                
                array[size] = new BButton( filepath.substring(pos ,filepath.length()), filepath.substring(0, filepath.length()) );
                System.out.println("----------> POS:" + filepath.substring( pos, filepath.length()) + "Size: " +filepath.substring(0, filepath.length()) );
                break;
            }
            else{
                
                start = pos; 
                pos = pos + filepath.substring(pos).indexOf(fileSeparator) + fileSeparator.length();
                System.out.println("----------> POS:" + filepath.substring(start, pos - fileSeparator.length()) + "Size: " +filepath.substring(0, pos-fileSeparator.length()) );
                array[size] = new BButton( filepath.substring(start, pos - fileSeparator.length()), filepath.substring(0, pos-fileSeparator.length()) );
                size++;
            }
        }

        System.out.println("----------> POS:" + pos + "Size: " +size);
        System.out.println("----------> " + fileSeparator + "kk"+fileSeparator.length()+"---->" + filepath+"kk"+filepath.length());
        
        return array;
    }
    
    private JLabel[] initLabels(){
        if(this.links.length > 0){
            JLabel array[] = new JLabel[this.links.length-1];
            for(int i = 0; i < this.links.length-1; i++){
                array[i] = new JLabel(">");
            }
            return array;
        }
        else{
            return null;
        }
    }
    public void SwitchContext(String filepath){
        this.panel.removeAll();
        this.filepath = filepath;    
        this.links = this.initLinks(filepath);
        this.labels = this.initLabels();
        //this.panel.revalidate();
        //this.panel.repain();
    }
    public BButton[] getLinkButtons(){
        return this.links;
    }
}
