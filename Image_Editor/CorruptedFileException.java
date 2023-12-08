/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;
/**
 *
 * @author dkefalas
 */
public class CorruptedFileException extends java.lang.Exception {
    public CorruptedFileException (){
        super ();
    }
    
    public CorruptedFileException (String msg){
        super(msg);
    }
}
