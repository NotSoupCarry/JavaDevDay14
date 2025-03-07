package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Models.Abbigliamento;
import Models.Alimentare;
import Models.Elettronico;
import Models.Prodotto;

public class GestoreProdotti {

    public void aggiungiProdotto(Prodotto prodotto) {
        Connection conn = DBContext.connessioneDatabase();
        if (conn == null)
            return;

        try {
            String sql = "INSERT INTO Prodotto (codice, nome, prezzo, tipo) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, prodotto.getCodice());
            stmt.setString(2, prodotto.getNome());
            stmt.setDouble(3, prodotto.getPrezzo());
            stmt.setString(4, prodotto.getTipo());

            stmt.executeUpdate();

            if (prodotto instanceof Alimentare) {
                String sqlAlimentare = "INSERT INTO Alimentare (codice, data_scadenza) VALUES (?, ?)";
                PreparedStatement stmtAlimentare = conn.prepareStatement(sqlAlimentare);
                stmtAlimentare.setString(1, prodotto.getCodice());
                stmtAlimentare.setDate(2, new java.sql.Date(((Alimentare) prodotto).getDataScadenza().getTime()));
                stmtAlimentare.executeUpdate();
            } else if (prodotto instanceof Elettronico) {
                String sqlElettronico = "INSERT INTO Elettronico (codice, mesi_garanzia) VALUES (?, ?)";
                PreparedStatement stmtElettronico = conn.prepareStatement(sqlElettronico);
                stmtElettronico.setString(1, prodotto.getCodice());
                stmtElettronico.setInt(2, ((Elettronico) prodotto).getMesiGaranzia());
                stmtElettronico.executeUpdate();
            } else if (prodotto instanceof Abbigliamento) {
                String sqlAbbigliamento = "INSERT INTO Abbigliamento (codice, taglia, materiale) VALUES (?, ?, ?)";
                PreparedStatement stmtAbbigliamento = conn.prepareStatement(sqlAbbigliamento);
                stmtAbbigliamento.setString(1, prodotto.getCodice());
                stmtAbbigliamento.setString(2, ((Abbigliamento) prodotto).getTaglia());
                stmtAbbigliamento.setString(3, ((Abbigliamento) prodotto).getMateriale());
                stmtAbbigliamento.executeUpdate();
            }

            System.out.println("Prodotto aggiunto con successo!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostraTuttiProdotti() {
        Connection conn = DBContext.connessioneDatabase();
        if (conn == null)
            return;

        List<Prodotto> prodotti = new ArrayList<>();

        try {
            String sql = "SELECT p.codice, p.nome, p.prezzo, p.tipo, " +
                    "a.data_scadenza, e.mesi_garanzia, ab.taglia, ab.materiale " +
                    "FROM Prodotto p " +
                    "LEFT JOIN Alimentare a ON p.codice = a.codice " +
                    "LEFT JOIN Elettronico e ON p.codice = e.codice " +
                    "LEFT JOIN Abbigliamento ab ON p.codice = ab.codice";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String codice = rs.getString("codice");
                String nome = rs.getString("nome");
                double prezzo = rs.getDouble("prezzo");
                String tipo = rs.getString("tipo");

                Prodotto prodotto = null;

                if ("Alimentare".equals(tipo)) {
                    Date dataScadenza = rs.getDate("data_scadenza");
                    prodotto = new Alimentare(codice, nome, prezzo, dataScadenza);
                } else if ("Elettronico".equals(tipo)) {
                    int mesiGaranzia = rs.getInt("mesi_garanzia");
                    prodotto = new Elettronico(codice, nome, prezzo, mesiGaranzia);
                } else if ("Abbigliamento".equals(tipo)) {
                    String taglia = rs.getString("taglia");
                    String materiale = rs.getString("materiale");
                    prodotto = new Abbigliamento(codice, nome, prezzo, taglia, materiale);
                }

                if (prodotto != null) {
                    prodotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Stampa i dettagli di ogni prodotto
        if (!prodotti.isEmpty()) {
            System.out.println("\n===== LISTA PRODOTTI =====");
            for (Prodotto prodotto : prodotti) {
                System.out.println(prodotto.getDettagli());
            }
        } else {
            System.out.println("Nessun prodotto disponibile.");
        }
    }

    public void rimuoviProdotto(String codice) {
        Connection conn = DBContext.connessioneDatabase();
        if (conn == null)
            return;

        try {
            String sql = "DELETE FROM Prodotto WHERE codice = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codice);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Prodotto eliminato con successo!");
            } else {
                System.out.println("Prodotto non trovato!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calcolaScontoAlimentariInScadenza() {
        List<Alimentare> alimentariInScadenza = new ArrayList<>();

        String sql = "SELECT p.codice, p.nome, p.prezzo, a.data_scadenza " +
                "FROM Prodotto p " +
                "JOIN Alimentare a ON p.codice = a.codice " +
                "WHERE p.tipo = 'Alimentare'";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            // Elenco di prodotti alimentari con sconto applicato
            while (rs.next()) {
                String codice = rs.getString("codice");
                String nome = rs.getString("nome");
                double prezzo = rs.getDouble("prezzo");
                Date dataScadenza = rs.getDate("data_scadenza");

                // Creiamo un oggetto Alimentare con i dati ottenuti dalla query
                Alimentare alimentare = new Alimentare(codice, nome, prezzo, dataScadenza);

                // Calcoliamo lo sconto
                double sconto = alimentare.calcolaSconto();

                // Se lo sconto è maggiore di 0, aggiungiamo il prodotto alla lista
                if (sconto > 0) {
                    double prezzoScontato = prezzo - sconto; // Prezzo finale dopo lo sconto
                    System.out.println("Prodotto: " + alimentare.getNome()
                            + ", Prezzo originale: " + prezzo +
                            ", Sconto applicato: " + sconto + ", Prezzo scontato: " + prezzoScontato);
                    alimentariInScadenza.add(alimentare); // Aggiungiamo alla lista
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String controlloCodiceUnico(Scanner scanner) {
        String codice;

        while (true) {
            codice = Controlli.controlloInputStringhe(scanner).trim();

            String sql = "SELECT COUNT(*) FROM Prodotto WHERE codice = ?";

            try (Connection conn = DBContext.connessioneDatabase();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, codice);
                ResultSet rs = stmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.print("Errore: Il codice \"" + codice
                            + "\" esiste già nel database.\nInserisci un codice diverso: ");
                } else {
                    return codice; // Il codice è valido, lo restituiamo
                }

            } catch (SQLException e) {
                System.out.println("Errore durante la verifica del codice nel database.");
                e.printStackTrace();
            }
        }
    }

    public void compraProdotto(Scanner scanner, String nomeProdotto) {
        String sqlVerifica = "SELECT codice FROM Prodotto WHERE nome = ? AND data_acquisto IS NULL";
        String sqlAcquisto = "UPDATE Prodotto SET data_acquisto = ? WHERE codice = ?";

        try (Connection conn = DBContext.connessioneDatabase();
                PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {

            stmtVerifica.setString(1, nomeProdotto);
            ResultSet rs = stmtVerifica.executeQuery();

            if (!rs.next()) {
                System.out.println("Errore: Prodotto non disponibile o già acquistato.");
                return;
            }

            String codiceProdotto = rs.getString("codice");
            java.util.Date oggi = new java.util.Date();
            java.sql.Date dataAcquistoSQL = new java.sql.Date(oggi.getTime());

            try (PreparedStatement stmtAcquisto = conn.prepareStatement(sqlAcquisto)) {
                stmtAcquisto.setDate(1, dataAcquistoSQL);
                stmtAcquisto.setString(2, codiceProdotto);
                int aggiornati = stmtAcquisto.executeUpdate();

                if (aggiornati > 0) {
                    System.out.println("Prodotto acquistato con successo! Data acquisto: " + dataAcquistoSQL);
                } else {
                    System.out.println("Errore durante l'acquisto del prodotto.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore durante il processo di acquisto.");
            e.printStackTrace();
        }
    }

    public void mostraProdottiAcquistati() {
        Connection conn = DBContext.connessioneDatabase();
        if (conn == null)
            return;

        List<Prodotto> prodottiAcquistati = new ArrayList<>();

        try {
            String sql = "SELECT p.codice, p.nome, p.prezzo, p.tipo, p.data_acquisto, " +
                    "a.data_scadenza, e.mesi_garanzia, ab.taglia, ab.materiale " +
                    "FROM Prodotto p " +
                    "LEFT JOIN Alimentare a ON p.codice = a.codice " +
                    "LEFT JOIN Elettronico e ON p.codice = e.codice " +
                    "LEFT JOIN Abbigliamento ab ON p.codice = ab.codice " +
                    "WHERE p.data_acquisto IS NOT NULL";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String codice = rs.getString("codice");
                String nome = rs.getString("nome");
                double prezzo = rs.getDouble("prezzo");
                String tipo = rs.getString("tipo");
                Date dataAcquisto = rs.getDate("data_acquisto");

                Prodotto prodotto = null;

                if ("Alimentare".equals(tipo)) {
                    Date dataScadenza = rs.getDate("data_scadenza");
                    prodotto = new Alimentare(codice, nome, prezzo, dataScadenza);
                } else if ("Elettronico".equals(tipo)) {
                    int mesiGaranzia = rs.getInt("mesi_garanzia");
                    prodotto = new Elettronico(codice, nome, prezzo, mesiGaranzia);
                } else if ("Abbigliamento".equals(tipo)) {
                    String taglia = rs.getString("taglia");
                    String materiale = rs.getString("materiale");
                    prodotto = new Abbigliamento(codice, nome, prezzo, taglia, materiale);
                }

                if (prodotto != null) {
                    System.out.println(prodotto.getDettagli() + ", Data Acquisto: " + dataAcquisto);
                    prodottiAcquistati.add(prodotto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prodottiAcquistati.isEmpty()) {
            System.out.println("Nessun prodotto acquistato.");
        }
    }

}
