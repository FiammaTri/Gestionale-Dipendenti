//Classe utilizzata per la gestione della tabella Employee
package Gestionale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employee {

	// Parametri di connessione

	/*
	 * metodo per inserire un nuovo dipendente nella tabella Employee
	 * 
	 * @param nome Nome del dipendente
	 * 
	 * @param cognome Cognome del dipendente
	 * 
	 * @param stipendioBase stipendio del dipendente
	 * 
	 * @param ruolo ruolo del dipendente
	 * 
	 * @param scanner scanner per le operazioni di inserimento
	 * 
	 * @return L'ID generato per il nuovo cliente
	 */
	public static int aggiuntaDipendente(Scanner scanner) {
	    String nome;
	    String cognome;
	    Double stipendioBase;
	    String ruolo = "Developer";
	    String tipoDipendente = ""; //Per capire se è un manager/developer
	    int idDipendente = -1;

	    System.out.print("Inserire il NOME: ");
	    nome = scanner.nextLine();
	    System.out.print("Inserire il COGNOME: ");
	    cognome = scanner.nextLine();
	    System.out.print("Inserire lo STIPENDIO BASE: ");
	    stipendioBase = scanner.nextDouble();
	    scanner.nextLine(); 
	    System.out.println("Il dipendente è un manager o un developer? [M/D]");
	    
	    //Per capire quale ruolo voglia inserire il dipedente
	    boolean tipoValido = false;
	    while (!tipoValido) {
	        tipoDipendente = scanner.nextLine();
	        if (tipoDipendente.equalsIgnoreCase("M") || tipoDipendente.equalsIgnoreCase("D")) {
	            tipoValido = true;
	        } else {
	            System.out.println("Tipo di dipendente non valido.");
	        }
	    }

	    String sql = "INSERT INTO gestionaledipendenti.EMPLOYEE (nome, cognome, stipendioBase, ruolo) VALUES (?,?,?,?)";
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        pstmt.setString(1, nome);
	        pstmt.setString(2, cognome);
	        pstmt.setDouble(3, stipendioBase);
	        if (tipoDipendente.equalsIgnoreCase("M")) {
	            ruolo = "Manager";
	        }
	        pstmt.setString(4, ruolo);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creazione dipendente fallita");
	        } else {
	            System.out.println("Dipendente aggiunto");
	        }

	        //Recupero la chiave generata
	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                idDipendente = generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creazione dipendente fallita, ID non recuperato");
	            }
	        }

	        //Gestione ruolo manager
	        if (tipoDipendente.equalsIgnoreCase("M")) {
	            System.out.print("Inserire il bonus per il manager: ");
	            Double bonus = scanner.nextDouble();
	            scanner.nextLine(); 

	            String sqlManager = "INSERT INTO gestionaledipendenti.Manager (bonus, id_Employee) VALUES (?, ?)";
	            try (PreparedStatement pstmtManager = conn.prepareStatement(sqlManager)) {
	                pstmtManager.setDouble(1, bonus);
	                pstmtManager.setInt(2, idDipendente);
	                pstmtManager.executeUpdate();
	                System.out.println("Manager aggiunto con successo.");
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        //Gestione developer
	        else if (tipoDipendente.equalsIgnoreCase("D")) {
	            String sqlDeveloper = "INSERT INTO gestionaledipendenti.Developer (id_Employee) VALUES (?)";
	            try (PreparedStatement pstmtDeveloper = conn.prepareStatement(sqlDeveloper)) {
	                pstmtDeveloper.setInt(1, idDipendente);
	                pstmtDeveloper.executeUpdate();
	                System.out.println("Developer aggiunto con successo.");
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return idDipendente;
	}

	/**
	 * Elimina un dipendente nella tabella EMPLOYEE dato il suo ID.
	 *
	 * @param id_Employee ID del dipendente
	 */
	public static void eliminazioneDipendente(Scanner scanner) {
	    int id_Employee;
	    System.out.print("Inserire l'ID del dipendente da eliminare: ");
	    id_Employee = scanner.nextInt();
	    scanner.nextLine();

	    //Controlla se esiste
	    if (!ricercaEmployee(id_Employee)) {
	        System.out.println("Nessun dipendente trovato con l'ID " + id_Employee + ". Eliminazione non possibile.");
	        return; 
	    }

	    //Elimina prima eventuali riferimenti
	    String deleteManagerSql = "DELETE FROM gestionaledipendenti.Manager WHERE id_Employee = ?";
	    String deleteDeveloperSql = "DELETE FROM gestionaledipendenti.Developer WHERE id_Employee = ?";

	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD())) {

	    	//Elimina il manager associato
	    	try (PreparedStatement pstmtManager = conn.prepareStatement(deleteManagerSql)) {
	    	    pstmtManager.setInt(1, id_Employee);
	    	    pstmtManager.executeUpdate();
	    	} catch (SQLException e) {
	    	    System.out.println("Errore durante l'eliminazione del Manager con ID " + id_Employee);
	    	    e.printStackTrace();  
	    	}

	    	//Elimina il Developer associato
	    	try (PreparedStatement pstmtDeveloper = conn.prepareStatement(deleteDeveloperSql)) {
	    	    pstmtDeveloper.setInt(1, id_Employee);
	    	    pstmtDeveloper.executeUpdate();
	    	} catch (SQLException e) {
	    	    System.out.println("Errore durante l'eliminazione del Developer con ID " + id_Employee);
	    	    e.printStackTrace();  
	    	}
	        //Eliminazione del dipedente
	        String sql = "DELETE FROM gestionaledipendenti.Employee WHERE id_Employee = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, id_Employee);
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                System.out.println("Dipendente con ID " + id_Employee + " eliminato correttamente.");
	            } else {
	                System.out.println("Nessun dipendente eliminato. Verificare l'ID.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Ricerca un dipendente.
	 *
	 * @param id_Employee ID del dipendente
	 */
	public static boolean ricercaEmployee(int id_Employee) {
	    String sql = "SELECT COUNT(*) FROM gestionaledipendenti.Employee WHERE id_Employee = ?";
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmtCheck = conn.prepareStatement(sql)) {

	        pstmtCheck.setInt(1, id_Employee);
	        ResultSet rs = pstmtCheck.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true; //esiste
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;//non esiste
	}

	/**
	 * Stampa le informazioni del dipendente dalla tabella Employee.
	 * 
	 * @param scanner 
	 */ 
	public static void stampaDipendente(Scanner scanner) {

		System.out.print("Inserire l'ID del dipendende da visualizzare: ");
		int sceltaId = scanner.nextInt();
		scanner.nextLine();

		String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM gestionaledipendenti.Employee where id_Employee="
				+ sceltaId;
		try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(),
				Connessione.getPASSWORD());
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			System.out.println("Informazioni dipendente:");
			rs.next();
			
			int id_Employee = rs.getInt("id_Employee");
			String nome = rs.getString("nome");
			String cognome = rs.getString("cognome");
			double stipendioBase = rs.getDouble("stipendioBase");
			String ruolo = rs.getString("ruolo");
			System.out.printf("ID: %d | Nome: %s | Cognome: %s | Stipendio Base: %.2f | Ruolo: %s", id_Employee, nome,
					cognome, stipendioBase, ruolo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aggiorna i dati del dipendente dato il suo ID.
	 *
	 * @param scanner 
	 */
	public static void aggiornamentoInfo(Scanner scanner) {

		System.out.print("Inserisci l'ID del dipendente per aggiornare le sue informazioni: ");
		int sceltaId = scanner.nextInt();
		String sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="
				+ sceltaId;

		try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(),
				Connessione.getPASSWORD()); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			System.out.println("Cosa vuoi modificare? \n 1) Nome \n 2) Cognome \n 3) Stipendio ");
			int scelta = scanner.nextInt();
			scanner.nextLine();

			int affectedRows = 0;
			if (scelta == 1) {
				try (PreparedStatement pstmtUp = conn.prepareStatement(
						"UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee=" + sceltaId)) {
					System.out.println("NOME: ");
					String nuovoNome = scanner.nextLine();
					pstmtUp.setString(1, nuovoNome);

					affectedRows = pstmtUp.executeUpdate();
				}

			} else if (scelta == 2) {
				try (PreparedStatement pstmtUp = conn.prepareStatement(
						"UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee =" + sceltaId)) {
					System.out.println("COGNOME: ");
					String nuovoCognome = scanner.nextLine();
					pstmtUp.setString(1, nuovoCognome);

					affectedRows = pstmt.executeUpdate();
				}

			} else if (scelta == 3) {
				try (PreparedStatement pstmtUp = conn.prepareStatement(
						"UPDATE gestionaledipendenti.Employee SET nome = ? WHERE id_Employee = " + sceltaId)) {
					System.out.println("STIPENDIO: ");
					double nuovoStipendio = scanner.nextDouble();
					pstmtUp.setDouble(1, nuovoStipendio);

					affectedRows = pstmt.executeUpdate();
				}
			}

			if (affectedRows > 0) {
				System.out.println("Informazioni aggiornate. ");
				sql = "SELECT id_Employee, nome, cognome, stipendioBase, ruolo FROM Employee where id_Employee="
						+ sceltaId;
			} else {
				System.out.println("Nessun dato aggiornato. Verificare l'ID.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean ricercaRuolo(int id_Employee, String nomeTabella, Scanner scanner) {
	    String sql = "SELECT * FROM gestionaledipendenti." + nomeTabella + " WHERE id_Employee=?;"; 
	    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD());
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, id_Employee);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Il dipendente con ID " + id_Employee + " è stato trovato.");
	            return true;
	        } else {
	            System.out.println("Nessun dipedente trovato con ID " + id_Employee + ". Verifica l'ID.");
	            return false;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}

	/**
	 * Aggiorna il ruolo del dipendente dato il suo ID.
	 *
	 * @param scanner 
	 */
	public static void aggiornamentoRuolo(Scanner scanner) {

    System.out.print("Inserisci l'ID del dipendente per aggiornare il suo ruolo: ");
    int sceltaId = scanner.nextInt();
    scanner.nextLine(); 

    //Verifica se il dipendente esiste
    if (!ricercaEmployee(sceltaId)) {
        System.out.println("Nessun dipendente trovato con l'ID " + sceltaId + ". Aggiornamento non possibile.");
        return; 
    }

    String sql = "UPDATE gestionaledipendenti.Employee SET ruolo = ? WHERE id_Employee = ?";

    try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(), Connessione.getPASSWORD())) {
        System.out.print("Inserisci il nuovo RUOLO: ");
        String nuovoRuolo = scanner.nextLine();
        //L'aggiornamento del ruolo
        try (PreparedStatement pstmtAggiorna = conn.prepareStatement(sql)) {
        	pstmtAggiorna.setString(1, nuovoRuolo);
        	pstmtAggiorna.setInt(2, sceltaId);
            int affectedRows = pstmtAggiorna.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Ruolo aggiornato con successo.");
            } else {
                System.out.println("Nessun dato aggiornato. Verificare l'ID.");
            }
        }
        if (ricercaRuolo(sceltaId, "manager", scanner) && !nuovoRuolo.equalsIgnoreCase("Manager")) {
            String Eliminasql = "DELETE FROM gestionaledipendenti.Manager WHERE id_Employee = ?";
            try (PreparedStatement pstmtEliminaManager = conn.prepareStatement(Eliminasql)) {
            	pstmtEliminaManager.setInt(1, sceltaId);
            	pstmtEliminaManager.executeUpdate();
                System.out.println("Referenza Manager rimossa.");
                System.out.print("Inserisci l'ID del Team del Developer: ");
                int idTeam = scanner.nextInt();
                scanner.nextLine(); 
                String insertDeveloperSql = "INSERT INTO gestionaledipendenti.Developer (id_Employee, id_Team) VALUES (?, ?)";
                try (PreparedStatement pstmtInserisciDeveloper = conn.prepareStatement(insertDeveloperSql)) {
                    pstmtInserisciDeveloper.setInt(1, sceltaId);
                    pstmtInserisciDeveloper.setInt(2, idTeam);
                    pstmtInserisciDeveloper.executeUpdate();
                    System.out.println("Nuovo Developer inserito con successo.");
                } catch (SQLException e) {
                    System.out.println("Errore durante l'inserimento del Developer.");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la rimozione del Manager.");
                e.printStackTrace();
            }
        }
        if (ricercaRuolo(sceltaId, "developer", scanner) && !nuovoRuolo.equalsIgnoreCase("Developer")) {
            String Eliminasql = "DELETE FROM gestionaledipendenti.Developer WHERE id_Employee = ?";
            try (PreparedStatement pstmtEliminaManager = conn.prepareStatement(Eliminasql)) {
            	pstmtEliminaManager.setInt(1, sceltaId);
            	pstmtEliminaManager.executeUpdate();
                System.out.println("Referenza Developer rimossa.");
                
                System.out.print("Inserisci il bonus del Manager: ");
                double bonus = scanner.nextDouble();
                scanner.nextLine(); 

                String inserimentoManagerSql = "INSERT INTO gestionaledipendenti.Manager (id_Employee, bonus) VALUES (?, ?)";
                try (PreparedStatement pstmtInserisciManager = conn.prepareStatement(inserimentoManagerSql)) {
                    pstmtInserisciManager.setInt(1, sceltaId);
                    pstmtInserisciManager.setDouble(2, bonus);
                    pstmtInserisciManager.executeUpdate();
                    System.out.println("Cambio ruolo avennuto con successo.");
                } catch (SQLException e) {
                    System.out.println("Errore durante l'inserimento del Manager.");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la rimozione del Developer.");
                e.printStackTrace();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

	/**
	 * Calocla i stipendi dei dipedenti.
	 *
	 */
	public static void calcoloStipendi(){
		String sqlmese = "SELECT SUM(stipendioBase) AS stipendiMensili FROM gestionaledipendenti.Employee;";
		String sqlanno = "SELECT SUM(stipendioBase)*12 AS stipendiAnnuali FROM gestionaledipendenti.Employee;";
		
		 try (Connection conn = DriverManager.getConnection(Connessione.getURL(), Connessione.getUSER(),
					Connessione.getPASSWORD());
					Statement stmt = conn.createStatement()){
			 		//parte il resultset per il mese
					ResultSet rsmese = stmt.executeQuery(sqlmese);
			 		rsmese.next();
    	        	double stipendiMensili=rsmese.getDouble("stipendiMensili");
    	        	System.out.println("Stipendi mensili: " + stipendiMensili +" euro.");
    	        	//parte il resultset per l'anno
    	        	ResultSet rsanno = stmt.executeQuery(sqlanno);
    	        	rsanno.next();
    	        	double stipendiAnnuali=rsanno.getDouble("stipendiAnnuali");
    	        	System.out.println("Stipendi annuali: " + stipendiAnnuali +" euro.");
    		 }
	 catch (SQLException e) {
		e.printStackTrace();
	}
}

}
