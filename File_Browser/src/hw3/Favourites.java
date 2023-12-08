/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.io.File;
import javax.swing.JPanel;

/**
 *
 * @author uesr1
 */
public class Favourites {
    String filepaths[];
    JPanel panel;
    VButton links[];
    xmlFile xml; 
    VisualControl visual;
    //boolean newEntry;
    
    public Favourites( JPanel panel , VisualControl visual){
        String path = System.getProperty("user.home") + System.getProperty("file.separator") + ".java-file-browser";
        File xmlFolder = new File( path ); 
        if( !xmlFolder.exists() ) {  
            xmlFolder.mkdir();
        }
        xml = new xmlFile( path + System.getProperty("file.separator") + "properties.xml", null);
        this.filepaths = xml.xmlReader();
        this.links = this.initLinks(filepaths);
        this.panel = panel;
        /*if( !isFav(System.getProperty("user.home")) ){
            VButton newLinks[] = new VButton[this.links.length + 1];
            for(int i = 0; i < this.links.length; i++){
                newLinks[i+1] = new VButton( links[i].getFile() );
            }
            newLinks[0] = new VButton( new File(System.getProperty("user.home")));
            this.links = newLinks;
        }*/
        this.visual = visual;
    }
    
    public void writeXml(){
        String Paths[] = new String[links.length];
        
        for( int i = 0; i < links.length; i++){
            Paths[i] = links[i].getFilePath();
        }
        xml.SetPaths(Paths);
        xml.xmlWriter();
    }
    
    public void visual(){
        this.panel.removeAll();
        for(int i = 0; i < this.links.length; i++){
           this.panel.add(links[i]);
        }
        
        this.panel.setOpaque(true);
        this.panel.revalidate();
        this.panel.repaint();
    }
    
    private VButton[] initLinks(String filepaths[]){
        String temp = null;
        VButton array[];
        File tempFile;
        
        array = new VButton[filepaths.length];
        
        for(int i = 0 ; i < filepaths.length; i++) {
            tempFile = new File(filepaths[i]);
            array[i] = new VButton( tempFile );
        }
        
        return array;
    }
    
    public void addFile( String Filepath){
        VButton newLinks[] = new VButton[this.links.length + 1];
        for(int i = 0; i < this.links.length; i++){
            newLinks[i] = new VButton( links[i].getFile() );
        }
        newLinks[this.links.length] = new VButton( new File(Filepath));
        
        this.links = newLinks;
        this.visual();
        this.visual.newFavListener();
        
    }
    public boolean isFav( String path) {
        for(int i = 0, j = 0; i < this.links.length; i++, j++){
            if( links[i].getFilePath().equals(path) ) {
                return true;
            }
        }
        return false;
    }
    
    public boolean rmFile ( String path ) {
        if( isFav( path) ){
            VButton newLinks[] = new VButton[this.links.length -1 ];
            for(int i = 0, j = 0; i < this.links.length; i++, j++){
                if( !links[i].getFilePath().equals(path) ) {
                    newLinks[j] = links[i];
                }
                else{ j--; }
            }
            this.links = newLinks;
            this.visual();
            
            return true;
        }
        return false;
    }
    
    public VButton[] getLinkButtons(){
        return this.links;
    }
}
