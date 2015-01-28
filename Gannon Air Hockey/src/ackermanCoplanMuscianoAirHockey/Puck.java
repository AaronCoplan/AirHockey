package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import javax.swing.JLabel;

public class Puck extends JLabel{

	private static final long serialVersionUID = 7028885027789799819L;
	private final int diameter = 30;
	
	public int getRadius(){return diameter/2;}
	public int getDiameter(){return diameter;}
	
	public int getCX(){return (this.getBounds().x + (diameter / 2));}
	public int getCY(){return (this.getBounds().y + (diameter / 2));}
	
	public int getX(){return this.getBounds().x;}
	public int getY(){return this.getBounds().y;}
}
