package ch.bfh.sokobomb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HighScore {
	private Connection connection;
	private Statement  statement;
	final private String createQuery = "CREATE TABLE IF NOT EXISTS highscore (id integer, name string, points integer)";
	final private String dropQuery   = "DROP TABLE IF EXISTS highscore";

	/**
	 * Prepare the sqlite database connection
	 * @throws SQLException 
	 */
	public HighScore() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Class not found");
		}

		this.connection = DriverManager.getConnection("jdbc:sqlite:res/sokobomb.db");

		this.statement = connection.createStatement();
		this.statement.setQueryTimeout(30);
		this.statement.executeUpdate(this.createQuery);
	}

	/**
	 * Add a new highscore entry
	 *
	 * @param name
	 * @param points
	 * @throws SQLException
	 */
	public void addScore(String name, int points) throws SQLException {
		this.statement.executeUpdate(
			"INSERT INTO highscore (id, name, points) VALUES(1, 'Test', 1337)"
		);
	}

	/**
	 * Print the high score
	 * @throws SQLException 
	 */
	@Override
	public String toString() {
		ResultSet highscore;
		String list = "";

		try {
			highscore = this.statement.executeQuery("SELECT * FROM highscore ORDER BY points DESC");
			while (highscore.next()) {
				list += highscore.getInt("points") + " " + highscore.getString("name") + " (" + highscore.getInt("id") + ")\n";
			}
		} catch (SQLException e) {
			// Ignore
			System.out.println("Error: " + e.getMessage());
		}

		return list;
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