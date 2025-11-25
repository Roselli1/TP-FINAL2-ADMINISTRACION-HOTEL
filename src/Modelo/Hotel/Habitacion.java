package Modelo.Hotel;
import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Interfaces.iToJSON;
import org.json.JSONObject;

public class Habitacion implements iToJSON
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

    public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }

    @Override
    public String toString() {
        return "Habitación " + numero +
                " | Tipo: " + tipoHabitacion.getTipoHabitacion() +
                " | Precio: $" + precioPorNoche +
                " | Estado: " + estadoHabitacion.getEstado();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("numero", numero);
        json.put("tipoHabitacion", tipoHabitacion.getTipoHabitacion());
        json.put("precioPorNoche", precioPorNoche);
        json.put("disponible", disponible);
        json.put("estadoHabitacion", estadoHabitacion.getEstado());

        return json;
    }

    public Habitacion (JSONObject jsonObject)
    {
        this.numero = jsonObject.getInt("numero");
        this.tipoHabitacion = TipoHabitacion.valueOf(jsonObject.getString("tipoHabitacion").toUpperCase());
        this.precioPorNoche = jsonObject.getDouble("precioPorNoche");
        this.disponible = jsonObject.getBoolean("disponible");
        this.estadoHabitacion = EstadoHabitacion.valueOf(jsonObject.getString("estadoHabitacion").toUpperCase());
    }
}
