/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

//import javax.swing.filechooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author uesr1
 */
public class window extends javax.swing.JFrame {

    /**
     * Creates new form window
     */
    public window() {
        
        initComponents();
      
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        favPanel.setBackground(Color.LIGHT_GRAY); 
        filePanel.setBackground(Color.WHITE);
        
        NewFile.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new window().setVisible(true);
                    }
                }); 
            }
        });
        
        ExitFile.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose(); 
            }
        });
        
        
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ////WRAP LAYOUT////////
        //filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ///////////////////////
        filePanel.setLayout(new WrapLayout(FlowLayout.LEFT));
        ///
        filePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        breadcrumpPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        breadcrumpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Listener listener = new Listener( CutEdit, CopyEdit, PasteEdit,DeleteEdit,
                                          RenameEdit, PropEdit, AddToFavEdit, searchButton , 
                                         searchTextFiled, filePanel, searchPanel, EditMenu, this);
        VisualControl visualControl  = new VisualControl( /*FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()*/"D:\\User\\Desktop", filePanel, 
                                        breadcrumpPanel, favPanel,listener, this);
        /*System.getProperty("user.home")*/
        listener.setVisualControl(visualControl);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closed!!!!!!!!!!!");
                visualControl.writeFavourites();
            }
        });
          
        SearchMenu.addMenuListener( new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if( !searchPanel.isVisible() ){
                    searchPanel.setVisible(true);
                }
                else{ 
                    searchPanel.setVisible(false);
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {     
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
            
        });
        
        searchPanel.setVisible(false);
         EditMenu.setEnabled(false);
        searchButton.addActionListener(listener);
        searchButton.setActionCommand("search");
        searchButton.setEnabled(true);
        
        AddToFavEdit.addActionListener(listener);
        AddToFavEdit.setActionCommand("addtofav");
        AddToFavEdit.setEnabled(false);
        
        CopyEdit.addActionListener(listener);
        CopyEdit.setActionCommand("copy");
        CopyEdit.setEnabled(false);
        
        CutEdit.addActionListener(listener);
        CutEdit.setActionCommand("cut");
        CutEdit.setEnabled(false);
        
        DeleteEdit.addActionListener(listener);
        DeleteEdit.setActionCommand("delete");
        DeleteEdit.setEnabled(false);
        
        PasteEdit.addActionListener(listener);
        PasteEdit.setActionCommand("paste");
        PasteEdit.setEnabled(false);
        
        RenameEdit.addActionListener(listener);
        RenameEdit.setActionCommand("rename");
        RenameEdit.setEnabled(false);
        
        PropEdit.addActionListener(listener);
        PropEdit.setActionCommand("properties");
        PropEdit.setEnabled(false);
         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        favouritesPanel = new javax.swing.JPanel();
        breadcrumpPanel = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        searchTextFiled = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        filePanel = new javax.swing.JPanel();
        favouritesPAnel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        favPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewFile = new javax.swing.JMenuItem();
        ExitFile = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        CutEdit = new javax.swing.JMenuItem();
        CopyEdit = new javax.swing.JMenuItem();
        PasteEdit = new javax.swing.JMenuItem();
        RenameEdit = new javax.swing.JMenuItem();
        DeleteEdit = new javax.swing.JMenuItem();
        MoreEditMenu = new javax.swing.JMenu();
        AddToFavEdit = new javax.swing.JMenuItem();
        PropEdit = new javax.swing.JMenuItem();
        SearchMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        favouritesPanel.setLayout(new java.awt.GridLayout(1, 0));

        searchButton.setText("Search");

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextFiled)
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchTextFiled)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(filePanel);

        favouritesPAnel.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        FileMenu.setText("File");

        NewFile.setText("New Window");
        FileMenu.add(NewFile);

        ExitFile.setText("Exit");
        FileMenu.add(ExitFile);

        jMenuBar1.add(FileMenu);

        EditMenu.setText("Edit");

        CutEdit.setText("Cut");
        EditMenu.add(CutEdit);

        CopyEdit.setText("Copy");
        EditMenu.add(CopyEdit);

        PasteEdit.setText("Paste");
        EditMenu.add(PasteEdit);

        RenameEdit.setText("Rename");
        EditMenu.add(RenameEdit);

        DeleteEdit.setText("Delete");
        EditMenu.add(DeleteEdit);

        MoreEditMenu.setText("More");

        AddToFavEdit.setText("Add to Favourites");
        MoreEditMenu.add(AddToFavEdit);

        PropEdit.setText("Properties");
        MoreEditMenu.add(PropEdit);

        EditMenu.add(MoreEditMenu);

        jMenuBar1.add(EditMenu);

        SearchMenu.setText("Search");
        jMenuBar1.add(SearchMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(favouritesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(favouritesPAnel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(favPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                    .addComponent(breadcrumpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 426, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 427, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 426, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 427, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(favouritesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(favPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(breadcrumpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
                            .addComponent(favouritesPAnel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 156, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 156, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 156, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 156, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new window().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddToFavEdit;
    private javax.swing.JMenuItem CopyEdit;
    private javax.swing.JMenuItem CutEdit;
    private javax.swing.JMenuItem DeleteEdit;
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenuItem ExitFile;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu MoreEditMenu;
    private javax.swing.JMenuItem NewFile;
    private javax.swing.JMenuItem PasteEdit;
    private javax.swing.JMenuItem PropEdit;
    private javax.swing.JMenuItem RenameEdit;
    private javax.swing.JMenu SearchMenu;
    private javax.swing.JPanel breadcrumpPanel;
    private javax.swing.JPanel favPanel;
    private javax.swing.JPanel favouritesPAnel;
    private javax.swing.JPanel favouritesPanel;
    private javax.swing.JPanel filePanel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextFiled;
    // End of variables declaration//GEN-END:variables
}
