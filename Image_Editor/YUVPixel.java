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
public class YUVPixel {
    private int pixel;
    
    public YUVPixel(short Y, short U, short V){
        pixel = 0;
        pixel = pixel | Y;
        pixel = pixel << 8 | U;
        pixel = pixel << 8 | V;
    }
    
    public YUVPixel(YUVPixel pixel){
        this.pixel = pixel.pixel;
    }
    
    public YUVPixel(RGBPixel pixel){
        this.pixel = 0;
        this.pixel = this.pixel | (((  66 * pixel.getRed() + 129 * pixel.getGreen() +  
                25 * pixel.getBlue() + 128) >> 8) +  16);
        this.pixel = this.pixel << 8 | ((( -38 * pixel.getRed() - 74 * pixel.getGreen() +  
               112 * pixel.getBlue() + 128) >> 8) +  128);
        this.pixel = this.pixel << 8 | ((( 112 * pixel.getRed() - 94 * pixel.getGreen() -  
               18 * pixel.getBlue() + 128) >> 8) +  128);
    }
    public short getY(){
        Integer temp;
        temp = ((0x00ff0000 & pixel) >>> 16 );
        
        return (temp.shortValue());
    }
    
    public short getU(){
        Integer temp;
        temp = ((0x0000ff00 & pixel) >>> 8 );
        
        return (temp.shortValue());
    }
    
    public short getV(){
        Integer temp;
        temp = (0x000000ff & pixel);
        
        return (temp.shortValue());
    }
    
    public void setY (short Y){
        int intY = Y;
        
        pixel = (pixel & 0xff00ffff) | (intY << 16);
    }
    
    public void setU (short U){
        int intU = U;
        
        pixel = (pixel & 0xffff00ff) | (intU << 8);
    }
    
    public void setV (short V){
        int intV = V;
        
        pixel = (pixel & 0xffffff00) | (intV);
    }
    
    
}
