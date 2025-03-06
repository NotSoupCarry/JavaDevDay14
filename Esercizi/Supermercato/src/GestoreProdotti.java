import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        try {
            String sql = "SELECT * FROM Prodotto";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String codice = rs.getString("codice");
                String nome = rs.getString("nome");
                double prezzo = rs.getDouble("prezzo");
                String tipo = rs.getString("tipo");
                System.out.println(
                        "Codice: " + codice + " | Nome: " + nome + " | Prezzo: " + prezzo + " euro | Tipo: " + tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

                // Calcoliamo lo sconto e lo aggiungiamo all'oggetto
                double sconto = alimentare.calcolaSconto();

                // Se lo sconto è maggiore di 0, aggiungiamo il prodotto alla lista
                if (sconto > 0) {
                    alimentariInScadenza.add(alimentare);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (alimentariInScadenza != null && !alimentariInScadenza.isEmpty()) {
            System.out.println("ALIMENTI IN SCONTO: ");
            for (Alimentare alimentoInScadenza : alimentariInScadenza) {
                System.out.println(alimentoInScadenza.getDettagli());
            }
        } else {
            System.out.println("Nessun Alimento in scadenza");
        }

    }

}
