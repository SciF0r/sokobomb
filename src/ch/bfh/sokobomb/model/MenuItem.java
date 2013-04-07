package ch.bfh.sokobomb.model;

import java.io.IOException;

/**
 * UNDER CONSTRUCTION!!!
 * 
 * @author Christoph Bruderer
 *
 */
public class MenuItem{
	final public static int END_GAME    = 0;
	final public static int RESUME_GAME = 1;
	final public static int RESET_LEVEL = 2;
	final public static int ACTION      = 3;
	
	final private String text;
	final private int action;
	private boolean checked;
	
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

}
