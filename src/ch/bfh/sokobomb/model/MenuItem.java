package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.model.coordinate.Coordinate;

/**
 * A menu item
 * 
 * @author Christoph Bruderer
 *
 */
public class MenuItem {

	final public static int NO_ACTION = Integer.MIN_VALUE;
	
	final private String text;
	final private int action;
	private boolean checked;
	private Coordinate min;
	private Coordinate max;
	
	public MenuItem(String text, int action) {
		this.text    = text;
		this.action  = action;
		this.checked = false;
	}
	
	public void setChecked(boolean checked){
		this.checked = checked;
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