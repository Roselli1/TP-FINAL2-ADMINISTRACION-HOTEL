package Modelo.Hotel;

import Interfaces.iToJSON;
import org.json.JSONObject;

import java.time.LocalDate;

public class Servicio implements iToJSON {
    private int nroServicio;
    private String descripcion;
    private double precio;

    public Servicio(int nroServicio, String descripcion, double precio) {
        this.nroServicio = nroServicio;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getNroServicio() {
        return nroServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }


    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("nroServicio", nroServicio);
        json.put("descripcion", descripcion);
        json.put("precio", precio);

        return json;
    }

    public Servicio(JSONObject obj) {
        this.nroServicio = obj.getInt("nroServicio");
        this.descripcion = obj.getString("descripcion");
        this.precio = obj.getDouble("precio");
    }

    @Override
    public String toString() {
        return "Servicio N°: " + nroServicio +
                " | Descripción: " + descripcion +
                " | Precio: $" + precio;
    }
}
