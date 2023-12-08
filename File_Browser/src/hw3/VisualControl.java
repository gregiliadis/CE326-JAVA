/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author uesr1
 */
public class VisualControl implements MouseListener{
    private String filepath;
    
    private JPanel filePanel;
    private BreadCrump breadcrump;
    private Listener control;
    private popMenu popup;
    private popDelete deletePopUp;
    private Favourites favourites;
    private JFrame frame;
    LayoutManager oldlayout;
    
    public VisualControl(String filepath, JPanel filePanel, JPanel breadcrumpPanel, JPanel favPanel ,Listener control , JFrame frame) {
        this.filepath = filepath;
        this.filePanel = filePanel;
        oldlayout = this.filePanel.getLayout();
        this.control = control;
        control.setCurrentPath(filepath);
        this.frame = frame;
        popup = new popMenu(control);
        breadcrump = new BreadCrump(filepath, breadcrumpPanel);
        favourites = new Favourites(favPanel, this);
        favourites.visual();
        //init listener for favourites
        newFavListener();
        //set favourites to contol
        control.setFavourites(favourites);
        deletePopUp = new popDelete(favPanel, favourites);
        //Set icons visible
        visual();
    }
    
    public void writeFavourites(){
        favourites.writeXml();
    }

    public void DisableFrame(){
        frame.setEnabled(false);
    }
    
    public void EnableFrame(){
        frame.setEnabled(true);
    }
   
    public String getCurrentPath(){
        return this.filepath;
    }
    
    public void visual(){
        File currentFile = new File(filepath);
        File contextfiles[];
        VButton contextButtons[];
        BButton breadcrumpLinks[];
        
        if( currentFile.isDirectory() ){
        	if( currentFile.listFiles() != null ){
            	contextButtons  = ButtonsInOrder( currentFile.listFiles() );
			}
			else{ contextButtons = null; }
        }
        
        breadcrumpLinks = breadcrump.getLinkButtons();
        for(BButton b : breadcrumpLinks){
            b.addMouseListener(this);
        }
 
        breadcrump.visual();
    }

    public void addControlListener(VButton button) {
        button.addMouseListener(control);
    }
    
