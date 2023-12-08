/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author uesr1
 */
public class Listener implements ActionListener, MouseListener{
    private String currentPath;
    private String action;
    private VButton selected, actSelected;
    private JMenuItem CutEdit, CopyEdit, PasteEdit, DeleteEdit, RenameEdit, PropEdit, AddToFavEdit;
    private Favourites favourites;
    private JButton search;
    private JTextField searchTextField;
    private VisualControl visualControl;
    private JPanel searchPanel, filePanel;
    private JMenu EditMenu;
    private JFrame frame;
    private SearchThread thread;
    
    public Listener(JMenuItem CutEdit, JMenuItem CopyEdit, JMenuItem PasteEdit, 
        JMenuItem DeleteEdit, JMenuItem RenameEdit, JMenuItem PropEdit, JMenuItem AddToFavEdit, JButton search, 
        JTextField searchTextField, JPanel filePanel, JPanel searchPanel, JMenu EditMenu, JFrame frame){
        action = null;
        selected = null;
        actSelected = null;
        currentPath = null;
        this.visualControl = null;
        this.favourites = null;
        this.CutEdit = CutEdit;
        this.CopyEdit = CopyEdit;
        this.PasteEdit = PasteEdit;
        this.DeleteEdit = DeleteEdit;
        this.RenameEdit = RenameEdit;
        this.PropEdit = PropEdit;
        this.AddToFavEdit = AddToFavEdit;
        this.search = search;
        this.searchTextField = searchTextField;
        this.searchPanel = searchPanel;
        this.filePanel = filePanel;
        this.EditMenu = EditMenu;
        this.frame = frame;
    }
    
    public void setVisualControl(VisualControl visualControl){
        this.visualControl = visualControl;
    }
    
    public void setFavourites( Favourites favourites){
        this.favourites = favourites;
    }
    
    public void DisableMenu() {
        EditMenu.setEnabled(false);
        CutEdit.setEnabled(false);
        CopyEdit.setEnabled(false);
        DeleteEdit.setEnabled(false);
        RenameEdit.setEnabled(false);
        PropEdit.setEnabled(false);
        AddToFavEdit.setEnabled(false); 
        //selected = null;
    }
    
    public void setCurrentPath(String path ){
        this.currentPath = path;
    }
    
    public String getActFilePath () { 
        if( actSelected != null) {
            return actSelected.getFilePath();
        }
        else{ return null; }
    
    }
    
    public String getAction() { 
        if(action != null ) {return action; }
        else{ return"nop"; }
    }
    
    public void setActSelected(VButton button) {
        actSelected = button;
    }  
    
