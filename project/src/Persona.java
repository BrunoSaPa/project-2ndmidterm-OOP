public class Persona {
    protected String nombre;
    protected int edad;
    protected int id;

    public Persona(String nombre, int edad, int id) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatos() {
        return "Nombre: " + this.nombre + "\n" +
               "Edad: " + this.edad + "\n" +
               "ID: " + this.id;
    }
    
}
