import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Color;
import java.util.Date;
import java.lang.Object;

public class GameLogic implements Runnable{
	
	long currentTime = 0, lastTime;
	long seconds = 0, timeFromStart, time;
	AchievementLogic achiev;
	MDClient client;
	TimePanel timePanel;
	Random rng = new Random();
	char[] operators = {'+', '-', '*', '/'};
	int[] divisorNumbers = {2, 4, 5, 10};
	int[] divideBy2Numbers = {10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80}; //36
	int[] divideBy4Numbers = {4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140}; //35
	int[] divideBy5Numbers = {5, 10,15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150, 155, 160, 165, 170, 175, 180}; //35
	int[] divideBy10Numbers = {100, 120, 130, 140 ,150 ,160 ,170, 180 ,190 ,200,240, 280, 330, 360, 390, 450, 550, 600, 720, 630, 840}; //21
	
	int num1, num2, num3;
	int score = 0;
	int frozenCounter = 0;

	int correctCounter = 0;
	int totalCorrectCounter = 0;
	
	boolean toldFrozen;
	int tempCounter;
	boolean thirdNum;
	boolean correct;
	boolean levelUp = false;
	boolean streak = false;
	boolean sum, difference, product,quotient;
	
	char operator, operator2;
	
	int correctAnswer = 0;
	boolean incremented = false;
	int level = 1;
	int frozenSeconds;
	boolean gameInProgress = true;
	
	
	public GameLogic(MDClient client, TimePanel timePanel){
		
		achiev = new AchievementLogic(this);
		
		this.client = client;
		this.timePanel = timePanel;
		
		client.input.addKeyListener(new KeyAdapter(){  
            public void keyPressed(KeyEvent e) {  
                if(e.getKeyCode()==KeyEvent.VK_ENTER){  
                    onEnter();
               }  
            }  
		}); 
				
		newNumbers();
	}
	
	public boolean validateInput(){
	
		try{
			Integer.parseInt(client.input.getText());
			return true;
		}
		catch(NumberFormatException ex){
			
			client.input.setText("");
			return false;
		}
		
	}
	
		public void calcMultiplier(){
	
	
		if(client.counter < 66)
		{
			client.multiplier = 0.5f;
			client.multiplierLabel.setText("X: " +client.multiplier);
		}
		else if( client.counter < 132)
		{
		
			client.multiplier = 1;
			client.multiplierLabel.setText("X: " +client.multiplier);
		}
		else 
		{
		
			client.multiplier = 1.5f;
			client.multiplierLabel.setText("X: " +client.multiplier);
		}
						
	}
	
	public void onLevelUp()
	{
		levelUp = true;
		client.counter = 100;
		level++;
	
		switch(level)
		{
		
		case 2: timePanel.delay = 280;
				break;
		case 3: timePanel.delay = 250;
				break;
		case 4: timePanel.delay = 200;
				break;
		case 5: timePanel.delay = 180;
				break;
		case 6: timePanel.delay = 150;
				break;
		case 7: timePanel.delay = 120;
				break;
		case 8: timePanel.delay = 100;
				break;
		case 9: timePanel.delay = 70;
				break;
		case 10: timePanel.delay = 50;
				break;
		}
		
	
	}

	
	public int getInput()
	{
	
		return Integer.parseInt(client.input.getText());
		
	}
	
