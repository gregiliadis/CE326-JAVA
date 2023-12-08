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
public class Histogram {
    private int []histogram;
    private long  elements;
    private int []equalizer;
    public Histogram(YUVImage img){
        histogram = new int [236];
        equalizer = new int [236];
        
        for (int obj: histogram) {obj = 0;}
        
        for (int i=0; i < img.getHeight(); i++){
            for (int j=0; j < img.getWidth(); j++){
                //System.out.println ("index: " + img.getPixel(i,j).getY());
                histogram[(int) img.getPixel(i,j).getY()]++;
            }
        }
        
        elements = img.getWidth() * img.getHeight();
    }
    
    public Histogram (Histogram histogram_s){
        histogram = new int [histogram_s.histogram.length];
        this.elements = histogram_s.elements;
        
        System.arraycopy(histogram_s.histogram, 0, histogram, 0, histogram.length);
    }
    
    public void equalize(){
        double []PMF = new double[histogram.length];
        double []CDF = new double[PMF.length];
        
        
        for (int i=0; i<histogram.length; i++){
            PMF[i] = ((double)histogram[i])/(double)elements;
            
        }
        
        CDF[0] = PMF[0];
        for (int i=1; i < PMF.length; i++){
            CDF[i] = PMF[i] + CDF[i-1];
        }
        
        for (int i=0; i < CDF.length; i++){
            equalizer[i] = (int) ((double)235 * CDF[i]);
        }
    }
    public short getEqualizedLuminocity(int luminocity){
        //Histogram histogramN = new Histogram (this);
        
        //histogramN.equalize();
        
        return ((short) equalizer[luminocity]);
    }
}
