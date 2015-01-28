package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import javax.swing.*;

public class Paddle extends JLabel{

	private static final long serialVersionUID = 673648286358715919L;
	private final int diameter = 50;
	
	public int getRadius(){return diameter/2;}
	public int getDiameter(){return diameter;}
	
	public int getCX(){return (this.getBounds().x + (diameter / 2));}
	public int getCY(){return (this.getBounds().y + (diameter / 2));}
}
