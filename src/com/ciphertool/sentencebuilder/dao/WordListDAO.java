package com.ciphertool.sentencebuilder.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.ciphertool.sentencebuilder.beans.Word;
import com.ciphertool.sentencebuilder.common.PartOfSpeech;

@Component
public class WordListDAO {
    private String username;
    private String password;
	private String connectionString;
	
	public List<Word> findAll() {
		ArrayList<Word> wordList = new ArrayList<Word>();
		Connection conn;
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			String selectString = "select word, part_of_speech, frequency_weight from parts_of_speech";
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
				Word nextWord = new Word(rs.getString("word"),PartOfSpeech.typeOf(rs.getString("part_of_speech").charAt(0)),rs.getInt("frequency_weight"));
				wordList.add(nextWord);
			}
			conn.close();
		}
		catch (SQLException sqle) {
			System.out.println("Caught SQLException while retrieving all words from database " + sqle);
		}

		return wordList;
	}
	
	public HashMap<PartOfSpeech, ArrayList<Word>> sortByPartOfSpeech(ArrayList<Word> allWords) {
		HashMap<PartOfSpeech, ArrayList<Word>> byPartOfSpeech = new HashMap<PartOfSpeech, ArrayList<Word>>();
		for (Word w : allWords) {
			if (!byPartOfSpeech.containsKey(w.getPOS())) {
				byPartOfSpeech.put(w.getPOS(), new ArrayList<Word>());
				byPartOfSpeech.get(w.getPOS()).add(w);
			}
			else {
				byPartOfSpeech.get(w.getPOS()).add(w);
			}
		}
		return byPartOfSpeech;
	}
	
	public boolean insert(String w, char pos, int weight) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
        try {
            String insertString = "insert into parts_of_speech (word, part_of_speech, frequency_weight) values (?,?,?)";
            stmt = conn.prepareStatement(insertString);
            stmt.setString(1, w);
            stmt.setString(2, String.valueOf(pos));
            stmt.setInt(3, weight);
            int numAffected = stmt.executeUpdate();
            if (numAffected == 0)
                return false;
        }
        catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
		return true;
		//remember to add the frequency weight somehow; for now just let it default to 1
	}
	
    private Connection getConnection() {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);

        try {
			conn = DriverManager.getConnection(connectionString, connectionProps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return conn;
    }

    @Required
    public void setUsername(String username) {
    	this.username = username;
	}

    @Required
	public void setPassword(String password) {
    	this.password = password;
	}

    @Required
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
}
