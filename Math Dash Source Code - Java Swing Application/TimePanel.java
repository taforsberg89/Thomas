import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.event.*;

public class TimePanel extends JPanel{

	int height;
	int width;
	float percentage= 1f;
	MDClient client;
	
	
	
	
	int flashCounter = 0;
	
	int delay = 300;
	
	Timer timer;
	
	public TimePanel(int height, int width, MDClient client){
	
		this.height = height;
		this.width = width;
		this.client = client;
		
		
	}

	
	public void paintComponent(final Graphics g){
	
		super.paintComponent(g);
	
			width = 400;
			float temp = width * percentage;
			
			width = StrictMath.round(temp);
			
			g.setColor(Color.black);
			g.fillRect(0,0, width, height);
			
			
	}
	

	
	public void setPercentage(float perc){
	
		percentage = perc;
	
	
	}
	
	public void decrementCounter(){
	
		client.counter--;
		
	}
	
	public void update(GameLogic logic){
	
		
			
			
				
			client.outputPanel.setBackground(client.bgColor);
			setPercentage(client.counter/200);
			if(!logic.streak){
				decrementCounter();
				logic.frozenSeconds = 0;
			}
			if(client.counter < 30){
					
				if(flashCounter == 2){
					client.outputPanel.setBackground(Color.RED);
					flashCounter = 0;
				}
				else
				{ 
					flashCounter += 1;
				}	
				
			}
			
				
				
			repaint();
			
			try{
			Thread.sleep(delay);
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			
			}
		
		
	}
	
}