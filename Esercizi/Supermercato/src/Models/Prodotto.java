package Models;
import Interfaces.IProdotto;
import java.util.Date;

public abstract class Prodotto implements IProdotto {
    protected String codice;
    protected String nome;
    protected double prezzo;
    protected Date data_acquisto;

    public Prodotto(String codice, String nome, double prezzo, Date data_acquisto) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
        this.data_acquisto = data_acquisto;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public abstract String getDettagli();

    public String getTipo() {
        return this.getClass().getSimpleName();
    }
}
