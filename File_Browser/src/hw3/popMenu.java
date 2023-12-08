/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author uesr1
 */
public class popMenu {
    JPopupMenu popup;

    public popMenu(Listener control) {
        popup = new JPopupMenu();
        
        JMenuItem menuItem;
        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("cut");
        popup.add(menuItem);
        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("copy");
        popup.add(menuItem);
        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("paste");
        popup.add(menuItem);
        menuItem = new JMenuItem("Rename");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("rename");
        popup.add(menuItem);
        menuItem = new JMenuItem("Delete");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("delete");
        popup.add(menuItem);
        menuItem = new JMenuItem("Properties");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("properties");
        popup.add(menuItem);
        menuItem = new JMenuItem("Add to Favourites");
        menuItem.addActionListener(control);
        menuItem.setActionCommand("addtofav");
        popup.add(menuItem);
        //Add listener to the text area so the popup menu can come up.
    }
    
    public void vis(MouseEvent e) {
        popup.show(e.getComponent(),
            e.getX(), e.getY());
    }
}
     
