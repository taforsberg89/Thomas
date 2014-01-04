import java.awt.*;
import javax.swing.*;
import java.net.*;
public class ImagePanel extends JPanel{

	
	ImageIcon img;
	int width, height;

	public ImagePanel(URL url, int height, int width){

		img = new ImageIcon(url);
		this.width = width;
		this.height = height;
		
	}

	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
    
		g.drawImage(img.getImage(),0,0,width,height,null);
		
		
	}

}