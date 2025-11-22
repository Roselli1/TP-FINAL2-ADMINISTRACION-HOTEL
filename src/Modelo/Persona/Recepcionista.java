package Modelo.Persona;

import Enums.EstadoHabitacion;
import Enums.Rol;
import Exceptions.*;
import Interfaces.IGestionEstadia;
import Interfaces.IGestionReserva;
import Modelo.Hotel.Habitacion;
import Gestores.GestorHotel;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import org.json.JSONObject;

import java.time.LocalDate;

public class Recepcionista  extends Persona implements IGestionReserva, IGestionEstadia
{
    private GestorHotel hotel;
    private UsuarioBase credenciales;

    public Recepcionista(String nombre, String apellido, String dni, String domicilio, String origen, GestorHotel hotel, String username, String password) {
        super(nombre, apellido, dni, domicilio, origen);
        this.hotel = hotel;
        this.credenciales = new UsuarioBase(username, password, Rol.RECEPCIONISTA);
    }

    public Recepcionista(JSONObject obj, GestorHotel hotel) {
        super(obj);
        this.hotel = hotel;
    }

    @Override
    public boolean crearReserva(Reserva reserva, int nroHabitacion) {
        if (reserva == null || hotel == null) {
            System.err.println("Error: El objeto Reserva o el GestorHotel son nulos.");
            return false;
        }
        try {
            boolean reservaExitosa = hotel.realizarReserva(reserva, nroHabitacion);

            if (reservaExitosa) {
                Habitacion habitacion = reserva.getHabitacion();
                habitacion.setEstadoHabitacion(EstadoHabitacion.RESERVADA);

                System.out.println("Reserva creada con éxito para la habitación " + nroHabitacion + ".");
            }

            return reservaExitosa;

        } catch (HabitacionNoDisponibleException | ReservaInvalidaException e) {
            System.err.println("Error al crear la reserva: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean cancelarReserva(Reserva reserva) {
        if (reserva == null || hotel == null) {
            System.err.println("Error: La reserva o el GestorHotel son nulos.");
            return false;
        }

        Habitacion habitacion = reserva.getHabitacion();

        try {
            reserva.cancelarReserva();

            if (hotel.getReservas().remove(reserva)) {
                System.out.println("Reserva Nro " + reserva.getNroReserva() + " eliminada del sistema.");

                if (habitacion.getEstadoHabitacion() == EstadoHabitacion.RESERVADA) {
                    habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
                    habitacion.setDisponible(true);
                    System.out.println("Estado de Habitación " + habitacion.getNumero() + " actualizado a DISPONIBLE.");
                } else {
                    System.out.println("Advertencia: La habitación " + habitacion.getNumero() +
                            " no estaba en estado RESERVADA. No se modificó el estado de la habitación (actual: " +
                            habitacion.getEstadoHabitacion().getEstado() + ").");
                }

                return true;
            } else {
                System.err.println("Error: La reserva no se encontró en la lista activa del hotel o ya fue cancelada.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error inesperado al cancelar reserva: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean hacerCheckIn(Reserva reserva)
    {
        //EL CHECK-IN valida la reserva, ocupa la habitacion, crea un registro de estadia y lo guarda en el registro de estadias del pasajero y del hotel
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
            RegistroEstadia registroEstadia =  new RegistroEstadia(pasajero, habitacion, LocalDate.now(), reserva.getFechaEgreso());

            //guarda el registro en el historial del pasjero
            pasajero.agregarHistoriaHotel( registroEstadia);
            //Agregar registro en el gestorHotel
            hotel.agregarRegistro(registroEstadia);

            //eliminar la reserva como si ya fue usada
            hotel.getReservas().remove(reserva);

            //si el checkin fue exitoso devuelve true
            return true;
        }
        catch (CheckInException  |RegistroEstadiaException e)
        {
            System.out.println("Error en el check-in: "+ e.getMessage());
            return false;
        }

    }

    @Override
    public boolean hacerCheckOut(Reserva reserva, int nroHabitacion)
    {

        try
        {
            Habitacion habitacion = reserva.getHabitacion();

            //Verificar si la habitacion esta ocupada
            if (habitacion.getEstadoHabitacion() != EstadoHabitacion.OCUPADA)
            {
                throw new CheckOutException("La habitacion no esta ocupada, no es posible hacer check-out.");
            }

            //Busca la estadia activa
            RegistroEstadia estadiaActiva= hotel.buscarEstadiaActiva(nroHabitacion);

            if (estadiaActiva == null)
            {
                throw new CheckOutException("Error: La habitación esta ocupada pero no se encontró un RegistroEstadia activo.");
            }

            //finaliza la estadia activa
            estadiaActiva.setCheckOut(LocalDate.now());

            //actualizar el estado de la habitacion
            habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
            habitacion.setDisponible(true);


            return true;

        }catch (CheckOutException e)
        {
            System.out.println("Error en el check-out: "+ e.getMessage());
            return false;
        } catch (Exception e)
        {
            System.out.println("Error inesperado en el check-out" + e.getMessage());
            return false;
        }


    }

    public boolean autenticar(String username, String password) throws UsuarioYaExisteException {
        return this.credenciales.autenticar(username, password);
    }
}
