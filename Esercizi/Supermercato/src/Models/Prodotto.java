public abstract class Prodotto implements IProdotto {
    protected String codice;
    protected String nome;
    protected double prezzo;

    public Prodotto(String codice, String nome, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
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
