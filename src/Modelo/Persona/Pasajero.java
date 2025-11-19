package Modelo.Persona;

import Modelo.Hotel.RegistroEstadia;

import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Persona
{
    //Atributos
    private List<RegistroEstadia> historiaHotel;
    private String telefono;
    private String email;
    private boolean solicitarReserva; //indica si el pasajero pide cancelar reserva


    //Constructor
    public Pasajero(String nombre, String apellido, String dni, String domicilio, String origen, String telefono, String email, boolean solicitarReserva) {
        super(nombre, apellido, dni, domicilio, origen);
        this.telefono = telefono;
        this.email = email;
        this.solicitarReserva = solicitarReserva;
        this.historiaHotel = new ArrayList<>();
    }

    //Getters
    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSolicitarReserva() {
        return solicitarReserva;
    }

    public List<RegistroEstadia> getHistoriaHotel() {
        return historiaHotel;
    }

    /// AGREGAR METODOS

    public void agregarHistoriaHotel(RegistroEstadia estadia){
        this.historiaHotel.add(estadia);
    }

    public boolean solicitarCancelacionReserva(){
        if (!solicitarReserva){
            return true;
        }
        return false;
    }
}

