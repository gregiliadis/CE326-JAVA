/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.time.Clock.system;
import java.util.Scanner;

/**
 *
 * @author dkefalas
 */
public class PPMImage extends RGBImage {
    private static final String magicNumber = "P3";
    
    /**
     *
     * @param FILE
     * @throws java.io.FileNotFoundException
     * @throws ce326.hw2.UnsupportedFileFormatException
     */
    public PPMImage(java.io.File FILE ) throws java.io.FileNotFoundException, 
        UnsupportedFileFormatException 
    {
        super ();
        
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
            int colorDepth = file.nextInt();
            if (width <= 0 || height <= 0 || colorDepth < 0 || colorDepth >255 ){
                throw new CorruptedFileException ("ERROR: PPM corrupted file.\n");
            }
            super.initPic (width, height, colorDepth);  
            RGBPixel pixel = new RGBPixel ();
           
            for (int row = 0; row < height; row++){
                for (int col = 0; col < width; col++){
                    if (file.hasNext()){
                        pixel.setRed(file.nextShort());
                        pixel.setGreen(file.nextShort());
                        pixel.setBlue(file.nextShort());
               
                        super.setPixel (row, col, pixel); 
                    }
                    else{
                        throw new CorruptedFileException ("ERROR: PPM corrupted file.\n");
                    }
                }
            }
        }
        catch (CorruptedFileException ex){
            System.err.println(ex);
        }
    }
    
    public PPMImage (RGBImage img){
        super (img);
    }
    
    public PPMImage(YUVImage img){
        super (img);
    }
    
    @Override
    public String toString(){
        StringBuffer str = new StringBuffer("");
        
        str.append( "P3\n" + super.getWidth() + " " + super.getHeight() + "\n");
        str.append( super.getColorDepth() + "\n");
        RGBPixel pixel;
        for (int row = 0; row < super.getHeight(); row++){
            for (int col = 0; col < super.getWidth(); col++){
                //System.out.println ("row: " + row + " col: " +col + " height: " + super.getHeight() + "width: " + super.getWidth());
                pixel = super.getPixel(row, col);
                str.append(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + "\n");
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
}
