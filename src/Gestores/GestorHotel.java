package Gestores;

import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.Reserva;
import Modelo.Persona.UsuarioBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorHotel
{
    private Map<Integer, Habitacion> habitaciones;
    private List<Reserva> reservas;
    private Map<String, UsuarioBase> usuarios;

    public GestorHotel() {
        this.habitaciones = new HashMap<>();
        this.reservas = new ArrayList<>();
        this.usuarios = new HashMap<>();
    }

    public Map<Integer, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public Map<String, UsuarioBase> getUsuarios() {
        return usuarios;
    }

    /// METODOS

    public boolean buscarHabitacionDisponible(int nroHabitacion) {
        for (Map.Entry<Integer, Habitacion> entry : habitaciones.entrySet()) {
            if (entry.getKey() == nroHabitacion) {
                if (entry.getValue().isDisponible()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean realizarReserva(Reserva reserva, int nroHabitacion)throws HabitacionNoDisponibleException, ReservaInvalidaException {
        for (Reserva res : reservas) {
            if (reserva.equals(res)) {
                throw new ReservaInvalidaException("Esta reserva ya existe.");
            }
        }
        if (!buscarHabitacionDisponible(nroHabitacion)) {
            throw new HabitacionNoDisponibleException("La habitación que solicita no está disponible en este momento.");
        }
        return reservas.add(reserva);
    }

    public boolean cancelarReserva(Reserva reserva,int nroHabitacion) {
        for (Reserva res :  reservas) {
            if (res.getHabitacion().getNumero() == nroHabitacion) {
                return reservas.remove(res);
            }
        }
        return false;
    }
}