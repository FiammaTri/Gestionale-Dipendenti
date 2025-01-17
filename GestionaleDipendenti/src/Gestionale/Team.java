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
	private static final String URL = "jdbc:mysql://localhost:3306/impresa2";
	private static final String USER = "root";
	private static final String PASSWORD = "Rossosangue1!";

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
	public static void visualizzaTeam(Scanner scanner) {
	    System.out.println("Inserire l'ID del team da visualizzare: ");
	    int idTeam = scanner.nextInt();
	    scanner.nextLine(); // Consuma il newline

	    String sql = "SELECT * FROM Team WHERE id = ?";
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, idTeam);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("ID: " + rs.getInt("id"));
	            System.out.println("Nome: " + rs.getString("nome"));
	            System.out.println("Descrizione: " + rs.getString("descrizione"));
	            System.out.println("Membri: " + rs.getString("membri"));
	        } else {
	            System.out.println("Nessun team trovato con l'ID specificato.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
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
	public static void assegnaManager(Scanner scanner) {
	    System.out.println("Inserire l'ID del team al quale assegnare un manager: ");
	    int teamId = scanner.nextInt();
	    scanner.nextLine(); // Consuma il newline
	    System.out.println("Inserire l'ID del manager: ");
	    int managerId = scanner.nextInt();
	    scanner.nextLine(); // Consuma il newline

	    String sql = "UPDATE Team SET managerId = ? WHERE id = ?";
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
    public static void visualizzaTeamManager(Scanner scanner) {
        System.out.println("Inserire l'ID del manager per visualizzare i suoi team: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        String sql = "SELECT * FROM Team WHERE managerId = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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

    public static void assegnaTeamAProgetto(Scanner scanner) {
        System.out.println("Inserire l'ID del team da assegnare: ");
        int idTeam = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        System.out.println("Inserire l'ID del progetto al quale assegnare il team: ");
        int idProgetto = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        String sql = "INSERT INTO TeamProgetto (id_team, id_progetto) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTeam);
            pstmt.setInt(2, idProgetto);

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
    public static void calcolaStipendioTotale(Scanner scanner) {
        System.out.println("Inserire l'ID del team: ");
        int idTeam = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        String sql = "SELECT SUM(d.stipendio) AS stipendioTotale " +
                     "FROM Dipendenti d " +
                     "INNER JOIN Team t ON FIND_IN_SET(d.id, t.membri) > 0 " +
                     "WHERE t.id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTeam);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double stipendioTotale = rs.getDouble("stipendioTotale");
                System.out.println("Stipendio totale del team: " + stipendioTotale);
            } else {
                System.out.println("Nessun dato trovato per il team specificato.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


