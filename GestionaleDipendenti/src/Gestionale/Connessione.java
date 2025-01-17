package Gestionale;

public class Connessione {
	
	// Parametri di connessione
	private static final String URL = "jdbc:mysql://localhost:3306/gestionaledipendenti";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	
	public static String getURL() {
		return URL;
	}
	public static String getUSER() {
		return USER;
	}
	public static String getPASSWORD() {
		return PASSWORD;
	}
	
}
