package Gestionale;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	/**
	 * Gestisce gli input di un si e no
	 * 
	 * @param frase Una stringa usata per stampare la domanda.
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return La risposta dell'utente
	 */
	
	static boolean richiesta(String frase, Scanner scanner) {
		boolean ris= false;
		String scelta=" ";
		do {
			System.out.print(frase+ " ");
			scelta= scanner.nextLine().trim().toLowerCase();
			if(!(scelta.equals("si")||scelta.equals("no"))) {
				System.out.println("input non valido .");
			}else if(scelta.equals("no")) 
			{
				return ris;
			}
		}while(!(scelta.equals("si")||scelta.equals("no")));
		ris=true;
		return ris;
	}
	
	
	/**
	 * Gestisce gli input delle scelte dell'untente per scegliere l'opzioni dei menu
	 * 
	 * @param frase Una stringa usata per stampare le opzioni dei menu.
	 * @param range Una stringa che indica un intervallo di numeri validi [n-n].
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 * @return L'opzione di scelta nel menu
	 */
	static int richiesta (String frase, String range, Scanner scanner) {
		String scelta=" ";
		System.out.println(frase);
			//Ciclo per vedere se l'input utente sia valido
			do {
				//Ciclo numero tentativi
				for(int i=0; i<3; i++) {
					System.out.print("Inserici il numero dell'opzione che vuoi selezionare: ");
					scelta= scanner.nextLine().trim().toLowerCase();
					//Controllo solo per i caratteri numerici
					if(!scelta.matches("\\d+")) {
						System.err.println("Il codice è solo caratteri numerici");
					}else if(!scelta.matches(range)) {//Controlla se abbia inserito numeri giusti dell'intervallo
						System.err.println("input non valido.");
					}else {
						break;
					}
				}
			}while(!scelta.matches(range));
		//Ritorna un intero
		return  Integer.parseInt(scelta); 
	}
	
	/**
	 * Gestisce il menu per gestire tutte le opzioni possibile effettuabili sui dipendenti
	 *
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	static void menuDipendenti(Scanner scanner) {
		boolean ripetizione = true; //Uscita dal esecuzione continua
		String menu= "\033[1m1) Inserire un nuovo dipendente\n\033[0m"
				+ "\033[1m2) Visualizza un determinato dipendente\n\033[0m"
				+ "\033[1m3) Aggiornare le informazioni di un dipendente\n\033[0m"
				+ "\033[1m4) Cambiare il ruolo di un dipendente\n\033[0m"
				+ "\033[1m5) Eliminare un dipendente\n\033[0m"
				+ "\033[1m6) Calcolare lo stipendio annuo di un dipendente\n\033[0m"
				+ "\033[1m7) Uscita\n\033[0m";
		//Ciclo per far continuare l'esecuzione
		while(ripetizione) {
			
	        System.out.println("\033[1;4;34mInterfaccia Dipendenti:\033[0m");
			
			switch(richiesta(menu, "[1-7]", scanner)){
			case 1:
				System.out.println("Hai scelto inserire un nuovo dipendente.");
				Employee.aggiuntaDipendente(scanner);
				break;
			case 2:
				System.out.println("Hai scelto visualizza un determinato dipendente.");
				Employee.stampaDipendente(scanner);
				break;
			case 3:
				System.out.println("Hai scelto aggiornare le informazioni di un dipendente.");
				Employee.aggiornamentoInfo(scanner);
				break;
			case 4:
				System.out.println("Hai scelto cambiare il ruolo di un dipendente.");
				Employee.aggiornamentoRuolo(scanner);
				break;
			case 5:
				System.out.println("Hai scelto eliminare un dipendente.");
				Employee.eliminazioneDipendente(scanner);
				break;
			case 6:
				System.out.println("Hai richiesto calcolare il totale degli stipendi.");
				Employee.calcoloStipendi();
				break;
			case 7:
				if(richiesta("Vuoi uscire dall'interfaccia dei dipendenti? [si/no]", scanner)) {
					System.out.println("Avvio uscita dal programma...");
					ripetizione = false; //Chiude direttamente l'esecuzione
				}
				 break;
			}
			System.out.println();
		}
	}
	
	/**
	 * Gestisce il menu per gestire tutte le opzioni possibile effettuabili sui developer
	 *
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	static void menuDeveloper(Scanner scanner) {
		boolean ripetizione = true; //Uscita dal esecuzione continua
		String menu= "\033[1m1) Assegnare un developer ad un nuovo team.\n\033[0m"
				+ "\033[1m2) Assegnare un developer ad un nuovo progetto.\n\033[0m"
				+ "\033[1m3) Aggiornare i linguaggi di un developer.\n\033[0m"
				+ "\033[1m4) Visualizzare tutti i progetti del developer.\n\033[0m"
				+ "\033[1m5) Visualizzare tutti i developer e i loro relativi progetti\n\033[0m"
				+ "\033[1m6) Uscita.\n\033[0m";
		//Ciclo per far continuare l'esecuzione
		while(ripetizione) {
			
	        System.out.println("\033[1;4;34mInterfaccia Developer:\033[0m");
			
			switch(richiesta(menu, "[1-6]", scanner)){
			case 1:
				System.out.println("Hai scelto assegnare un developer ad un nuovo team.");
				Developer.assegnamentoTeam(scanner);
				break;
			case 2:
				System.out.println("Hai scelto assegnare un developer ad un nuovo progetto.");
				Developer.assegnamentoProgetto(scanner);
				break;
			case 3:
				System.out.println("Hai scelto aggiornare i linguaggi di un developer.");
				int scelta;
		    	//Ciclo per far conitnuare l'inserimento anche in caso di errore
			    while(true) {
				    try {
					   	System.out.print("1) Assegnare al developer un nuovo linguaggio.\n"
					   			+ "2) Rimuovere un assegnazione di un linguaggio da un developer.\n"
					   			+ "Scegli l'opzione [1-2]: ");
					    scelta=scanner.nextInt();
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
			    //Mista la scelta dell utente
			    if(scelta==1) {
			    	Developer.assegnamentoLinguaggio(scanner);
			    }if(scelta==2) {
			    	Developer.togliereLinguaggio(scanner);
			    }else {
			    	System.out.println("Hai inserito un campo non valido.");
			    }
				break;
			case 4:
				System.out.println("Hai scelto visualizzare tutti i progetti del developer.");
				Developer.StampaDevProg(scanner);
				break;
			case 5:
				System.out.println("Visualizzare tutti i developer e i loro relativi progetti.");
				Developer.Stampa();
				break;
			case 6:
				if(richiesta("Vuoi uscire dall'interfaccia developer? [si/no]", scanner)) {
					System.out.println("Avvio uscita dal programma...");
					ripetizione = false; //Chiude direttamente l'esecuzione
				}
				 break;
			}
			System.out.println();
		}
	}
	
	/**
	 * Gestisce il menu per gestire tutte le opzioni possibile effettuabili sui manager
	 *
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	static void menuManager(Scanner scanner) {
		boolean ripetizione = true; //Uscita dal esecuzione continua
		String menu= "\033[1m1) Assegnare un manager ad un nuovo team.\n\033[0m"
				+ "\033[1m2) Visualizzare il team del manager.\n\033[0m"
				+ "\033[1m3) Uscita.\n\033[0m";
		//Ciclo per far continuare l'esecuzione
		while(ripetizione) {
			
	        System.out.println("\033[1;4;34mInterfaccia Manager:\033[0m");
			
			switch(richiesta(menu, "[1-4]", scanner)){
			case 1:
				System.out.println("Hai scelto assegnare un manager ad un nuovo team.");
				Manager.assegnamentoTeam(scanner);
				break;
			case 2:
				System.out.println("Hai visualizzare il team del manager.");
				Manager.StampaManTeam(scanner);
				break;
			case 3:
				if(richiesta("Vuoi uscire dall'interfaccia manager? [si/no]", scanner)) {
					System.out.println("Avvio uscita dal programma...");
					ripetizione = false; //Chiude direttamente l'esecuzione
				}
				 break;
			}
			System.out.println();
		}
	}
	
	/**
	 * Gestisce il menu per gestire tutte le opzioni possibile effettuabili sui team
	 *
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	static void menuTeam(Scanner scanner) {
		boolean ripetizione = true; //Uscita dal esecuzione continua
		String menu= "\033[1m2)1) Inserisci un nuovo team.\n\033[0m"
				+ "\033[1m2)2) Visualizzare infromazione del team.\n\033[0m"
				+ "\033[1m2)3) Modificare un team.\n\033[0m"
				+ "\033[1m2)4) Eliminare un team.\n\033[0m"
				+ "\033[1m2)5) Assegnare al team ad un nuovo progetto.\n\033[0m"
				+ "\033[1m2)6) Calcolare lo stipendio totale del team.\n\033[0m"
				+ "\033[1m2)7) Uscita.\n\033[0m";
		//Ciclo per far continuare l'esecuzione
		while(ripetizione) {
			
	        System.out.println("\033[1;4;34mInterfaccia Team:\033[0m");
	        
			
			switch(richiesta(menu, "[1-7]", scanner)){
			case 1:
				Team.aggiungiTeam(scanner);
				System.out.println("Hai scelto inserire un nuovo team.");
				break;
			case 2:
				System.out.println("Hai scelto visualizza un determinato team.");
				break;
			case 3:
				Team.aggiornaTeam(scanner);
				System.out.println("Hai scelto aggiornare le informazioni di un team.");
				break;
			case 4:
				Team.eliminaTeam(scanner);
				System.out.println("Hai scelto eliminare un team.");
				break;
			case 5:
				
		System.out.println("Hai scelto assegnare al team ad un nuovo progetto.");
				break;
			case 6:
				System.out.println("Hai scelto calcolare lo stipendio totale del team.");
				break;
			case 7:
				if(richiesta("Vuoi uscire dall'interfaccia team? [si/no]", scanner)) {
					System.out.println("Avvio uscita dal programma...");
					ripetizione = false; //Chiude direttamente l'esecuzione
				}
				 break;
			}

			System.out.println();
		}
	}
	
	/**
	 * Gestisce il menu per gestire tutte le opzioni possibile effettuabili sui progetti
	 *
	 * @param scanner Lo scanner utilizzato per leggere l'input dell'utente.
	 * 
	 */
	static void menuProgetto(Scanner scanner) {
		boolean ripetizione = true; //Uscita dal esecuzione continua
		String menu= "\033[1m2)1) Inserisci un nuovo progetto.\n\033[0m"
				+ "\033[1m2)2) Visualizzare infromazione del progetto.\n\033[0m"
				+ "\033[1m2)3) Modificare un progetto.\n\033[0m"
				+ "\033[1m2)4) Eliminare un progetto.\n\033[0m"
				+ "\033[1m2)5) Calcolare lo stipendio totale del progetto.\n\033[0m"
				+ "\033[1m2)6) Uscita.\n\033[0m";
		//Ciclo per far continuare l'esecuzione
		while(ripetizione) {
			
	        System.out.println("\033[1;4;34mInterfaccia Progetto:\033[0m");
			
			switch(richiesta(menu, "[1-6]", scanner)){
			case 1:
				System.out.println("Hai scelto inserire un nuovo progetto.");
				break;
			case 2:
				System.out.println("Hai scelto visualizza un determinato progetto.");
				break;
			case 3:
				System.out.println("Hai scelto aggiornare le informazioni di un progetto.");
				break;
			case 4:
				System.out.println("Hai scelto eliminare un progetto.");
				break;
			case 5:
				System.out.println("Hai scelto calcolare lo stipendio totale del team.");
				break;
			case 6:
				if(richiesta("Vuoi uscire dall'interfaccia dei progetti? [si/no]", scanner)) {
					System.out.println("Avvio uscita dal programma...");
					ripetizione = false; //Chiude direttamente l'esecuzione
				}
				 break;
			}
			
			System.out.println();

		}
	}

	public static void main(String[] args) {
		Database.createTables();
		//Dichiarazione dello scanner
		Scanner scanner = new Scanner (System.in);
		
		System.out.println("Avvio del programma...\n");
		
		//Ciclo per continuare con il programma
		while(true) {
			
			String menu = "\033[1m1) Gestione dei dipendenti.\n\033[0m"
					+ "\033[1m2) Gestione dei developer.\n\033[0m"
					+ "\033[1m3) Gestione dei manager.\n\033[0m"
					+ "\033[1m4) Gestione dei team.\n\033[0m"
					+ "\033[1m5) Gestione dei progetti.\n\033[0m"
					+ "\033[1m6) Uscita.\n\033[0m";
			
	        System.out.println("\033[1;4;34mInterfaccia Programma:\033[0m");
			
			//menu con cui interegisce l'utente
			switch(richiesta(menu, "[1-8]", scanner)) {
				case 1:
					System.out.println("Hai scelto gestione dei dipendenti.");
					menuDipendenti(scanner); //Richiamo interfaccia gestione dei dipendenti
					break;
				case 2:
					System.out.println("Hai scelto gestione dei developer");
					menuDeveloper(scanner); //Richiamo interfaccia gestione developer
					break;
				case 3:
					System.out.println("Hai scelto gestione dei manager");
					menuManager(scanner); //Richiamo interfaccia gestione manager
					break;
				case 4:
					System.out.println("Hai scelto gestione del team.");
					menuTeam(scanner); //Richiamo interfaccia gestione team
					break;
				case 5:
					System.out.println("Hai scelto gestione dei progetti.");
					menuProgetto(scanner); //Richiamo interfaccia gestione progetto
					break;
				case 6:
					if(richiesta("Vuoi uscire dal programma? [si/no]", scanner)) {
						System.out.println("Avvio uscita dal programma...");
						scanner.close();
						System.exit(0); //Chiude direttamente l'esecuzione
					}
					break;
			}
			
			System.out.println();

		}
	}
}
