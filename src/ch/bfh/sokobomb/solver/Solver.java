package ch.bfh.sokobomb.solver;

import java.util.Collection;
import java.util.LinkedList;

import ch.bfh.sokobomb.command.BombMoveCommand;
import ch.bfh.sokobomb.command.MoveCommand;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.exception.NoSolutionFoundException;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;
import ch.bfh.sokobomb.model.tiles.Target;
import ch.bfh.sokobomb.parser.Token;

/**
 * Class to solve a game, using recursion
 *
 * Sokoban is P-Complete, only optimizations possible
 *
 * @author Denis Simonet
 */
public class Solver {

	private PlayField field;
	private LinkedList<MoveCommand> commands = new LinkedList<MoveCommand>(); 
//	private HashMap<Field, PriorityQueue<Path>> cache;

	public Solver(PlayField field) {
		this.field = field;
	}

	/**
	 * Tries to solve a game
	 *
	 * @return The list of commands which have to be executed in order to solve the field
	 * @throws NoSolutionFoundException
	 * @throws InvalidCoordinateException 
	 */
	public LinkedList<MoveCommand> solve() throws NoSolutionFoundException, InvalidCoordinateException {
		if (this.iteration()) {
			return this.commands;
		}				

		throw new NoSolutionFoundException("There is no solution");
	}

	/**
	 * One iteration goes through all bombs and tries to move them towards the target
	 *
	 * @param field
	 * @return True if a solution was found, false otherwise
	 * @throws InvalidCoordinateException 
	 */
	private boolean iteration() throws InvalidCoordinateException {
		MoveCommand command;

		for (Bomb bomb: this.field.getBombs()) {
			try {
				// Continue if current bomb already is in a target
				if (this.field.getCache().getNodeAtCoordinate(bomb.getCoordinate()).getType() == Token.TARGET) {
					continue;
				}
			} catch (InvalidCoordinateException e) {
				e.printStackTrace();
				System.exit(0);
			}

			for (Target target: this.field.getFreeTargets()) {
				Path bombPath = new Path(this.field, bomb.getCoordinate(), target.getCoordinate());

				if (!bombPath.hasNext()) {
					continue;
				}


				TileCoordinate nextCoordinate = bombPath.next();

				int dx = nextCoordinate.getX() - bomb.getCoordinate().getX();
				int dy = nextCoordinate.getY() - bomb.getCoordinate().getY();

				TileCoordinate playerCoordinate = new TileCoordinate(
					bomb.getCoordinate().getX() - dx,
					bomb.getCoordinate().getY() - dy
				);

				Path playerPath = new Path(this.field, this.field.getPlayer().getCoordinate(), playerCoordinate);
/*				if (!playerPath.hasNext()) {
					continue;
				}*/

				command = new BombMoveCommand(this.field, nextCoordinate, bomb);
				command.execute();

				boolean success = this.field.hasWon() || iteration();
				command.undo();

				if (success) {
					this.commands.addFirst(command);
					// addAll
					
					return true;
				}
			}
		}

		return false;
	}
}