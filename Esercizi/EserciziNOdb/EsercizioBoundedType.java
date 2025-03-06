public class EsercizioBoundedType {

    public static void main(String[] args) {
        try {
            Printer<Integer> integerPrinter = new Printer<>(123);
            integerPrinter.printValue(); // Stampa: 123

            Printer<Double> doublePrinter = new Printer<>(3.14);
            doublePrinter.printValue(); // Stampa: 3.14

            //Printer<String> stringPrinter = new Printer<>("Hello, World!"); 
            //stringPrinter.printValue();

        } catch (CustomException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

// Classe Printer con Generics Bounded Type
class Printer<T extends Number> { 
    T valueToPrint;

    // Costruttore per inizializzare il valore da stampare
    public Printer(T valueToPrint) throws CustomException {
        if (!(valueToPrint instanceof Number)) {
            throw new CustomException(
                    "Il tipo inserito non è numerico. Tipo fornito: " + valueToPrint.getClass().getName());
        }
        this.valueToPrint = valueToPrint;
    }

    // Metodo per stampare il valore
    public void printValue() {
        System.out.println(valueToPrint);
    }
}

// Classe di eccezione personalizzata
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
