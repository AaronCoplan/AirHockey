package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Point;
import java.awt.Robot;
import java.awt.Cursor;
import java.awt.AWTException;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JOptionPane;

//true width is 444, true height is 572
public class FourPersonClientGAME {

	private JFrame frame;
	private Puck puck;
	private JLabel hostGoal, opponent1Goal, opponent2Goal, opponent3Goal;
	private JLabel hostScore, opponent1Score; //these need created and added to the board
	private Paddle opponent1Paddle, opponent2Paddle, opponent3Paddle, hostPaddle;
	
	private int puckX, puckY;
	
	private int hostPaddleX, hostPaddleY, opponent1PaddleX, opponent1PaddleY, opponent2PaddleX, opponent2PaddleY, opponent3PaddleX, opponent3PaddleY;
	private int hostNumGoals = 0, opp1NumGoals = 0;
	
	private Cursor blankCursor;
	
	private Robot robot;
	private FourPersonClient client;
	
	private final int PADDLE_DIAMETER = 50;
	private final int PLAYER_NUMBER;
	
	public FourPersonClientGAME(FourPersonClient client, int PLAYER_NUMBER){
		
		this.client = client;
		this.PLAYER_NUMBER = PLAYER_NUMBER;
		setUp();
	}
	
	public void setUp(){
		
		
		try{
			this.robot = new Robot();
		}catch(AWTException e){
			System.out.println("Could not instantiate robot...");
		}
		
		frame = new JFrame("Air Hockey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.addMouseMotionListener(new ML());
		
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.setCursor(blankCursor);
		
		puck = new Puck();
		puck.setBounds(297-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);
		
		hostPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/redPaddle.png"));
		hostPaddle.setIcon(userPaddleIcon);
		hostPaddle.setBounds(297-hostPaddle.getRadius(), 286+200-hostPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent1Paddle = new Paddle();
		ImageIcon opponent1PaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/bluePaddle.png"));
		opponent1Paddle.setIcon(opponent1PaddleIcon);
		opponent1Paddle.setBounds(297-opponent1Paddle.getRadius(), 286-200-opponent1Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent2Paddle = new Paddle();
		ImageIcon opponent2PaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/purplePaddle.png"));
		opponent2Paddle.setIcon(opponent2PaddleIcon);
		opponent2Paddle.setBounds(50+15, 286-opponent2Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent3Paddle = new Paddle();
		ImageIcon opponent3PaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/greenPaddle.png"));
		opponent3Paddle.setIcon(opponent3PaddleIcon);
		opponent3Paddle.setBounds(544-65, 286-opponent3Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		hostGoal = new JLabel();
		hostGoal.setOpaque(true);
		hostGoal.setBackground(Color.red);
		hostGoal.setBounds(297-65, 522, 130, 50);
		
		opponent1Goal = new JLabel();
		opponent1Goal.setOpaque(true);
		opponent1Goal.setBackground(Color.blue);
		opponent1Goal.setBounds(297-65, 0, 130, 50);
		
		opponent2Goal = new JLabel();
		opponent2Goal.setOpaque(true);
		opponent2Goal.setBackground(Color.magenta);
		opponent2Goal.setBounds(0, 236+50-65, 50, 130);
		
		opponent3Goal = new JLabel();
		opponent3Goal.setOpaque(true);
		opponent3Goal.setBackground(Color.green);
		opponent3Goal.setBounds(544, 236+50-65, 50, 130);
		
		JLabel back = new JLabel();
		back.setBounds(50, 50, 594, 472);
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/woodBack.png"));
		back.setIcon(icon);
		
		JLabel tempLeftWall = new JLabel();
		tempLeftWall.setOpaque(true);
		tempLeftWall.setBackground(Color.black);
		tempLeftWall.setBounds(0, 0, 50, 600);
		
		JLabel tempRightWall = new JLabel();
		tempRightWall.setOpaque(true);
		tempRightWall.setBackground(Color.black);
		tempRightWall.setBounds(544, 0, 50, 600);
		
		JLabel tempTopWall = new JLabel();
		tempTopWall.setOpaque(true);
		tempTopWall.setBackground(Color.black);
		tempTopWall.setBounds(0, 0, 600, 50);
		
		JLabel tempBottomWall = new JLabel();
		tempBottomWall.setOpaque(true);
		tempBottomWall.setBackground(Color.black);
		tempBottomWall.setBounds(0, 522, 600, 50);
		
		frame.add(puck);
		frame.add(hostPaddle);
		frame.add(opponent1Paddle);
		frame.add(opponent2Paddle);
		frame.add(opponent3Paddle);
		frame.add(hostGoal);
		frame.add(opponent1Goal);
		frame.add(opponent2Goal);
		frame.add(opponent3Goal);
		frame.add(tempLeftWall);
		frame.add(tempRightWall);
		frame.add(tempTopWall);
		frame.add(tempBottomWall);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setMousePosition();
		run();
	}
	
	public void setMousePosition(){
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//getBounds?
		int x = ((int)dimension.getWidth() / 2);
		int y = ((int)dimension.getHeight() / 2) + 186 - hostPaddle.getRadius();
		
		robot.mouseMove(x, y);
	}
	
	public void run(){
		
		while(hostNumGoals < 7 && opp1NumGoals < 7){
			
			try{
				if(PLAYER_NUMBER == 1){
					client.sendLocations(opponent1PaddleX, opponent1PaddleY);
				}else if(PLAYER_NUMBER == 2){
					client.sendLocations(opponent2PaddleX, opponent2PaddleY);
				}else if(PLAYER_NUMBER == 3){
					client.sendLocations(opponent3PaddleX, opponent3PaddleY);
				}
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, "Error with Print Writer!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}

			String input = null;
			try{
				input = client.readAllLocations();
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, "Error with Buffered Reader!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
			if(false/*handle goals*/){ 
				//goal action
			}else if(false/*handle goals*/){ 
				//goal action
			}else if(false/*handle goals*/){
				//goal action
			}else if(false/*handle goals*/){
				//goal action
			}else{ 
				if(PLAYER_NUMBER == 1){
					int index;
					hostPaddleX = Integer.parseInt(input.substring(0, (index = input.indexOf(" "))));
					input = input.substring(index+1);
				}else if(PLAYER_NUMBER == 2){
					
				}else if(PLAYER_NUMBER == 3){
					
				}
			}
			
			puck.setBounds(puckX, puckY, 30, 30);
			hostPaddle.setBounds(hostPaddleX, hostPaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent1Paddle.setBounds(opponent1PaddleX, opponent1PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent2Paddle.setBounds(opponent2PaddleX, opponent2PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent3Paddle.setBounds(opponent3PaddleX, opponent3PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
		}
		
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	public void goal(JLabel goal){
		puck.setVisible(false);
		puck.setBounds(0, 0, 30, 30);
		Color normalColor = goal.getBackground();
		
		if(goal.equals(hostGoal)){
			opp1NumGoals++;
		}else{
			hostNumGoals++;
		}
		opponent1Score.setText(client.getYourName() + ": " + opp1NumGoals);
		hostScore.setText(client.getHostName() + ": " + hostNumGoals);
		
		for(int x = 0; x < 25; x++){
			if((x % 2)== 0){
				goal.setBackground(Color.white);
			}else{
				goal.setBackground(normalColor);
			}
			
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		goal.setBackground(normalColor);
		
		//reset everything to original positions
		puck.setVisible(true);
		puck.setBounds(222-15, 286-15, 30, 30);
		hostPaddle.setBounds(222-25, 286+200-25, 50 ,50);
		opponent1Paddle.setBounds(222-25, 286-200-25, 50, 50);
		setMousePosition();
	}
	
	private class ML implements MouseMotionListener{

		private Point userPreviousPoint = new Point(0, 0);
		//DO THE SAME THING FOR BOTH DRAGGED AND MOVED
		public void mouseDragged(MouseEvent e) {
			mouseEvent(e);
		}
		
		public void mouseMoved(MouseEvent e) {
			mouseEvent(e);
		}
		
		public void mouseEvent(MouseEvent e){
			
			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;
			
			//if((mouseX - hostPaddle.getRadius()) >= 50 && (mouseX + hostPaddle.getRadius()) <= 394 && (mouseY - hostPaddle.getDiameter()) >= 50 && ((mouseY) >= 322 && (mouseY)<=522)){
				frame.setCursor(blankCursor);
				hostPaddleX = mouseX - hostPaddle.getRadius();
				hostPaddleY = mouseY - hostPaddle.getDiameter();
			/*}else{
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}*/
		}
	}
}
