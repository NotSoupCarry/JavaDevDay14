public class EsercizioRawType {

    public static void main(String[] args) {
        // Creazione di un oggetto Printer con tipo String
        Printer<String> stringPrinter = new Printer<>("Ciaooooo!");
        stringPrinter.printValue(); 

        // Creazione di un oggetto Printer con tipo Integer
        Printer<Integer> integerPrinter = new Printer<>(123);
        integerPrinter.printValue(); 

        // Creazione di un oggetto Printer con tipo Double
        Printer<Double> doublePrinter = new Printer<>(3.14);
        doublePrinter.printValue(); 
    }
}

// Classe Printer con Generics
class Printer<T> { 
    private T valueToPrint;

    // Costruttore 
    public Printer(T valueToPrint) {
        this.valueToPrint = valueToPrint;
    }

    // Metodo per stampare il valore
    public void printValue() {
        System.out.println(valueToPrint);
    }
}
