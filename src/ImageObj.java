import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImageObj extends JPanel{
	
	public BufferedImage img;
	public boolean imReaded;
	private Dimension dimension;

	public ImageObj(){
		super();
		img = new BufferedImage(400,300,BufferedImage.TYPE_INT_ARGB);
	}
	public void readImage(String patch){		
	    try {
	            img = ImageIO.read(new File(patch));
	    }catch (IOException e){
	        System.out.println("Error");
	    }
		dimension = new Dimension(img.getWidth(), img.getHeight());
		setPreferredSize(dimension);
		imReaded = true;
	}
	
	public void rotateCw()
	{
	    int width  = img.getWidth();
	    int height = img.getHeight();
	    BufferedImage newImage = new BufferedImage( height, width, img.getType() );
	 
	    for( int i=0 ; i < width ; i++ )
	        for( int j=0 ; j < height ; j++ )
	            newImage.setRGB( height-1-j, i, img.getRGB(i,j) );
	 
	    img = newImage;
	}
	public void rotateCCw()
	{
	    int width  = img.getWidth();
	    int height = img.getHeight();
	    BufferedImage newImage = new BufferedImage( height, width, img.getType() );
	 
	    for( int i=0 ; i < width ; i++ )
	        for( int j=0 ; j < height ; j++ )
	            newImage.setRGB( j, width-1-i, img.getRGB(i,j) );
	 
	    img = newImage;
	}
	
	public void clearImg(){
		img = new BufferedImage(400,300,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void resizeImg(int height, int width){
		BufferedImage resizedImage = new BufferedImage(width , height, img.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(img, 0, 0, width , height , null);
		g.dispose();
		img = resizedImage;
	}
	public void zoomInImg(){
		 int width  = img.getWidth()*2;
		 int height = img.getHeight()*2;
		 resizeImg(height, width);

	}
	
	public void zoomOutImg(){
		 int width  = (int)(img.getWidth()*0.5);
		 int height = (int)(img.getHeight()*0.5);
		 resizeImg(height, width);

	}
	
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this);
    }

}
