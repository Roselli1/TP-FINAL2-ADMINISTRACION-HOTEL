package Interfaces;

import Modelo.Hotel.Habitacion;
import Modelo.Hotel.Reserva;

public interface IGestionReserva
{
    boolean crearReserva (Reserva reserva);

    boolean cancelarReserva(Reserva reserva);

    boolean hacerCheckIn (Reserva reserva);

    boolean hacerCheckOut(Habitacion habitacion);
}
