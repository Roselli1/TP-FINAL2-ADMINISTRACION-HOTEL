package Modelo.Persona;

import Interfaces.iToJSON;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Persona implements iToJSON
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
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", origen='" + origen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("dni", dni);
        json.put("nombre", nombre);
        json.put("apellido", apellido);
        json.put("domicilio", domicilio);
        json.put("origen", origen);

        return json;
    }

    public Persona(JSONObject obj) {
        this.dni = obj.getString("dni");
        this.nombre = obj.getString("nombre");
        this.apellido = obj.getString("apellido");
        this.domicilio = obj.getString("domicilio");
        this.origen = obj.getString("origen");
    }
}
