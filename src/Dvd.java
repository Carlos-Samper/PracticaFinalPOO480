public class Dvd extends Articulo implements IPrestable {

    public static final int diasMaxDevolucion = 3;
    String director;
    int duracion;

    public Dvd(String titulo, String director, int duracion) {
        super(titulo);
        this.director = director;
        this.duracion = duracion;
    }

    @Override
    public void prestar() {
        setDisponible(false);
    }

    @Override
    public void devolver() {
        setDisponible(true);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}