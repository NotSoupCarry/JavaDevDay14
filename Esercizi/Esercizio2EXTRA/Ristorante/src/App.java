import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe per la connessione al database
class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ristoranteappdb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connessioneDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Classe per le operazioni sul db
class DatabaseOperations {
    private Connection conn;

    public DatabaseOperations(Connection conn) {
        this.conn = conn;
    }

    // Metodo per aggiungere un utente al database
    public void aggiungiUtente(String nome, String email) throws SQLException {
        // Controllo se l'utente esiste già
        String checkUserSQL = "SELECT COUNT(*) FROM utenti WHERE nome = ? OR email = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkUserSQL)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("L'utente con questo nome o email esiste già nel sistema.");
            } else {
                // Se non esiste, inseriamo il nuovo utente
                String insertUserSQL = "INSERT INTO utenti (nome, email) VALUES (?, ?)";
                try (PreparedStatement insertPS = conn.prepareStatement(insertUserSQL)) {
                    insertPS.setString(1, nome);
                    insertPS.setString(2, email);
                    insertPS.executeUpdate();
                    System.out.println("Utente aggiunto con successo!");
                }
            }
        }
    }

    // Metodo per visualizzare tutti gli utenti
    public void visualizzaUtenti() throws SQLException {
        String selectUserSQL = "SELECT * FROM utenti";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectUserSQL);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                System.out.println("Nome: " + nome + ", Email: " + email);
            }
        }
    }

    // Metodo per aggiungere un'ordinazione per nome utente
    public void aggiungiOrdinazione(String nomeUtente, String piatto, double prezzo) throws SQLException {
        // Controlliamo se l'utente esiste nel database
        String checkUserSQL = "SELECT id FROM utenti WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkUserSQL)) {
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();

            // Se l'utente non esiste, chiediamo di reinserire il nome utente
            if (!rs.next()) {
                System.out.println("Utente con nome " + nomeUtente + " non trovato.");
                return; // Esce dal metodo senza aggiungere l'ordinazione
            } else {
                int userId = rs.getInt("id");
                // Procediamo con l'inserimento dell'ordinazione
                String insertOrderSQL = "INSERT INTO ordinazioni (user_id, piatto, prezzo) VALUES (?, ?, ?)";
                try (PreparedStatement insertPS = conn.prepareStatement(insertOrderSQL)) {
                    insertPS.setInt(1, userId);
                    insertPS.setString(2, piatto);
                    insertPS.setDouble(3, prezzo);
                    insertPS.executeUpdate();
                    System.out.println("Ordinazione aggiunta per l'utente: " + nomeUtente);
                }
            }
        }
    }

    // Metodo per visualizzare tutte le ordinazioni di un determinato utente
    public void visualizzaOrdinazioni(String nomeUtente) throws SQLException {
        String checkUserSQL = "SELECT id FROM utenti WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkUserSQL)) {
            ps.setString(1, nomeUtente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String selectOrderSQL = "SELECT * FROM ordinazioni WHERE user_id = ?";
                try (PreparedStatement orderPS = conn.prepareStatement(selectOrderSQL)) {
                    orderPS.setInt(1, userId);
                    ResultSet orderRS = orderPS.executeQuery();
                    while (orderRS.next()) {
                        String piatto = orderRS.getString("piatto");
                        double prezzo = orderRS.getDouble("prezzo");
                        System.out.println("Piatto: " + piatto + ", Prezzo: " + prezzo + "€");
                    }
                }
            } else {
                System.out.println("Utente con nome " + nomeUtente + " non trovato.");
            }
        }
    }
}

class Piatto {
    private String ingredienti;
    private double prezzo;
    private String chef;

    public Piatto(String ingredienti, double prezzo, String chef) {
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
        this.chef = chef;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public String getChef() {
        return chef;
    }

    @Override
    public String toString() {
        return "Piatto: " + ingredienti + " | Prezzo: " + prezzo + " | Chef: " + chef;
    }
}

// Classe per la gestione delle ordinazioni
class Ordinazione {
    private double prezzo;
    private Piatto piatto;

