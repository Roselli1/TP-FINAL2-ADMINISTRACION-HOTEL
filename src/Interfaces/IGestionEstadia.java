package Interfaces;

import Modelo.Hotel.Reserva;

public interface IGestionEstadia {
    boolean hacerCheckIn (Reserva reserva);

    boolean hacerCheckOut(Reserva reserva, int nroHabitacion);
}
