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
        this.precioPorDia = 5000;
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
        json.put("habitacion", habitacion);
        json.put("pasajero", pasajero);
        json.put("fechaIngreso", fechaIngreso);
        json.put("fechaEgreso", fechaEgreso);
        json.put("estado", estado);
        json.put("precioPorDia", precioPorDia);

        return json;
    }

    public Reserva(JSONObject obj) {
        this.nroReserva = obj.getInt("nroReserva");
        this.habitacion = (Habitacion) obj.get("habitacion");
        this.pasajero = (Pasajero) obj.get("pasajero");
        this.fechaIngreso = (LocalDate) obj.get("fechaIngreso");
        this.fechaEgreso = (LocalDate) obj.get("fechaEgreso");
        this.estado = (boolean) obj.get("estado");
        this.precioPorDia = (long) obj.get("precioPorDia");
    }
}
