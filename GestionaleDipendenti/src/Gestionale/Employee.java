//Classe utilizzata per la gestione della tabella Employee
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Employee {

	// Parametri di connessione 
	private static final String URL = "jdbc:mysql://localhost:3306/gestionaledipendenti";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	
	/* metodo per inserire un nuovo dipendente nella tabella Employee
	 * @param nome  Nome del dipendente
     * @param cognome  Cognome del dipendente
     * @param stipendioBase stipendio del dipendente
     * @param ruolo  ruolo del dipendente
     * @param scanner scanner per le operazioni di inserimento
     * @return L'ID generato per il nuovo cliente
	*/
	public static int aggiuntaDipendente(Scanner scanner) {	
		String nome;
		String cognome; 
		Double stipendioBase; 
		String ruolo;
		System.out.println("Inserire il NOME: ");
		nome = scanner.nextLine();
		System.out.println("Inserire il COGNOME: ");
		cognome = scanner.nextLine();
		System.out.println("Inserire lo STIPENDIO BASE: ");
		stipendioBase = scanner.nextDouble();
		scanner.nextLine();
		System.out.println("Inserire il RUOLO: ");
		ruolo = scanner.nextLine();
				
		String sql = "INSERT INTO EMPLOYEE (nome, cognome, stipendioBase, ruolo) VALUES (?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setDouble(3, stipendioBase);
			pstmt.setString(4, ruolo);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creazione dipendente fallita, nessuna riga aggiunta");
			} else System.out.println("Dipendente aggiunto");
			// Recupero la chiave generata (ID auto-increment)
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creazione dipendente fallita, ID non recuperato");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // In caso di errore
	}

	/**
     * Elimina un dipendente nella tabella EMPLOYEE dato il suo ID.
     *
     * @param id_Employee ID del dipendente
     */
    public static void eliminazioneDipendente(Scanner scanner) {
    	int id_Employee;
    	System.out.println("Inserire l'ID del dipendende da eliminare: ");
    	id_Employee=scanner.nextInt();
    	
        String sql = "DELETE FROM CLIENTS WHERE id_Employee = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_Employee);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cliente con ID " + id_Employee + " eliminato correttamente.");
            } else {
                System.out.println("Nessun cliente eliminato. Verificare l'ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stampa le informazioni del dipendente dalla tabella Employee.
     */
    public static void stampaDipendente(Scanner scanner) {
    	
    	System.out.println("Inserire l'ID del dipendende da visualizzare: ");
    	int sceltaId=scanner.nextInt();
    	
        String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="+sceltaId;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
            System.out.println("Informazioni dipendente:");
            
            	int id_Employee = rs.getInt("id_Employee");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                double stipendioBase = rs.getDouble("stipendioBase");
                String ruolo = rs.getString("ruolo");

                System.out.printf("ID: %d | Nome: %s | Cognome: %s | Stipendio Base: %.2f | Ruolo: %s",
                        id_Employee, nome, cognome, stipendioBase, ruolo);
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
