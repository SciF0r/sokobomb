package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.field.Field;

abstract public class Command {

	/**
	 * Execute the command
	 */
	abstract public void execute(Field field);
}