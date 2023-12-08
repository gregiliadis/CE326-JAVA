/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.nio.file.Files;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author uesr1
 */
public class SearchThread  extends Thread {
    String currentPath, prefix, type;
    JPanel filePanel, searchPanel;
    JButton searchButton;
    VisualControl visual;
    LayoutManager oldlayout;
    VButton array[];
    int size;
    int inter;
    
    public SearchThread ( String currentPath, String prefix, String type, JButton searchButton ,
                         JPanel filePanel, JPanel searchPanel, VisualControl visual) {
        this.currentPath = currentPath;
        this.prefix = prefix.toLowerCase();
        this.type = type;
        this.filePanel = filePanel;
        this.searchPanel = searchPanel;
        this.visual = visual;
        this.searchButton = searchButton;
        this.size = 0;
        this.inter = 1;
    }
    
    public void run() {
        Search( this.currentPath, this.prefix, this.type, this.filePanel);
     
        System.out.println("Size: " + size + ";;;"+ array.length);
        visual.DisableFrame();
        this.visual = visual;
        this.filePanel.removeAll();
        oldlayout = this.filePanel.getLayout();
        this.filePanel.setLayout( new BoxLayout(filePanel, BoxLayout.Y_AXIS));
        ////TAJINOMISH
        //File SearchFiles[] = new File[array.length];
        //for(int i = 0; i < array.length; i++){
        //    SearchFiles[i] = array[i].getFile();
        //}
        //array = visual. ButtonsInOrder(SearchFiles);
        
        for(VButton b : array ) {
            filePanel.add( b );
        }
        filePanel.revalidate();
        filePanel.repaint();
        visual.EnableFrame();
        
        searchButton.setText("Search");
        //searchPanel.setVisible(false);
    }
    
    public void Search(String currentPath, String prefix, String type, JPanel panel) {
        if ( inter == 0 ){return;} 
        
        if (this.interrupted()) {
            System.out.println("STOOOOOP");
            inter = 0;
            return;
        }
        
        File currFile = new File( currentPath);
        if( !currFile.isHidden() ){
            //System.out.println(currentPath);
            if( currFile.isDirectory() && !Files.isSymbolicLink(currFile.toPath()) ){
                File fileList[] = currFile.listFiles();
                if( fileList != null) {
                    for(File f : fileList){
                        Search(f.getAbsolutePath(), prefix, type, panel);
                    }
                }
            }
           
            if( currFile.getName().toLowerCase().contains(prefix) && currFile.getName().toLowerCase().contains(type) ){
                //System.out.println(f.getAbsolutePath());
                VButton b = new VButton(currFile.getAbsolutePath());
                visual.addControlListener(b);
                b.addMouseListener(visual);
                
                if( size == 0 ){
                    array = new VButton[1];
                    array[0] = b;
                    size++;
                }
                else{
                    size++;
                    VButton NewArray[] = new VButton[size];
                    System.arraycopy(this.array, 0 , NewArray , 0, this.array.length); 
                    NewArray[ NewArray.length - 1] = b; 
                    array = NewArray;
                }
            }
        }
    }
}
