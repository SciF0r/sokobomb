import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

public class Test {
	private int x;
	private int y;
	private int row=6;
	private int col=8;

	public static void main(String[] args){
		Test t =new Test();
		t.loadLibs();
		t.start();
	}
	private void loadLibs(){
		try {
			LibraryLoader.loadNativeLibraries();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("sorry");
			System.exit(0);
		}
	}
	public void start(){
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		Display.setTitle("OpengL - TEST");

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, -1, 1);
		glMatrixMode(GL_MODELVIEW);

		while ( !Display.isCloseRequested()){
			//clear buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			//set color
			glColor3f(1.0f, 1.0f, 0.0f);

			//draw all fields
			int delta=4;
			int size=100;
			for ( int i=0; i<col; i++){
				int xpos = i*100;
				for(int j=0; j<row; j++){
					int ypos=j*size;
					glBegin(GL_QUADS);
					glVertex2i(xpos+delta,ypos+delta);
					glVertex2i(xpos+size-delta,ypos+delta);
					glVertex2i(xpos+size-delta,ypos+size-delta);
					glVertex2i(xpos+delta,ypos+size-delta);
					glEnd();
				}
			}

			drawPlayer(x,y);
			System.out.println(x+" "+y);

			pollInput();
			Display.update();



		}
		Display.destroy();
		System.exit(0);

	}

	private void drawPlayer(int x,int y){
		//set color
		glColor3f(0.0f, 0.0f, 1.0f);

		glBegin(GL_QUADS);
		int size=100;
		int s = 50;
		int offset=25;
		glVertex2i(x*size+offset, y*size+offset);
		glVertex2i(x*size+s+offset, y*size+offset);
		glVertex2i(x*size+offset+s, y*size+offset+s);
		glVertex2i(x*size+offset, y*size+offset+s);
		glEnd();
	}

	private void pollInput(){
		//Keyboard.enableRepeatEvents(false);
		if (Mouse.isButtonDown(0)) {
			int mousex = Mouse.getX();
			int mousey = Mouse.getY();
			//this.drawPlayer(x/100, y/100);
			x=mousex/100;
			y=mousey/100;
		}
		while (Keyboard.next()){
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if (x<col-1){
					x=x+1;
					//Display.sync(10);
					//System.out.println(x +" "+y);
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if (x>0){
					x=x-1;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if (y<row-1){
					y=y+1;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if (y>0){
					y=y-1;
				}
			}
		}
	}
}
