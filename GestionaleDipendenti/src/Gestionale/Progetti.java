//Classe utilizzata per la gestione della tabella Progetti
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Progetti {
	
	private static final String URL = "jdbc:mysql://localhost:3306/impresa2";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	
	/**
	 * Ricerca se un progetto esiste 
	 * 
	 * @param id_Prog ID progetti.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un progetto inserito
	 */
	public static boolean ricercaProgetti(int id_Prog, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.progetti WHERE id_Progetti= ?;"; //Query per cercare il progetto
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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

}
