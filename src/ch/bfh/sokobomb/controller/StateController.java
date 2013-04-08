package ch.bfh.sokobomb.controller;

import java.io.IOException;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.states.DesignState;
import ch.bfh.sokobomb.states.EndGameState;
import ch.bfh.sokobomb.states.HighscoreState;
import ch.bfh.sokobomb.states.HomeState;
import ch.bfh.sokobomb.states.PauseState;
import ch.bfh.sokobomb.states.PlayState;
import ch.bfh.sokobomb.states.PlayerMovingState;
import ch.bfh.sokobomb.states.State;
import ch.bfh.sokobomb.states.WonState;

public class StateController {

	/**
	 * Current state
	 */
	private State state;

	public StateController(int initialState) {
		this.setState(initialState);
	}

	/**
	 * Sets a new state
	 *
	 * @param state
	 */
	public void setState(int stateId) {
		switch (stateId) {
			case State.DESIGN:
				this.state = new DesignState();
				break;
			case State.END_GAME:
				this.state = new EndGameState();
				break;
			case State.HIGHSCORE:
				this.state = new HighscoreState();
				break;
			case State.PAUSE:
				this.state = new PauseState();
				break;
			case State.PLAYER_MOVING:
				this.state = new PlayerMovingState();
				break;
			case State.PLAY:
				this.state = new PlayState();
				break;
			case State.WON:
				this.state = new WonState();
				break;
			case State.HOME:
				this.state = new HomeState();
				break;
		}

		this.state.entry();
	}

	/**
	 * Draws the current state
	 */
	public void draw() {
		try {
			this.state.draw();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Polls the Keyboard and Mouse an triggers event handlers
	 */
	public void pollInput() {
		this.state.pollInput();
	}

	/**
	 * @param stateClass
	 * @return Whether the current state is of type stateClass
	 */
	public boolean isState(int stateId) {
		return this.state.getStateId() == stateId;
	}

	/**
	 * Calls setPlayerPosition() on the current state
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		this.state.setPlayerPosition(coordinate);
	}
}