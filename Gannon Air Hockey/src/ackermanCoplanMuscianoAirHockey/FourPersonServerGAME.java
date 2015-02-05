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
public class FourPersonServerGAME {

	private JFrame frame;
	private Puck puck;
	private JLabel hostGoal, opponent1Goal, opponent2Goal, opponent3Goal;
	private JLabel hostScore, opponent1Score; //these need created and added to the board
	private Paddle opponent1Paddle, opponent2Paddle, opponent3Paddle, hostPaddle;
	
	private double puckSpeed, puckVX, puckVY, puckDIRX, puckDIRY;
	private int puckX, puckY;
	
	private int hostPaddleX, hostPaddleY, opponent1PaddleX, opponent1PaddleY, opponent2PaddleX, opponent2PaddleY, opponent3PaddleX, opponent3PaddleY;
	private int hostNumGoals = 0, opp1NumGoals = 0;
	
	private int hostPaddleSpeed = 0, opponent1PaddleSpeed = 0, opponent2PaddleSpeed = 0, opponent3PaddleSpeed = 0;
	private Point opponent1PreviousPoint = new Point(0, 0), opponent2PreviousPoint = new Point(0, 0), opponent3PreviousPoint = new Point(0, 0);
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
		//opponent1Paddle.setBounds(297-opponent1Paddle.getRadius(), 286-200-opponent1Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent2Paddle = new Paddle();
		ImageIcon opponent2PaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/purplePaddle.png"));
		opponent2Paddle.setIcon(opponent2PaddleIcon);
		//opponent2Paddle.setBounds(50+15, 286-opponent2Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
		opponent3Paddle = new Paddle();
		ImageIcon opponent3PaddleIcon = new ImageIcon(this.getClass().getClassLoader().getResource("ackermanCoplanMuscianoAirHockey/greenPaddle.png"));
		opponent3Paddle.setIcon(opponent3PaddleIcon);
		//opponent3Paddle.setBounds(544-65, 286-opponent3Paddle.getRadius(), PADDLE_DIAMETER, PADDLE_DIAMETER);
		
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
			
			//read data
			String opp1Locations = server.readOpponent1Locations();
			String opp2Locations = server.readOpponent2Locations();
			String opp3Locations = server.readOpponent3Locations();
			
