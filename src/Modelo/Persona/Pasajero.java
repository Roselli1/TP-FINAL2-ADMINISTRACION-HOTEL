package Modelo.Persona;

import Enums.EstadoHabitacion;
import Exceptions.CheckInException;
import Exceptions.CheckOutException;
import Exceptions.RegistroEstadiaException;
import Gestores.GestorHotel;
import Interfaces.IGestionEstadia;
import Interfaces.IGestionReserva;
import Interfaces.iToJSON;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Persona implements iToJSON, IGestionReserva, IGestionEstadia
{
    //Atributos
    private List<RegistroEstadia> historiaHotel;
    private String telefono;
    private String email;
    private boolean solicitarReserva; //indica si el pasajero pide cancelar reserva
    private GestorHotel hotel;

    //Constructor
    public Pasajero(String nombre, String apellido, String dni, String domicilio, String origen, String telefono, String email, boolean solicitarReserva, GestorHotel hotel) {
        super(nombre, apellido, dni, domicilio, origen);
        this.telefono = telefono;
        this.email = email;
        this.solicitarReserva = solicitarReserva;
        this.historiaHotel = new ArrayList<>();
        this.hotel = hotel;
    }

    //Getters
    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSolicitarReserva() {
        return solicitarReserva;
    }

    public List<RegistroEstadia> getHistoriaHotel() {
        return historiaHotel;
    }

    /// AGREGAR METODOS

    public void agregarHistoriaHotel(RegistroEstadia estadia){
        this.historiaHotel.add(estadia);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("nombre", getNombre());
        json.put("apellido", getApellido());
        json.put("dni", getDni());
        json.put("domicilio", getDomicilio());
        json.put("origen", getOrigen());
        json.put("telefono", getTelefono());
        json.put("email", getEmail());
        json.put("solicitarReserva", solicitarReserva);

        return json;
    }

    public Pasajero(JSONObject obj) {
        super(obj.getString("nombre"),
                obj.getString("apellido"),
                obj.getString("dni"),
                obj.getString("domicilio"),
                obj.getString("origen"));
        this.telefono = obj.getString("telefono");
        this.email = obj.getString("email");
        this.solicitarReserva = obj.getBoolean("solicitarReserva");
    }

    /// FALTA DESARROLLAR METODOS

    //Solicita una reserva
    @Override
    public boolean crearReserva(Reserva reserva, int nroHabitacion) {
        return false;
    }

    //Solicita cancelar una reserva
    @Override
    public boolean cancelarReserva(Reserva reserva) {
        return false;
    }

    @Override
    public boolean hacerCheckIn(Reserva reserva) {
        // EL CHECK-IN valida la reserva, ocupa la habitacion, crea un registro de estadia y lo guarda
        if (reserva == null || hotel == null) {
            System.err.println("Error: El objeto Reserva o el GestorHotel son nulos.");
            return false;
        }
        try {
            //obtiene la habitacion asociada a la reserva y el pasajero
            Habitacion habitacion = reserva.getHabitacion();
            Pasajero pasajero = reserva.getPasajero();

            //si la habitacion no esta disponible no se puede hacer check in
            if (habitacion.getEstadoHabitacion() != EstadoHabitacion.RESERVADA && habitacion.getEstadoHabitacion() != EstadoHabitacion.DISPONIBLE) {
                throw new CheckInException("La habitacion no esta disponible para hacer check-in.");
            }
            //marcar la habitación como OCUPADA y no disponible
            habitacion.setEstadoHabitacion(EstadoHabitacion.OCUPADA);
            habitacion.setDisponible(false);

            //crear un RegistroEstadia con la info de la reserva
            RegistroEstadia registroEstadia = new RegistroEstadia(pasajero, habitacion, LocalDate.now(), reserva.getFechaEgreso());

            //guarda el registro en el historial del pasajero
            this.agregarHistoriaHotel(registroEstadia);
            //Agregar registro en el gestorHotel
            hotel.agregarRegistro(registroEstadia);

            //eliminar la reserva como si ya fue usada
            hotel.getReservas().remove(reserva);

            System.out.println("Check-in realizado con éxito para la habitación " + habitacion.getNumero() + ".");
            return true;
        } catch (CheckInException | RegistroEstadiaException e) {
            System.err.println("Error en el check-in: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado en el check-in: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hacerCheckOut(Reserva reserva, int nroHabitacion) {
        if (reserva == null || hotel == null) {
            System.err.println("Error: La reserva o el GestorHotel son nulos.");
            return false;
        }

        try {
            Habitacion habitacion = reserva.getHabitacion();

            // Verificar si la habitacion esta ocupada
            if (habitacion.getEstadoHabitacion() != EstadoHabitacion.OCUPADA) {
                throw new CheckOutException("La habitacion no esta ocupada, no es posible hacer check-out.");
            }

            // Busca la estadia activa por número de habitación
            RegistroEstadia estadiaActiva = hotel.buscarEstadiaActiva(nroHabitacion);

            if (estadiaActiva == null) {
                throw new CheckOutException("Error: La habitación esta ocupada pero no se encontró un RegistroEstadia activo.");
            }

            // Finaliza la estadia activa (establece la fecha de CheckOut)
            estadiaActiva.setCheckOut(LocalDate.now());

            // Actualizar el estado de la habitacion
            habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
            habitacion.setDisponible(true);

            System.out.println("Check-out realizado con éxito para la habitación " + nroHabitacion + ".");
            return true;

        } catch (CheckOutException e) {
            System.err.println("Error en el check-out: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado en el check-out: " + e.getMessage());
            return false;
        }
    }
}

