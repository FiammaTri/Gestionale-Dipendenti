//Classe utilizzata per la gestione della tabella Developer
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Developer {
	
	/**
	 * Gestisce l'assegnamento del developer ad un team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void assegnamentoTeam (Scanner scanner) {
    	int id_Dev;
    	int id_Team;

    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del developer da assegnare ad un nuovo team: ");
			    id_Dev=scanner.nextInt();
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
	    if(ricercaDeveloper(id_Dev, scanner) && Team.ricercaTeam(id_Team, scanner)) {
		   	//Preparo la query
		    String sql = "UPDATE impresa2.developer SET id_Team= ? WHERE id_Developer = ?;";
		    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Team);
		        pstmt.setInt(2, id_Dev);
		        
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Developer con ID " + id_Dev + " assegnato al nuovo team correttamente.");
	            } else {
	                System.out.println("Nessun developer è stato assegnato ad un nuovo team. Verificare l'ID.");
	            }
		    		
			}catch (SQLException x) {
		        x.printStackTrace();
		    }
	    }
	}
	
	/**
	 * Gestisce l'assegnamento del developer ad un progetto
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return il nuovo id creato sulla tabella developerprogettiassegnati
	 */
	public static void assegnamentoProgetto (Scanner scanner) {
		int id_Dev;
		int id_Prog; 
		
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del developer da assegnare ad un nuovo progetto: ");
			    id_Dev=scanner.nextInt();
			    scanner.nextLine();
		    	System.out.print("Inserisci l'ID del nuovo progetto da assegnare: ");
		    	id_Prog=scanner.nextInt();
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
	    if(ricercaDeveloper(id_Dev, scanner) && Progetti.ricercaProgetti(id_Prog, scanner)) {
	    	
	    	String query= "SELECT * FROM impresa2.developerprogettiassegnati WHERE id_Developer=? AND progettiAssegnati=?;";
	    	int controllo = controlloAssegnamento(query, id_Dev, id_Prog, scanner);
	    	if(controllo==1 || controllo==-1){
	    		return; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "INSERT INTO developerprogettiassegnati (id_Developer, progettiAssegnati) "
		    			+ "VALUES (?,?);";
		    
			try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Prog);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Assegnamento fallito. Nessun developer è stato aggiunto al nuovo progetto");
				} else System.out.println("Developer aggiunto");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return; //In caso di errore
	}
	
	/**
	 * Gestisce l'assegnamento del developer ad un linguaggio
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return il nuovo id creato sulla tabella developerlinguaggi
	 */
	public static void assegnamentoLinguaggio (Scanner scanner) {
		int id_Dev;
		int id_Lig; 
		
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del developer da assegnare ad un nuovo linguaggio: ");
			    id_Dev=scanner.nextInt();
			    scanner.nextLine();
		    	System.out.print("Inserisci l'ID del nuovo linguaggio da assegnare: ");
		    	id_Lig=scanner.nextInt();
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
	    if(ricercaDeveloper(id_Dev, scanner) && Linguaggio.ricercaLinguaggio(id_Lig, scanner)) {
	    	
	    	String query = "SELECT * FROM impresa2.developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;"; //Query per cercare se non esiste un altro doppione
	    	int controllo = controlloAssegnamento(query, id_Dev, id_Lig, scanner);
	    	if(controllo==1 || controllo==-1){
	    		return; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "INSERT INTO developerlinguaggi (id_Developer, id_Linguaggio) "
		    		+ "VALUES (?,?);";
		    
			try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Lig);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Assegnamento fallito. Nessun linguaggio è stato assegnato.");
				} else System.out.println("Developer aggiunto");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return; //In caso di errore
	}
	
	
	public static int controlloAssegnamento(String sql, int id1, int id2, Scanner scanner) {
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id1);
	        pstmt.setInt(2, id2);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Esiste gia un assegnazione.");
	            return 1;
	        } else {
	            return 0; //Assegnazione non trovata
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; //In caso di errore
	    }
	}
	
	/**
	 * controlla se esiste gia una riga nella developerprogettiassegnati
	 * 
	 * @param id_Dev L'id del developer.
	 * @param id_Prog L'id del progetto.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return controlla se l'assegnazione esite
	 */
	public static int controlloAssegnamentoProgetto (int id_Dev, int id_Prog, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developerprogettiassegnati WHERE id_Developer=? AND progettiAssegnati=?;"; //Query per cercare se non esiste un altro doppione
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Dev);
	        pstmt.setInt(1, id_Prog);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Esiste gia un assegnazione.");
	            return 1;
	        } else {
	            return 0; //Assegnazione non trovata
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; //In caso di errore
	    }
	}
	
	/**
	 * controlla se esiste gia una riga nella developerlinguaggi
	 * 
	 * @param id_Dev L'id del developer.
	 * @param id_Lig L'id della lingua.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return controlla se l'assegnazione esite
	 */
	public static int controlloAssegnamentoLinguaggio (int id_Dev, int id_Lig, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;"; //Query per cercare se non esiste un altro doppione
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Dev);
	        pstmt.setInt(1, id_Lig);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Esiste gia un assegnazione.");
	            return 1;
	        } else {
	            return 0; //Assegnazione non trovata
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; //In caso di errore
	    }
	}
	
	/**
	 * Gestisce l'eliminazione del developer ad un linguaggio
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  Id eliminato nella tabella developerlinguaggi
	 */
	public static void togliereLinguaggio (Scanner scanner) {
		int id_Dev;
		int id_Lig; 
		
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
			   	System.out.print("Inserire l'ID del developer da eliminare l'assegnare ad un linguaggio: ");
			    id_Dev=scanner.nextInt();
			    scanner.nextLine();
		    	System.out.print("Inserisci l'ID del linguaggio assegnato da eliminare: ");
		    	id_Lig=scanner.nextInt();
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
	    if(ricercaDeveloper(id_Dev, scanner) && Linguaggio.ricercaLinguaggio(id_Lig, scanner)) {
	    	String query = "SELECT * FROM impresa2.developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;"; //Query per cercare se non esiste un altro doppione
	    	int controllo = controlloAssegnamento(query, id_Dev, id_Lig, scanner);
	    	if(controllo==0 || controllo==-1){
	    		return; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "DELETE FROM developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;";
		    
			try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Lig);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Eliminazione fallita.");
				} else System.out.println("Eliminazione riuscita");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return; //In caso di errore
	}
	
	/**
	 * Stampa un developer con i sui progetti scelto dall'utente 
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void StampaDevProg (Scanner scanner) {
		
		int sceltaId=0;
	
    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
	    while(true) {
		    try {
		    	System.out.print("Inserire l'ID del developer da visualizzare: ");
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
	    if(ricercaDeveloper(sceltaId, scanner)==false) {
	    	System.out.println("Developer non esiste.");
	    	return;
	    }
    	
	    String sql= " ";
        sql = String.format("SELECT A.id_Developer, C.nome, B.progettiAssegnati\r\n"
        		+ "FROM impresa2.developer A\r\n"
        		+ "INNER JOIN impresa2.developerprogettiassegnati B\r\n"
        		+ "ON A.id_Developer = B.id_Developer\r\n"
        		+ "INNER JOIN impresa2.progetti C\r\n"
        		+ "ON B.progettiAssegnati = C.id_Progetti\r\n"
        		+ "WHERE A.id_Developer = %d;"
        		, sceltaId);
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni developer:");
                while (rs.next()) {
                    int id_Developer = rs.getInt("id_Developer");
                    int id_Progetti = rs.getInt("progettiAssegnati");
                    String nome = rs.getString("nome");

                    System.out.printf("ID: %d | Nome progetto: %s | ID Progetto: %d%n",
                            id_Developer, nome, id_Progetti);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Stampa tutti gli developer con i loro progetti  
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 */
	public static void Stampa() {
		String sql= " ";
        sql = "SELECT A.id_Developer, D.nome, D.cognome, A.id_Team, C.id_Progetti, C.nome AS nomeP\r\n"
        		+ "FROM employee D INNER JOIN impresa2.developer A \r\n"
        		+ "ON D.id_Employee = A. id_Employee\r\n"
        		+ "LEFT JOIN developerprogettiassegnati B\r\n"
        		+ "ON  A.id_Developer = B.id_Developer\r\n"
        		+ "INNER JOIN progetti C\r\n"
        		+ "ON progettiAssegnati = id_Progetti\r\n"
        		+ "GROUP BY A.id_Developer, A.id_Team, D.nome, D.cognome, A.id_Team, C.id_Progetti, C.nome;";
        
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        		System.out.println("Informazioni developer:");
                while (rs.next()) {
                    int id_Developer = rs.getInt("id_Developer");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    int id_Team = rs.getInt("id_Team");
                    int id_Progetti = rs.getInt("id_Progetti");
                    String nomeP = rs.getString("nomeP");

                    System.out.printf("ID: %d | Nome: %s | Cognome: %s | ID Team: %d | ID Progetto: %d | ID Nome progetto: %s%n",
                            id_Developer, nome, cognome, id_Team, id_Progetti, nomeP);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Ricerca se un developer esiste
	 * 
	 * @param id_Dev ID developer.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un developer inserito
	 */
	public static boolean ricercaDeveloper(int id_Dev, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developer WHERE id_Developer=?;"; //Query per cercare il developer
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Dev);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Developer con ID " + id_Dev + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun developer trovato. Verifica l'ID.");
	            return false; //Developer non trovato
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; //In caso di errore
	    }
	}

}
