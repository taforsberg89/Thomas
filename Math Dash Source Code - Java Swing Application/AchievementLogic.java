import java.io.*;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AchievementLogic{

	Achievement[] achievements = new Achievement[9];
	GameLogic logic;
	int prettyfast= 0, veryfast = 0, lightningfast = 0;
	int hotterStreak, hottestStreak;
	JFrame achievementFrame;
	JPanel achievPanel;
	public AchievementLogic(GameLogic logic){
	
		this.logic = logic;
		achievements[0] = new Achievement("Hot Streak", "Answer Correct 10 Times in a Row");
		achievements[1] = new Achievement("Hotter Streak", "Answer Correct 10 Times in a Row While Playing at Level 3 or Above");
		achievements[2] = new Achievement("Hottest Streak", "Answer Correct 10 Times in a Row While Playing at Level 6 or Above");
		achievements[3] = new Achievement("Pretty Fast", "Take Less Than 5 Seconds to Answer Correct 5 Times in a Row at Level 3 or Above");
		achievements[4] = new Achievement("Very Fast", "Take Less Than 3 seconds to Answer Correct 5 Times in a Row at Level 3 or Above");
		achievements[5] = new Achievement("Lightning Fast!", "Take Less Than 1 second to Answer Correct 5 Times in a Row at Level 3 or Above");
		achievements[6] = new Achievement("Middle name; Calculator", "Score 2000 Points in 3 Minutes");
		achievements[7] = new Achievement("The Count", "Answer Correct 200 Times in One Game");
		achievements[8] = new Achievement("Like a Boss", "Reach Level 10");
		
			
		
	}

	public void update(){
		
		if(logic.level == 10){
			achievements[8].achieved = true;

		}
		
		if(logic.totalCorrectCounter == 200)
		{
		
			achievements[7].achieved = true;
		}
		
		if(logic.correctCounter > 9){
			achievements[0].achieved = true;
			
		}
			
		if(logic.correctCounter > 9 && logic.level > 3)
			achievements[1].achieved = true;
			
		if(logic.correctCounter > 9 && logic.level > 6)
			achievements[2].achieved = true;
			
		
		
		if(logic.score > 2000 && logic.timeFromStart < 180)
		{
			achievements[6].achieved = true;
		}
	}
	
	public void fastAchievs(){
		
		if(logic.seconds < 1 && logic.correct && logic.level >= 3)
		{	
			if(!logic.incremented){
				lightningfast++;
				logic.incremented = true;
			}
			if(lightningfast == 5)
			{
				achievements[5].achieved = true;
			}
		}
		else{
		
			lightningfast = 0;
		}
		
		if(logic.seconds < 3 && logic.correct  && logic.level>= 3)
		{
			if(!logic.incremented){
				veryfast++;
				logic.incremented = true;
			}
			if(lightningfast == 5)
			{
				achievements[4].achieved = true;
			}
		}
		else{
		
			veryfast = 0;
		}
	
		if(logic.seconds < 5 && logic.correct  && logic.level >= 3)
		{
			if(!logic.incremented){
				prettyfast++;
				logic.incremented = true;
			}
			if(lightningfast == 5)
			{
				achievements[3].achieved = true;
			}
		}
		else{
		
			prettyfast = 0;
		}
	
	
	}
	public void saveAchievs()
	{
	
		logic.client.menu.prefs.putBoolean("achiev1", achievements[0].achieved);
		logic.client.menu.prefs.putBoolean("achiev2", achievements[1].achieved);
		logic.client.menu.prefs.putBoolean("achiev3", achievements[2].achieved);
		logic.client.menu.prefs.putBoolean("achiev4", achievements[3].achieved);
		logic.client.menu.prefs.putBoolean("achiev5", achievements[4].achieved);
		logic.client.menu.prefs.putBoolean("achiev6", achievements[5].achieved);
		logic.client.menu.prefs.putBoolean("achiev7", achievements[6].achieved);
		logic.client.menu.prefs.putBoolean("achiev8", achievements[7].achieved);
		logic.client.menu.prefs.putBoolean("achiev9", achievements[8].achieved);
		
	
	}



	public void loadAchievs()
	{
		
		
		achievements[0].achieved = logic.client.menu.prefs.getBoolean("achiev1", false);
		achievements[1].achieved = logic.client.menu.prefs.getBoolean("achiev2", false);
		achievements[2].achieved = logic.client.menu.prefs.getBoolean("achiev3", false);
		achievements[3].achieved = logic.client.menu.prefs.getBoolean("achiev4", false);
		achievements[4].achieved = logic.client.menu.prefs.getBoolean("achiev5", false);
		achievements[5].achieved = logic.client.menu.prefs.getBoolean("achiev6", false);
		achievements[6].achieved = logic.client.menu.prefs.getBoolean("achiev7", false);
		achievements[7].achieved = logic.client.menu.prefs.getBoolean("achiev8", false);
		achievements[8].achieved = logic.client.menu.prefs.getBoolean("achiev9", false);
		
		
		
		//Achievement frame
		JButton achievBackBtn = new JButton("Back");
		achievementFrame = new JFrame();
		achievementFrame.setSize(800,600);
		achievementFrame.setResizable(false);
		achievementFrame.setVisible(false);
		achievementFrame.setLayout(new BorderLayout());
		achievementFrame.setTitle("Achievements");
		achievementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		achievementFrame.setLocationRelativeTo(null);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(achievBackBtn);
		JPanel headerPanel = new JPanel();
		JLabel header = new JLabel();
		header.setText("Achievements");
		headerPanel.add(header);
		
		achievBackBtn.addActionListener(logic.client.menu.backList);
		
		achievPanel = new JPanel();
		achievPanel.setLayout(null);
		JLabel[] labels = new JLabel[18];
	

		
		int achievCounter = 0;
		
		
			
			
			
		
		
		for(int i = 0; i < 18; i++)
		{
			if(i % 2 == 0){
				labels[i] = new JLabel();
				labels[i].setBounds(50, 0 + (i * 25), 400, 100);
				labels[i].setText("<html>" + achievements[achievCounter].name + " <br> " + achievements[achievCounter].objective + "</html>");
				achievPanel.add(labels[i]);
				
				
			}
			else
			{	
			
				
				labels[i] = new JLabel();
				labels[i].setBounds(600, 0 + (i * 25), 100, 100);
				String temp = new Boolean(achievements[achievCounter].achieved).toString();
			
				if(temp.equals("true"))
				{
					labels[i].setText("Achieved!");
					
				}
				else
				{
					labels[i].setText("In progress");
					
				}
				achievPanel.add(labels[i]);
				achievCounter++;
			}
			
		}
		achievementFrame.add(headerPanel, BorderLayout.NORTH);
		achievementFrame.add(achievPanel, BorderLayout.CENTER);
		achievementFrame.add(buttonPanel, BorderLayout.SOUTH);
	
	}

}