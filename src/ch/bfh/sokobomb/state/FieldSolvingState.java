package ch.bfh.sokobomb.state;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.command.Command;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.solver.Solver;

/**
 * This state moves the player along a given path
 *
 * The path is calculated by Dijkstra
 *
 * @author Denis Simonet
 */
public class FieldSolvingState extends State {

	private LinkedList<Command> commands;
	private Iterator<Command> commandsIterator;
	private long timestamp;

	public FieldSolvingState() {
		this.stateId = State.SOLVING;
	}

	@Override
	public void doEntry() {
		this.timestamp = 0;

		Solver solver = new Solver((PlayField)Application.getFieldController().getField());
		this.commands = solver.solve();

		if (this.commands == null) {
			System.exit(0);
		}

		this.commandsIterator = this.commands.iterator();
	}

	@Override
	public void handleKeyPress(int key) {
		Application.getStateController().setState(State.PLAY);
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Application.getStateController().setState(State.PLAY);
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Application.getFieldController().drawField();

		if ((System.currentTimeMillis() - this.timestamp) < 200) {
			return;
		}

		this.timestamp = System.currentTimeMillis();

		if (this.commandsIterator.hasNext()) {
			this.commandsIterator.next().execute(Application.getFieldController().getField());
		}
		else {
			System.out.println("no next");
			Application.getStateController().setState(State.PLAY);
		}
	}
}