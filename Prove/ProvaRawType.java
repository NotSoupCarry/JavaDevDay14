public class ProvaRawType {

}

class Printer<T> // T è la convenzione base per nominare il tipo (T = “Type”) dette RawType
{
    T valueToPrint;

    public Printer(T valueToPrint) {
        this.valueToPrint = valueToPrint;
    }

    public void printValue() {
        System.out.println(valueToPrint);
    }
}