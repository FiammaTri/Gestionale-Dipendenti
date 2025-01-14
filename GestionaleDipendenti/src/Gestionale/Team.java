//Classe utilizzata per la gestione della tabella Team
package Gestionale;

public class Team {
	// rendiamo gli attributi privati per poterli usare solo in questa classe
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

	public void aggiungiMembro(int idMembro) {
		String nuovoMembro = String.valueOf(idMembro); // Converte l'intero in stringa
		if (!membri.contains(nuovoMembro)) {
			if (membri.isEmpty()) {
				membri = nuovoMembro; // Se la stringa è vuota, aggiungi direttamente il membro
			} else {
				membri += "," + nuovoMembro; // Altrimenti aggiungi una virgola e poi il membro
			}
		}
	}

	public void rimuoviMembro(int idMembro) {
		// Converte l'id in una stringa come abbiamo fatto in precedenza
		String membroDaRimuovere = String.valueOf(idMembro);

		// Ciclo ci controllo che controlla se l'id del membro esiste nella lista
		if (membri.contains(membroDaRimuovere)) {
			// Se l'id è l'unico membro, basta svuotare la lista
			if (membri.equals(membroDaRimuovere)) {
				membri = ""; // Rimuove il membro unico
			} else {
				// Se l'id è nel mezzo o alla fine, lo rimuoviamo usando replace
				membri = membri.replace(membroDaRimuovere + ",", ""); // Rimuove il membro con la virgola dopo
				membri = membri.replace("," + membroDaRimuovere, ""); // Rimuove il membro con la virgola prima
				membri = membri.replace(membroDaRimuovere, ""); // Rimuove il membro se è l'unico
			}
		}

		// Rimuove eventuali virgole in eccesso all'inizio o alla fine
		if (membri.startsWith(",")) {
			membri = membri.substring(1); // Rimuove la virgola iniziale
		}
		if (membri.endsWith(",")) {
			membri = membri.substring(0, membri.length() - 1); // Rimuove la virgola finale
		}

	}

	// Metodo per stampare i dettagli del team
	public void stampaDettagli() {
		System.out.println("ID: " + id + ", Nome: " + nome + ", Descrizione: " + descrizione); //output, stampa
		System.out.println("Membri del Team: " + membri);
	}
}
