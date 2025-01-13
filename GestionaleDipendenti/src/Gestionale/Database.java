//Classe utilizzata per REAZIONE, MODIFICA e GESTIONE del database
package Gestionale;

import java.sql.*;

public class Database {

	// Parametri di connessione
	private static final String URL = "jdbc:mysql://localhost:3306/gestionaledipendenti";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public static void createTables() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			
			//creazione tabella Employee
			 String createEmployeeTable = "CREATE TABLE IF NOT EXISTS Employee ("
					+ "id_Employee INT AUTO_INCREMENT PRIMARY KEY, "
					+ "nome VARCHAR(100) NOT NULL, "
					+ "cognome VARCHAR(100) NOT NULL, "
					+ "stipendioBase DECIMAL(10, 2) NOT NULL, "
					+ "ruolo VARCHAR(100) NOT NULL"
					+ ");";
			 
			//creazione tabella Manager
			 
			 
			//creazione tabella Developer
			 
			 
			//creazione tabella Linguaggio
			  String createLinguaggioTable = "CREATE TABLE IF NOT EXISTS Linguaggio (" +
	                    "id_Linguaggio INT AUTO_INCREMENT PRIMARY KEY, " +
	                    "tipologia VARCHAR(100), " +
	                    "nome VARCHAR(100) NOT NULL" +
	                    ");";
			 
			//creazione tabella Team
			  String createTeamTable = "CREATE TABLE IF NOT EXISTS Team (" +
	                    "id_Team INT AUTO_INCREMENT PRIMARY KEY, " +
	                    "id_Progetti INT, " +
	                    "descrizionelavoro TEXT, " +
	                    "nome VARCHAR(100), " +
	                    "id_Manager INT, " +
	                    "FOREIGN KEY (id_Progetti) REFERENCES Progetti(id_Progetti) " +
	                    "ON DELETE SET NULL " +
	                    "ON UPDATE CASCADE, " +
	                    "FOREIGN KEY (id_Manager) REFERENCES Manager(id_Manager) " +
	                    "ON DELETE SET NULL " +
	                    "ON UPDATE CASCADE" +
	                    ");";
			 
			 
			//creazione tabella Progetti
			  String createProgettiTable = "CREATE TABLE IF NOT EXISTS Progetti (" +
	                    "id_Progetti INT AUTO_INCREMENT PRIMARY KEY, " +
	                    "nome VARCHAR(100) NOT NULL" +
	                    ");";
			 
			 
		}

	 catch (SQLException e) {
        e.printStackTrace();
	}
	}
}