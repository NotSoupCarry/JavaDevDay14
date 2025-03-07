package Models;
import Interfaces.IRestituibile;

public class Abbigliamento extends Prodotto implements IRestituibile {
    private String taglia;
    private String materiale;
    private boolean provato;

    public Abbigliamento(String codice, String nome, double prezzo, String taglia, String materiale) {
        super(codice, nome, prezzo, null);
        this.taglia = taglia;
        this.materiale = materiale;
        this.provato = false;
    }

    public String getTaglia() {
        return taglia;
    }

    public String getMateriale() {
        return materiale;
    }

    public void prova() {
        provato = true;
    }

    @Override
    public boolean èRestituibile() {
        return !provato; // Se provato, non è più restituibile
    }

    @Override
    public String getDettagli() {
        return "Abbigliamento [Codice: " + codice + ", Nome: " + nome + ", Prezzo: " + prezzo + " euro, Taglia: " + taglia + ", Materiale: " + materiale + ", Restituibile: " + (èRestituibile() ? "Sì" : "No") + "]";
    }
}
