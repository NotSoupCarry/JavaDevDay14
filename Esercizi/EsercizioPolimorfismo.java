public class EsercizioPolimorfismo {
    public static void main(String[] args) {
        Persona persona1 = new Persona("jack");
        persona1.saluta();
        Persona pirata1 = new Pirata("jack sparrow");
        pirata1.saluta();
        ;
    }
}

class Persona {
    String nome;

    Persona(String nome) {
        this.nome = nome;
    }

    public void saluta() {
        System.out.println("Ciao sono " + nome);
    }
}

class Pirata extends Persona {

    Pirata(String nome) {
        super(nome);
    }

    public void saluta() {
        System.out.println("SONO IL PIRATA " + nome.toUpperCase() + " AAARGH ! ! !");
    }
}