//Classe utilizzata per la gestione della tabella Linguaggio
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Linguaggio {
	
	private static final String URL = "jdbc:mysql://localhost:3306/impresa2";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	
	
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

}
