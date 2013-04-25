package ch.bfh.sokobomb.controller;

import java.io.IOException;
import java.util.Stack;

import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.DijkstraNode;
import ch.bfh.sokobomb.parser.Token;

/**
 * Manage fields
 *
 * @author Denis Simonet
 */
public class FieldController {

	private Field field;
	private Stack<Field> oldFields = new Stack<Field>();

	public FieldController(Field initialField) {
		this.field = initialField;
	}

	/**
	 * @param field Sets the field to be drawn
	 */
	public void setField(Field field) {
		this.oldFields.push(this.field);
		this.field = field;
	}

	/**
	 * Restore the old field
	 */
	public void restoreOldField() {
		if (!this.oldFields.empty()) {
			this.field = this.oldFields.pop();
		}
	}

	/**
	 * @return The field
	 */
	public Field getField() {
		return this.field;
	}

	/**
	 * Says the field to be drawn
	 */
	public void draw() {
		this.field.draw();
	}

	/**
	 * Draws the field
	 */
	public void drawField() {
		try {
			this.field.drawField();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * @param item The item to be added to the field
	 */
	public void addNode(DijkstraNode node) {
		this.field.addNode(node);
	}

	/**
	 * @param token The token to be added to the field
	 */
	public void addTileByToken(Token token) {
		this.field.addTileByToken(token);
	}

	/**
	 * Remove the node at a coordinate
	 *
	 * @param coordinate
	 */
	public void removeNode(TileCoordinate coordinate) {
		this.field.removeNode(coordinate);
	}

	/**
	 * Starts a new game
	 */
	public void startGame() {
		PlayField playField = (PlayField)this.field;
		playField.startGame();
	}
}