/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author dkefalas
 */
public class YUVImage {
    private int width;
    private int height;
    private short Y, U, V;
    private YUVPixel [][]pic;
    final static String magicNumber = "YUV3"; 
    
    public YUVImage(int width, int height){
        this.width = width;
        this.height = height;
        pic = new YUVPixel[height][width];
        for(YUVPixel obj[]: pic){
            for(YUVPixel pixel: obj){
                pixel.setY((short)16);
                pixel.setU((short)128);
                pixel.setV((short)128);
            }
        }
    }
    public YUVImage(YUVImage copyImg){
        this (copyImg.width, copyImg.height);
        
        for (int i =0; i<height;i++){
           System.arraycopy(copyImg.pic[i], 0, pic[i], 0, copyImg.pic[i].length);
        } 
    }
    
    public YUVImage(RGBImage RGBImg){
        this.width = RGBImg.getWidth();
        this.height = RGBImg.getHeight();
        pic = new YUVPixel[height][width];
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                pic[i][j] = new YUVPixel(RGBImg.getPixel(i, j));
            }
        } 
    }
    
    public YUVImage(java.io.File FILE) throws UnsupportedFileFormatException, FileNotFoundException{
        
        try{
            Scanner file = new Scanner(FILE);
            if (file.hasNext() == false ){
                throw new UnsupportedFileFormatException ("ERROR: file is empty.");
            }
            if (!file.next().equals(magicNumber)){
                throw new UnsupportedFileFormatException ("ERROR: input file is not"
                +" a ppm image.");
            }
            int width = file.nextInt();
            int height = file.nextInt();
            if (width <= 0 || height <= 0 ){
                throw new CorruptedFileException ("ERROR: YUV corrupted file.\n");
            }
       
            this.width = width;
            this.height = height;
            pic = new YUVPixel[height][width];
            
            for (int row = 0; row < height; row++){
                for (int col = 0; col < width; col++){
                    if (file.hasNext()){
                        pic[row][col] = new YUVPixel (file.nextShort(), file.nextShort(),
                                                 file.nextShort());
                    }
                    else{
                        throw new CorruptedFileException ("ERROR: YUV corrupted file.\n");
                    }
                }
            }
        }
        catch (CorruptedFileException ex){
            System.err.println(ex);
        }
    }
    
    public int getWidth(){
        return(width);
    }
    
    public int getHeight(){
        return(height);
    }
    
    public YUVPixel getPixel (int row, int col){
        return (pic[row][col]);
    }
    
    @Override
    public String toString(){
        StringBuffer str = new StringBuffer("");
        
        str.append( "YUV3\n" + width + " " + height + "\n");
        
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                //System.out.println ("row: " + row + " col: " +col + " height: " + super.getHeight() + "width: " + super.getWidth());
                str.append(pic[row][col].getY() + " " + pic[row][col].getU() + " " + pic[row][col].getV() + "\n");
            }
        }
        
        return(str.toString());
    }
    
    public void toFile(java.io.File file){
        
        try (FileOutputStream out = new FileOutputStream(file.getCanonicalPath())){
            byte[] str = toString().getBytes();
            out.write(str, 0, str.length);
            
        }
        catch (IOException ex){
            System.err.println(ex);
        }
    }
    
    public void equalize(){
        Histogram histogram = new Histogram (this);
        
        histogram.equalize();
        
        for (YUVPixel []row: pic){
            for (YUVPixel pixel: row){
                pixel.setY(histogram.getEqualizedLuminocity(pixel.getY()));
            }
        }
    }
}
