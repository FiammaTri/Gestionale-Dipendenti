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
			 
			 
			//creazione tabella Team
			 
			 
			//creazione tabella Progetti
			 
			 
		}

	 catch (SQLException e) {
        e.printStackTrace();
	}
	}
}