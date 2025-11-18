package Modelo.Hotel;
import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;

public class Habitacion
{
    //Atributos
    private int numero;
    private TipoHabitacion tipoHabitacion;
    private double precioPorNoche;
    private boolean disponible;
    private EstadoHabitacion estadoHabitacion;

    //Constructor
    public Habitacion(int numero, TipoHabitacion tipoHabitacion, double precioPorNoche, EstadoHabitacion estadoHabitacion)
    {
        this.numero = numero;
        this.tipoHabitacion = tipoHabitacion;
        this.precioPorNoche = precioPorNoche;
        this.disponible = true; //por defecto la habitacion esta disponible
        this.estadoHabitacion = estadoHabitacion;

    }


    //Getters
    public boolean isDisponible() {
        return disponible;
    }

    public int getNumero() {
        return numero;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public EstadoHabitacion getEstadoHabitacion() {
        return estadoHabitacion;
    }

    //Setters
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    @Override
    public String toString() {
        return "Modelo.Hotel.Habitacion{" +
                "numero=" + numero +
                ", tipoHabitacion=" + tipoHabitacion.getTipoHabitacion() +
                ", precioPorNoche=" + precioPorNoche +
                ", disponible=" + disponible +
                ", estadoHabitacion=" + estadoHabitacion.getEstado() +
                '}';
    }
}
