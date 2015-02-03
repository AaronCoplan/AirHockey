package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.Font;
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
public class FourPersonServerGAME {

	private JFrame frame;
	private Puck puck;
	private JLabel hostGoal, opponent1Goal, hostScore, opponent1Score;
	private Paddle opponent1Paddle, hostPaddle;
	
	private double puckSpeed, puckVX, puckVY, puckDIRX, puckDIRY;
	private int puckX, puckY;
	
	private int hostPaddleX, hostPaddleY, opponent1PaddleX, opponent1PaddleY;
	private int hostNumGoals = 0, opp1NumGoals = 0;
	
	private int hostPaddleSpeed = 0, opponent1PaddleSpeed = 0;
	private Point opponent1PreviousPoint = new Point(0, 0);
	private Cursor blankCursor;
	
	private Robot robot;
	private FourPersonServer server;
	
	private final double FRICTION = 1.0045, ENERGY_TRANSFER = 1.00023;
	private final int PADDLE_DIAMETER = 50;
	
	public FourPersonServerGAME(FourPersonServer server){
		
		this.server = server;
		setUp();
	}
	
	public void setUp(){
		
		puckSpeed = 0;
		puckDIRX = 1;
		puckDIRY = 1;
		
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
		puck.setBounds(222-puck.getRadius(), 286-puck.getRadius(), 30, 30);
		ImageIcon puckIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/puck.png"));
		puck.setIcon(puckIcon);
		
		hostPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/redPaddle.png"));
		hostPaddle.setIcon(userPaddleIcon);
		hostPaddle.setBounds(222-hostPaddle.getRadius(), 286+200-hostPaddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent1Paddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/bluePaddle.png"));
		opponent1Paddle.setIcon(opponentPaddleIcon);
		opponent1Paddle.setBounds(222-opponent1Paddle.getRadius(), 286-200-opponent1Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		hostGoal = new JLabel();
		hostGoal.setOpaque(true);
		hostGoal.setBackground(Color.red);
		hostGoal.setBounds(157, 522, 130, 50);
		
		opponent1Goal = new JLabel();
		opponent1Goal.setOpaque(true);
		opponent1Goal.setBackground(Color.blue);
		opponent1Goal.setBounds(157, 0, 130, 50);
		
		hostScore = new JLabel(server.getHostName() + ": " + hostNumGoals, JLabel.CENTER);
		hostScore.setOpaque(true);
		hostScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		hostScore.setForeground(Color.red);
		hostScore.setBackground(Color.black);
		hostScore.setBounds(15, 532, 90, 30);
		
		opponent1Score = new JLabel(server.getOpponent1Name() + ": " + opp1NumGoals, JLabel.CENTER);
		opponent1Score.setOpaque(true);
		opponent1Score.setFont(new Font("Arial Bold", Font.BOLD, 15));
		opponent1Score.setForeground(Color.blue);
		opponent1Score.setBackground(Color.black);
		opponent1Score.setBounds(15, 10, 90, 30);
		
		JLabel back = new JLabel();
		back.setBounds(50, 50, 344, 472);
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/woodBack.png"));
		back.setIcon(icon);
		
		//Add a midline here
		//center y value is 236
		JLabel midline = new JLabel();
		midline.setOpaque(true);
		midline.setBackground(Color.black);
		midline.setBounds(50,276,344,20);
		
		JLabel walls = new JLabel();
		walls.setBounds(0, 0, 444, 572);
		ImageIcon wallsIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/border.jpg"));
		walls.setIcon(wallsIcon);
		
		frame.add(puck);
		frame.add(hostPaddle);
		frame.add(opponent1Paddle);
		frame.add(hostGoal);
		frame.add(opponent1Goal);
		frame.add(hostScore);
		frame.add(opponent1Score);
		frame.add(back);
		frame.add(walls);
		frame.add(midline);
		frame.setSize(450, 600);
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
			
			//read data
			//String input = server.readPositions();
			
			//check if goal and print accordingly
			if(checkIfUserGoal()){
				//server.send("User Goal");
				goal(opponent1Goal);
			}else if(checkIfOpponentGoal()){
				//server.send("Opponent Goal");
				goal(hostGoal);
			}else{
				try{
					//server.send(puckX + " " + puckY + " " + userPaddleX + " " + userPaddleY);
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Error with Print Writer!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			//opponentPaddleX = Integer.parseInt(input.substring(0, input.indexOf(" ")));
			//opponentPaddleY = Integer.parseInt(input.substring(input.indexOf(" ") + 1));
			
			calculateOpponentPaddleSpeed();
			
			puckX = puck.getX();
			puckY = puck.getY();
			
			wallCollision();
			puckMove();
			wallCollision();
			
			puck.setBounds(puckX, puckY, 30, 30);
			hostPaddle.setBounds(hostPaddleX, hostPaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent1Paddle.setBounds(opponent1PaddleX, opponent1PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			
			friction();
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
		}
		
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	public void calculateOpponentPaddleSpeed(){
		
		int opponentPaddleCX  = opponent1PaddleX + opponent1Paddle.getRadius();
		int opponentPaddleCY = opponent1PaddleY + opponent1Paddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);
		
		opponent1PaddleSpeed = (int)((currentPoint.distance(opponent1PreviousPoint)) / ENERGY_TRANSFER);
		opponent1PreviousPoint = currentPoint;
		
	}
	
	public void friction(){
		
		puckSpeed /= FRICTION;
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
		opponent1Score.setText(server.getOpponent1Name() + ": " + opp1NumGoals);
		hostScore.setText(server.getHostName() + ": " + hostNumGoals);
		
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
		puckSpeed = 0;
		setMousePosition();
	}
	
	public boolean checkIfUserGoal(){
		
		if(puck.getY() <= 50){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius()) <= 287){
				return true;
			}
		}
			
		return false;
	}
	
	public boolean checkIfOpponentGoal(){
		
		if((puck.getY() + puck.getDiameter()) >= 521){
			if((puck.getCX() - puck.getRadius()) >= 157 && (puck.getCX() + puck.getRadius() <= 287)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public void puckStep(){
		
		puckVX = puckSpeed*puckDIRX;
		puckVY = puckSpeed*puckDIRY;
		
		puckX += (int)puckVX;
		puckY += (int)puckVY;
		
		wallCollision();
	}
	
	public void puckMove(){
		
		puckStep();
		if(userPaddleCollision()){
			
			puckDIRX = (puck.getCX() - hostPaddle.getCX()) / (Math.sqrt(((puck.getCX() - hostPaddle.getCX()) * (puck.getCX() - hostPaddle.getCX())) + ((puck.getCY() - hostPaddle.getCY()) * (puck.getCY() - hostPaddle.getCY()))));
			puckDIRY = (puck.getCY() - hostPaddle.getCY()) / (Math.sqrt(((puck.getCX() - hostPaddle.getCX()) * (puck.getCX() - hostPaddle.getCX())) + ((puck.getCY() - hostPaddle.getCY()) * (puck.getCY() - hostPaddle.getCY()))));
			if(hostPaddleSpeed < 5){
				puckSpeed += hostPaddleSpeed;
			}else if(hostPaddleSpeed <= 15){//could change 15
				puckSpeed = hostPaddleSpeed;
			}else{
				puckSpeed = 15;
			}
			puckStep();
		}else if(opponentPaddleCollision()){
			
			puckDIRX = (puck.getCX() - opponent1Paddle.getCX()) / (Math.sqrt(((puck.getCX() - opponent1Paddle.getCX()) * (puck.getCX() - opponent1Paddle.getCX())) + ((puck.getCY() - opponent1Paddle.getCY()) * (puck.getCY() - opponent1Paddle.getCY()))));
			puckDIRY = (puck.getCY() - opponent1Paddle.getCY()) / (Math.sqrt(((puck.getCX() - opponent1Paddle.getCX()) * (puck.getCX() - opponent1Paddle.getCX())) + ((puck.getCY() - opponent1Paddle.getCY()) * (puck.getCY() - opponent1Paddle.getCY()))));
			if(opponent1PaddleSpeed < 5){
				puckSpeed += opponent1PaddleSpeed;
			}else if(hostPaddleSpeed <= 15){
				puckSpeed = opponent1PaddleSpeed;
			}else{
				puckSpeed = 15;
			}
			puckStep();
		}
		
	}
	
	
	public boolean userPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - hostPaddle.getCX()) * (puck.getCX() - hostPaddle.getCX()) + ((puck.getCY() - hostPaddle.getCY()) * (puck.getCY() - hostPaddle.getCY())));
		if(distance <= (puck.getRadius() + hostPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean opponentPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - opponent1Paddle.getCX()) * (puck.getCX() - opponent1Paddle.getCX()) + ((puck.getCY() - opponent1Paddle.getCY()) * (puck.getCY() - opponent1Paddle.getCY())));
		if(distance <= (puck.getRadius() + opponent1Paddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public void wallCollision(){
		
		if(puck.getX() <= 50 && puck.getY() <= 50){ //upper left corner
			puckDIRX = Math.abs(puckDIRX);
			puckDIRY = Math.abs(puckDIRY);
		}else if(puck.getX() <= 50 && (puck.getY() + puck.getDiameter()) >= 522){ //bottom left corner
			puckDIRX= Math.abs(puckDIRX);
			puckDIRY= -Math.abs(puckDIRY);
		}else if((puck.getX() + puck.getDiameter()) >= 394 && puck.getY() <= 50){ //upper right corner
			puckDIRX= -Math.abs(puckDIRX);
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getX() + puck.getDiameter()) >= 394 && (puck.getY() + puck.getDiameter()) >= 522){ //bottom right corner
			puckDIRX = -Math.abs(puckDIRX);
			puckDIRY = -Math.abs(puckDIRY);
		}else if(puck.getX() <= 50 /*left wall border*/){
			puckDIRX = Math.abs(puckDIRX);
		}else if((puck.getX() + puck.getDiameter()) >= 394 /*right wall border*/){
			puckDIRX = -Math.abs(puckDIRX);
		}else if(puck.getY() <= 50 /*top wall border*/){
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getY() + puck.getDiameter()) >= 522 /*bottom wall border*/){
			puckDIRY = -Math.abs(puckDIRY);
		}
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
			
			if((mouseX - hostPaddle.getRadius()) >= 50 && (mouseX + hostPaddle.getRadius()) <= 394 && (mouseY - hostPaddle.getDiameter()) >= 50 && ((mouseY) >= 322 && (mouseY)<=522)){
				frame.setCursor(blankCursor);
				hostPaddleX = mouseX - hostPaddle.getRadius();
				hostPaddleY = mouseY - hostPaddle.getDiameter();
			}else{
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}
			
			hostPaddleSpeed = (int)((e.getPoint().distance(userPreviousPoint)) / ENERGY_TRANSFER);
			
			userPreviousPoint = e.getPoint();
		}
	}
}
