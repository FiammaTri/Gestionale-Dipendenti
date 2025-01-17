//Classe utilizzata per la gestione della tabella Linguaggio
package Gestionale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Linguaggio {
	//Classe utilizzata per la gestione della tabella Linguaggio


	// Parametri di connessione statici
		private static final String URL = "jdbc:mysql://localhost:3306/impresa2";
		private static final String USER = "root";
		private static final String PASSWORD = "Rossosangue1!";
		
		/**
		 * Inserisce un nuovo linguaggio nella tabella.
		 * 
		 * @param nome Nome del linguaggio.
		 * @return true se l'inserimento è avvenuto con successo, altrimenti false.
		 */
		public static boolean inserisciLinguaggio(String nome) {
		    String sql = "INSERT INTO linguaggio (nome) VALUES (?);";
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setString(1, nome);

		        int rowsAffected = pstmt.executeUpdate();
		        if (rowsAffected > 0) {
		            System.out.println("Linguaggio inserito con successo.");
		            return true;
		        } else {
		            System.out.println("Errore durante l'inserimento del linguaggio.");
		            return false;
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

		/**
		 * Aggiorna il nome di un linguaggio esistente.
		 * 
		 * @param id_Lig ID del linguaggio da aggiornare.
		 * @param nuovoNome Nuovo nome del linguaggio.
		 * @return true se l'aggiornamento è avvenuto con successo, altrimenti false.
		 */
		public static boolean aggiornaLinguaggio(int id_Lig, String nuovoNome) {
		    String sql = "UPDATE linguaggio SET nome = ? WHERE id_Linguaggio = ?;";
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setString(1, nuovoNome);
		        pstmt.setInt(2, id_Lig);

		        int rowsAffected = pstmt.executeUpdate();
		        if (rowsAffected > 0) {
		            System.out.println("Linguaggio aggiornato con successo.");
		            return true;
		        } else {
		            System.out.println("Errore durante l'aggiornamento del linguaggio. Verifica l'ID.");
		            return false;
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

		
		/**
		 * Ricerca se un linguaggio esiste 
		 * 
		 * @param id_Lig ID linguaggio.
		 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
		 * 
		 * @return  un valore booleano. è vero se l'id dato corrispende ad un linguaggio inserito
		 */
		public static boolean ricercaLinguaggio(int id_Lig, Scanner scanner) {
		    String sql = "SELECT * FROM linguaggio WHERE id_Linguaggio= ?;"; //Query per cercare il progetto
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setInt(1, id_Lig);

		        //Eseguiamo la query
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) { 
		            System.out.println("Linguaggio con ID " + id_Lig + " è stato trovato.");
		            return true;
		        } else {
		            System.out.println("Nessun linguaggio trovato. Verifica l'ID.");
		            return false; //Progetto non trovato
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false; //In caso di errore
		    }
		}
		/**
		 * Elimina un linguaggio dalla tabella.
		 * 
		 * @param id_Lig ID del linguaggio da eliminare.
		 * @return true se l'eliminazione è avvenuta con successo, altrimenti false.
		 */
		public static boolean eliminaLinguaggio(int id_Lig) {
		    String sql = "DELETE FROM linguaggio WHERE id_Linguaggio = ?;";
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setInt(1, id_Lig);

		        int rowsAffected = pstmt.executeUpdate();
		        if (rowsAffected > 0) {
		            System.out.println("Linguaggio eliminato con successo.");
		            return true;
		        } else {
		            System.out.println("Errore durante l'eliminazione del linguaggio. Verifica l'ID.");
		            return false;
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		/**
		 * Stampa tutti i linguaggi presenti nella tabella.
		 */
		public static void visualizzaLinguaggi() {
		    String sql = "SELECT * FROM linguaggio;";
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {

		        System.out.println("Lista di tutti i linguaggi:");
		        while (rs.next()) {
		            int id = rs.getInt("id_Linguaggio");
		            String nome = rs.getString("nome");
		            System.out.printf("ID: %d, Nome: %s%n", id, nome);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}


	}
