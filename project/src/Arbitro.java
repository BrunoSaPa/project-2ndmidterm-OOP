public class Arbitro extends Persona {
    private int experiencia; 

    public Arbitro(String nombre, int edad, int id, int experiencia) {
        super(nombre, edad, id);
        this.experiencia = experiencia;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public void getDatos() {
        super.getDatos();
        System.out.println("Experiencia: " + experiencia);
        
    }
}
