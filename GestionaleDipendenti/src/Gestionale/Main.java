package Gestionale;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//Database.createTables(); //FUNZIONA FINO A CREAZIONEDEVELOPER. BISOGNA INVERTIRE L'ORDINE E METTERE PRIMA CREAZIONE TEAM
		Employee.aggiuntaDipendente(scanner); //FUNZIONA
		//Employee.eliminazioneDipendente(scanner); //DA VERIFICARE
		Employee.stampaDipendente(scanner);
		scanner.close();
	}

}
