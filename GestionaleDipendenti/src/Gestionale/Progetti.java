//Classe utilizzata per la gestione della tabella Progetti
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Progetti {
	
	
	/**
	 * Ricerca se un progetto esiste 
	 * 
	 * @param id_Prog ID progetti.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un progetto inserito
	 */
	public static boolean ricercaProgetti(int id_Prog, Scanner scanner) {
	    String sql = "SELECT * FROM gestionaledipendenti.progetti WHERE id_Progetti= ?;"; //Query per cercare il progetto
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Prog);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Progetto con ID " + id_Prog + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun progetto trovato. Verifica l'ID.");
	            return false; //Progetto non trovato
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; //In caso di errore
	    }
	}
	
	/**
	 * Gestisce inserimento di un progetto
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	public static void InserisciProg (Scanner scanner) {
		String nome;
		
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
		    	System.out.print("Inserire il nome del nuovo progetto: ");
		    	nome=scanner.nextLine();
		    	if(!nome.isEmpty()) {
		    		break;
		    	}
	    	}catch(Exception e) {
	            System.err.println("Errore: " + e.getMessage());
	    	}
	   	}
		   	//Preparo la query
		    String sql = "INSERT INTO gestionaledipendenti.progetti (nome) VALUES (?);";
		    
			try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setString(1, nome);
		        
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Inserimento fallito. Nessun progetto è stato aggiunto.");
				} else System.out.println("Progetto aggiunto");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return; //In caso di errore
	}
	
	/**
	 * Stampa i progetti  
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void Stampa() {
		String sql= " ";
        sql = "SELECT A.id_Progetti, A.nome, count(COALESCE(B.id_Team)) AS Num_Team\r\n"
        		+ "FROM gestionaledipendenti.progetti A LEFT JOIN gestionaledipendenti.team B\r\n"
        		+ "ON A.id_Progetti = B.id_Progetti\r\n"
        		+ "GROUP BY A.id_Progetti, B.id_Progetti;";
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni progetti:");
                while (rs.next()) {
                    int id_Progetti = rs.getInt("id_Progetti");
                    String nome = rs.getString("nome");
                    int Num_Team = rs.getInt("Num_Team");

                    System.out.printf("ID progetti: %d | Nome: %s | Numero di team: %d%n",
                    		id_Progetti, nome, Num_Team);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Stampa il totale degli stipendi per progetti  
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void StampaStipendi() {
		String sql= " ";
        sql = "SELECT A.id_Progetti, A.nome, SUM(E.stipendioBase) AS totale_stipendi\r\n"
        		+ "FROM gestionaledipendenti.Progetti A JOIN gestionaledipendenti.DeveloperProgettiAssegnati B\r\n"
        		+ "ON A.id_Progetti = B.progettiAssegnati\r\n"
        		+ "JOIN gestionaledipendenti.Developer D ON B.id_Developer = D.id_Developer\r\n"
        		+ "JOIN gestionaledipendenti.Employee E ON D.id_Employee = E.id_Employee\r\n"
        		+ "GROUP BY A.id_Progetti, A.nome;";
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni progetti:");
                while (rs.next()) {
                    int id_Progetti = rs.getInt("id_Progetti");
                    String nome = rs.getString("nome");
                    double Num_Team = rs.getDouble("totale_stipendi");

                    System.out.printf("ID progetti: %d | Nome: %s | Totale stipendi: %.2f euro%n",
                    		id_Progetti, nome, Num_Team);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Aggiorna il progetto
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void aggiornamentoProg(Scanner scanner) {

        System.out.print("Inserisci l'ID del progetto per aggiornare le sue informazioni: ");
        int sceltaId = scanner.nextInt();
        String sql = "SELECT id_Progetti, nome FROM gestionaledipendenti.Progetti where id_Progetti="
                + sceltaId;

        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(),
                Connessione.getPASSWORD()); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            scanner.nextLine();

            int affectedRows = 0;
            try (PreparedStatement pstmtUp = conn.prepareStatement(
                    "UPDATE gestionaledipendenti.progetti SET nome = ? WHERE id_Progetti=" + sceltaId)) {
                System.out.println("NOME: ");
                String nuovoNome = scanner.nextLine();
                pstmtUp.setString(1, nuovoNome);
                affectedRows = pstmtUp.executeUpdate();
            }

            if (affectedRows > 0) {
                System.out.println("Informazioni aggiornate. ");
            } else {
                System.out.println("Nessun dato aggiornato. Verificare l'ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void eliminazioneProgetto(Scanner scanner) {

        System.out.print("Inserisci l'ID del progetto per eliminarlo: ");
        int sceltaId = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM gestionaledipendenti.Progetti where id_Progetti=?";
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(),
                Connessione.getPASSWORD()); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sceltaId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Progetto con ID " + sceltaId + " eliminato correttamente.");
            } else {
                System.out.println("Nessun cliente eliminato. Verificare l'ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
