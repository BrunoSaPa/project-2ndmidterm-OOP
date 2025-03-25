
public class Jugador extends Persona implements Statistics {
    private int puntuacionTotal;
    private int turnosJugados;
    private int juegosJugados;
    private int juegosGanados;

    public Jugador(String nombre, int edad, int id) {
        super(nombre, edad, id);
        this.puntuacionTotal = 0;
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.turnosJugados = 0;
    }

    public Jugador(Jugador j){
        super(j.getNombre(), j.getEdad(), j.getId());
        this.puntuacionTotal = 0;
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.turnosJugados = 0;
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public void increasePuntuacionTotal() {
        this.puntuacionTotal++;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public void setJuegosJugados(int juegosJugados) {
        this.juegosJugados = juegosJugados;
    }
    
    public void increaseJuegosJugados(){
        this.juegosJugados++;
    };

    public int getTurnosJugados() {
        return turnosJugados;
    }

    public void increaseTurnosJugados(){
        this.turnosJugados++;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    public void increaseJuegosGanados(){
        this.juegosGanados++;
    }


    @Override
    public void getDatos() {
        super.getDatos();
        System.out.println("Puntuación total: " + puntuacionTotal);
        System.out.println("Juegos jugados: " + juegosJugados);
        System.out.println("Juegos ganados: " + juegosGanados);
        System.out.println("Turnos jugados: " + turnosJugados);
    }
}
