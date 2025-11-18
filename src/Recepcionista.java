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
        throw new UnsupportedOperationException("El pasajero no puede hacer check-in.");
    }

    @Override
    public boolean hacerCheckOut(Habitacion habitacion) {
        throw new UnsupportedOperationException("El pasajero no puede hacer check-out.");
    }
}
