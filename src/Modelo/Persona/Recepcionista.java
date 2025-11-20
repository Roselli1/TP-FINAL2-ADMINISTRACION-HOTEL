package Modelo.Persona;

import Enums.EstadoHabitacion;
import Enums.Rol;
import Exceptions.*;
import Interfaces.IGestionReserva;
import Modelo.Hotel.Habitacion;
import Gestores.GestorHotel;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;

public class Recepcionista  extends UsuarioBase implements IGestionReserva
{
    private GestorHotel hotel;

    public Recepcionista(String username, String password, GestorHotel hotel)
    {
        super(username, password, Rol.RECEPCIONISTA);
        this.hotel = hotel;
    }

    @Override
    public boolean crearReserva(Reserva reserva, int nroHabitacion) {
        return reserva != null;
    }

    @Override
    public boolean cancelarReserva(Reserva reserva, int nroHabitacion) {
        return reserva != null;
    }


    @Override
    public boolean hacerCheckIn(Reserva reserva, int nroHabitacion)
    {
        //EL CHECK-IN valida la reserva, ocupa la habitacion, crea un registro de estadia y lo guarda en el registro de estadias del pasajero y del hotel
        try {


            //obtiene la habitacion asociada a la reserva
            Habitacion habitacion = reserva.getHabitacion();

            //si la habitacion no esta disponible no se puede hacer check in
            if (!habitacion.isDisponible()) {
                throw new CheckInException("La habitacion no esta disponible para hacer check-in.");
            }
            //marcar la habitación como OCUPADA y no disponible
            habitacion.setEstadoHabitacion(EstadoHabitacion.OCUPADA);
            habitacion.setDisponible(false);

            //crear un RegistroEstadia con la info de la reserva
            RegistroEstadia registroEstadia = new RegistroEstadia(reserva.getPasajero(), reserva.getHabitacion(), reserva.getFechaIngreso(), reserva.getFechaEgreso());

            //guarda el registro en el historial del pasjero
            reserva.getPasajero().agregarHistoriaHotel(registroEstadia);
            //Agregar registro en el gestorHotel
            hotel.agregarRegistro(registroEstadia);

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
    public boolean hacerCheckOut(Reserva reserva, int nroHabitacion) {
        return hotel.cancelarReserva(reserva,nroHabitacion);
    }


    @Override
    public boolean autenticar(String username, String password)throws UsuarioYaExisteException {
        if (username.equals(this.username) && password.equals(this.password)) {
            throw new UsuarioYaExisteException("Este usuario ya existe.");
        }
        return true;
    }
}
