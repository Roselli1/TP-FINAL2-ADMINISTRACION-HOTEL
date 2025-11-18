package Modelo;

import Interfaces.IGestionReserva;

public class Recepcionista  extends UsuarioBase implements IGestionReserva
{
    private Hotel hotel;

    public Recepcionista(String username, String password, Hotel hotel)
    {
        super(username, password, Rol.RECEPCIONISTA);
        this.hotel = hotel;
    }

    @Override
    public boolean crearReserva(Reserva reserva) {
        return reserva != null;
    }

    @Override
    public boolean cancelarReserva(Reserva reserva) {
        return reserva != null;
    }

    @Override
    public boolean hacerCheckIn(Reserva reserva) {
        /// IMPLEMENTAR CODIGO
        return reserva != null;
    }

    @Override
    public boolean hacerCheckOut(Habitacion habitacion) {
        return habitacion != null; ///IMPLEMENTARCODIGO
    }


    @Override
    public boolean autenticar(String username, String password) {
        return false;
    }
}
