import java.util.List;

public class Pasajero extends Persona
{
    //Atributos
    private List<RegistroEstadia> historiaHotel;
    private String telefono;
    private String email;
    private boolean solicitarReserva; //indica si el pasajero pide cancelar reserva


    public Pasajero(String nombre, String apellido, String dni, String domicilio, String origen, String telefono, String email List<RegistroEstadia> historiaHotel)
    {
        super(nombre, apellido, dni, domicilio, origen);
        this.telefono = telefono;
        this.email = email;
        this.solicitarReserva = false;
        this.historiaHotel = historiaHotel;
    }
}
