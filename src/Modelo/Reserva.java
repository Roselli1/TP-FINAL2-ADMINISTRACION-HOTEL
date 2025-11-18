package Modelo;

import java.time.LocalDate;

public class Reserva
{
    private static int contador = 0;
    private final int nroReserva;
    private Habitacion habitacion;
    private Pasajero pasajero;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private boolean estado;

    public Reserva(Habitacion habitacion, Pasajero pasajero, LocalDate fechaIngreso, LocalDate fechaEgreso, boolean estado) {
        this.habitacion = habitacion;
        this.pasajero = pasajero;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.estado = estado;
        this.nroReserva = contador++;
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

    //Metodos
    public void cancelarReserva()
    {
        this.estado = false;
    }

    @Override
    public String toString() {
        return "Modelo.Reserva{" +
                "habitación=" + habitacion +
                ", pasajero=" + pasajero +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaEgreso=" + fechaEgreso +
                ", nroReserva=" + nroReserva +
                ", estado=" + estado +
                '}';
    }
}
