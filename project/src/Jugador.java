
public class Jugador extends Persona {
    private int puntuacionTotal;
    private int juegosJugados;
    private int juegosGanados;

    public Jugador(String nombre, int edad, int id) {
        super(nombre, edad, id);
        this.puntuacionTotal = 0;
        this.juegosJugados = 0;
        this.juegosGanados = 0;
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public void setJuegosJugados(int juegosJugados) {
        this.juegosJugados = juegosJugados;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    @Override
    public void getDatos() {
        super.getDatos();
        System.out.println("Puntuación total: " + puntuacionTotal);
        System.out.println("Juegos jugados: " + juegosJugados);
        System.out.println("Juegos ganados: " + juegosGanados);
    }
}
