package Utils;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Controlli {

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
                scanner.next();
            } else {
                valore = scanner.nextInt();
                if (valore >= 0) {
                    return valore;
                }
                System.out.print("Il numero non può essere negativo. Riprova: ");
            }
        }
    }

    // Metodo per controllare l'input double
    public static double controlloInputDouble(Scanner scanner) {
        double valore;
        while (true) {
            if (!scanner.hasNextDouble()) {
                System.out.print("Devi inserire un numero decimale (double). Riprova: ");
                scanner.next();
            } else {
                valore = scanner.nextDouble();
                if (valore >= 0) {
                    return valore;
                }
                System.out.print("Il numero non può essere negativo. Riprova: ");
            }
        }
    }

    // Metodo per controllare l'input data (formato yyyy-MM-dd) e restituire un
    // oggetto java.util.Date
    public static Date controlloInputData(Scanner scanner) {
        Date data = null;
        while (data == null) {
            String dataString = scanner.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                // Converto la stringa in LocalDate
                LocalDate localDate = LocalDate.parse(dataString, formatter);

                // Converto LocalDate in java.util.Date
                // Primo passo: Converto LocalDate in LocalDateTime
                java.time.LocalDateTime localDateTime = localDate.atStartOfDay();

                // Secondo passo: Converto LocalDateTime in Date
                data = java.sql.Timestamp.valueOf(localDateTime);

            } catch (DateTimeParseException e) {
                System.out.print("Data non valida. Inserisci una data nel formato yyyy-MM-dd: ");
            }
        }
        return data;
    }
}
