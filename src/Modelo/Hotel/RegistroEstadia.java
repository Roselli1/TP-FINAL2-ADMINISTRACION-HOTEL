package Modelo.Hotel;

import Interfaces.iToJSON;
import Modelo.Persona.Pasajero;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroEstadia implements iToJSON {
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Servicio> consumos;

    public RegistroEstadia(Pasajero pasajero, Habitacion habitacion, LocalDate checkIn, LocalDate checkOut) {
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.consumos = new ArrayList<>();
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public List<Servicio> getConsumos() {
        return consumos;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Estadía | Hab: " + habitacion.getNumero() +
                " | Pasajero: " + pasajero.getNombre() + " " + pasajero.getApellido() +
                " | Entrada: " + checkIn +
                " | Salida: " + (checkOut != null ? checkOut : "En curso");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("pasajero", pasajero.toJSON());
        json.put("habitacion", habitacion.toJSON());
        json.put("checkIn", checkIn.toString());
        if (checkOut != null) {
            json.put("checkOut", checkOut);
        }else
        {
            json.put("checkOut", JSONObject.NULL);
        }
        JSONArray arrayConsumos = new JSONArray();
        for (Servicio s : consumos) {
            arrayConsumos.put(s.toJSON());
        }
        json.put("consumos", arrayConsumos);

        return json;
    }

    public void agregarConsumo(Servicio servicio) {
        if (servicio != null) {
            this.consumos.add(servicio);
        }
    }

    public RegistroEstadia(JSONObject obj) {
        pasajero = new Pasajero(obj.getJSONObject("pasajero"));
        habitacion = new Habitacion(obj.getJSONObject("habitacion"));
        checkIn = LocalDate.parse(obj.getString("checkIn"));
        if (obj.has("checkOut") && !obj.isNull("checkOut")) {
            checkOut = LocalDate.parse(obj.getString("checkOut"));
        } else {
            checkOut = null;
        }

        // Recuperamos la lista de consumos iterando (sin castear a la fuerza)
        consumos = new ArrayList<>();
        JSONArray arrConsumos = obj.optJSONArray("consumos");
        if (arrConsumos != null) {
            for (int i = 0; i < arrConsumos.length(); i++) {
                JSONObject jsonServicio = arrConsumos.getJSONObject(i);
                consumos.add(new Servicio(jsonServicio)); // Asume que Servicio tiene este constructor
            }
        }
    }
}
