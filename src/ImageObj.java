import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImageObj extends JPanel implements MouseListener, MouseWheelListener  {
	
	public BufferedImage img;
	private BufferedImage resizedImage;
	public boolean imReaded;
	private Dimension dimension;
	public boolean alignFlag;
	private int margin;
	private double zoom;
	private int dragposition;
	private int dragpositionh;
	private int xposepress;
	private int yposepress;

	public ImageObj(){
		super(new BorderLayout());
		addMouseListener(this);
		addMouseWheelListener(this);
		img = new BufferedImage(400,300,BufferedImage.TYPE_INT_ARGB);
		resizedImage = null;
		alignFlag = false;
		zoom = 1;
	}
	public void readImage(String patch){		
	    try {
	            img = ImageIO.read(new File(patch));
	            resizedImage = img;
	            zoom = 1;
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
	    
		int widthr  = resizedImage.getWidth();
		int heightr = resizedImage.getHeight();
		    BufferedImage newImageres = new BufferedImage( heightr, widthr, resizedImage.getType() );
		 
		for( int i=0 ; i < widthr ; i++ )
		    for( int j=0 ; j < heightr ; j++ )
		    	newImageres.setRGB( heightr-1-j, i, resizedImage.getRGB(i,j) );
		 
		resizedImage = newImageres;
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
	    
		int widthr  = resizedImage.getWidth();
		int heightr = resizedImage.getHeight();
		    BufferedImage newImageres = new BufferedImage( heightr, widthr, resizedImage.getType() );
		 
		for( int i=0 ; i < widthr ; i++ )
		    for( int j=0 ; j < heightr ; j++ )
		    	newImageres.setRGB( j, widthr-1-i, resizedImage.getRGB(i,j) );
		 
		resizedImage = newImageres;
	}
	
	public void clearImg(){
		
		img = new BufferedImage(400,300,BufferedImage.TYPE_INT_ARGB);
		resizedImage = new BufferedImage(400,300,BufferedImage.TYPE_INT_ARGB);
	}
	
	public void resizeImg(){
		int width  = (int)(img.getWidth()*zoom);
		int height = (int)(img.getHeight()*zoom);
		resizedImage = new BufferedImage(width , height, img.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(img, 0, 0, width , height , null);
		g.dispose();
	}
	public void zoomInImg(){
		 zoom = zoom*1.3;
		 resizeImg();
	}
	
	public void zoomOutImg(){
		 zoom = zoom/1.3;
		 resizeImg();
	}
	
	public void allignleft(int margintmp){
		
		margin = margintmp;
		if(alignFlag == false){
			alignFlag = true;
		}else
		{
			alignFlag = false;
		}
		
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(zoom != 1){
            int x = (this.getWidth() - resizedImage.getWidth(null)) / 2;
            int y = (this.getHeight() - resizedImage.getHeight(null)) / 2;
            
            if(alignFlag == true){
            	g2d.drawImage(resizedImage, margin, y, null);
            }else
            {
            	g2d.drawImage(resizedImage, x + dragposition/2, y + dragpositionh/2, null);
            }
            

        }
        else{
        	
        	int x = (this.getWidth() - img.getWidth(null)) / 2;
        	int y = (this.getHeight() - img.getHeight(null)) / 2;
        
        
        	if(alignFlag == true){
        		g2d.drawImage(img, margin, y, null);
        	}else
        	{
        		g2d.drawImage(img, x + dragposition/2, y + dragpositionh/2, null);
            }
        
        }

    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		xposepress = Math.abs(e.getX());
		yposepress = Math.abs(e.getY());
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		dragposition = dragposition + (Math.abs(e.getX()) - xposepress);
		
		dragpositionh = dragpositionh + (Math.abs(e.getY()) - yposepress);

		this.revalidate();
		this.repaint();
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation();
	    if (notches < 0) {
			zoomInImg();
    		this.revalidate();
    		this.repaint();
	    } else {
			zoomOutImg();
    		this.revalidate();
    		this.repaint();
	   }
	}



}
