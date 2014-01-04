import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.prefs.Preferences;

public class MenuFrame extends JFrame{

	MDClient gameplayScreen;
	JFrame highscoreFrame, achievementFrame;
	JPanel highscorePanel, achievPanel;
	AchievBackListener backList = new AchievBackListener();
	GameBackListener clientBackList = new GameBackListener();
	Preferences prefs;
	
	public MenuFrame(){
	
		gameplayScreen = new MDClient(this);
		
		 prefs = Preferences.userNodeForPackage(getClass());
		
		setSize(800,600);
		setResizable(false);
		setVisible(true);
		setLayout(null);
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Container cp = getContentPane();
		
	
		JPanel buttonPanel = new JPanel();
		URL url = this.getClass().getResource("mathBG.jpg");
		ImagePanel bgPic = new ImagePanel(url, 800, 800);
		bgPic.setBounds(0,0,800,600);
		buttonPanel.setBounds(300, 150, 200, 400);
		JButton startGame = new JButton("Start game");
		JButton highscore = new JButton("Highscores");
		JButton achievements = new JButton("Achievements");
		JButton exit = new JButton("Exit");
		
		exit.addActionListener(new ExitButtonListener());
		startGame.addActionListener(new StartButtonListener());
		highscore.addActionListener(new HighscoreButtonListener());
		achievements.addActionListener(new AchievementButtonListener());
		buttonPanel.setLayout(new GridLayout(4,1));
		buttonPanel.add(startGame);
		buttonPanel.add(highscore);
		buttonPanel.add(achievements);
		buttonPanel.add(exit);
		
		
		
		
	
		
		int counter = 1;
		int highscoreCounter = 0;
		int achievCounter = 0;
		
	try
		{	
			
			loadHighscores();
			gameplayScreen.gameLogic.achiev.loadAchievs();
		}
		catch(Exception ex)
		{
			
		}
		
		
		cp.add(buttonPanel);
		cp.add(bgPic);
	
		repaint();
		validate();
	
	}
	
	
	public void loadHighscores()
	{
		int counter = 0;
		int highscoreCounter = 4;
		int label = 1;
			
		//Highscore frame
		JPanel highscoreButtonPanel = new JPanel();
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new BackButtonListener());
		highscoreButtonPanel.add(backBtn);
		highscoreFrame = new JFrame();
		highscoreFrame.setSize(400,600);
		highscoreFrame.setResizable(false);
		highscoreFrame.setVisible(false);
		highscoreFrame.setLayout(new BorderLayout());
		highscoreFrame.setTitle("Highscores");
		highscoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		highscoreFrame.setLocationRelativeTo(null);
		JLabel headerLabel = new JLabel("Highscores");
		headerPanel.add(headerLabel);
		
		gameplayScreen.highscores[4] = prefs.getInt("Highscore1", 0);
		gameplayScreen.highscores[3] = prefs.getInt("Highscore2", 0);
		gameplayScreen.highscores[2] = prefs.getInt("Highscore3", 0);
		gameplayScreen.highscores[1] = prefs.getInt("Highscore4", 0);
		gameplayScreen.highscores[0] = prefs.getInt("Highscore5", 0);
	
				
		highscorePanel = new JPanel();
		highscorePanel.setLayout(new GridLayout(5, 2));
		
		for(int i = 0; i < 10; i++)
		{
			if(i % 2 == 0){
			
				highscorePanel.add(new JLabel(label + "."));
				label++;
				
			}
			else
			{
				highscorePanel.add(new JLabel(Integer.toString(gameplayScreen.highscores[highscoreCounter])));
				highscoreCounter--;
				
			
			}
			
		}
		
		highscoreFrame.add(headerPanel, BorderLayout.NORTH);
		highscoreFrame.add(highscorePanel, BorderLayout.CENTER);
		highscoreFrame.add(highscoreButtonPanel, BorderLayout.SOUTH);
	}
	
	public void backToMenu(){
		
		
		highscoreFrame.setVisible(false);
		setVisible(true);
	
	
	}
	
	public static boolean isWindows() {
 
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
 
	}
	
	public void gameBack(){
		
		
		gameplayScreen.setVisible(false);
		gameplayScreen.gameLogic.achiev.saveAchievs();
		gameplayScreen.saveHighscores();
		setVisible(true);
		gameplayScreen.runner.stop();
		
	
	}
	
	public void newClient(){
		
		gameplayScreen = null;
		gameplayScreen = new MDClient(this);
		
			loadHighscores();
			gameplayScreen.gameLogic.achiev.loadAchievs();
		
	}
	
	private class BackButtonListener implements ActionListener
	{
			public void actionPerformed(ActionEvent e)
			{
				backToMenu();
			
			}
	
	}
	
	private class GameBackListener implements ActionListener
	{
			public void actionPerformed(ActionEvent e)
			{	
				gameplayScreen.gameLogic.insertHighscore();
				gameplayScreen.saveHighscores();
				gameBack();
			
			}
	
	}
	
	private class AchievBackListener implements ActionListener
	{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(true);
				gameplayScreen.gameLogic.achiev.achievementFrame.setVisible(false);
			
			}
	
	}
	
	private class AchievementButtonListener implements ActionListener
	{
	
			public void actionPerformed(ActionEvent e)
			{
				try{
					
					gameplayScreen.gameLogic.achiev.loadAchievs();
					
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Unable to load highscores");
					ex.printStackTrace();
				}	
						
				setVisible(false);
				gameplayScreen.setVisible(false);
				highscoreFrame.setVisible(false);
				gameplayScreen.gameLogic.achiev.achievementFrame.setVisible(true);
				
	
			}
	}
	
	private class ExitButtonListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
		
			
			gameplayScreen.saveHighscores();
			gameplayScreen.gameLogic.achiev.saveAchievs();
			
			System.exit(0);
		
		
		}
	
	}
	
	private class StartButtonListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
			
			newClient();
			gameplayScreen.runner.start();
			gameplayScreen.input.requestFocus();
			gameplayScreen.setVisible(true);
			setVisible(false);
		
		}
	}
	
	private class HighscoreButtonListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e)
		{
			try{
			loadHighscores();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Unable to load highscores");
				ex.printStackTrace();
			}	
					
			setVisible(false);
			gameplayScreen.setVisible(false);
			highscoreFrame.setVisible(true);
			
		}

	}
	
	
	
	public static void main(String [] args){
	
	
	  new MenuFrame();
	
	
	
	}
	
}	

