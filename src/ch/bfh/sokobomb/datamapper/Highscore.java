package ch.bfh.sokobomb.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.HighscoreItem;

/**
 * Provides methods for highscore manipulation 
 *
 * @author Denis Simonet
 */
public class Highscore {

	private Statement statement;
	final private String createQuery = "CREATE TABLE IF NOT EXISTS highscore (id integer, level integer, name string, points integer)";
	final private String dropQuery   = "DROP TABLE IF EXISTS highscore";

	/**
	 * Prepare the sqlite database connection
	 * @throws SQLException 
	 */
	public Highscore() throws SQLException {
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
	public void addScore(HighscoreItem item) throws SQLException {
		PreparedStatement addScoreStatement = Application.getConnection().prepareStatement(
			"INSERT INTO highscore (id, level, name, points) VALUES(?, ?, ?, ?)"
		);

		addScoreStatement.setInt(1, this.getNextId());
		addScoreStatement.setInt(2, item.getLevel());
		addScoreStatement.setString(3, item.getName());
		addScoreStatement.setInt(4, item.getPoints());
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
	public ArrayList<HighscoreItem> getItems() {
		ResultSet highscore;
		ArrayList<HighscoreItem> items = new ArrayList<HighscoreItem>();

		try {
			highscore = this.statement.executeQuery("SELECT * FROM highscore ORDER BY points DESC LIMIT 10");

			HighscoreItem item;
			while (highscore.next()) {
				item = new HighscoreItem();
				item.setId(highscore.getInt("id"));
				item.setLevel(highscore.getInt("level"));
				item.setPoints(highscore.getInt("points"));
				item.setName(highscore.getString("name"));
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