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
public class RGBPixel {
    private int pixel;
    
    public RGBPixel (short red, short green, short blue){
        pixel = 0x0000;
        pixel = pixel | red;
        pixel = pixel << 8 | green;
        pixel = pixel << 8 | blue;
    }
    
    public RGBPixel (RGBPixel pixel){
        this.pixel = pixel.pixel;
    }
    public RGBPixel(){
        pixel = 0;
    }
    public short getRed(){
        Integer temp;
        temp = ((0x00ff0000 & pixel) >>> 16 );
        
        return (temp.shortValue());
    }
    
    public short getGreen(){
        Integer temp;
        temp = ((0x0000ff00 & pixel) >>> 8 );
        
        return (temp.shortValue());
    }
    
    public short getBlue(){
        Integer temp;
        temp = (0x000000ff & pixel);
        
        return (temp.shortValue());
    }
    
    public void setRed (short red){
        int intRed = red;
        
        pixel = (pixel & 0xff00ffff) | (intRed << 16);
    }
    
    public void setGreen (short green){
        int intGreen = green;
        
        pixel = (pixel & 0xffff00ff) | (intGreen << 8);
    }
    
    public void setBlue (short blue){
        int intBlue = blue;
        
        pixel = (pixel & 0xffffff00) | (intBlue);
    }
    
    public int getRGB (){
        return (pixel);
    }
    
    public void setRGB (int value){
        pixel = value;
    }
    
    public final void setRGB (short red, short green, short blue){
        setRed (red);
        setGreen (green);
        setBlue (blue);
    }
    
    @Override
    public String toString(){
        return (getRed() + " " + getGreen() + " " + getBlue());
    }    
}
