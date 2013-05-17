package ch.bfh.sokobomb.command;

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