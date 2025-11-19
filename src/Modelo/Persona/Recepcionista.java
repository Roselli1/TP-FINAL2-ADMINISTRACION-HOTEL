package Modelo.Persona;

import Enums.Rol;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;
import Exceptions.UsuarioYaExisteException;
import Interfaces.IGestionReserva;
import Modelo.Hotel.Habitacion;
import Gestores.GestorHotel;
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
    public boolean hacerCheckIn(Reserva reserva, int nroHabitacion) {
        try{
            return hotel.realizarReserva(reserva,nroHabitacion);
        } catch (HabitacionNoDisponibleException | ReservaInvalidaException e) {
            System.out.println(e.getMessage());
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
