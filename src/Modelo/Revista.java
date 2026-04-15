package Modelo;

public class Revista extends Articulo implements IPrestable {

    public static final int diasMaxDevolucion = 7;
    String issn;
    int numero;

    public Revista(String titulo, String issn, int numero) {
        super(titulo);
        this.issn = issn;
        this.numero = numero;
    }

    @Override
    public void prestar() {
        setDisponible(false);
    }

    @Override
    public void devolver() {
        setDisponible(true);
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}

