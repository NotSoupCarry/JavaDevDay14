package Models;
import java.util.Calendar;
import java.util.Date;

import Interfaces.IRestituibile;

public class Elettronico extends Prodotto implements IRestituibile  {
    private int mesiGaranzia;
    private Date dataAcquisto;

    public Elettronico(String codice, String nome, double prezzo, int mesiGaranzia) {
        super(codice, nome, prezzo);
        this.mesiGaranzia = mesiGaranzia;
        this.dataAcquisto = new Date(); 
    }

    public int getMesiGaranzia() {
        return mesiGaranzia;
    }

    @Override
    public boolean èRestituibile() {
        // Verifica che la data di acquisto sia entro il periodo di garanzia
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataAcquisto);
        cal.add(Calendar.MONTH, mesiGaranzia);
        return cal.getTime().after(new Date());
    }

    @Override
    public String getDettagli() {
        return "Elettronico [Codice: " + codice + ", Nome: " + nome + ", Prezzo: " + prezzo + "€, Garanzia: " + mesiGaranzia + " mesi, In garanzia: " + (èRestituibile() ? "Sì" : "No") + "]";
    }
}
