package ch.bfh.sokobomb.command;

/**
 * Interface for the command pattern
 *
 * @author Denis Simonet
 */
abstract public class Command {

	/**
	 * Execute the command
	 */
	abstract public void execute();

	/**
	 * Undo the command
	 */
	abstract public void undo();
}