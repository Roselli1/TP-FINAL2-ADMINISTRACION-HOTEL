package Modelo.Hotel;

import Modelo.Persona.UsuarioBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel
{
    private Map<Integer,Habitacion> habitaciones;
    private List<Reserva> reservas;
    private Map<String, UsuarioBase> usuarios;

    public Hotel() {
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
            if (entry.getValue().isDisponible()){
                return true;
            }
        }
        return false;
    }

    public boolean realizarReserva(Reserva reserva, int nroHabitacion) {
        if (buscarHabitacionDisponible(nroHabitacion)) {
            return reservas.add(reserva);
        }
        return false;
    }
}