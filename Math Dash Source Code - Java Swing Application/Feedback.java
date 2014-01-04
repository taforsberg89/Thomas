import java.awt.Color;
import java.util.Random;

public class Feedback implements Runnable{
	
	MDClient client;
	GameLogic logic;
	String[] yayWords = {"Awesome!", "Splendid!", "Great work!", "Keep it up!", "Incredible!", "Fantastic!", "Well done!", "Perfect!", "Fabulous!", "Terrific!", "Keep going!"};
	
	
	public Feedback(MDClient client, GameLogic logic){
	
		this.client = client;
		this.logic = logic;
		
			
	}
	
	
	public boolean yayWord(){
		
		boolean jippi = false;
				
		if(logic.correctCounter < 10){
			if(logic.correctCounter == logic.tempCounter + 3){
				jippi = true;
				logic.tempCounter = logic.correctCounter;
			}
		}
		else
		{
				if(logic.correctCounter == logic.tempCounter + 5){
				jippi = true;
				logic.tempCounter = logic.correctCounter;
			}
		
		
		}
		
		return jippi;
	}
	
	
	public void run(){
	
	
		this.client = client;
		this.logic = logic;
		
		int timeToSleep = 700;
		
		if(logic.correct){
			logic.correctCounter++;
			client.correct.setForeground(Color.blue);
			client.correct.setText("Correct!!");
			
			if(logic.streak && !logic.toldFrozen)
			{
				
				logic.toldFrozen = true;
				client.correct.setText("<html>Sick Streak! <br> Time Frozen!! </html>");
				timeToSleep = 1500;
				
			}
			else if(yayWord()){
			
				client.correct.setText("<html>Correct!! <br> " + yayWords[new Random().nextInt(10)] + "</html>");
				timeToSleep = 1000;
			}
			
		}
		else{
		
			client.correct.setForeground(Color.red);
			client.correct.setText("<html>Wrong! <br>Answer:" + logic.correctAnswer + "</html>");
			logic.correctCounter = 0;
		
		}
		
		
		if(logic.levelUp){
		
			client.correct.setText("<html> LEVEL UP!! <br> Difficulty increased!</html>");
			timeToSleep = 1500;
			logic.levelUp= false;
			
		}
		
		try{
		Thread.sleep(timeToSleep);
		}
		catch(InterruptedException ex){
		
		
		
		}
		client.correct.setText("");
	
	
	
	
	
	}












}