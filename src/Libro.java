public class Libro extends Articulo implements IPrestable {

    public static final int diasMaxDevolucion = 15;
    String autor;
    String isbn;

    public Libro(String titulo, String autor, String isbn) {
        super(titulo);
        this.autor = autor;
        this.isbn = isbn;
    }

    @Override
    public void prestar(){
        setDisponible(false);
    }
    @Override
    public void devolver(){
        setDisponible(true);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

