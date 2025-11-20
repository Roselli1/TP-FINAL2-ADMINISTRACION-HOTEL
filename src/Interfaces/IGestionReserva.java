package Interfaces;

import Modelo.Hotel.Habitacion;
import Modelo.Hotel.Reserva;

public interface IGestionReserva
{
    boolean crearReserva (Reserva reserva, int nroHabitacion);

    boolean cancelarReserva(Reserva reserva);

    boolean hacerCheckIn (Reserva reserva);

    boolean hacerCheckOut(Reserva reserva, int nroHabitacion);
}
