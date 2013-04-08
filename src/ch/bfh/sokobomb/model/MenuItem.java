package ch.bfh.sokobomb.model;

import java.io.IOException;

/**
 * UNDER CONSTRUCTION!!!
 * 
 * @author Christoph Bruderer
 *
 */
public class MenuItem{
	final public static int NO_ACTION   = 0;
	final public static int RESUME_GAME = 1;
	final public static int RESET_LEVEL = 2;
	final public static int EXIT_GAME   = 3;
	final public static int START_GAME  = 4;
	final public static int DESIGN_MODE = 5;
	final public static int END_GAME    = 6;
	
	final private String text;
	final private int action;
	private boolean checked;
	private Coordinate min;
	private Coordinate max;
	
	public MenuItem(String text, int action){
		this.text=text;
		this.action = action;
		this.checked=false;
	}
	
	public void setChecked(boolean b){
		this.checked=b;
	}
	
	public boolean isChecked(){
		return this.checked;
	}
	
	public String getText(){
		return this.text;
	}
	
	public int getAction(){
		return this.action;
	}
	
	public void setMinCoord(Coordinate min){
		this.min = min;
	}
	public Coordinate getMinCoord(){
		return min;
	}
	
	public void setMaxCoord(Coordinate max){
		this.max = max;
	}
	
	public Coordinate getMaxCoord(){
		return max;
	}
	
	public boolean containsCoordinate(Coordinate coord){
		return coord.getX() > min.getX() && coord.getX() < max.getX() &&
				coord.getY() > min.getY() && coord.getY() < max.getY();
	}

}
