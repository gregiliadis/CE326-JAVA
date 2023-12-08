/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.io.File;
import javax.swing.JButton;

/**
 *
 * @author uesr1
 */
public class BButton extends JButton {
    String filepath;
    
    public BButton(String name, String filepath){
        super(name);
        this.filepath = filepath;
        super.setContentAreaFilled(true);
        super.setVisible(true);
    }
    
    public File getFile () {
        File file = new File(this.filepath);
        return file;
    }
    public String getFilePath () {
        return this.filepath;
    }
}
