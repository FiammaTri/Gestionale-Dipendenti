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

	// Parametri di connessione statici
	private static final String URL = "jdbc:mysql://localhost:3306/gestionaledipendenti";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

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

	// Metodo per aggiungere un nuovo team al database
	public static int aggiungiTeam(Scanner scanner) {

		// inpur richiesto: nome, descrizione e membri inseriti tramite lo scanner
		System.out.println("Inserire il NOME del team: ");
		String nome = scanner.nextLine();
		System.out.println("Inserire la DESCRIZIONE del team: ");
		String descrizione = scanner.nextLine();
		System.out.println("Inserire i MEMBRI del team (ID separati da virgola): ");
		String membri = scanner.nextLine();
        //utilizza una query sql insert to per inserire i dati nella tabella 
		String sql = "INSERT INTO Team (nome, descrizione, membri) VALUES (?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				//per prevenire SQL injection.
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, nome);
			pstmt.setString(2, descrizione);
			pstmt.setString(3, membri);

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creazione del team fallita, nessuna riga aggiunta.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					System.out.println("Team aggiunto con successo.");
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creazione del team fallita, ID non recuperato.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // In caso di errore
	}

	// Metodo per eliminare un team dal database
	public static void eliminaTeam(Scanner scanner) {
		System.out.println("Inserire l'ID del team da eliminare: ");
		int idTeam = scanner.nextInt();
		scanner.nextLine(); // Consuma il newline
       //Eliminare i record corrispondenti
		String sql = "DELETE FROM Team WHERE id = ?";
		//conferma se l'eliminazione è avvenuta stampando un messaggio
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
    // Metodo per aggiornare i dettagli di un team
    public static void aggiornaTeam(Scanner scanner) {
        System.out.println("Inserire l'ID del team da aggiornare: ");
        int idTeam = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        System.out.println("Inserire il NUOVO NOME del team: ");
        String nome = scanner.nextLine();
        System.out.println("Inserire la NUOVA DESCRIZIONE del team: ");
        String descrizione = scanner.nextLine();
        System.out.println("Inserire i NUOVI MEMBRI del team (ID separati da virgola): ");
        String membri = scanner.nextLine();

        String sql = "UPDATE Team SET nome = ?, descrizione = ?, membri = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, descrizione);
            pstmt.setString(3, membri);
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
	 * Ricerca se un team esiste 
	 * 
	 * @param id_Team ID team.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return  un valore booleano. è vero se l'id dato corrispende ad un team inserito
	 */
	public static boolean ricercaTeam(int id_Team, Scanner scanner) {
	    String sql = "SELECT * FROM impresa2.team WHERE id_Team=?;"; //Query per cercare il Team
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