    public Ordinazione(Piatto piatto) {
        this.piatto = piatto;
        this.prezzo = piatto.getPrezzo();
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Piatto getPiatto() {
        return piatto;
    }
}

// Classe per il menu
class Menu {
    private List<Piatto> piattiMenu;

    public Menu() {
        this.piattiMenu = new ArrayList<>();
    }

    public void aggiungiPiatto(Piatto piatto) {
        piattiMenu.add(piatto);
    }

    public void mostraMenu() {
        for (Piatto piatto : piattiMenu) {
            System.out.println(piatto);
        }
    }
}

// Classe Controlli
class Controlli {
    // Metodo per controllare che l'input stringa non sia vuoto
    public static String controlloInputStringhe(Scanner scanner) {
        String valore;
        do {
            valore = scanner.nextLine().trim();
            if (valore.isEmpty()) {
                System.out.print("Input non valido. Inserisci un testo: ");
            }
        } while (valore.isEmpty());
        return valore;
    }

    // Metodo per controllare l'input intero
    public static int controlloInputInteri(Scanner scanner) {
        int valore;
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.print("Devi inserire un numero intero. Riprova: ");
                scanner.next(); // Consuma l'input errato
            } else {
                valore = scanner.nextInt();
                if (valore >= 0) {
                    return valore;
                }
                System.out.print("Il numero non può essere negativo. Riprova: ");
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Connection conn = DBContext.connessioneDatabase();
        DatabaseOperations dbOperations = new DatabaseOperations(conn);
        Scanner scanner = new Scanner(System.in);

        // Creazione e avvio del menu principale
        MenuUtente menuPrincipale = new MenuUtente(dbOperations, scanner);
        menuPrincipale.menuPrincipale();
    }
}

class MenuUtente {
    private DatabaseOperations dbOperations;
    private Scanner scanner;

    public MenuUtente(DatabaseOperations dbOperations, Scanner scanner) {
        this.dbOperations = dbOperations;
        this.scanner = scanner;
    }

    public void menuPrincipale() {
        boolean exitMainMenu = false;
        while (!exitMainMenu) {
            // Visualizza il menu delle operazioni
            System.out.println("\nMenu:");
            System.out.println("1. Aggiungi un nuovo utente");
            System.out.println("2. Visualizza tutti gli utenti");
            System.out.println("3. Aggiungi un'ordinazione");
            System.out.println("4. Visualizza le ordinazioni di un utente");
            System.out.println("5. Esci");

            System.out.print("Scegli un'opzione: ");
            int scelta = Controlli.controlloInputInteri(scanner);
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Aggiungi un nuovo utente
                    System.out.print("Inserisci il nome dell'utente: ");
                    String nome = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Inserisci l'email dell'utente: ");
                    String email = Controlli.controlloInputStringhe(scanner);
                    try {
                        dbOperations.aggiungiUtente(nome, email);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    // Visualizza tutti gli utenti
                    try {
                        dbOperations.visualizzaUtenti();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    // Aggiungi un'ordinazione per nome utente
                    System.out.print("Inserisci il nome dell'utente: ");
                    String nomeUtenteOrdinazione = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Inserisci il nome del piatto: ");
                    String piatto = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Inserisci il prezzo del piatto: ");
                    double prezzo = scanner.nextDouble();
                    try {
                        dbOperations.aggiungiOrdinazione(nomeUtenteOrdinazione, piatto, prezzo);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    // Visualizza le ordinazioni di un utente
                    System.out.print("Inserisci il nome dell'utente: ");
                    String nomeUtenteVisualizza = Controlli.controlloInputStringhe(scanner);
                    try {
                        dbOperations.visualizzaOrdinazioni(nomeUtenteVisualizza);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    // Esci
                    System.out.println("Uscita dal programma.");
                    exitMainMenu = true;
                    return;

                default:
                    System.out.println("Opzione non valida, riprova.");
                    break;
            }
        }

    }

}