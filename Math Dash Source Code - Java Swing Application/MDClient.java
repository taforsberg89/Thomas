import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.*;

public class MDClient extends JFrame{

	JLabel output, score, correct, multiplierLabel, streakCounter;
	JTextField input;
	JButton backBtn = new JButton("Quit");
	float counter = 100;
	float multiplier = 1f;
	Color bgColor;
	JPanel inputPanel, outputPanel;
	public TimePanel timePanel;
	Thread runner;
	MenuFrame menu;
	GameLogic gameLogic;
	Color color;
	int[] highscores = new int[5];
	
	public MDClient(MenuFrame menu){
		
		this.menu = menu;
		color = new Color(119,181,255);
		setSize(800,600);
		setResizable(false);
		setVisible(false);
		setLayout(null);
		setTitle("Math Dash");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(menu);
		
		
		
		
		inputPanel = new JPanel();
		
		outputPanel = new JPanel();
		
		output = new JLabel();
		input = new JTextField();
		score = new JLabel();
		correct = new JLabel();
		streakCounter = new JLabel();
		multiplierLabel = new JLabel();
		
		timePanel = new TimePanel(50,400, this);
		
		streakCounter.setText("Streak: 0");
		score.setText("<html>Score: <br> 0</html>");
		multiplierLabel.setText("X: " + multiplier);
		inputPanel.setLayout(null);
		outputPanel.add(output);
		inputPanel.add(input);
		output.setFont(new Font("Courier", Font.BOLD, 48));
		input.setFont(new Font("Courier", 0, 22));
		correct.setFont(new Font("Courier", 0, 18));
		score.setFont(new Font("Courier", 0, 16));
		streakCounter.setFont(new Font("Courier", 0, 16));
		multiplierLabel.setFont(new Font("Courier", 0, 16));
		input.setSize(300,40);
		input.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.setBounds(260,300,400,150);
		timePanel.setBounds(200,450,400,50);
		outputPanel.setBounds(200,100,400,75);
		score.setBounds(680,20, 100, 100);
		multiplierLabel.setBounds(680, 60, 100, 100);
		correct.setBounds(370,200,150,100);
		backBtn.setBounds(380, 530, 80, 30);
		streakCounter.setBounds(50, 30, 100, 100);
		backBtn.addActionListener(menu.clientBackList);
		bgColor = outputPanel.getBackground();
		gameLogic = new GameLogic(this, timePanel);
		runner = new Thread(gameLogic);
		

		add(inputPanel);
		add(outputPanel);
		add(timePanel);
		add(score);
		add(correct);
		add(streakCounter);
		add(multiplierLabel);
		add(backBtn);
		
		
		
		input.requestFocus();
		repaint();
		
	}

	
	public void setOutputText(String str){
	
	
		output.setText(str);
	
	}
	
	public void saveHighscores()
	{
		
		bubbleSort(highscores);
		
		menu.prefs.putInt("Highscore1", highscores[4]);
		menu.prefs.putInt("Highscore2", highscores[3]);
		menu.prefs.putInt("Highscore3", highscores[2]);
		menu.prefs.putInt("Highscore4", highscores[1]);
		menu.prefs.putInt("Highscore5", highscores[0]);
		
	
		
	}
	
	public static void bubbleSort(int[] intArray) {
          
               
		int n = intArray.length;
		int temp = 0;
	   
		for(int i=0; i < n; i++){
				for(int j=1; j < (n-i); j++){
					   
						if(intArray[j-1] > intArray[j]){
								//swap the elements!
						temp = intArray[j-1];
						intArray[j-1] = intArray[j];
						intArray[j] = temp;
						}
					   
				}
		}

    }
	
		
	
}
