package Modelo;

public class Habitacion
{
    //Atributos
    private int numero;
    private TipoHabitacion tipoHabitacion;
    private double precioPorNoche;
    private boolean disponible;


    //Constructor
    public Habitacion(int numero, TipoHabitacion tipoHabitacion, double precioPorNoche)
    {
        this.numero = numero;
        this.tipoHabitacion = tipoHabitacion;
        this.precioPorNoche = precioPorNoche;
        this.disponible = true; //por defecto la habitacion esta disponible
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

    //Setters
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    @Override
    public String toString() {
        return "Modelo.Habitacion{" +
                "numero=" + numero +
                ", tipoHabitacion=" + tipoHabitacion +
                ", precioPorNoche=" + precioPorNoche +
                ", disponible=" + disponible +
                '}';
    }
}
