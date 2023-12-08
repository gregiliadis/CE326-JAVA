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
public class RGBImage implements Image {
    private int width;
    private int height;
    private int colorDepth;
    private RGBPixel [][]pic;
    
    public final static int MAX_COLORDEPTH = 255;
    
    public RGBImage (){
        width = 0;
        height =0;
        colorDepth = 255;
        pic = null;
    }
    
    public RGBImage (int width, int height, int colorDepth){
        this.width = width;
        this.height = height;
        this.colorDepth = colorDepth;
        pic = new RGBPixel[height][width];
    }
    
    public RGBImage (RGBImage copyImg){
        this (copyImg.width, copyImg.height, copyImg.colorDepth);
        
        for (int i =0; i<height;i++){
           System.arraycopy(copyImg.pic[i], 0, pic[i], 0, copyImg.pic[i].length);
        }     
    }
    
    public RGBImage(YUVImage YUVImg){
        this.width = YUVImg.getWidth();
        this.height = YUVImg.getHeight();
        this.colorDepth = 255;
        pic = new RGBPixel[height][width];
        short red = 0, green = 0, blue = 0;
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                red = clip( (short)(( 298 * (YUVImg.getPixel(i, j).getY() - 16) + 409 * 
                        (YUVImg.getPixel(i, j).getV() - 128) + 128) >> 8) );
                green = clip( (short)(( 298 * (YUVImg.getPixel(i, j).getY() - 16) - 100 * 
                        (YUVImg.getPixel(i, j).getU() - 128) - 208 * 
                                (YUVImg.getPixel(i, j).getV() - 128) + 128) >> 8));
                blue = clip( (short)(( 298 * (YUVImg.getPixel(i, j).getY() - 16 ) + 516 * 
                       (YUVImg.getPixel(i, j).getU() - 128) + 128) >> 8));
                pic[i][j] = new RGBPixel(red, green, blue);
            }
        } 
    }
    
    private short clip( short num ){
        if(num > 255){
            return((short)255);
        }
        if(num < 0 ){
            return((short)0);
        }
        return(num);
    }
    /**
     *
     * @param width
     * @param height
     * @param colorDepth
     */
    public void initPic (int width, int height, int colorDepth){
        this.width = width;
        this.height = height;
        this.colorDepth = colorDepth;
        pic = new RGBPixel[height][width];
    }
    
    public int getWidth(){
        return (width);
    }
    
    private void setHeight (int height){
        this.height = height;
    }
    
    private void setWidth (int width){
        this.width = width;
    }
    
    public int getHeight (){
        return (height);
    }
    
    public int getColorDepth(){
        return (colorDepth);
    }
    
    public RGBPixel getPixel (int row, int col){
        return (pic[row][col]);
    }
    
    public void setPixel (int row, int col, RGBPixel pixel){
        pic[row][col] = new RGBPixel(pixel);
    }
    
    @Override
    public void grayscale() {
        for (RGBPixel[] pic1 : pic) {
            for (RGBPixel item : pic1) {
                short val = (short) ((short) (item.getRed()* 0.3) 
                        + (short) (item.getGreen() * 0.59)
                        + (short) (item.getBlue()*0.11));
                        
                item.setRed(val);
                item.setGreen (val);
                item.setBlue (val);
            }
        }
    }

    @Override
    public void doublesize() {
        //RGBImage doublePic = new RGBImage(width * 2, height * 2, colorDepth);
        RGBPixel [][] oldPic; 
        oldPic = pic;
        pic = new RGBPixel[2 * height][2 * width];
        
        for(int i = 0 ; i < height; i++) {
            for(int j = 0; j < width ; j++){
                setPixel(2 * i, 2 * j, oldPic[i][j]);
                setPixel(2 * i + 1, 2 * j, oldPic[i][j]);
                setPixel(2 * i, 2 * j + 1, oldPic[i][j]);
                setPixel(2 * i + 1, 2 * j + 1, oldPic[i][j]);
            }
        }
        width = 2 * width;
        height = 2 * height;
    }

    @Override
    public void halfsize() {
        RGBPixel [][]oldPic;
        short red, green, blue;
        oldPic = pic;
        pic = new RGBPixel[height/2][width/2];
        RGBPixel pixel = new RGBPixel(); 
        for (int i=0; i < height/2; i++){
            for (int j=0; j < width/2; j++){
                red = (short)((oldPic[2*i][2*j].getRed() + oldPic[2*i + 1][2*j].getRed() 
                      + oldPic[2*i][2*j+1].getRed() + oldPic[2*i+1][2*j+1].getRed())/4);
                
                green = (short)((oldPic[2*i][2*j].getGreen() + oldPic[2*i + 1][2*j].getGreen() 
                      + oldPic[2*i][2*j+1].getGreen() + oldPic[2*i+1][2*j+1].getGreen())/4);
                
                blue = (short)((oldPic[2*i][2*j].getBlue() + oldPic[2*i + 1][2*j].getBlue() 
                      + oldPic[2*i][2*j+1].getBlue() + oldPic[2*i+1][2*j+1].getBlue())/4);
                pixel.setRed(red);
                pixel.setGreen (green);
                pixel.setBlue(blue);
                
                setPixel (i,j,pixel);
            }
        }
        
        setWidth (width/2);
        setHeight(height/2);
    }
    
    private RGBPixel []getCollumn (int index){
        RGBPixel []collumn; 
        collumn = new RGBPixel[getHeight()];
        
        for (int i =collumn.length -1 ; i >= 0 ; i--){
            collumn[i] = pic[collumn.length - 1 - i][index];
        }
        
        return(collumn);
    }
    
    @Override
    public void rotateClockwise() {
        RGBPixel [][]newPic;
        newPic = new RGBPixel [getWidth()][getHeight()];
        RGBPixel []collumn;
        
        for (int i =0; i<width;i++){
           collumn = getCollumn (i);
           System.arraycopy(collumn, 0, newPic[i], 0, collumn.length);
        }
        
        pic = newPic;
        int temp = getHeight();
        setHeight(getWidth());
        setWidth (temp);
    }
}
