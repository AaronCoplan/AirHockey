package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED BUT CURRENTLY NOT IN USE

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class Playback {

	private static JFrame frame;
	private static Puck puck;
	private static Paddle userPaddle, computerPaddle;
	private static ArrayList<Integer>coords = new ArrayList<Integer>();
	
	public static void main(String[] args) throws Exception{
		
		Playback playback = new Playback(coords);
		
		//File file = new File("U:\\GAME.txt");
		//Scanner sc = new Scanner(file);
		playback.setUp();
		
		for(int x = coords.size()-600;x<coords.size();x+=6){
			
			int puckX = coords.get(x);
			int puckY = coords.get(x+1);
			int userPaddleX = coords.get(x+2);
			int userPaddleY = coords.get(x+3);
			int opponentPaddleX = coords.get(x+4);
			int opponentPaddleY = coords.get(x+5);
			
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);
			computerPaddle.setBounds(opponentPaddleX, opponentPaddleY, 50, 50);
			
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){e.printStackTrace();}
		}
		
		//sc.close();
	}
	public Playback(ArrayList<Integer> coordinates)
	{
		for(int x = 0;x<coordinates.size();x++)
		{
			coords.add(coordinates.get(x));
		}
	}
	
	public void setUp(){

		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);

		puck = new Puck();
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);

		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/redPaddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), 50 ,50);

		computerPaddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/bluePaddle.png"));
		computerPaddle.setIcon(opponentPaddleIcon);
		computerPaddle.setBounds(222-computerPaddle.getRadius(), 286-200-computerPaddle.getRadius(), 50, 50);

		JLabel userGoal = new JLabel();
		userGoal.setOpaque(true);
		userGoal.setBackground(Color.red);
		userGoal.setBounds(172, 522, 100, 50);

		JLabel opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		opponentGoal.setBackground(Color.blue);
		opponentGoal.setBounds(172, 0, 100, 50);

		JLabel userScore = new JLabel("You: ", JLabel.CENTER);
		userScore.setOpaque(true);
		userScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		userScore.setForeground(Color.red);
		userScore.setBackground(Color.black);
		userScore.setBounds(15, 532, 90, 30);

		JLabel opponentScore = new JLabel("Computer: ", JLabel.CENTER);
		opponentScore.setOpaque(true);
		opponentScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		opponentScore.setForeground(Color.blue);
		opponentScore.setBackground(Color.black);
		opponentScore.setBounds(15, 10, 90, 30);

		JLabel board = new JLabel();
		board.setBounds(50, 50, 344, 472);
		ImageIcon boardIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/woodBack.png"));
		board.setIcon(boardIcon);

		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border.jpg"));
		walls.setIcon(wallsIcon);

		frame.add(puck);
		frame.add(userPaddle);
		frame.add(computerPaddle);
		frame.add(userGoal);
		frame.add(opponentGoal);
		frame.add(userScore);
		frame.add(opponentScore);
		frame.add(board);
		frame.add(walls);
		frame.setSize(450, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
