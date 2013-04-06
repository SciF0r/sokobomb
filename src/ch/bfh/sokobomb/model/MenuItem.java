package ch.bfh.sokobomb.model;

import java.io.IOException;

/**
 * UNDER CONSTRUCTION!!!
 * 
 * @author Christoph Bruderer
 *
 */
public class MenuItem{
	
	final private String text;
	private boolean checked;
	
	public MenuItem(String text){
		this.text=text;
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

}
