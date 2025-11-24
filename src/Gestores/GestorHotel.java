package Gestores;

import Enums.EstadoHabitacion;
import Enums.Rol;
import Enums.TipoHabitacion;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.RegistroEstadiaException;
import Exceptions.ReservaInvalidaException;
import Exceptions.UsuarioYaExisteException;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import Modelo.Persona.Administrador;
import Modelo.Persona.Pasajero;
import Modelo.Persona.Recepcionista;
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
    private List<Recepcionista> recepcionistas;
    private List<Administrador> administradores;

    private GestoraHabitaciones gestoraHabitaciones;
    private GestorReservas gestoraReservas;
    private GestoraUsuarios gestoraUsuarios;
    private GestoraEstadias gestoraEstadias;
    private GestoraPasajeros gestoraPasajeros;
    private GestoraRecepcionistas gestoraRecepcionistas;
    private GestoraAdministradores gestoraAdministradores;

    // --- CONSTRUCTOR ---
    public GestorHotel()
    {
        this.habitaciones = new HashMap<>();
        this.reservas = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.registrosEstadias = new ArrayList<>();
        this.pasajeros= new ArrayList<>();
        this.recepcionistas= new ArrayList<>();
        this.administradores= new ArrayList<>();

        this.gestoraHabitaciones= new GestoraHabitaciones("habitaciones");
        this.gestoraReservas= new GestorReservas("reservas");
        this.gestoraUsuarios= new GestoraUsuarios("usuarios");
        this.gestoraEstadias= new GestoraEstadias("registrosEstadias");
        this.gestoraPasajeros= new GestoraPasajeros("pasajeros");
        this.gestoraRecepcionistas= new GestoraRecepcionistas("recepcionistas");
        this.gestoraAdministradores= new GestoraAdministradores("administradores");

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

        //Cargar Recepcionistas
        gestoraRecepcionistas.cargarRecepcionista("recepcionistas");
        for (Recepcionista r : gestoraRecepcionistas.getElementos()) {
            this.recepcionistas.add(r);
            // Registramos en el sistema de Login
            this.usuarios.put(r.getCredenciales().getUsername(), r.getCredenciales());
        }

        //Cargar Administradores
        gestoraAdministradores.cargarAdministador("administradores");
        for (Administrador r : gestoraAdministradores.getElementos()) {
            {
                this.administradores.add(r);
                // Registramos en el sistema de Login
                this.usuarios.put(r.getCredenciales().getUsername(), r.getCredenciales());
            }
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

        // Guardar Recepcionistas
        gestoraRecepcionistas.getElementos().clear();
        for (Recepcionista r : recepcionistas) {
            gestoraRecepcionistas.agregarElemento(r);
        }
        gestoraRecepcionistas.guardar("recepcionistas");

        // Guardar Administradores
        gestoraAdministradores.getElementos().clear();
        for (Administrador r : administradores) {
            gestoraAdministradores.agregarElemento(r);
        }
        gestoraAdministradores.guardar("administradores");

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

    public List<Administrador> getAdministradores() { return administradores; }

    public List<Recepcionista> getRecepcionistas() { return recepcionistas; }


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
            throw new UsuarioYaExisteException("El nombre de usuario '" + usuario.getUsername() + "' ya esta en uso.");
        }

        usuarios.put(usuario.getUsername(), usuario);
        return true;
    }

    public void registrarPasajero(Pasajero pasajero) throws UsuarioYaExisteException {
        if (pasajero == null) return;

        if (usuarios.containsKey(pasajero.getUsername())) {
            throw new UsuarioYaExisteException("El nombre de usuario '" + pasajero.getUsername() + "' ya está en uso.");
        }

        this.pasajeros.add(pasajero);
        this.usuarios.put(pasajero.getUsername(), pasajero.getCredenciales());
    }

    public void listarHabitaciones() {
        System.out.println("\n--- HABITACIONES SIMPLES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getTipoHabitacion()== TipoHabitacion.SIMPLE)
            {
                System.out.println(h.toString());
            }
        }

        System.out.println("\n--- HABITACIONES DOBLES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getTipoHabitacion()== TipoHabitacion.DOBLE)
            {
                System.out.println(h.toString());
            }
        }

        System.out.println("\n--- SUITES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getTipoHabitacion()== TipoHabitacion.SUITE)
            {
                System.out.println(h.toString());
            }
        }

    }

    public void mostrarHabitacionesDisponibles() {
        System.out.println("\n--- HABITACIONES SIMPLES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE && h.isDisponible() && h.getTipoHabitacion() == TipoHabitacion.SIMPLE)
            {
                System.out.println(h.toString());
            }
        }

        System.out.println("\n--- HABITACIONES DOBLES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE && h.isDisponible() && h.getTipoHabitacion() == TipoHabitacion.DOBLE)
            {
                System.out.println(h.toString());
            }
        }

        System.out.println("\n--- SUITES ---");
        for (Habitacion h : habitaciones.values()) {
            if (h.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE && h.isDisponible() && h.getTipoHabitacion() == TipoHabitacion.SUITE)
            {
                System.out.println(h.toString());
            }
        }
    }


    public Pasajero buscarPasajeroPorUsername(String username) {
        // Recorremos la lista interna de pasajeros
        for (Pasajero p : pasajeros) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null; // Si no lo encuentra
    }

    public Recepcionista buscarReceopcionistaPorUsername(String username) {
        for (Recepcionista r: recepcionistas)
        {
            if (r.getCredenciales().getUsername().equals(username))
            {
                return r;
            }
        }
        return null;  // Si no lo encuentra
    }

    public Administrador buscarAdministradorPorUsername(String username) {
        for (Administrador a: administradores)
        {
            if (a.getCredenciales().getUsername().equals(username))
            {
                return a;
            }
        }
        return null; // Si no lo encuentra
    }

    public void verReservasPasajero(Pasajero pasajero) {
        boolean tieneReservas = false;

        for (Reserva r : reservas) {
            if (r.getPasajero().equals(pasajero)) {
                System.out.println(r.toString());
                tieneReservas = true;
            }
        }
        if (!tieneReservas) {
            System.out.println("El pasajero no tiene ninguna reserva registrada.");
        }
    }

    public List<Reserva> buscarReservasPorPasajero(Pasajero pasajero) {

        List<Reserva> resultado = new ArrayList<>();

        for (Reserva r : this.reservas)
        {
        // Comparamos por DNI para asegurar identidad
            if (r.getPasajero().getDni().equals(pasajero.getDni())) {
            resultado.add(r);
            }
        }
        return resultado;
}

    public void agregarHabitacion(Habitacion habitacion) {
        if (habitacion != null) {
            this.habitaciones.put(habitacion.getNumero(), habitacion);
        }
    }

    public void agregarStaff(UsuarioBase credenciales, String nombre, String apellido, String dni, String dir, String origen) throws UsuarioYaExisteException {
        if (usuarios.containsKey(credenciales.getUsername())) {
            throw new UsuarioYaExisteException("El usuario ya existe.");
        }

        if (credenciales.getRol() == Rol.ADMINISTRADOR) {
            Administrador admin = new Administrador(nombre, apellido, dni, dir, origen, this, credenciales.getUsername(), credenciales.getPassword());
            this.administradores.add(admin);
            this.usuarios.put(credenciales.getUsername(), credenciales);
        }
        else if (credenciales.getRol() == Rol.RECEPCIONISTA) {
            Recepcionista recep = new Recepcionista(nombre, apellido, dni, dir, origen, this, credenciales.getUsername(), credenciales.getPassword());
            this.recepcionistas.add(recep);
            this.usuarios.put(credenciales.getUsername(), credenciales);
        }
    }
}