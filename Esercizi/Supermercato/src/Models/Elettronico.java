package Models;
import java.util.Calendar;
import java.util.Date;

import Interfaces.IGarantibile;

public class Elettronico extends Prodotto implements IGarantibile {
    private int mesiGaranzia;
    private Date dataAcquisto;

    public Elettronico(String codice, String nome, double prezzo, int mesiGaranzia) {
        super(codice, nome, prezzo, null);
        this.mesiGaranzia = mesiGaranzia;
        this.dataAcquisto = new Date(); 
    }

    public int getMesiGaranzia() {
        return mesiGaranzia;
    }

    @Override
    public boolean inGaranzia() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataAcquisto);
        cal.add(Calendar.MONTH, mesiGaranzia);
        return cal.getTime().after(new Date());
    }
    

    @Override
    public String getDettagli() {
        return "Elettronico [Codice: " + codice + ", Nome: " + nome + ", Prezzo: " + prezzo + "€, Garanzia: " + mesiGaranzia + " mesi, In garanzia: " + (inGaranzia() ? "Sì" : "No") + "]";
    }
}