			//check if goal and print accordingly
			if(checkIfHostScoredOn()){
				System.out.println("Hit red goal");
			}else if(checkIfOpponent1ScoredOn()){
				System.out.println("Hit blue goal");
			}else if(checkIfOpponent2ScoredOn()){
				System.out.println("Hit magenta goal");
			}else if(checkIfOpponent3ScoredOn()){
				System.out.println("Hit green goal");
			}else{
				try{
					server.sendLocations(hostPaddleX, hostPaddleY, opponent1PaddleX, opponent1PaddleY, opponent2PaddleX, opponent2PaddleY, opponent3PaddleX, opponent3PaddleY, puckX, puckY);
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Error with Print Writer!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			int index;
			opponent1PaddleX = Integer.parseInt(opp1Locations.substring(0, (index = opp1Locations.indexOf(" "))));
			opponent1PaddleY = Integer.parseInt(opp1Locations.substring(index+1));
			opponent2PaddleX = Integer.parseInt(opp2Locations.substring(0,  (index = opp2Locations.indexOf(" "))));
			opponent2PaddleY = Integer.parseInt(opp2Locations.substring(index+1));
			opponent3PaddleX = Integer.parseInt(opp3Locations.substring(0, (index = opp3Locations.indexOf(" "))));
			opponent3PaddleY = Integer.parseInt(opp3Locations.substring(index+1));
			
			calculateOpponent1PaddleSpeed();
			calculateOpponent2PaddleSpeed();
			calculateOpponent3PaddleSpeed();
			
			puckX = puck.getX();
			puckY = puck.getY();
			
			wallCollision();
			puckMove();
			wallCollision();
			
			puck.setBounds(puckX, puckY, 30, 30);
			hostPaddle.setBounds(hostPaddleX, hostPaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent1Paddle.setBounds(opponent1PaddleX, opponent1PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent2Paddle.setBounds(opponent2PaddleX, opponent2PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			opponent3Paddle.setBounds(opponent3PaddleX, opponent3PaddleY, PADDLE_DIAMETER, PADDLE_DIAMETER);
			
			friction();
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){}
		}
		
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	public void calculateOpponent1PaddleSpeed(){
		
		int opponentPaddleCX  = opponent1PaddleX + opponent1Paddle.getRadius();
		int opponentPaddleCY = opponent1PaddleY + opponent1Paddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);
		
		opponent1PaddleSpeed = (int)((currentPoint.distance(opponent1PreviousPoint)) / ENERGY_TRANSFER);
		opponent1PreviousPoint = currentPoint;
	}
	
	public void calculateOpponent2PaddleSpeed(){

		int opponentPaddleCX  = opponent2PaddleX + opponent2Paddle.getRadius();
		int opponentPaddleCY = opponent2PaddleY + opponent2Paddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);

		opponent2PaddleSpeed = (int)((currentPoint.distance(opponent2PreviousPoint)) / ENERGY_TRANSFER);
		opponent2PreviousPoint = currentPoint;
	}

	public void calculateOpponent3PaddleSpeed(){

		int opponentPaddleCX  = opponent3PaddleX + opponent3Paddle.getRadius();
		int opponentPaddleCY = opponent3PaddleY + opponent3Paddle.getRadius();
		Point currentPoint = new Point(opponentPaddleCX, opponentPaddleCY);

		opponent3PaddleSpeed = (int)((currentPoint.distance(opponent3PreviousPoint)) / ENERGY_TRANSFER);
		opponent3PreviousPoint = currentPoint;
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
	
	
	public boolean checkIfOpponent1ScoredOn(){
		
		if(puck.getY() <= opponent1Goal.getBounds().getMaxY()){
			if((puck.getCX() - puck.getRadius()) >= opponent1Goal.getBounds().getMinX() && (puck.getCX() + puck.getRadius()) <= opponent1Goal.getBounds().getMaxX()){
				return true;
			}
		}	
		return false;
	}
	
	public boolean checkIfHostScoredOn(){
		
		if((puck.getY() + puck.getDiameter()) >= 521){
			if((puck.getCX() - puck.getRadius()) >= hostGoal.getBounds().getMinX() && (puck.getCX() + puck.getRadius()) <= hostGoal.getBounds().getMaxX()){
				return true;
			}
		}
		return false;	
	}
	
	public boolean checkIfOpponent2ScoredOn(){
		
		if(puck.getX() <= 50){
			if((puck.getCY() - puck.getRadius()) >= opponent2Goal.getBounds().getMinY() && (puck.getCY() + puck.getRadius()) <= opponent2Goal.getBounds().getMaxY()){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIfOpponent3ScoredOn(){
		
		if((puck.getX() + puck.getDiameter()) >= 544){
			if((puck.getCY() - puck.getRadius()) >= opponent3Goal.getBounds().getMinY() && (puck.getCY() + puck.getRadius()) <= opponent3Goal.getBounds().getMaxY()){
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
		}else if(opponent1PaddleCollision()){
			
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
		}else if(opponent2PaddleCollision()){
			
			puckDIRX = (puck.getCX() - opponent2Paddle.getCX()) / (Math.sqrt(((puck.getCX() - opponent2Paddle.getCX()) * (puck.getCX() - opponent2Paddle.getCX())) + ((puck.getCY() - opponent2Paddle.getCY()) * (puck.getCY() - opponent2Paddle.getCY()))));
			puckDIRY = (puck.getCY() - opponent2Paddle.getCY()) / (Math.sqrt(((puck.getCX() - opponent2Paddle.getCX()) * (puck.getCX() - opponent2Paddle.getCX())) + ((puck.getCY() - opponent2Paddle.getCY()) * (puck.getCY() - opponent2Paddle.getCY()))));
			if(opponent2PaddleSpeed < 5){
				puckSpeed += opponent2PaddleSpeed;
			}else if(hostPaddleSpeed <= 15){
				puckSpeed = opponent2PaddleSpeed;
			}else{
				puckSpeed = 15;
			}
			puckStep();
		}else if(opponent3PaddleCollision()){
			
			puckDIRX = (puck.getCX() - opponent3Paddle.getCX()) / (Math.sqrt(((puck.getCX() - opponent3Paddle.getCX()) * (puck.getCX() - opponent3Paddle.getCX())) + ((puck.getCY() - opponent3Paddle.getCY()) * (puck.getCY() - opponent3Paddle.getCY()))));
			puckDIRY = (puck.getCY() - opponent3Paddle.getCY()) / (Math.sqrt(((puck.getCX() - opponent3Paddle.getCX()) * (puck.getCX() - opponent3Paddle.getCX())) + ((puck.getCY() - opponent3Paddle.getCY()) * (puck.getCY() - opponent3Paddle.getCY()))));
			if(opponent3PaddleSpeed < 5){
				puckSpeed += opponent3PaddleSpeed;
			}else if(hostPaddleSpeed <= 15){
				puckSpeed = opponent3PaddleSpeed;
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
	
	public boolean opponent1PaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - opponent1Paddle.getCX()) * (puck.getCX() - opponent1Paddle.getCX()) + ((puck.getCY() - opponent1Paddle.getCY()) * (puck.getCY() - opponent1Paddle.getCY())));
		if(distance <= (puck.getRadius() + opponent1Paddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean opponent2PaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - opponent2Paddle.getCX()) * (puck.getCX() - opponent2Paddle.getCX()) + ((puck.getCY() - opponent2Paddle.getCY()) * (puck.getCY() - opponent2Paddle.getCY())));
		if(distance <= (puck.getRadius() + opponent1Paddle.getRadius())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean opponent3PaddleCollision(){
		
		double distance = Math.sqrt((puck.getCX() - opponent3Paddle.getCX()) * (puck.getCX() - opponent3Paddle.getCX()) + ((puck.getCY() - opponent3Paddle.getCY()) * (puck.getCY() - opponent3Paddle.getCY())));
		if(distance <= (puck.getRadius() + opponent3Paddle.getRadius())){
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
		}else if((puck.getX() + puck.getDiameter()) >= 544 && puck.getY() <= 50){ //upper right corner
			puckDIRX= -Math.abs(puckDIRX);
			puckDIRY = Math.abs(puckDIRY);
		}else if((puck.getX() + puck.getDiameter()) >= 544 && (puck.getY() + puck.getDiameter()) >= 522){ //bottom right corner
			puckDIRX = -Math.abs(puckDIRX);
			puckDIRY = -Math.abs(puckDIRY);
		}else if(puck.getX() <= 50 /*left wall border*/){
			puckDIRX = Math.abs(puckDIRX);
		}else if((puck.getX() + puck.getDiameter()) >= 544 /*right wall border*/){
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
			
			//if((mouseX - hostPaddle.getRadius()) >= 50 && (mouseX + hostPaddle.getRadius()) <= 394 && (mouseY - hostPaddle.getDiameter()) >= 50 && ((mouseY) >= 322 && (mouseY)<=522)){
				frame.setCursor(blankCursor);
				hostPaddleX = mouseX - hostPaddle.getRadius();
				hostPaddleY = mouseY - hostPaddle.getDiameter();
			/*}else{
				Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
				frame.setCursor(cursor);
			}*/
			
			hostPaddleSpeed = (int)((e.getPoint().distance(userPreviousPoint)) / ENERGY_TRANSFER);
			
			userPreviousPoint = e.getPoint();
		}
	}
}
