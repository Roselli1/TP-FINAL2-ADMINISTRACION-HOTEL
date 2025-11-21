package Interfaces;

import Modelo.Hotel.Reserva;

public interface IGestionReserva
{
    boolean crearReserva (Reserva reserva, int nroHabitacion);

    boolean cancelarReserva(Reserva reserva);
}