	public void giveFeedback()
	{
	
		new Thread(new Feedback(client,this)).start();
	}
	
	
	public void newNumbers(){
	
		thirdNum = false;
		
		switch(level){
		
			case 1: num1 = rng.nextInt(10);
					num2 = rng.nextInt(10);
					operator = operators[rng.nextInt(2)];
					
					
					if(operator == '+')
					{
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else
					{
						if(num2  > num1){
					
						int temp = num2; 
						num2 = num1;
						num1 = temp;
					
						}
						sum = false;
						quotient = false;
						product = false;
						difference = true;
									
					}
					
					client.setOutputText(num1 + " " + operator + " " + num2);
					break;
			case 2:	num1 = rng.nextInt(20);
					num2 = rng.nextInt(20);
					operator = operators[rng.nextInt(2)];
					if(operator == '+')
					{
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else
					{
						if(num2  > num1){
					
						int temp = num2; 
						num2 = num1;
						num1 = temp;
					
						}
					
						sum = false;
						quotient = false;
						product = false;
						difference = true;
									
					}
					client.setOutputText(num1 + " " + operator + " " + num2);	
					break;
			case 3: num1 = 10 + rng.nextInt(20);
					num2 = 10 + rng.nextInt(10);
					operator = operators[rng.nextInt(2)];
					if(operator == '+')
					{
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else
					{
					
						sum = false;
						quotient = false;
						product = false;
						difference = true;
									
					}
										
					//30% chance for an additional number
					if(rng.nextInt(10) > 7){
						
						thirdNum = true;
						num3 = rng.nextInt(10);
						operator2 = operators[rng.nextInt(2)];
						client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					}
					else{
						thirdNum = false;
						client.setOutputText(num1 + " " + operator + " " + num2);
					}
					break;
			case 4: float temp = rng.nextFloat();
										
					if(temp < 0.33){
						num1 = 25 + rng.nextInt(50);
						num2 = 20 + rng.nextInt(20);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.66){
					
						num1 = 20 + rng.nextInt(30);
						num2 = 10 + rng.nextInt(20);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else
					{
						num1 = rng.nextInt(10);
						num2 = rng.nextInt(10);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					
					client.setOutputText(num1 + " " + operator + " " + num2);
					break;
			case 5: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 50 + rng.nextInt(100);
						num2 = 30 + rng.nextInt(70);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 50 + rng.nextInt(50);
						num2 = 20 + rng.nextInt(50);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = rng.nextInt(10);
						num2 = rng.nextInt(10);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
					
					
					if(rng.nextInt(10) > 5){
						
						thirdNum = true;
						num3 = rng.nextInt(20);
						operator2 = operators[rng.nextInt(2)];
						client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					}
					else{
						thirdNum = false;
						client.setOutputText(num1 + " " + operator + " " + num2);
					}
					break;
					
			case 6: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 100 + rng.nextInt(100);
						num2 = 100 + rng.nextInt(100);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 100 + rng.nextInt(100);
						num2 = 50 + rng.nextInt(50);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = rng.nextInt(20);
						num2 = rng.nextInt(10);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
					//70% chance for a third number
					if(rng.nextInt(10) > 7){
						
						thirdNum = true;
						num3 = 10 + rng.nextInt(20);
						operator2 = operators[rng.nextInt(2)];
						client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					}
					else{
						thirdNum = false;
						client.setOutputText(num1 + " " + operator + " " + num2);
					}
					break;
			case 7: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 200 + rng.nextInt(250);
						num2 = 200 + rng.nextInt(250);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 100 + rng.nextInt(200);
						num2 = 50 + rng.nextInt(150);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = rng.nextInt(30);
						num2 = rng.nextInt(20);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
											
					thirdNum = true;
					num3 = 20 + rng.nextInt(30);
					operator2 = operators[rng.nextInt(3)];
					client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					break;
			case 8: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 100 + rng.nextInt(200);
						num2 = 100 + rng.nextInt(200);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 100 + rng.nextInt(150);
						num2 = 50 + rng.nextInt(100);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = 10 + rng.nextInt(20);
						num2 = 10 + rng.nextInt(15);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
											
					thirdNum = true;
					num3 = 40 + rng.nextInt(60);
					operator2 = operators[rng.nextInt(3)];
					client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					break;
			case 9: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 100 + rng.nextInt(200);
						num2 = 100 + rng.nextInt(200);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 100 + rng.nextInt(150);
						num2 = 50 + rng.nextInt(100);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = 15 + rng.nextInt(15);
						num2 = 15 + rng.nextInt(20);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
											
					thirdNum = true;
					num3 = 100 + rng.nextInt(100);
					operator2 = operators[rng.nextInt(3)];
					client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
						
					break;
			case 10: temp = rng.nextFloat();
			
					if(temp < 0.25){
						num1 = 100 + rng.nextInt(200);
						num2 = 100 + rng.nextInt(200);
						operator = '+';
						sum = true;
						quotient = false;
						product = false;
						difference = false;
					}
					else if (temp < 0.50){
					
						num1 = 100 + rng.nextInt(150);
						num2 = 50 + rng.nextInt(100);
						operator = '-';
						sum = false;
						quotient = false;
						product = false;
						difference = true;
					}
					else if(temp < 0.75){
					
						num1 = rng.nextInt(20);
						num2 = rng.nextInt(15);
						operator = '*';
						sum = false;
						quotient = false;
						product = true;
						difference = false;
					
					}
					else{
						
						num2 = divisorNumbers[rng.nextInt(3)];
						if(num2 == 2){
							num1 = divideBy2Numbers[rng.nextInt(35)];
						}
						else if(num2 == 4)
						{
							num1 = divideBy4Numbers[rng.nextInt(34)];
						}
						else if(num2 == 5)
						{
							num1 = divideBy5Numbers[rng.nextInt(34)];
						}
						else if(num2 == 10)
						{
							num1 = divideBy10Numbers[rng.nextInt(20)];
						}
						operator = '/';
						sum = false;
						quotient = true;
						product = false;
						difference = false;
						
					}
											
					thirdNum = true;
					num3 = 100 + rng.nextInt(200);
					operator2 = operators[rng.nextInt(3)];
					client.setOutputText(num1 + " " + operator + " " + num2 + " " + operator2 + " " + num3);
					break;			
		}
		
	}
	
	public void calcCorrectAnswer(){
		
		correctAnswer = 0;
		
				
		if(thirdNum)
		{
		
			if(sum && operator2 == '+')
			{
				correctAnswer = num1 + num2 + num3;
				
			}
			else if(difference && operator2 == '+')
			{
				correctAnswer = (num1 - num2) + num3;
				
			}
			else if(product && operator2 == '+')
			{
				correctAnswer = (num1 * num2) + num3;
				
			}
			else if(quotient && operator2 == '+')
			{	
				correctAnswer = (int)(num1 / num2) + num3;
				
			}
			else if(sum && operator2 == '-')
			{
			correctAnswer = (num1 + num2) - num3;
				
			}
			else if(difference && operator2 == '-')
			{
			 	correctAnswer = (num1 - num2) - num3;
			}
			else if(product && operator2 == '-')
			{
				correctAnswer = (num1 * num2) - num3;
			}
			else if(quotient && operator2 == '-')
			{	
				correctAnswer = (num1 / num2) - num3;
			}
			else if(sum && operator2 == '*')
			{
				correctAnswer = num1 + (num2 * num3);
			}
			else if(difference && operator2 == '*')
			{
				correctAnswer = num1 - (num2 * num3);
			}
			else if(product && operator2 == '*')
			{
				correctAnswer = num1 * num2 * num3;
			}
			else if(quotient && operator2 == '*')
			{
				correctAnswer = num1 / num2 * num3;
			
			}
			
		}
		else
		{
			if(sum)
			{
				correctAnswer = num1 + num2;
			}
			else if(difference)
			{		
				correctAnswer = num1 - num2;
			}
			else if(product)
			{
				correctAnswer = num1 * num2;
			}
			else if (quotient)
			{
				correctAnswer = num1 / num2;
			
			}
		
		}
		
	}
	
	public void checkAnswer(){
		
		calcCorrectAnswer();
		
		if(getInput() == correctAnswer)
		{
			correct = true;
			totalCorrectCounter++;
			frozenCounter++;
		}
		else{
			correct = false;
			correctCounter = 0;
			frozenCounter = 0;
			}
	}
	
	public void calcScore(){
		
		switch(level){
		
		case 1: if(correct){
					giveFeedback();
					score += 10 * client.multiplier;
					client.counter += 15;
				}
				else{
					giveFeedback();
					score -= 5;
				}
				break;
		case 2: if(correct){
					
					giveFeedback();
					score += 30 * client.multiplier;
					client.counter += 25;
				}
				else{
				    
					giveFeedback();
					score -= 5;
					client.counter -= 10;
				}
				break;
		case 3: if(correct){
					
					giveFeedback();
					score += 40 * client.multiplier;
					client.counter += 25;
				}
				else{
					
					giveFeedback();
					score -= 5;
					client.counter -= 15;
				}
				break;
		case 4: if(correct){
					
					giveFeedback();
					score += 50 * client.multiplier;
					client.counter += 25;
				}
				else{
				
					giveFeedback();
					score -= 5;
					client.counter -= 15;
				}
				break;	
		case 5: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;

			}
			else
			{
				giveFeedback();
				client.counter -= 15;
				
			}
			break;
		case 6: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;
		
			}
			else
			{
				giveFeedback();
				client.counter -= 15;
			
			}
			break;
		case 7: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;
				
		
			}
			else
			{
				giveFeedback();
				client.counter -= 15;
				
			
			}
			break;
		case 8: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;
				
		
			}
			else
			{
				giveFeedback();
				client.counter -= 15;
				
			
			}
			break;	
		case 9: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;
				
		
			}
			else
			{
				giveFeedback();
				client.counter -= 15;
				
			
			}
			break;
		case 10: if(correct){
				
				giveFeedback();
				score += 50 * client.multiplier;
				client.counter += 25;
				
		
			}
			else
			{
				giveFeedback();
				client.counter -= 15;
				
			
			}
			break;		
		default: System.out.println("Something went wrong");
					score = 0;
		}
		
		if(score <= 0)
			score = 0;
		
		if(client.counter >= 200){
			
			onLevelUp();
			
		}
		
		
		

		
	}
	
		
	public void onEnter(){
	
	
		if(validateInput()){
			achiev.fastAchievs();
			seconds = 0;
			checkAnswer();
			calcScore();
			client.score.setText("<html>Score: <br> " + score + "</html>");
			client.streakCounter.setText("Streak: " + correctCounter);
			client.input.setText("");
			client.input.requestFocus();
			newNumbers();
			
		}
	}
	