    public void newFavListener() {
        VButton favouriteLinks[];
        
        favouriteLinks = favourites.getLinkButtons();
        for(VButton b : favouriteLinks){
            b.addMouseListener(favListener);
        }
    }
    MouseListener favListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if( SwingUtilities.isLeftMouseButton(e) ){
                VButton clicked;
                if( e.getComponent() instanceof VButton ){
                    clicked = (VButton) e.getComponent();  
                    if( !clicked.getFile().exists() ){
                        JDialog d = new JDialog(frame , "WARNING", true);  
                        d.add( new JLabel ( "   File does no longer exits: " + 
                                clicked.getFile().getAbsolutePath() )); 
                        //d.add( new JLabel ( "   Probably path has been changed.")); 
                        d.setLocation( e.getLocationOnScreen() );
                        d.setSize(300,100);    
                        d.setVisible(true);  
                    }
                    else {
                        System.out.println(clicked.getFilePath());
                        SwitchContext( clicked.getFilePath() );
                        control.setCurrentPath(clicked.getFilePath());
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if( SwingUtilities.isRightMouseButton( e ) ){
                VButton clicked;
                if( e.getComponent() instanceof VButton ){
                    clicked = (VButton) e.getComponent();
                    deletePopUp.setButton(clicked);
                }        
                maybeShowDelete(e);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if( SwingUtilities.isRightMouseButton( e ) ){
                maybeShowDelete(e);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    };
    
    private void maybeShowDelete (MouseEvent e) {
        if (e.isPopupTrigger()) {
            deletePopUp.vis(e);
        }
    }
    private VButton addContextButton(File file){
        VButton button = new VButton( file );
        //////////////////
        button.addMouseListener(control);
        /////////////////
        button.addMouseListener(this);
        this.filePanel.add(button);

        if( control.getAction().equals("cut") ) {
            if(button.getFilePath().equals(control.getActFilePath()) ) {
                control.setActSelected(button);
                button.setDisabled();
                button.setEnabled(false);
            }
        }
        if( control.getAction().equals("copy") ) {
            if(button.getFilePath().equals(control.getActFilePath()) ) {
                control.setActSelected(button);
                button.setContentAreaFilled(true);
            }
        }
        return button;
    }
    
     public VButton[] ButtonsInOrder(File files[]){
         VButton []array = new VButton[files.length];
         int i, j, pos;
         File key;
         
         for (i = 1; i < files.length; i++) {  
            key = files[i];  
            j = i - 1;  
  
            while ( ( j >= 0 ) && (files[j].getName().compareTo( key.getName() ) > 0) ) {  
                files[j + 1] = files[j];  
                j = j - 1;  
            }
            files[j + 1] = key;  
        }  
        
         pos = 0;
        for (File f : files) {
            if( f.isDirectory() ){
                array[pos] = addContextButton(f);
                pos++;
            }
        }
        
        for (File f : files) {
            if( !f.isDirectory() ){
                array[pos] = addContextButton(f);
                pos++;
            }
        }
        
        return array;
    }
    
    public void SwitchContext(String filepath){
         filePanel.removeAll();
         /////////WRAP LAYOUT/////////////
         filePanel.setLayout(new WrapLayout(FlowLayout.LEFT));
         ////////////////////////
         this.filepath = filepath;
         filePanel.revalidate();
         filePanel.repaint();
         breadcrump.SwitchContext(filepath);
         //breadcrump.visual();
         control.setCurrentPath(filepath);
         control.DisableMenu();
         this.visual();
         filePanel.revalidate();
         filePanel.repaint();
    }
     
     ////Mporei na mpei sthn add!!!!
    @Override
    public void mouseClicked(MouseEvent e) {
        if( SwingUtilities.isLeftMouseButton( e ) ){
            if( e.getComponent() instanceof VButton ){
                VButton b = (VButton) e.getComponent();
                if( b.getClick() == 0 ){
                    b.setContentAreaFilled(true);
                    b.click();
                }
                else{ 
                    if( b.getClick() != -2  ){
                        if( b.getClick() == -1 ){ 
                            b.click(); b.click();
                            b.setContentAreaFilled(true);
                        }
                        else{
                            b.zero();
                            //b.setOpaque(false);
                            b.setContentAreaFilled(false);
                            if( b.getFile().isDirectory() ){
                                System.out.println(b.getFilePath());
                                this.SwitchContext(b.getFilePath());
                                control.setCurrentPath(b.getFilePath());
                            }
                            else{ 
                                if (b.getFile().canExecute()){
                                    try {
                                        Runtime.getRuntime().exec(b.getFile().getAbsolutePath(), null, new File(b.getFile().getParent()));
                                    } catch (IOException ex) {
                                        try {
                                            Desktop.getDesktop().open(b.getFile());
                                        } catch (IOException ex1) {
                                            JDialog d = new JDialog(frame , "WARNING", true);  
                                            d.add( new JLabel ("  Unable to open: " + b.getFile().getName())); 
                                            d.setLocation( e.getLocationOnScreen() );
                                            d.setSize(300,100);    
                                            d.setVisible(true);  
                                        }
                                    }
                                }
                                else{
                                    try {
                                        Desktop.getDesktop().open(b.getFile());
                                    } catch (IOException ex) {
                                        JDialog d = new JDialog( frame, "WARNING", true);  
                                        d.add( new JLabel ("   Unable to open: " + b.getFile().getName())); 
                                        d.setLocation( e.getLocationOnScreen() );
                                        d.setSize(300,100);    
                                        d.setVisible(true); 
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if( e.getComponent() instanceof BButton ){
                BButton b = (BButton) e.getComponent();
                if( b.getFile().isDirectory() ){
                    System.out.println(b.getFilePath());
                    this.SwitchContext(b.getFilePath());
                    
                    control.setCurrentPath(b.getFilePath());
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if( SwingUtilities.isRightMouseButton( e ) ){
             maybeShowPopup(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if( SwingUtilities.isRightMouseButton( e ) ){
            maybeShowPopup(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.vis(e);
        }
    }

   
}
