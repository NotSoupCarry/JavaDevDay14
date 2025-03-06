import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe astratta Veicolo
abstract class Veicolo {
    protected String marca;
    protected String modello;
    protected int annoProduzione;

    public Veicolo(String marca, String modello, int annoProduzione) {
        this.marca = marca;
        this.modello = modello;
        this.annoProduzione = annoProduzione;
    }

    public abstract void mostraDettagli();

    public int getAnnoProduzione() {
        return annoProduzione;
    }
}

// Sottoclasse Automobile
class Automobile extends Veicolo {
    private int numeroPorte;
    private String carburante;

    public Automobile(String marca, String modello, int annoProduzione, int numeroPorte, String carburante) {
        super(marca, modello, annoProduzione);
        this.numeroPorte = numeroPorte;
        this.carburante = carburante;
    }

    @Override
    public void mostraDettagli() {
        System.out.println("Automobile: " + marca + " " + modello + " (" + annoProduzione +
                "), " + numeroPorte + " porte, Carburante: " + carburante);
    }
}

// Sottoclasse Moto
class Moto extends Veicolo {
    private String tipologia;
    private int cilindrata;

    public Moto(String marca, String modello, int annoProduzione, String tipologia, int cilindrata) {
        super(marca, modello, annoProduzione);
        this.tipologia = tipologia;
        this.cilindrata = cilindrata;
    }

    @Override
    public void mostraDettagli() {
        System.out.println("Moto: " + marca + " " + modello + " (" + annoProduzione +
                "), Tipologia: " + tipologia + ", Cilindrata: " + cilindrata + "cc");
    }
}

// Sottoclasse Camion
class Camion extends Veicolo {
    private double capacitaCarico;
    private int numeroAssi;

    public Camion(String marca, String modello, int annoProduzione, double capacitaCarico, int numeroAssi) {
        super(marca, modello, annoProduzione);
        this.capacitaCarico = capacitaCarico;
        this.numeroAssi = numeroAssi;
    }

    @Override
    public void mostraDettagli() {
        System.out.println("Camion: " + marca + " " + modello + " (" + annoProduzione +
                "), Capacità di carico: " + capacitaCarico + " tonnellate, Assi: " + numeroAssi);
    }
}

// Classe per gestire i veicoli
class GestoreVeicoli {
    private List<Veicolo> listaVeicoli = new ArrayList<>();

    // Metodo per aggiungere un veicolo alla lista
    public void aggiungiVeicolo(Veicolo veicolo) {
        listaVeicoli.add(veicolo);
    }

    // Metodo per mostrare i dettagli di tutti i veicoli
    public void mostraTuttiVeicoli() {
        if (listaVeicoli.isEmpty()) {
            System.out.println("Nessun veicolo presente.");
            return;
        }
        for (Veicolo veicolo : listaVeicoli) {
            veicolo.mostraDettagli();
        }
    }

    // Metodo per trovare i veicoli più vecchi
    public List<Veicolo> trovaVeicoliPiuVecchi() {
        List<Veicolo> veicoliPiuVecchi = new ArrayList<>();

        if (listaVeicoli.isEmpty()) {
            return veicoliPiuVecchi; // Ritorna una lista vuota se non ci sono veicoli
        }

        // Trova l'anno di produzione più vecchio
        int annoMinimo = listaVeicoli.get(0).getAnnoProduzione();
        for (Veicolo veicolo : listaVeicoli) {
            if (veicolo.getAnnoProduzione() < annoMinimo) {
                annoMinimo = veicolo.getAnnoProduzione();
            }
        }

        // Aggiungi alla lista tutti i veicoli con quell'anno minimo
        for (Veicolo veicolo : listaVeicoli) {
            if (veicolo.getAnnoProduzione() == annoMinimo) {
                veicoliPiuVecchi.add(veicolo);
            }
        }

        return veicoliPiuVecchi;
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

// Classe Main per testare il codice
public class EsercizioClassiAstratte {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestoreVeicoli gestore = new GestoreVeicoli();

        while (true) {
            System.out.println("\n===== MENU GESTIONE VEICOLI =====");
            System.out.println("1 Aggiungi un veicolo");
            System.out.println("2 Mostra tutti i veicoli");
            System.out.println("3 Trova i veicoli più vecchi");
            System.out.println("4 Esci");
            System.out.print(" Scegli un'opzione: ");

            int scelta = Controlli.controlloInputInteri(scanner);
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    System.out.println("\nSeleziona il tipo di veicolo:");
                    System.out.println("1 Automobile");
                    System.out.println("2 Moto");
                    System.out.println("3 Camion");
                    System.out.print("Scelta: ");
                    int tipoVeicolo = Controlli.controlloInputInteri(scanner);
                    scanner.nextLine();

                    System.out.print("Inserisci la marca: ");
                    String marca = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Inserisci il modello: ");
                    String modello = Controlli.controlloInputStringhe(scanner);
                    System.out.print("Inserisci l'anno di produzione: ");
                    int annoProduzione = Controlli.controlloInputInteri(scanner);
                    scanner.nextLine();

                    if (tipoVeicolo == 1) {
                        System.out.print("Numero di porte: ");
                        int numeroPorte = Controlli.controlloInputInteri(scanner);
                        scanner.nextLine();
                        System.out.print("Tipo di carburante: ");
                        String carburante = Controlli.controlloInputStringhe(scanner);
                        gestore.aggiungiVeicolo(
                                new Automobile(marca, modello, annoProduzione, numeroPorte, carburante));

                    } else if (tipoVeicolo == 2) {
                        System.out.print("Tipologia: ");
                        String tipologia = Controlli.controlloInputStringhe(scanner);
                        System.out.print("Cilindrata (cc): ");
                        int cilindrata = Controlli.controlloInputInteri(scanner);
                        scanner.nextLine();
                        gestore.aggiungiVeicolo(new Moto(marca, modello, annoProduzione, tipologia, cilindrata));

                    } else if (tipoVeicolo == 3) {
                        System.out.print("Capacità di carico (tonnellate): ");
                        double capacitaCarico = scanner.nextDouble();
                        System.out.print("Numero di assi: ");
                        int numeroAssi = Controlli.controlloInputInteri(scanner);
                        scanner.nextLine();
                        gestore.aggiungiVeicolo(new Camion(marca, modello, annoProduzione, capacitaCarico, numeroAssi));

                    } else {
                        System.out.println("Scelta non valida!");
                    }
                    break;

                case 2:
                    System.out.println("\nLista veicoli:");
                    gestore.mostraTuttiVeicoli();
                    break;

                case 3:
                    List<Veicolo> veicoliVecchi = gestore.trovaVeicoliPiuVecchi();
                    if (!veicoliVecchi.isEmpty()) {                        
                        System.out.println("\nI veicoli più vecchi sono dell'anno: " + veicoliVecchi.get(0).getAnnoProduzione());
                        for (Veicolo v : veicoliVecchi) {
                            v.mostraDettagli();
                        }
                    } else {
                        System.out.println("Nessun veicolo presente.");
                    }
                    break;

                case 4:
                    System.out.println("Uscita dal programma.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opzione non valida, riprova.");
                    break;
            }
        }
    }
}
