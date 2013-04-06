package ch.bfh.sokobomb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ch.bfh.sokobomb.controller.StateController;
import ch.bfh.sokobomb.states.State;

public class Application {

	private static Connection connection;
	private static StateController stateController;

	/**
	 * @return A DBManager instance
	 */
	public static Connection getConnection() {
		if (Application.connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException("Class not found");
			}

			try {
				Application.connection = DriverManager.getConnection("jdbc:sqlite:res/sokobomb.db");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}

		return Application.connection;
	}

	/**
	 * @return The state controller instance
	 */
	public static StateController getStateController() {
		if (Application.stateController == null) {
			Application.stateController = new StateController(State.PLAY);
		}

		return Application.stateController;
	}
}