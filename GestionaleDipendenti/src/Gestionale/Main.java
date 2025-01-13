package Gestionale;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//Database.createTables(); //FUNZIONA FINO A CREAZIONEDEVELOPER. BISOGNA INVERTIRE L'ORDINE E METTERE PRIMA CREAZIONE TEAM
		Employee.aggiuntaDipendente(scanner); //FUNZIONA
		//Employee.eliminazioneDipendente(scanner); //DA VERIFICARE
		Employee.stampaDipendente(scanner);
		
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
