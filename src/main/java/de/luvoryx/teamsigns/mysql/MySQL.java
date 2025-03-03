package de.luvoryx.ranksigns.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import de.luvoryx.ranksigns.RankSigns;

public class MySQL {
	
	public static String host = RankSigns.cfg.getString("MySQL.Host");
	public static String port = String.valueOf(RankSigns.cfg.getInt("MySQL.Port"));
	public static String database = RankSigns.cfg.getString("MySQL.Datenbank");
	public static String username = RankSigns.cfg.getString("MySQL.Benutzername");
	public static String password = RankSigns.cfg.getString("MySQL.Passwort");
	
	public static Connection connection;
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static boolean isConnected() {
		return (connection == null ? false : true);
	}
	
	public static void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	public static ArrayList<UUID> getGroupUsers(String rank) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM permissions_inheritance WHERE parent = ?");
			ps.setString(1, rank);
			ResultSet result = ps.executeQuery();
			ArrayList<UUID> uuids = new ArrayList<UUID>();
			while (result.next()) {
				UUID uuid = UUID.fromString(result.getString("child"));
				uuids.add(uuid);
			}
			result.close();
	        ps.close();
	        return uuids;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}