    public void CopyFolder(Path source, Path dest) {
        File destFile = new File(dest.toString());
        if (destFile.exists()) {
            frame.setEnabled(false);
            ModalBoxOverWrite ovewrite = new ModalBoxOverWrite(source, dest, frame, visualControl);
            System.out.println("OverWrite");
        }
        else{
            /******************COPY IN THE SAME FOLDER***************/
            //Copy your self into you!!!!!!!!!
            File temp = new File(source.toString());
            if( temp.isDirectory() ){
                try { 
                    Files.copy(source, dest); 
                }
                catch(IOException ex) { 
                    System.out.println("IOException occured - UNABLE TO COPY FILE"); 
                }
                File list[] = temp.listFiles();
                if( list != null ){
                    System.out.println("############################################");
                    for(File f : list ){
                           
                        String tempDest = dest.toString() + "/" + f.getName();
                        Path DestPath = Paths.get(tempDest);
                        System.out.println(DestPath.toString());
                        CopyFolder(f.toPath(), DestPath );
                    }
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
    
    public void DeleteFolder(Path source) {
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
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if("copy".equals(e.getActionCommand()) ){
            this.action = e.getActionCommand();
            if( actSelected != null ) { 
                actSelected.zero();
                actSelected.setContentAreaFilled(false);
                actSelected.setEnabled(true);
            }
            actSelected = selected;
            
            actSelected.selected();
            
            //Indicate selected
            actSelected.setContentAreaFilled(true);
            
            DisableMenu();
            PasteEdit.setEnabled(true);
        }
        
        if("cut".equals(e.getActionCommand()) ){
            this.action = e.getActionCommand();
            if( actSelected != null ) { 
                actSelected.zero();
                actSelected.setContentAreaFilled(false);
                actSelected.setEnabled(true);
            }
           
            actSelected = selected;
            //Disable icon
            actSelected.setDisabled();
            actSelected.setEnabled(false);
            
            DisableMenu();
            PasteEdit.setEnabled(true);
        }
        if("paste".equals(e.getActionCommand()) ){
           
            Path SrcPath = Paths.get( actSelected.getFilePath() );
            String destStr = currentPath + "/" + actSelected.getNameOnly();
            Path DestPath = Paths.get(destStr);
            System.out.println("dest: "+DestPath.toString());
            System.out.println("src: "+ SrcPath.toString());
            //Paste file in the folder of source file-->ModalBox
            if( actSelected != null ) { 
                actSelected.zero();
                actSelected.setContentAreaFilled(false);
                actSelected.setEnabled(true);
            }
            if( !SrcPath.toString().equals(DestPath.toString()) ){
                CopyFolder(SrcPath, DestPath); 
                if( this.action.equals("cut") ) {
                    DeleteFolder(SrcPath); 
                }
                this.action = null;
                PasteEdit.setEnabled(false);
                //refresh file panel
                visualControl.SwitchContext(currentPath);
            }
            else{ 
                //MODAL BOX
                System.out.println("SAME DEST AND SOURCE");
            }
        }
        if("delete".equals(e.getActionCommand()) ){
            Path SrcPath = Paths.get( selected.getFilePath() );
            frame.setEnabled(false);
            
            ModalBoxDelete delete = new ModalBoxDelete(SrcPath, frame, visualControl, favourites);
          
            DeleteEdit.setEnabled(false);
        }
        if("rename".equals(e.getActionCommand()) ){
            System.out.println(currentPath);
            frame.setEnabled(false);
            
            ModalBoxRename rename = new ModalBoxRename(selected, frame, visualControl, favourites);
   
            RenameEdit.setEnabled(false);
            selected.selected();
        }
        if("properties".equals(e.getActionCommand()) ){
            frame.setEnabled(false);
            ModalBoxProp prop = new ModalBoxProp( selected.getFile(), frame); 
        }
        
        if( "addtofav".equals(e.getActionCommand()) ){
            if( !favourites.isFav( currentPath ) ){
                favourites.addFile( currentPath );
            }
            //favourites.addFile( selected.getFilePath() );
            
        }
        if( "search".equals( e.getActionCommand()) ) {
            if( e.getSource() instanceof JButton ){
                JButton b = (JButton) e.getSource();
                if( b.getText().equals("Search") ){
                    String temp = searchTextField.getText();
                    String currPath =null;
                    b.setText("Stop");
                    if( visualControl != null) {
                        System.out.println("-------->" + temp);
                        currPath = visualControl.getCurrentPath();
                    }
                    System.out.println("-------->" + temp + "currPath: " +currPath);
                    thread = new SearchThread( currPath , temp, "", b, filePanel, searchPanel, visualControl);
                    thread.start();
                }
                else{
                    if( thread.isAlive() ){
                        thread.interrupt();
                        b.setText("Search");
                        searchPanel.setVisible(false); 
                    }
                }
            }
        }
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {
        if( SwingUtilities.isLeftMouseButton(e) ){
            VButton clicked;
            if( e.getComponent() instanceof VButton ){
                clicked = (VButton) e.getComponent();
                if( clicked.getClick() == 0 ){    
                    if( selected != null ) {
                        if( selected != actSelected) {
                            selected.setContentAreaFilled(false);
                            selected.zero();
                        }
                        selected = clicked;
                        ///
                        EditMenu.setEnabled(true);
                        CutEdit.setEnabled(true);
                        CopyEdit.setEnabled(true);
                        DeleteEdit.setEnabled(true);
                        RenameEdit.setEnabled(true);
                        PropEdit.setEnabled(true);
                        AddToFavEdit.setEnabled(true);
                    }
                    else{ 
                        selected = clicked;
                        ///
                        EditMenu.setEnabled(true);
                        CutEdit.setEnabled(true);
                        CopyEdit.setEnabled(true);
                        DeleteEdit.setEnabled(true);
                        RenameEdit.setEnabled(true);
                        PropEdit.setEnabled(true);
                        AddToFavEdit.setEnabled(true);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {  
        if( SwingUtilities.isRightMouseButton(e) ) {
            VButton clicked;
            if( e.getComponent() instanceof VButton ){
                clicked = (VButton) e.getComponent();
                if( selected != null ) {
                    if( selected != actSelected) {
                        selected.setContentAreaFilled(false);
                        selected.zero();
                    }
                }
                selected = clicked;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
