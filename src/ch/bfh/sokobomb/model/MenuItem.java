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
	
	public void setChecked(){
		this.checked=true;
	}
	
	public void setUnchecked(){
		this.checked=false;
	}

}
