/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author uesr1
 */
public class popDelete {
    JPopupMenu popup;
    VButton item;
    JPanel panel;
    Favourites favourites;
    
    public popDelete(JPanel panel, Favourites favourites ) {
        this.favourites = favourites;
        popup = new JPopupMenu();
        
        JMenuItem menuItem = new JMenuItem("Delete");
        menuItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if( item != null ) {
                   favourites.rmFile(item.getFilePath());
               }
            }
            
        });
        popup.add(menuItem);
        this.item = null;
        this.panel = panel;
    }
    
    public void vis(MouseEvent e) {
        popup.show(e.getComponent(),
            e.getX(), e.getY());
    }
    
    public void setButton(VButton b){
        item = b; 
    }
}
