//Classe utilizzata per la gestione della tabella Manager
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
	
	public static void assegnamentoTeam (Scanner scanner) {
    	int id_Man;
    	int id_Team;

    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del Manager da assegnare ad un nuovo team: ");
			   	id_Man=scanner.nextInt();
			    scanner.nextLine();
		    	System.out.print("Inserisci l'ID del nuovo team da assegnare: ");
		    	id_Team=scanner.nextInt();
		    	scanner.nextLine();
		    	break;
		    }catch(InputMismatchException e) { //Eccezione se i valori non siano numerici
		    	System.err.println("Errore: hai inserito un valore non numerico");
		    	e.printStackTrace();
	    		System.err.print("Ripeti l'inserimento");
	    	}catch(Exception e) {
	            System.err.println("Errore: " + e.getMessage());
	    	}
	   	}
	    
	    //Controlla se l'ide developer esista
	    if(ricercaManger(id_Man, scanner) && Team.ricercaTeam(id_Team, scanner)) {
	    	
		   	//Preparo la query
		    String sql = "UPDATE impresa2.team SET id_Manager = ? WHERE id_Team= ?;";
		    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Man);
		        pstmt.setInt(2, id_Team);
		        
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Manager con ID " + id_Man + " assegnato al nuovo team correttamente.");
	            } else {
	                System.out.println("Nessun manager è stato assegnato ad un nuovo team. Verificare l'ID.");
	            }
		    		
			}catch (SQLException x) {
		        x.printStackTrace();
		    }
	    }
	}
	
	/**
	 * Stampa un manager con i sui compagni di team scelto dall'utente 
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void StampaManTeam (Scanner scanner) {
		
		int sceltaId=0;
	
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
		    	System.out.print("Inserire l'ID del manager da visualizzare: ");
		    	sceltaId=scanner.nextInt();
		    	scanner.nextLine();
		    	break;
		    }catch(InputMismatchException e) { //Eccezione se i valori non siano numerici
		    	System.err.println("Errore: hai inserito un valore non numerico");
		    	e.printStackTrace();
	    		System.err.print("Ripeti l'inserimento");
	    	}catch(Exception e) {
	            System.err.println("Errore: " + e.getMessage());
	    	}
	   	}
    	
	    //Controlla se l'ide developer esista
	    if(ricercaManger(sceltaId, scanner)==false) {
	    	System.out.println("Manager non esiste.");
	    	return;
	    }
    	
	    String sql= " ";
        sql = String.format("SELECT C.id_Team, B.nome, C.id_Developer, D.nome AS nomeD, D.cognome\r\n"
        		+ "FROM impresa2.manager A INNER JOIN impresa2.team B \r\n"
        		+ "ON A.id_Manager = B.id_Manager\r\n"
        		+ "INNER JOIN impresa2.developer C\r\n"
        		+ "ON B.id_Team = C.id_Team\r\n"
        		+ "INNER JOIN impresa2.employee D\r\n"
        		+ "ON D.ID_Employee = C.ID_Employee\r\n"
        		+ "WHERE A.id_Manager=%d;"
        		, sceltaId);
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni manager:");
                while (rs.next()) {
                    int id_Team = rs.getInt("id_Team");
                    String nome = rs.getString("nome");
                    int id_Developer = rs.getInt("id_Developer");
                    String nomeD = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    System.out.printf("ID team: %d | Nome team: %s | ID Developer: %d | Nome developer: %s | Cognome developer: %s%n",
                    		id_Team, nome, id_Developer, nomeD, cognome);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Ricerca se un manager esiste
	 * 
	 * @param id_Man ID manager.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un manager inserito
	 */
	public static boolean ricercaManger(int id_Man, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.manager WHERE id_Manager=?;"; //Query per cercare il developer
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Man);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Manager con ID " + id_Man + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun manager trovato. Verifica l'ID.");
	            return false; //Developer non trovato
	        }
	
	public static void assegnamentoTeam (Scanner scanner) {
    	int id_Man;
    	int id_Team;

    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del Manager da assegnare ad un nuovo team: ");
			   	id_Man=scanner.nextInt();
			    scanner.nextLine();
		    	System.out.print("Inserisci l'ID del nuovo team da assegnare: ");
		    	id_Team=scanner.nextInt();
		    	scanner.nextLine();
		    	break;
		    }catch(InputMismatchException e) { //Eccezione se i valori non siano numerici
		    	System.err.println("Errore: hai inserito un valore non numerico");
		    	e.printStackTrace();
	    		System.err.print("Ripeti l'inserimento");
	    	}catch(Exception e) {
	            System.err.println("Errore: " + e.getMessage());
	    	}
	   	}
	    
	    //Controlla se l'ide developer esista
	    if(ricercaManger(id_Man, scanner) && Team.ricercaTeam(id_Team, scanner)) {
	    	
		   	//Preparo la query
		    String sql = "UPDATE impresa2.team SET id_Manager = ? WHERE id_Team= ?;";
		    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Man);
		        pstmt.setInt(2, id_Team);
		        
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Manager con ID " + id_Man + " assegnato al nuovo team correttamente.");
	            } else {
	                System.out.println("Nessun manager è stato assegnato ad un nuovo team. Verificare l'ID.");
	            }
		    		
			}catch (SQLException x) {
		        x.printStackTrace();
		    }
	    }
	}
	
	/**
	 * Stampa un manager con i sui compagni di team scelto dall'utente 
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void StampaManTeam (Scanner scanner) {
		
		int sceltaId=0;
	
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
		    	System.out.print("Inserire l'ID del manager da visualizzare: ");
		    	sceltaId=scanner.nextInt();
		    	scanner.nextLine();
		    	break;
		    }catch(InputMismatchException e) { //Eccezione se i valori non siano numerici
		    	System.err.println("Errore: hai inserito un valore non numerico");
		    	e.printStackTrace();
	    		System.err.print("Ripeti l'inserimento");
	    	}catch(Exception e) {
	            System.err.println("Errore: " + e.getMessage());
	    	}
	   	}
    	
	    //Controlla se l'ide developer esista
	    if(ricercaManger(sceltaId, scanner)==false) {
	    	System.out.println("Manager non esiste.");
	    	return;
	    }
    	
	    String sql= " ";
        sql = String.format("SELECT C.id_Team, B.nome, C.id_Developer, D.nome AS nomeD, D.cognome\r\n"
        		+ "FROM impresa2.manager A INNER JOIN impresa2.team B \r\n"
        		+ "ON A.id_Manager = B.id_Manager\r\n"
        		+ "INNER JOIN impresa2.developer C\r\n"
        		+ "ON B.id_Team = C.id_Team\r\n"
        		+ "INNER JOIN impresa2.employee D\r\n"
        		+ "ON D.ID_Employee = C.ID_Employee\r\n"
        		+ "WHERE A.id_Manager=%d;"
        		, sceltaId);
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni manager:");
                while (rs.next()) {
                    int id_Team = rs.getInt("id_Team");
                    String nome = rs.getString("nome");
                    int id_Developer = rs.getInt("id_Developer");
                    String nomeD = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    System.out.printf("ID team: %d | Nome team: %s | ID Developer: %d | Nome developer: %s | Cognome developer: %s%n",
                    		id_Team, nome, id_Developer, nomeD, cognome);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Ricerca se un manager esiste
	 * 
	 * @param id_Man ID manager.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un manager inserito
	 */
	public static boolean ricercaManger(int id_Man, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.manager WHERE id_Manager=?;"; //Query per cercare il developer
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Man);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Manager con ID " + id_Man + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun manager trovato. Verifica l'ID.");
	            return false; //Developer non trovato
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; //In caso di errore
	    }
	}
	
	

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; //In caso di errore
	    }
	}
	
	
	
	
	//x Giordano
	//metodo per stampare l'elenco dei manager e il team ai quali sono assegnati
	public static void elencoManager() {
		
	}
	
}
