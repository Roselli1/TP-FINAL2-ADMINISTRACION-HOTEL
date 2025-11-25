package Modelo.Hotel;

import Interfaces.iToJSON;
import Modelo.Persona.Pasajero;
import org.json.JSONObject;

import java.time.LocalDate;

public class Reserva implements iToJSON
{
    private static int contador = 0;
    private final int nroReserva;
    private Habitacion habitacion;
    private Pasajero pasajero;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private boolean estado;
    private long precioPorDia;

    public Reserva(Habitacion habitacion, Pasajero pasajero, LocalDate fechaIngreso, LocalDate fechaEgreso, boolean estado) {
        this.habitacion = habitacion;
        this.pasajero = pasajero;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.estado = estado;
        this.nroReserva = contador++;
        this.precioPorDia = (long) habitacion.getPrecioPorNoche();
    }
    //Getters
    public Habitacion getHabitacion() {
        return habitacion;
    }

    public static int getContador() {
        return contador;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public boolean isEstado() {
        return estado;
    }

    public long getPrecioPorDia() {
        return precioPorDia;
    }

    public int getNroReserva() {
        return nroReserva;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    //Metodos
    public void cancelarReserva()
    {
        this.estado = false;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "habitación=" + habitacion +
                ", pasajero=" + pasajero +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaEgreso=" + fechaEgreso +
                ", nroReserva=" + nroReserva +
                ", estado=" + estado +
                ", precioPorDia=" + precioPorDia +
                '}';
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("nroReserva", nroReserva);
        json.put("habitacion", habitacion.toJSON());
        json.put("pasajero", pasajero.toJSON());
        json.put("fechaIngreso", fechaIngreso.toString());
        json.put("fechaEgreso", fechaEgreso.toString());
        json.put("estado", estado);
        json.put("precioPorDia", precioPorDia);

        return json;
    }

    public Reserva(JSONObject obj) {
        this.nroReserva = obj.getInt("nroReserva");

        if (this.nroReserva >= contador) {
            contador = this.nroReserva + 1;
        }

        this.habitacion = new Habitacion(obj.getJSONObject("habitacion"));
        this.pasajero = new Pasajero(obj.getJSONObject("pasajero"));
        this.fechaIngreso = LocalDate.parse(obj.getString("fechaIngreso"));
        this.fechaEgreso = LocalDate.parse(obj.getString("fechaEgreso"));
        this.estado = obj.getBoolean("estado");
        this.precioPorDia = obj.getLong("precioPorDia");
    }
}
