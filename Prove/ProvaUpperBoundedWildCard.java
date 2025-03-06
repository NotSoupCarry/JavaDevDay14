
import java.util.ArrayList;
import java.util.List;

public class ProvaUpperBoundedWildCard {

    // Il metodo accetta una lista di elementi che sono Number o sottoclassi di Number.
    public static void printNumbers(List<? extends Number> numbers) {
        for (Number num : numbers) {
            System.out.println(num);
        }
        // numbers.add(3.14); // NON COMPILA: non è possibile aggiungere elementi. numbers è in read-only perché ancora non sa che tipo di elementi contiene

    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(20);

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(3.14);
        doubleList.add(2.718);

        // Entrambe le liste possono essere passate al metodo, perché Integer e Double estendono Number.
        printNumbers(intList);
        printNumbers(doubleList);
    }
}