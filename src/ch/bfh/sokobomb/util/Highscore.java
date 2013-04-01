package ch.bfh.sokobomb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Highscore {
	private Connection connection;
	private Statement  statement;
	final private String createQuery = "CREATE TABLE IF NOT EXISTS highscore (id integer, name string, points integer)";
	final private String dropQuery   = "DROP TABLE IF EXISTS highscore";

	/**
	 * Prepare the sqlite database connection
	 * @throws SQLException 
	 */
	public Highscore() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Class not found");
		}

		this.connection = DriverManager.getConnection("jdbc:sqlite:res/sokobomb.db");

		this.statement = connection.createStatement();
		this.statement.setQueryTimeout(30);

		// TODO remove, this is for testing only
		this.reset();
		this.addScore("test", 1337);
		this.addScore("SciFi", 42);
		this.addScore("Bla", 4000);
	}

	/**
	 * Add a new highscore entry
	 *
	 * @param name
	 * @param points
	 * @throws SQLException
	 */
	public void addScore(String name, int points) throws SQLException {
		PreparedStatement addScoreStatement = this.connection.prepareStatement(
			"INSERT INTO highscore (id, name, points) VALUES(?, ?, ?)"
		);

		addScoreStatement.setInt(1, this.getNextId());
		addScoreStatement.setString(2, name);
		addScoreStatement.setInt(3, points);
		addScoreStatement.execute();
	}

	/**
	 * Returns the next id for an insert
	 *
	 * @return The id
	 * @throws SQLException
	 */
	private int getNextId() throws SQLException {
		ResultSet nextId = this.statement.executeQuery("SELECT id FROM highscore ORDER BY id DESC LIMIT 1");
		if (nextId.next()) {
			return nextId.getInt("id") + 1;
		}

		return 1;
	}

	/**
	 * Return all items
	 *
	 * @return
	 */
	public ArrayList<String> getItems() {
		ResultSet highscore;
		ArrayList<String> items = new ArrayList<String>();

		// TODO Probably return points/name pair instead of string
		try {
			String item;
			highscore = this.statement.executeQuery("SELECT * FROM highscore ORDER BY points DESC LIMIT 10");
			while (highscore.next()) {
				item = highscore.getInt("points") + " " + highscore.getString("name");
				items.add(item);
			}
		}
		catch (SQLException e) {
			// Ignore
		}

		return items;
	}

	/**
	 * Drops the highscore table
	 *
	 * @throws SQLException
	 */
	public void reset() throws SQLException {
		this.statement.executeUpdate(this.dropQuery);
		this.statement.executeUpdate(this.createQuery);
	}
}