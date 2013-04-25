package ch.bfh.sokobomb.solver;

import java.io.IOException;
import java.util.LinkedList;

import ch.bfh.sokobomb.command.BombMoveCommand;
import ch.bfh.sokobomb.command.Command;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.tiles.Bomb;
import ch.bfh.sokobomb.model.tiles.Target;
import ch.bfh.sokobomb.parser.Token;

public class Solver {

	private PlayField field;
	private LinkedList<Command> commands = new LinkedList<Command>(); 
//	private HashMap<Field, PriorityQueue<Path>> cache;

	public Solver(PlayField field) {
		this.field = field;
	}

	public LinkedList<Command> solve() {
		try {
			if (this.iteration(this.field)) {
				return this.commands;
			}				
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return null;
	}

	private boolean iteration(PlayField field) throws CloneNotSupportedException {
		BombMoveCommand command;

		field = (PlayField)field.clone();

		bombloop:
		for (Bomb bomb: field.getBombs()) {
			for (Target target: field.getTargets()) {
				Path path = new Path(this.field, bomb, target);

				if (path == null || !path.hasNext()) {
					continue;
				}

				try {
					int type = field.getCache().getNodeAtCoordinate(path.next()).getType();
					if (type == Token.TARGET) {
						continue bombloop;
					}
				} catch (InvalidCoordinateException e) {
					e.printStackTrace();
					System.exit(0);
				}

				command = new BombMoveCommand(path.next(), bomb);
				command.execute(field);

				if (iteration(field)) {
					this.commands.addFirst(command);
					return true;
				}
			}
		}

		if (field.hasWon()) {
			return true;
		}

		return false;
	}
}