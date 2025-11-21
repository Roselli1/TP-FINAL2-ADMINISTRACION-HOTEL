package Modelo.Hotel;

import Interfaces.iToJSON;
import org.json.JSONObject;

import java.time.LocalDate;

public class Servicio implements iToJSON {
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

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("descripcion", descripcion);
        json.put("precio", precio);
        json.put("fecha", fecha);

        return json;
    }

    public Servicio(JSONObject obj) {
        this.descripcion = obj.getString("descripcion");
        this.precio = obj.getDouble("precio");
        this.fecha = LocalDate.parse(obj.getString("fecha"));
    }
}
