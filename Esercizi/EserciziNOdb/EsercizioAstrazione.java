import java.util.*;

// Interfaccia generale per tutti i prodotti
interface IProdotto {
    double getPrezzo();
    String getDettagli();
}

// Interfacce specifiche per comportamenti extra
interface IScontabile {
    double calcolaSconto();
}

interface IGarantibile {
    boolean inGaranzia();
}

interface IRestituibile {
    boolean puòEssereRestituito();
}

interface IFidelizzabile {
    int calcolaPuntiFedelta();
}

// Classe astratta base per un prodotto
abstract class Prodotto implements IProdotto {
    protected String codice;
    protected String nome;
    protected double prezzo;

    public Prodotto(String codice, String nome, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public abstract String getDettagli();
}

// Classe Alimentare
class Alimentare extends Prodotto implements IScontabile, IFidelizzabile {
    private Date dataScadenza;

    public Alimentare(String codice, String nome, double prezzo, Date dataScadenza) {
        super(codice, nome, prezzo);
        this.dataScadenza = dataScadenza;
    }

    public double calcolaSconto() {
        Date oggi = new Date();
        long differenza = dataScadenza.getTime() - oggi.getTime();
        long giorni = differenza / (1000 * 60 * 60 * 24);
        return (giorni <= 5) ? prezzo * 0.2 : 0; // 20% di sconto se scade tra 5 giorni
    }

    public int calcolaPuntiFedelta() {
        return (int) (prezzo / 2);
    }

    public String getDettagli() {
        return "Alimentare: " + nome + " - Prezzo: " + prezzo + "€ - Scadenza: " + dataScadenza;
    }
}

// Classe Elettronico
class Elettronico extends Prodotto implements IGarantibile, IFidelizzabile {
    private int mesiGaranzia;

    public Elettronico(String codice, String nome, double prezzo, int mesiGaranzia) {
        super(codice, nome, prezzo);
        this.mesiGaranzia = mesiGaranzia;
    }

    public boolean inGaranzia() {
        return mesiGaranzia > 0;
    }

    public int calcolaPuntiFedelta() {
        return (int) (prezzo / 10);
    }

    public String getDettagli() {
        return "Elettronico: " + nome + " - Prezzo: " + prezzo + "€ - Garanzia: " + mesiGaranzia + " mesi";
    }
}

// Classe Abbigliamento
class Abbigliamento extends Prodotto implements IRestituibile, IFidelizzabile {
    private String taglia;
    private String materiale;

    public Abbigliamento(String codice, String nome, double prezzo, String taglia, String materiale) {
        super(codice, nome, prezzo);
        this.taglia = taglia;
        this.materiale = materiale;
    }

    public boolean puòEssereRestituito() {
        return true; // Sempre restituibile entro 30 giorni
    }

    public int calcolaPuntiFedelta() {
        return (int) (prezzo / 5);
    }

    public String getDettagli() {
        return "Abbigliamento: " + nome + " - Prezzo: " + prezzo + "€ - Taglia: " + taglia + " - Materiale: " + materiale;
    }
}

// Classe GestoreProdotti
class GestoreProdotti {
    private List<IProdotto> prodotti = new ArrayList<>();

    public void aggiungiProdotto(IProdotto prodotto) {
        prodotti.add(prodotto);
    }

    public void rimuoviProdotto(String codice) {
        prodotti.removeIf(p -> (p instanceof Prodotto) && ((Prodotto) p).codice.equals(codice));
    }

    public void mostraTuttiProdotti() {
        if (prodotti.isEmpty()) {
            System.out.println("Nessun prodotto disponibile.");
            return;
        }
        for (IProdotto p : prodotti) {
            System.out.println(p.getDettagli());
        }
    }

    public void applicaSconti() {
        for (IProdotto p : prodotti) {
            if (p instanceof IScontabile) {
                IScontabile scontabile = (IScontabile) p;
                double sconto = scontabile.calcolaSconto();
                if (sconto > 0) {
                    System.out.println("Sconto applicato a " + ((Prodotto) p).nome + ": -" + sconto + "€");
                }
            }
        }
    }
}

// Classe Main
public class EsercizioAstrazione {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestoreProdotti gestore = new GestoreProdotti();

        while (true) {
            System.out.println("\n===== MENU SUPERMERCATO =====");
            System.out.println("1. Aggiungi un prodotto");
            System.out.println("2. Mostra tutti i prodotti");
            System.out.println("3. Applica sconti ai prodotti alimentari");
            System.out.println("4. Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.println("\nSeleziona il tipo di prodotto:");
                    System.out.println("1. Alimentare");
                    System.out.println("2. Elettronico");
                    System.out.println("3. Abbigliamento");
                    System.out.print("Scelta: ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Inserisci il codice: ");
                    String codice = scanner.nextLine();
                    System.out.print("Inserisci il nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Inserisci il prezzo: ");
                    double prezzo = scanner.nextDouble();
                    scanner.nextLine();

                    switch (tipo) {
                        case 1:
                            System.out.print("Inserisci la data di scadenza (yyyy-MM-dd): ");
                            String data = scanner.nextLine();
                            try {
                                Date dataScadenza = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(data);
                                gestore.aggiungiProdotto(new Alimentare(codice, nome, prezzo, dataScadenza));
                            } catch (Exception e) {
                                System.out.println("Formato data non valido.");
                            }
                            break;
                        case 2:
                            System.out.print("Inserisci mesi di garanzia: ");
                            int mesiGaranzia = scanner.nextInt();
                            gestore.aggiungiProdotto(new Elettronico(codice, nome, prezzo, mesiGaranzia));
                            break;
                        case 3:
                            System.out.print("Inserisci la taglia: ");
                            String taglia = scanner.next();
                            System.out.print("Inserisci il materiale: ");
                            String materiale = scanner.next();
                            gestore.aggiungiProdotto(new Abbigliamento(codice, nome, prezzo, taglia, materiale));
                            break;
                        default:
                            System.out.println("Scelta non valida.");
                            break;
                    }
                    break;
                case 2:
                    gestore.mostraTuttiProdotti();
                    break;
                case 3:
                    gestore.applicaSconti();
                    break;
                case 4:
                    System.out.println("Chiusura del programma.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
    }
}
