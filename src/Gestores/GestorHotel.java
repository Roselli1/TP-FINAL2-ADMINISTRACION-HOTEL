package Gestores;

import Enums.EstadoHabitacion;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.RegistroEstadiaException;
import Exceptions.ReservaInvalidaException;
import Exceptions.UsuarioYaExisteException;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import Modelo.Persona.Pasajero;
import Modelo.Persona.UsuarioBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorHotel
{
    // --- ATRIBUTOS ---
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

    // --- CONSTRUCTOR ---
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

        cargarDatosIniciales();
    }

    // --- Cargar y Guardar Datos ---
    private void cargarDatosIniciales() {
        System.out.println("----- Cargando datos del sistema -----");

        //Cargar habitaciones
        gestoraHabitaciones.cargarHabitacion("habitaciones");
        for (Habitacion h : gestoraHabitaciones.getElementos()) {
            this.habitaciones.put(h.getNumero(), h);
        }


        //Cargar Usuarios
        gestoraUsuarios.cargarUsuario("usuarios");
        for (UsuarioBase u : gestoraUsuarios.getElementos()) {
            this.usuarios.put(u.getUsername(), u);
        }


        //Cargar Pasajeros
        gestoraPasajeros.cargarPasajero("pasajeros");
        for (Pasajero p : gestoraPasajeros.getElementos()) {
            this.pasajeros.add(p);
        }


        //Cargar Reservas
        gestoraReservas.cargarReservas("reservas");
        for (Reserva r : gestoraReservas.getElementos()) {
            this.reservas.add(r);
        }


        //Cargar Estadias
        gestoraEstadias.cargarEstadia("registrosEstadias");
        for (RegistroEstadia r : gestoraEstadias.getElementos()) {
            this.registrosEstadias.add(r);
        }


            System.out.println("----- Carga finalizada -----");
    }

    public void guardarTodosLosDatos() {
        System.out.println("--- Iniciando Backup General ---");

        // Guardar Habitaciones
        gestoraHabitaciones.getElementos().clear();
        for (Habitacion h : habitaciones.values()) {
            gestoraHabitaciones.agregarElemento(h);
        }
        gestoraHabitaciones.guardar("habitaciones");


        // Guardar Usuarios
        gestoraUsuarios.getElementos().clear();
        for (UsuarioBase u : usuarios.values()) {
            gestoraUsuarios.agregarElemento(u);
        }
        gestoraUsuarios.guardar("usuarios");

        // Guardar Pasajeros
        gestoraPasajeros.getElementos().clear();
        for (Pasajero p : pasajeros) {
            gestoraPasajeros.agregarElemento(p);
        }
        gestoraPasajeros.guardar("pasajeros");

        // Guardar Reservas
        gestoraReservas.getElementos().clear();
        for (Reserva r : reservas) {
            gestoraReservas.agregarElemento(r);
        }
        gestoraReservas.guardar("reservas");

        // Guardar Estadías
        gestoraEstadias.getElementos().clear();
        for (RegistroEstadia reg : registrosEstadias) {
            gestoraEstadias.agregarElemento(reg);
        }
        gestoraEstadias.guardar("registrosEstadias");

        System.out.println("Backup finalizado correctamente.");
    }

    // --- GETTERS ---
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

    public List<Pasajero> getPasajeros() {return pasajeros;}


    // --- METODOS ---

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
        //Agregar pasajero a la lista si no existe
        if (!pasajeros.contains(reserva.getPasajero()))
        {
            pasajeros.add(reserva.getPasajero());
        }
        return reservas.add(reserva);
    }

    public boolean cancelarReserva(Reserva reserva) {
        for (Reserva res :  reservas) {
            if (res.getHabitacion() == reserva.getHabitacion()) {
                //Vuelve la habitacion a estar disponible
                res.getHabitacion().setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
                res.getHabitacion().setDisponible(true);
                return reservas.remove(res);
            }
        }
        return false;
    }

    public void agregarRegistro(RegistroEstadia registroEstadia) throws RegistroEstadiaException {
        if (registroEstadia== null)
        {
            throw new RegistroEstadiaException("El registro de estadia no puede ser nulo");
        }

        registrosEstadias.add(registroEstadia);
    }

    public RegistroEstadia buscarEstadiaActiva(int nroHabitacion) {
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