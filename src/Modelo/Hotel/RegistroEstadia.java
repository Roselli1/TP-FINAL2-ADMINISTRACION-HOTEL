package Modelo.Hotel;

import Modelo.Persona.Pasajero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroEstadia {
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Servicio> consumos;

    public RegistroEstadia(Pasajero pasajero, Habitacion habitacion, LocalDate checkIn, LocalDate checkOut) {
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.consumos = new ArrayList<>();
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public List<Servicio> getConsumos() {
        return consumos;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}
