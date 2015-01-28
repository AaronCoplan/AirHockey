package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.image.*;

//true width is 444, true height is 572
public class ComputerGAME {

	private JFrame frame;
	private Puck puck;
	private JLabel userGoal, opponentGoal, userScore, opponentScore;
	private Paddle computerPaddle, userPaddle;
	
	private JLabel attackmark;
	
	private double puckSpeed, puckVX, puckVY, puckDIRX, puckDIRY;
	private int puckX, puckY;
	
	private int userPaddleX, userPaddleY;
	private int yourNumGoals = 0, oppNumGoals = 0;
	
	private int userPaddleSpeed = 0, opponentPaddleSpeed = 0;
	private Point opponentPreviousPoint = new Point(0, 0);
	private Cursor blankCursor;
	
	private AI ai;
	private Robot robot;
	
	public ComputerGAME(){
		
		ai = new AI(4);
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
		
		userPaddle = new Paddle();
		ImageIcon userPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/redPaddle.png"));
		userPaddle.setIcon(userPaddleIcon);
		userPaddle.setBounds(222-userPaddle.getRadius(), 286+200-userPaddle.getRadius(), 50 ,50);
		
		computerPaddle = new Paddle();
		ImageIcon opponentPaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/bluePaddle.png"));
		computerPaddle.setIcon(opponentPaddleIcon);
		computerPaddle.setBounds(222-computerPaddle.getRadius(), 286-200-computerPaddle.getRadius(), 50, 50);
		
		userGoal = new JLabel();
		userGoal.setOpaque(true);
		userGoal.setBackground(Color.red);
		userGoal.setBounds(172, 522, 100, 50);
		
		opponentGoal = new JLabel();
		opponentGoal.setOpaque(true);
		opponentGoal.setBackground(Color.blue);
		opponentGoal.setBounds(172, 0, 100, 50);
		
		userScore = new JLabel("You: " + yourNumGoals, JLabel.CENTER);
		userScore.setOpaque(true);
		userScore.setFont(new Font("Arial Bold", Font.BOLD, 15));
		userScore.setForeground(Color.red);
		userScore.setBackground(Color.black);
		userScore.setBounds(15, 532, 90, 30);
		
		opponentScore = new JLabel("Computer: " + oppNumGoals, JLabel.CENTER);
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
		
		this.attackmark = new JLabel("", JLabel.CENTER);
		attackmark.setBounds(344, 0, 100, 50);
		attackmark.setOpaque(true);
		
		frame.add(puck);
		frame.add(userPaddle);
		frame.add(computerPaddle);
		frame.add(userGoal);
		frame.add(opponentGoal);
		frame.add(userScore);
		frame.add(opponentScore);
		frame.add(attackmark);
		frame.add(board);
		frame.add(walls);
		frame.setSize(450, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setMousePosition();
		run();
	}
	
	public void setMousePosition(){
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = ((int)dimension.getWidth() / 2);
		int y = ((int)dimension.getHeight() / 2) + 186 - userPaddle.getRadius();
		
		robot.mouseMove(x, y);
	}
	
	public void run(){
		
		while(yourNumGoals < 7 && oppNumGoals < 7){
			
			ai.move(computerPaddle, puck, puckDIRX, puckDIRY, puckSpeed, attackmark);
			
			if(checkIfUserGoal()){
				System.out.println("User Goal");
				goal(opponentGoal);
			}else if(checkIfOpponentGoal()){
				System.out.println("Opponent Goal");
				goal(userGoal);
			}
			
			calculateOpponentPaddleSpeed();
			
			puckX = puck.getX();
			puckY = puck.getY();
			
			wallCollision();
			puckMove();
			wallCollision();
			
			puck.setBounds(puckX, puckY, 30, 30);
			userPaddle.setBounds(userPaddleX, userPaddleY, 50, 50);
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
		}
		
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	public void calculateOpponentPaddleSpeed(){
		
		int opponentPaddleCX  = computerPaddle.getBounds().x + computerPaddle.getRadius();
		int opponentPaddleCY = computerPaddle.getBounds().y + computerPaddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);
		
		opponentPaddleSpeed = (int)((currentPoint.distance(opponentPreviousPoint)) / 1.00023);
		opponentPreviousPoint = currentPoint;
		
	}
	
	public void friction(){
		
		puckSpeed /= 1.0045;
		/*if(puckSpeed > 0){
			puckSpeed -= 0.009;
		}*/
	}
	
	public void goal(JLabel goal){
		puck.setVisible(false);
		puck.setBounds(0, 0, 30, 30);
		Color normalColor = goal.getBackground();
		
		if(goal.equals(userGoal)){
			oppNumGoals++;
		}else{
			yourNumGoals++;
		}
		opponentScore.setText("Computer: " + oppNumGoals);
		userScore.setText("You: " + yourNumGoals);
		
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
		userPaddle.setBounds(222-25, 286+200-25, 50 ,50);
		computerPaddle.setBounds(222-25, 286-200-25, 50, 50);
		puckSpeed = 0;
		setMousePosition();
	}
	
	public boolean checkIfUserGoal(){
		
		if(puck.getY() <= 50){
			if((puck.getCX() - puck.getRadius()) >= 172 && (puck.getCX() + puck.getRadius()) <= 272){
				return true;
			}
		}
			
		return false;
	}
	
	public boolean checkIfOpponentGoal(){
		
		if((puck.getY() + puck.getDiameter()) >= 521){
			if((puck.getCX() - puck.getRadius()) >= 172 && (puck.getCX() + puck.getRadius() <= 272)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public void puckStep(){
		
		friction();
		puckVX = puckSpeed*puckDIRX;
		puckVY = puckSpeed*puckDIRY;
		puckX += (int)puckVX;
		puckY += (int)puckVY;
		
		wallCollision();
	}
	
	public void puckMove(){
		
		puckStep();
		if(userPaddleCollision()){
			
			puckDIRX = (puck.getCX() - userPaddle.getCX()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));
			puckDIRY = (puck.getCY() - userPaddle.getCY()) / (Math.sqrt(((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX())) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY()))));
			if(userPaddleSpeed < 5){
				puckSpeed += userPaddleSpeed;
			}else if(userPaddleSpeed <= 15){
				puckSpeed = userPaddleSpeed;
			}else{
				puckSpeed = 15;
			}
			puckStep();
		}else if(opponentPaddleCollision()){
			
			puckDIRX = (puck.getCX() - computerPaddle.getCX()) / (Math.sqrt(((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX())) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY()))));
			puckDIRY = (puck.getCY() - computerPaddle.getCY()) / (Math.sqrt(((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX())) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY()))));
			if(opponentPaddleSpeed < 5){
				puckSpeed += opponentPaddleSpeed;
			}else if(userPaddleSpeed <= 15){
				puckSpeed = opponentPaddleSpeed;
			}else{
				puckSpeed = 15;
			}
			puckStep();
		}
		
	}
	
	
	public boolean userPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - userPaddle.getCX()) * (puck.getCX() - userPaddle.getCX()) + ((puck.getCY() - userPaddle.getCY()) * (puck.getCY() - userPaddle.getCY())));
		if(distance <= (puck.getRadius() + userPaddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean opponentPaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - computerPaddle.getCX()) * (puck.getCX() - computerPaddle.getCX()) + ((puck.getCY() - computerPaddle.getCY()) * (puck.getCY() - computerPaddle.getCY())));
		if(distance <= (puck.getRadius() + computerPaddle.getRadius())){
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
			
			if((mouseX - userPaddle.getRadius()) >= 50 && (mouseX + userPaddle.getRadius()) <= 394 && (mouseY - userPaddle.getDiameter()) >= 50 && (mouseY) <= 522){
				frame.setCursor(blankCursor);
				userPaddleX = mouseX - userPaddle.getRadius();
				userPaddleY = mouseY - userPaddle.getDiameter();
			}else{
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}
			
			userPaddleSpeed = (int)((e.getPoint().distance(userPreviousPoint)) / 1.00023);
			
			userPreviousPoint = e.getPoint();
		}
	}
}
