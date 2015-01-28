package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Playback {

	private static JFrame frame;
	private static Puck puck;
	private static Paddle userPaddle, computerPaddle;
	
	public static void main(String[] args) throws Exception{
		
		Playback playback = new Playback();
		
		File file = new File("C:\\Users\\Coplans5\\Desktop\\GAME.txt");
		Scanner sc = new Scanner(file);
		playback.setUp();
		
		while(sc.hasNext()){
			
			int puckX = Integer.parseInt(sc.nextLine());
			int puckY = Integer.parseInt(sc.nextLine());
			int userPaddleX = Integer.parseInt(sc.nextLine());
			int userPaddleY = Integer.parseInt(sc.nextLine());
			int opponentPaddleX = Integer.parseInt(sc.nextLine());
			int opponentPaddleY = Integer.parseInt(sc.nextLine());
			
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);
			computerPaddle.setBounds(opponentPaddleX, opponentPaddleY, 50, 50);
			
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){e.printStackTrace();}
		}
		
		sc.close();
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
