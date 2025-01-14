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
				
		String sql = "INSERT INTO gestionaledipendenti.EMPLOYEE (nome, cognome, stipendioBase, ruolo) VALUES (?,?,?,?)";
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
    	
        String sql = "DELETE FROM gestionaledipendenti.Employee WHERE id_Employee = ?";
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
    	scanner.nextLine();
    	
        String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM gestionaledipendenti.Employee where id_Employee="+sceltaId;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
            System.out.println("Informazioni dipendente:");
            rs.next();
            String foundType = rs.getString(1);
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
    
    /**
     * Aggiorna i dati del dipendente dato il suo ID.
     *
     * @param id_Employee ID del dipendente
     * @param nuovoNome nuovo nome del dipendente
     * @param nuovoCognome nuovo cognome del dipendente
     * @param nuovoStipendio nuovo stipendio del dipendente
     */
    
    
    public static void aggiornamentoInfo(Scanner scanner) {
    	
    	System.out.println("Inserisci l'ID del dipendente per aggiornare le sue informazioni: ");
    	int sceltaId=scanner.nextInt();
    	String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="+sceltaId;
    	
    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		System.out.println("Cosa vuoi modificare? \n 1) Nome \n 2) Cognome \n 3) Stipendio ");
    		int scelta=scanner.nextInt();
    		scanner.nextLine();
    	
    	
    		int affectedRows=0; 
    		if (scelta==1) {
    			try (PreparedStatement pstmtUp = conn.prepareStatement("UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee="+sceltaId)) {
    			System.out.println("NOME: ");
    			String nuovoNome=scanner.nextLine();
    			System.out.println("PROVA 1");
    			pstmtUp.setString(1, nuovoNome);
    			
    			System.out.println("PROVA 2");
    			affectedRows = pstmtUp.executeUpdate(); 
    			System.out.println("PROVA EXECUTE");
    			}
    			
    		} else if (scelta==2) {
    			try (PreparedStatement pstmtUp = conn.prepareStatement("UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee ="+sceltaId)) {
    			System.out.println("COGNOME: ");
    			String nuovoCognome=scanner.nextLine();
    			pstmtUp.setString(1, nuovoCognome);
    			
                affectedRows = pstmt.executeUpdate();
    			}
    			
    		} else if (scelta==3) {
    			try (PreparedStatement pstmtUp = conn.prepareStatement("UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee = "+sceltaId)) {
    			System.out.println("STIPENDIO: ");
    			double nuovoStipendio=scanner.nextDouble();
    			pstmtUp.setDouble(1, nuovoStipendio);
    			
    	          affectedRows = pstmt.executeUpdate();
    		}
    		}
    		System.out.println("PROVA 3");
            
            if (affectedRows > 0) {
                System.out.println("Informazioni aggiornate. ");
                sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="+sceltaId;
            } else {
                System.out.println("Nessun dato aggiornato. Verificare l'ID.");
            }
    
    }  catch (SQLException e) {
        e.printStackTrace();
}
    }
    	 public static void aggiornamentoRuolo(Scanner scanner) {
    	    	
    	    	System.out.println("Inserisci l'ID del dipendente per aggiornare il suo ruolo: ");
    	    	int sceltaId=scanner.nextInt();
    	    	String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="+sceltaId;
    	    	
    	    	try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    	    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    	    		scanner.nextLine();
    	    
    	    		int affectedRows=0; 
    	    			try (PreparedStatement pstmtUp = conn.prepareStatement("UPDATE gestionaledipendenti.Employee SET ruolo = ? WHERE id_Employee="+sceltaId)) {
    	    			System.out.println("RUOLO: ");
    	    			String nuovoRuolo=scanner.nextLine();
    	    			pstmtUp.setString(1, nuovoRuolo);
    	    			affectedRows = pstmtUp.executeUpdate(); 
    	    			}
    	    		
    	            if (affectedRows > 0) {
    	                System.out.println("Informazioni aggiornate. ");
    	            } else {
    	                System.out.println("Nessun dato aggiornato. Verificare l'ID.");
    	            }
    	    
    	    }  catch (SQLException e) {
    	        e.printStackTrace();
    	}
}
} 

