package ch.bfh.sokobomb.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ch.bfh.sokobomb.Application;

/**
 * Provides methods for highscore manipulation 
 *
 * @author Denis Simonet
 */
public class Levels {

	private Statement statement;
	final private String createQuery = "CREATE TABLE IF NOT EXISTS highscore (id integer, level integer, name string, points integer)";
	final private String dropQuery   = "DROP TABLE IF EXISTS highscore";

	/**
	 * Prepare the sqlite database connection
	 * @throws SQLException 
	 */
	public Levels() throws SQLException {
		this.statement = Application.getConnection().createStatement();
		this.statement.setQueryTimeout(30);
	}

	/**
	 * Add a new highscore entry
	 *
	 * @param name
	 * @param points
	 * @throws SQLException
	 */
	public void addScore(int level, String name, int points) throws SQLException {
		PreparedStatement addScoreStatement = Application.getConnection().prepareStatement(
			"INSERT INTO highscore (id, level, name, points) VALUES(?, ?, ?, ?)"
		);

		addScoreStatement.setInt(1, this.getNextId());
		addScoreStatement.setInt(2, level);
		addScoreStatement.setString(3, name);
		addScoreStatement.setInt(4, points);
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
	 * Empties the highscore table
	 *
	 * @throws SQLException
	 */
	public void reset() throws SQLException {
		this.statement.executeUpdate(this.dropQuery);
		this.statement.executeUpdate(this.createQuery);
	}

	/**
	 * Returns the highest score of a level
	 *
	 * @param level The level identifier
	 * @return The score. 0 if none.
	 */
	public int getHighestScore(int level) {
		PreparedStatement highestScoreStatement;
		try {
			highestScoreStatement = Application.getConnection().prepareStatement("SELECT points FROM highscore WHERE level = ? ORDER BY points DESC LIMIT 1");

			highestScoreStatement.setInt(1, level);
			ResultSet highestScore = highestScoreStatement.executeQuery(); 
			
			if (highestScore.next()) {
				return highestScore.getInt("points");
			}
		} catch (SQLException e) {
			return 0;
		}

		return 0;
	}
}