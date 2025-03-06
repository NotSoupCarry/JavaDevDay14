import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Classe Piatto
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

    public void stampaPiatto() {
        System.out.println("Piatto: " + ingredienti + " | Prezzo: " + prezzo + "€ | Chef: " + chef);
    }

    @Override
    public String toString() {
        return "Piatto: " + ingredienti + " | Prezzo: " + prezzo + " | Chef: " + chef;
    }
}

// Classe Menu che gestisce una lista di piatti
class Menu {
    private List<Piatto> piatti;

    public Menu() {
        this.piatti = new ArrayList<>();
    }

    public void mostraMenu() {
        System.out.println("\n--- MENU DEL RISTORANTE ---");
        for (Piatto piatto : piatti) {
            System.out.println(piatto);
        }
    }
}

// Classe Ordinazione che gestisce il conto totale
class Ordinazione {
    private double prezzo;

    Ordinazione(Piatto piatto) {
        prezzo = piatto.getPrezzo();
    }

    public double getPrezzo() {
        return prezzo;
    }
}

// Classe principale con il Main
public class EsercizioPolimorfismo2 {
    public static void main(String[] args) {

        Menu menu = new Menu();

        // Creazione dei piatti
        Piatto p1 = new Piatto("Lasagna", 12.50, "Giacomo");
        Piatto p2 = new Piatto("Carbonara", 10.00, "Mimmo");
        Piatto p3 = new Piatto("Tiramisù", 6.50, "The ashen Lord");

        menu.mostraMenu();

        Ordinazione o1 = new Ordinazione(p1);
        Ordinazione o2 = new Ordinazione(p2);
        Ordinazione o3 = new Ordinazione(p3);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        // Calcola e stampa il totale
        double totale = o1.getPrezzo() + o2.getPrezzo() + o3.getPrezzo();
        System.out.println("\nTotale dell'ordinazione: " + String.format(Locale.ITALIAN, "%.2f", totale) + " euro");
    }
}
