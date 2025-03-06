package Models;
import java.text.SimpleDateFormat;
import java.util.Date;

import Interfaces.IScontabile;

public class Alimentare extends Prodotto implements IScontabile {
    private Date dataScadenza;

    public Alimentare(String codice, String nome, double prezzo, Date dataScadenza) {
        super(codice, nome, prezzo);
        this.dataScadenza = dataScadenza;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    @Override
    public double calcolaSconto() {
        long oggi = new Date().getTime();

        long giorniScadenza = (dataScadenza.getTime() - oggi) / (1000 * 60 * 60 * 24);

        if (giorniScadenza >= 0 && giorniScadenza <= 2) {
            return prezzo * 0.3; // Applica uno sconto del 30%
        }
        return 0;
    }

    @Override
    public double getPrezzo() {
        return prezzo - calcolaSconto();
    }

    @Override
    public String getDettagli() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Alimentare [Codice: " + codice + ", Nome: " + nome + ", Prezzo: " + getPrezzo() + "€, Scadenza: "
                + sdf.format(dataScadenza) + "]";
    }
}
