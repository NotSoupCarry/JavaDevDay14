import java.util.Date;
import java.util.Scanner;

public class AppSupermercato {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestoreProdotti gestore = new GestoreProdotti();
        boolean exitMainMenu = false;
        while (!exitMainMenu) {
            System.out.println("\n===== MENU SUPERMERCATO =====");
            System.out.println("1. Aggiungi un prodotto");
            System.out.println("2. Mostra tutti i prodotti");
            System.out.println("3. Rimuovi un prodotto");
            System.out.println("4. Calcola sconto per prodotti alimentari in scadenza");
            System.out.println("5. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.print("Codice: ");
                    String codice = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Nome: ");
                    String nome = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Prezzo: ");
                    double prezzo = Controlli.controlloInputDouble(scanner);
                    scanner.nextLine();
                    System.out.print("Tipo (1=Alimentare, 2=Elettronico, 3=Abbigliamento): ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    if (tipo == 1) {
                        System.out.print("Data scadenza (YYYY-MM-DD): ");
                        Date dataScadenza = Controlli.controlloInputData(scanner);
                        gestore.aggiungiProdotto(new Alimentare(codice, nome, prezzo, dataScadenza));

                    } else if (tipo == 2) {
                        System.out.print("Mesi di garanzia: ");
                        int mesi = Controlli.controlloInputInteri(scanner);
                        gestore.aggiungiProdotto(new Elettronico(codice, nome, prezzo, mesi));
                    } else if (tipo == 3) {
                        System.out.print("Taglia: ");
                        String taglia = Controlli.controlloInputStringhe(scanner);
                        System.out.print("Materiale: ");
                        String materiale = Controlli.controlloInputStringhe(scanner);
                        gestore.aggiungiProdotto(new Abbigliamento(codice, nome, prezzo, taglia, materiale));
                    }
                    break;
                case 2:
                    gestore.mostraTuttiProdotti();
                    break;
                case 3:
                    System.out.print("Inserisci codice del prodotto da eliminare: ");
                    String cod = Controlli.controlloInputStringhe(scanner);
                    gestore.rimuoviProdotto(cod);
                    break;
                case 4:
                    gestore.calcolaScontoAlimentariInScadenza();
                    break;
                case 5:
                    System.out.println("Chiusura del programma.");
                    exitMainMenu = true;
                    return;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
        scanner.close();
    }
}
