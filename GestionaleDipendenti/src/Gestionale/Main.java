package Gestionale;

public class Main {

	public static void main(String[] args) {
		// Creazione del team per usare la classe creata provvisorio
        Team team = new Team("Team 1", "Responsabile del progetto 1", "1,2");

        // Aggiunta di un membro
        team.aggiungiMembro(3);

        // Rimozione di un membro
        team.rimuoviMembro(1);

        // Stampa dei dettagli del team
        team.stampaDettagli();
        

	}

}
