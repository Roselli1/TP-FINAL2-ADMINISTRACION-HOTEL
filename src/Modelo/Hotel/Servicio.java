package Modelo.Hotel;

import java.time.LocalDate;

public class Servicio {
    private String descripcion;
    private double precio;
    private LocalDate fecha;

    public Servicio(String descripcion, double precio, LocalDate fecha) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
