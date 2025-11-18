package Modelo;

public abstract class Persona
{
    //Atributos
    //Son datos que nunca cambian
    private final String nombre;
    private final String apellido;
    private final String dni;
    private final String domicilio;
    private final String origen; //Pais origen

    //Constructor
    public Persona(String nombre, String apellido, String dni, String domicilio, String origen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.domicilio = domicilio;
        this.origen = origen;
    }

    //Getters
    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOrigen() {
        return origen;
    }


    @Override
    public String toString()
    {
        return "Modelo.Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", origen='" + origen + '\'' +
                '}';
    }


}
