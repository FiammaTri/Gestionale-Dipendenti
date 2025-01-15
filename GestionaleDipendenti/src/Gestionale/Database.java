//Classe utilizzata per REAZIONE, MODIFICA e GESTIONE del database
package Gestionale;

import java.sql.*;

public class Database {

	// Parametri di connessione passati con i metodi Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD()
	

	public static void createTables() {
		try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
				Statement stmt = conn.createStatement()) {

			// creazione tabella Employee
			String createEmployeeTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Employee ("
					+ "id_Employee INT AUTO_INCREMENT PRIMARY KEY, " 
					+ "nome VARCHAR(100) NOT NULL, "
					+ "cognome VARCHAR(100) NOT NULL, " 
					+ "stipendioBase DECIMAL(10, 2) NOT NULL, "
					+ "ruolo VARCHAR(100) NOT NULL" 
					+ ");";

			// creazione tabella Manager
			String createManagerTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Manager ("
					+ "id_Manager INT AUTO_INCREMENT PRIMARY KEY, " 
					+ "bonus DECIMAL(10, 2) NOT NULL, "
					+ "id_Employee INT NOT NULL, "
					+ "CONSTRAINT idemployee FOREIGN KEY (id_Employee) REFERENCES employee(id_Employee) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE" 
					+ ");";

			// creazione tabella Developer
			String createDeveloperTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Developer ("
					+ "id_Developer INT AUTO_INCREMENT PRIMARY KEY, " 
					+ "id_Employee INT, " 
					+ "id_Team INT, " // riferimento al team del developer
					+ "CONSTRAINT idemployeedev FOREIGN KEY (id_Employee) REFERENCES employee(id_Employee) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE, "
					+ "CONSTRAINT fk_team FOREIGN KEY (id_Team) REFERENCES Team(id_Team) " 
					+ "ON DELETE SET NULL "
					+ "ON UPDATE CASCADE" 
					+ ");";

			// creazione tabella Linguaggio
			String createLinguaggioTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Linguaggio ("
					+ "id_Linguaggio INT AUTO_INCREMENT PRIMARY KEY, " 
					+ "tipologia VARCHAR(100), "
					+ "nome VARCHAR(100) NOT NULL" 
					+ ");";
			
			// creazione tabella Team
			String createTeamTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Team (" 
					+ "id_Team INT AUTO_INCREMENT PRIMARY KEY, "
					+ "id_Progetti INT, " 
					+ "descrizionelavoro TEXT, " 
					+ "nome VARCHAR(100), " 
					+ "id_Manager INT, "
					+ "FOREIGN KEY (id_Progetti) REFERENCES Progetti(id_Progetti) " 
					+ "ON DELETE SET NULL "
					+ "ON UPDATE CASCADE, " 
					+ "FOREIGN KEY (id_Manager) REFERENCES Manager(id_Manager) "
					+ "ON DELETE SET NULL " 
					+ "ON UPDATE CASCADE" 
					+ ");";

			// creazione tabella Progetti
			String createProgettiTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.Progetti ("
					+ "id_Progetti INT AUTO_INCREMENT PRIMARY KEY, " 
					+ "nome VARCHAR(100) NOT NULL" 
					+ ");";

			// creazione tabella DeveloperProgettiAssegnati con relazione tra Developer e Progetti
			String createDeveloperProgettiAssegnatiTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.DeveloperProgettiAssegnati ("
					+ "id_Developer INT NOT NULL, " 
					+ "progettiAssegnati INT NOT NULL,  "
					+ "CONSTRAINT fk_developer FOREIGN KEY (id_Developer) REFERENCES Developer(id_Developer) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE, "
					+ "CONSTRAINT fk_progetto FOREIGN KEY (progettiAssegnati) REFERENCES Progetti(id_Progetti) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE, " 
					+ "dataInizio DATE, " 
					+ "dataFine DATE" + ");";

			// creazione tabella DeveloperLinguaggi con relazione tra Developer e Linguaggi
			String createDeveloperLinguaggiTable = "CREATE TABLE IF NOT EXISTS gestionaledipendenti.DeveloperLinguaggi ("
					+ "id_Developer INT NOT NULL, " 
					+ "id_Linguaggio INT NOT NULL, "
					+ "CONSTRAINT fk_developer2 FOREIGN KEY (id_Developer) REFERENCES Developer(id_Developer) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE, "
					+ "CONSTRAINT fk_linguaggio2 FOREIGN KEY (id_Linguaggio) REFERENCES Linguaggio(id_Linguaggio) "
					+ "ON DELETE CASCADE " 
					+ "ON UPDATE CASCADE " 
					+ ");";
			
			stmt.execute(createEmployeeTable);
			System.out.println("Tabella Employee creata");
			stmt.execute(createManagerTable);
			System.out.println("Tabella Manager creata");
			stmt.execute(createProgettiTable);
			System.out.println("Tabella Progetti creata");
			stmt.execute(createTeamTable);
			System.out.println("Tabella Team creata");
			stmt.execute(createDeveloperTable);
			System.out.println("Tabella Developer creata");
			stmt.execute(createLinguaggioTable);
			System.out.println("Tabella Linguaggio creata");
			stmt.execute(createDeveloperProgettiAssegnatiTable);
			System.out.println("Tabella DeveloperProgettiAssegnati creata");
			stmt.execute(createDeveloperLinguaggiTable);
			System.out.println("Tabella DeveloperLinguaggi creata");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
