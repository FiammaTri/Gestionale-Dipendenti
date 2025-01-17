//Classe utilizzata per la gestione della tabella Team
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Team {

	// rendiamo gli attributi privati per poterli usare solo in questa classe
	/*
	 * parametro id che deve essere un intero, parametro nome che deve essere una
	 * stringa, paramentro membri che anche questo va bene come stringa,
	 */

	private int id;
	private String nome;
	private String descrizione;
	private String membri; // Gli ID dei membri come stringa separata da virgole

	// Costruttore senza ID (ad esempio per la creazione di un nuovo team)
	public Team(String nome, String descrizione, String membri) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.membri = membri;
	}

	// Costruttore con ID (ad esempio per un team già esistente nel database)
	public Team(int id, String nome, String descrizione, String membri) {
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.membri = membri;
	}

	// metodo getter che permette di accedere ai valori degli attributi resi privati
	// in questa classe
	public int getId() {
		return id; // restituisce l'id del team
	}

	public String getNome() {
		return nome; // restituisce il nome del team
	}

	public String getDescrizione() {
		return descrizione; // restituisce la descrizione del team
	}

	public String getMembri() {
		return membri; // restituisce la stringa che contiene gli id dei membri
	}

	// Metodo setter per aggiornare i valori degli attributi privati
	public void setId(int id) {
		this.id = id; // permette di aggiornare l'id del tema
	}

	public void setNome(String nome) {
		this.nome = nome;// aggiorna il nome del team
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione; // aggiorna la descizione del team
	}

	public void setMembri(String membri) {
		this.membri = membri; // aggiorna l'id dei membri
	}

	/**
	 * Aggiungere un team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  il nuovo ID team aggiunto
	 */
	public static void aggiungiTeam(Scanner scanner) {

		// inpur richiesto: nome, descrizione e membri inseriti tramite lo scanner
		System.out.print("Inserire il NOME del team: ");
		String nome = scanner.nextLine();
		System.out.print("Inserire la DESCRIZIONE del team: ");
		String descrizione = scanner.nextLine();
		System.out.print("Inserire l'id del manager da assegnare al nuovo team: ");
		int id_Manager = scanner.nextInt();
		scanner.nextLine();
        //utilizza una query sql insert to per inserire i dati nella tabella 
		String sql = "INSERT INTO Team (nome, descrizionelavoro, id_Manager) VALUES (?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
				//per prevenire SQL injection.
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, nome);
			pstmt.setString(2, descrizione);
			pstmt.setInt(3, id_Manager);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creazione del team fallita, nessuna riga aggiunta.");
			}else {
				System.out.println("Creazione del team aggiunto con successo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ; // In caso di errore
	}
	
	/**
	 * Visualizzare un determinato team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	public static void visualizzaTeam(Scanner scanner) {
	    System.out.print("Inserire l'ID del team da visualizzare: ");
	    int idTeam = scanner.nextInt();
	    scanner.nextLine(); // Consuma il newline

	    String sql = "SELECT * FROM team WHERE id_Team = ?";
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, idTeam);
	        ResultSet rs = pstmt.executeQuery();
	        
	        System.out.println();
	        if (rs.next()) {
	            System.out.println("ID: " + rs.getInt("id_Team"));
	            System.out.println("Nome: " + rs.getString("nome"));
	            System.out.println("Descrizione: " + rs.getString("descrizionelavoro"));
	            System.out.println("ID manager: " + rs.getString("id_Manager"));
	            System.out.println("----------------------------------");
	        } else {
	            System.out.println("Nessun team trovato con l'ID specificato.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Elimina un team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	public static void eliminaTeam(Scanner scanner) {
		System.out.print("Inserire l'ID del team da eliminare: ");
		int idTeam = scanner.nextInt();
		scanner.nextLine(); 
       //Eliminare i record corrispondenti
		String sql = "DELETE FROM Team WHERE id_Team = ?";
		//conferma se l'eliminazione è avvenuta stampando un messaggio
		try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idTeam);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Team con ID " + idTeam + " eliminato correttamente.");
			} else { //se l'id non è valido come id  o non corrisponde al team. avverte l'utente
				System.out.println("Nessun team eliminato. Verificare l'ID.");
			}
        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * Aggiornare un team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
    public static void aggiornaTeam(Scanner scanner) {
        System.out.println("Inserire l'ID del team da aggiornare: ");
        int idTeam = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        System.out.print("Inserire il NUOVO NOME del team: ");
        String nome = scanner.nextLine();
        System.out.print("Inserire la NUOVA DESCRIZIONE del team: ");
        String descrizione = scanner.nextLine();
        System.out.print("Inserire l'id del manager: ");
        int membri = scanner.nextInt();
        scanner.nextLine();

        String sql = "UPDATE Team SET nome = ?, descrizionelavoro = ?, id_Manager = ? WHERE id_Team = ?";
        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, descrizione);
            pstmt.setInt(3, membri);
            pstmt.setInt(4, idTeam);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Il Team è stato aggiornato con successo.");
            } else {
                System.out.println("Nessun team aggiornato. Verifica nuovamente l'ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * assegnare un manager ad un team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	public static void assegnaManager(Scanner scanner) {
	    System.out.print("Inserire l'ID del team al quale assegnare un manager: ");
	    int teamId = scanner.nextInt();
	    scanner.nextLine();
	    System.out.print("Inserire l'ID del manager: ");
	    int managerId = scanner.nextInt();
	    scanner.nextLine(); 

	    String sql = "UPDATE Team SET id_Manager = ? WHERE id_Team = ?";
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, managerId);
	        pstmt.setInt(2, teamId);

	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("Manager assegnato correttamente al team.");
	        } else {
	            System.out.println("Nessun team aggiornato. Verifica l'ID.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Visualizzare con un determinato manager il suo team
	 * 
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	   public static void visualizzaTeamManager(Scanner scanner) {
	        System.out.println("Inserire l'ID del manager per visualizzare i suoi team: ");
	        int managerId = scanner.nextInt();
	        scanner.nextLine(); 

	        String sql = "SELECT * FROM Team WHERE managerId = ?";
	        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, managerId);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                System.out.println("Team gestiti dal manager con ID " + managerId + ":");
	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String nome = rs.getString("nome");
	                    String descrizione = rs.getString("descrizione");
	                    String membri = rs.getString("membri");

	                    System.out.println("ID: " + id + ", Nome: " + nome + ", Descrizione: " + descrizione + ", Membri: " + membri);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	   
		/**
		 * assegnare un team ad un progetto
		 * 
		 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
		 * 
		 */
	    public static void assegnaTeamAProgetto(Scanner scanner) {
	        System.out.print("Inserire l'ID del team da assegnare: ");
	        int idTeam = scanner.nextInt();
	        scanner.nextLine(); 

	        System.out.print("Inserire l'ID del progetto al quale assegnare il team: ");
	        int idProgetto = scanner.nextInt();
	        scanner.nextLine(); 

	        String sql = "UPDATE gestionaledipendenti.team SET id_Progetti= ? WHERE id_Team = ?;";
	        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, idProgetto);
	            pstmt.setInt(2, idTeam);

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Team assegnato al progetto con successo.");
	            } else {
	                System.out.println("Assegnazione del team al progetto fallita.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
		/**
		 * Calcolare lo stipendio totale del team
		 * 
		 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
		 * 
		 */
	    public static void calcolaStipendioTotale(Scanner scanner) {
	        System.out.print("Inserire l'ID del team: ");
	        int idTeam = scanner.nextInt();
	        scanner.nextLine(); 

	        String sql = "SELECT SUM(B.stipendioBase) AS stipendioTotale\r\n"
	        		+ "FROM gestionaledipendenti.developer A INNER JOIN gestionaledipendenti.employee B\r\n"
	        		+ "ON A.id_Employee = B.id_Employee\r\n"
	        		+ "WHERE A.id_Team=?;";

	        try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, idTeam);

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                double stipendioTotale = rs.getDouble("stipendioTotale");
	                System.out.println("Stipendio totale del team: " + stipendioTotale +" euro");
	            } else {
	                System.out.println("Nessun dato trovato per il team specificato.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
    
	/**
	 * Ricerca se un team esiste 
	 * 
	 * @param id_Team ID team.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un team inserito
	 */
	public static boolean ricercaTeam(int id_Team, Scanner scanner) {
	    String sql = "SELECT * FROM gestionaledipendenti.team WHERE id_Team=?;"; //Query per cercare il Team
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
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
	
}


