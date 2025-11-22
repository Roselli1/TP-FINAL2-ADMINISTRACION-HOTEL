package Gestores;

import Exceptions.HabitacionNoDisponibleException;
import Exceptions.RegistroEstadiaException;
import Exceptions.ReservaInvalidaException;
import Exceptions.UsuarioYaExisteException;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import Modelo.Persona.Pasajero;
import Modelo.Persona.UsuarioBase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorHotel
{
    private Map<Integer, Habitacion> habitaciones;
    private List<Reserva> reservas;
    private Map<String, UsuarioBase> usuarios;
    private List<RegistroEstadia> registrosEstadias;
    private List<Pasajero> pasajeros;

    private GestoraHabitaciones gestoraHabitaciones;
    private GestorReservas gestoraReservas;
    private GestoraUsuarios gestoraUsuarios;
    private GestoraEstadias gestoraEstadias;
    private GestoraPasajeros gestoraPasajeros;

    public GestorHotel()
    {
        this.habitaciones = new HashMap<>();
        this.reservas = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.registrosEstadias = new ArrayList<>();
        this.pasajeros= new ArrayList<>();

        this.gestoraHabitaciones= new GestoraHabitaciones("habitaciones");
        this.gestoraReservas= new GestorReservas("reservas");
        this.gestoraUsuarios= new GestoraUsuarios("usuarios");
        this.gestoraEstadias= new GestoraEstadias("registrosEstadias");
        this.gestoraPasajeros= new GestoraPasajeros("pasajeros");
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

    public List<RegistroEstadia> getRegistrosEstadias() {
        return registrosEstadias;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        JSONArray habArray = new JSONArray();
        for (Habitacion h : habitaciones.values()) {
            habArray.put(h.toJSON());
        }
        json.put("habitaciones", habArray);

        JSONArray resArray = new JSONArray();
        for (Reserva r : reservas) {
            resArray.put(r.toJSON());
        }
        json.put("reservas", resArray);

        JSONArray regArray = new JSONArray();
        for (RegistroEstadia reg : registrosEstadias) {
            regArray.put(reg.toJSON());
        }
        json.put("registrosEstadias", regArray);

        JSONArray userArray = new JSONArray();
        for (UsuarioBase u : usuarios.values()) {
            userArray.put(u.toJSON());
        }
        json.put("usuarios", userArray);

        return json;
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

    public boolean cancelarReserva(Reserva reserva) {
        for (Reserva res :  reservas) {
            if (res.getHabitacion() == reserva.getHabitacion()) {
                return reservas.remove(res);
            }
        }
        return false;
    }

    public void agregarRegistro(RegistroEstadia registroEstadia) throws RegistroEstadiaException
    {
        if (registroEstadia== null)
        {
            throw new RegistroEstadiaException("El registro de estadia no puede ser nulo");
        }

        registrosEstadias.add(registroEstadia);
    }

    public RegistroEstadia buscarEstadiaActiva(int nroHabitacion)
    {
        for (RegistroEstadia registroEstadia : registrosEstadias) {
            if (registroEstadia.getHabitacion().getNumero() == nroHabitacion && registroEstadia.getCheckOut() == null)
            {
                return registroEstadia;
            }
        }
        return null;

    }

    public boolean agregarUsuario(UsuarioBase usuario) throws UsuarioYaExisteException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        if (usuarios.containsKey(usuario.getUsername())) {
            throw new UsuarioYaExisteException("El usuario con el nombre de usuario '" + usuario.getUsername() + "' ya existe en el sistema.");
        }

        usuarios.put(usuario.getUsername(), usuario);
        return true;
    }
}