	public void insertHighscore()
	{
	
		if(score > (client.highscores[4]))
		{	
			int temp = client.highscores[4];
			client.highscores[4] = score;
			client.highscores[0] = client.highscores[1];
			client.highscores[1] = client.highscores [2];
			client.highscores[2] = client.highscores[3];
			client.highscores[3] = temp;
		}
		else if(score > client.highscores[3])
		{				
			int temp = client.highscores[3];
			client.highscores[3] = score;
			client.highscores[0] = client.highscores[1];
			client.highscores[1] = client.highscores [2];
			client.highscores[2] = temp;
		}
		else if(score > client.highscores[2])
		{
			int temp = client.highscores[2];
			client.highscores[2] = score;
			client.highscores[0] = client.highscores[1];
			client.highscores[1] = temp;
		}
		else if(score > client.highscores[1])
		{		
			int temp = client.highscores[1];
			client.highscores[1] = score;
			client.highscores[0] = temp;
		}
		else if(score > client.highscores[0]){
		
			client.highscores[0] = score;
		
		}
	
	
	}
	
	public void run(){
			
		
		while(gameInProgress){
			lastTime = currentTime;
			currentTime = System.currentTimeMillis();
			
			calcMultiplier();
			
			
			
			timePanel.update(this);
			
			if(frozenCounter == 10)
			{
				streak = true;
				giveFeedback();
				frozenCounter = 0;
			}
			
			if(frozenSeconds > 5)
			{
			
				streak = false;
				toldFrozen = false;
			}
			achiev.update();
			
			if(client.counter <= 0)
			{
					insertHighscore();
					try{
					client.saveHighscores();
					achiev.saveAchievs();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Unable to save highscore");
					
					}
					
					JOptionPane.showMessageDialog(null,"<html>Game Over! <br> Final Score: " + score + "</html>");
										
					client.menu.gameBack();
					
					gameInProgress = false;
					
					
			}
			time += currentTime - lastTime;
			
			
			
			if(time > 1000){
				seconds++;
				timeFromStart++;
				frozenSeconds++;
				time = 0;
			}
			incremented = false;
			
		}
	}
	
	

}