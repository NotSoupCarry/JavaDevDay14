import java.util.ArrayList;
import java.util.List;

public class ProvaLowerBoundedWildCard {

    // Il metodo accetta una lista che accetta Integer o suoi supertipi.
    public static void addInteger(List<? super Integer> list) {
        // È possibile aggiungere un Integer (o un suo sottotipo)
        list.add(42);
        /*
         * list.add(3.14); Errore di compilazione!
         * perchè sa sicuramente che è un integer ma non pu sapere che altro tipo può
         * essere
         */
    }

    public static void main(String[] args) {
        // Lista di Number: Number è supertipo di Integer.
        List<Number> numberList = new ArrayList<>();
        addInteger(numberList);
        System.out.println("numberList: " + numberList);

        // Lista di Object: Object è supertipo di Integer.
        List<Object> objectList = new ArrayList<>();
        addInteger(objectList);
        System.out.println("objectList: " + objectList);

        // Lista di Integer: Integer è il tipo base richiesto dalla classe.
        // List<Integer> intList = new ArrayList<>();
        // addInteger(intList);
    }
}
