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
	
	private static final String URL = "jdbc:mysql://localhost:3306/gestionaledipendenti";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	
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
	    if(ricercaDeveloper(id_Dev, scanner) && ricercaTeam(id_Team, scanner)) {
		   	//Preparo la query
		    String sql = "UPDATE developer SET id_Team= ? WHERE id_Developer = ?;";
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
	
	public static int assegnamentoProgetto (Scanner scanner) {
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
	    if(ricercaDeveloper(id_Dev, scanner) && ricercaProgetti(id_Prog, scanner)) {
	    	
	    	
	    	int controllo = controlloAssegnamentoProgetto(id_Dev, id_Prog, scanner);
	    	if(controllo==1 || controllo==-1){
	    		return -1; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "INSERT INTO developerprogettiassegnati (id_Developer, progettiAssegnati) "
		    			+ "VALUES (?,?);";
		    
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Prog);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Assegnamento fallito. Nessun developer è stato aggiunto al nuovo progetto");
				} else System.out.println("Developer aggiunto");
				//Recupero la chiave generata (ID auto-increment)
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					} else {
						throw new SQLException("Creazione Developer fallita, ID non recuperato");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return -1; //In caso di errore
	}
	
	public static int controlloAssegnamentoProgetto (int id_Dev, int id_Prog, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developerprogettiassegnati WHERE id_Developer=? AND progettiAssegnati=?;"; //Query per cercare se non esiste un altro doppione
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
	
	public static int assegnamentoLinguaggio (Scanner scanner) {
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
	    if(ricercaDeveloper(id_Dev, scanner) && ricercaLinguaggio(id_Lig, scanner)) {
	    	
	    	int controllo = controlloAssegnamentoLinguaggio(id_Dev, id_Lig, scanner);
	    	if(controllo==1 || controllo==-1){
	    		return -1; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "INSERT INTO developerlinguaggi (id_Developer, id_Linguaggio) "
		    		+ "VALUES (?,?);";
		    
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Lig);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Assegnamento fallito. Nessun linguaggio è stato assegnato.");
				} else System.out.println("Developer aggiunto");
				//Recupero la chiave generata (ID auto-increment)
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					} else {
						throw new SQLException("Assegnazione nuovo linguaggio fallita, ID non recuperato");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return -1; //In caso di errore
	}
	
	public static int controlloAssegnamentoLinguaggio (int id_Dev, int id_Lig, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;"; //Query per cercare se non esiste un altro doppione
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
	
	public static int togliereLinguaggio (Scanner scanner) {
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
	    if(ricercaDeveloper(id_Dev, scanner) && ricercaLinguaggio(id_Lig, scanner)) {
	    	
	    	int controllo = controlloAssegnamentoLinguaggio(id_Dev, id_Lig, scanner);
	    	if(controllo==0 || controllo==-1){
	    		return -1; //In caso di errore o se sia gia stato assegnato
	    	}
	    	
		   	//Preparo la query
		    String sql = "DELETE FROM developerlinguaggi WHERE id_Developer=? AND id_Linguaggio=?;";
		    
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
		    	//Riempo i placeholder '?'
		        pstmt.setInt(1, id_Dev);
		        pstmt.setInt(2, id_Lig);
	
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Eliminazione fallita.");
				} else System.out.println("Eliminazione riuscita");
				//Recupero la chiave generata (ID auto-increment)
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					} else {
						throw new SQLException("Eliminazione fallita, ID non recuperato");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	System.err.println("ID non valido.");
	    }
		return -1; //In caso di errore
	}
	
	public static boolean ricercaDeveloper(int id_Dev, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.developer WHERE id_Developer=?;"; //Query per cercare il developer
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
	
	public static boolean ricercaTeam(int id_Team, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.team WHERE id_Team=?;"; //Query per cercare il Team
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Team);

	        //Eseguiamo la query
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) { 
	            System.out.println("Team con ID " + id_Team + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun team trovato. Verifica l'ID.");
	            return false; //Team non trovato
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; //In caso di errore
	    }
	}
	
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
