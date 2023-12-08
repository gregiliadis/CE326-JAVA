/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dkefalas
 */
public class PPMImageStacker {
    private List <PPMImage> stack;
    private PPMImage pic;
    
    public PPMImageStacker(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException{
        if (!dir.exists() ){
            System.err.print ("[ERROR] Directory " + dir.getName() +" does not exist!");
        }
        else{
            if (!dir.isDirectory()){
                System.err.print ("[ERROR] " + dir.getName() +" is not a directory!");
            }
            else{
                File[] files = dir.listFiles();
                stack = new ArrayList <> ();
                for (File obj: files){
                    //System.out.println ( obj.getCanonicalPath() );
                    stack.add(new PPMImage (obj));
                }
                pic = new PPMImage (stack.get(0));
            }
        }
    }
    
    public void stack(){
        RGBPixel pixel = new RGBPixel ();
        int red, green, blue;
        
        
        for( int i = 0 ; i < pic.getHeight(); i++){
            for(int j = 0; j < pic.getWidth(); j++){
                red = 0; green = 0; blue = 0;
                for (PPMImage obj : stack ){ 
                   red = (red + obj.getPixel(i,j).getRed());
                   green = (green + obj.getPixel(i,j).getGreen());
                   blue =  (blue + obj.getPixel(i,j).getBlue());
                }
                pixel.setRed( (short)(red/stack.size()));
                pixel.setGreen( (short)(green/ stack.size()));
                pixel.setBlue( (short)(blue/ stack.size()));
                pic.setPixel(i, j, pixel);
            }
        }
        /*System.out.println ("SHIIIT");
        for (PPMImage obj : stack.subList(1, stack.size() - 1)){
            for(int i=0; i < pic.getHeight(); i++){
                for (int j = 0; j < pic.getWidth(); j++){
                    pixel.setRed((short) ((pic.getPixel(i, j).getRed() + 
                            obj.getPixel(i, j).getRed())/2));
                    pixel.setGreen((short) ((pic.getPixel(i, j).getGreen() + 
                            obj.getPixel(i, j).getGreen())/2));
                    pixel.setBlue((short) ((pic.getPixel(i, j).getBlue() + 
                            obj.getPixel(i, j).getBlue())/2));
                    if( obj.equals(stack.get(stack.size() - 1))){
                        pixel.setRed( (short)((pixel.getRed() * 2)/ stack.size()));
                        pixel.setGreen( (short)((pixel.getGreen() * 2)/ stack.size()));
                        pixel.setBlue( (short)((pixel.getBlue() * 2)/ stack.size()));
                    }
                    pic.setPixel(i, j, pixel);
                }
            }
        }*/
    }
    
    public PPMImage getStackedImage(){
        return (pic);
    }
}

