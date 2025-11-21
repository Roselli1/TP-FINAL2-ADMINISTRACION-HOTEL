package Modelo.Hotel;

import Interfaces.iToJSON;
import Modelo.Persona.Pasajero;
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
        return "RegistroEstadia{" +
                "pasajero=" + pasajero +
                ", habitacion=" + habitacion +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", consumos=" + consumos +
                '}';
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("pasajero", pasajero);
        json.put("habitacion", habitacion);
        json.put("checkIn", checkIn);
        json.put("checkOut", checkOut);
        json.put("consumos", consumos);

        return json;
    }

    public RegistroEstadia(JSONObject obj) {
        pasajero = new Pasajero(obj.getJSONObject("pasajero"));
        habitacion = new Habitacion(obj.getJSONObject("habitacion"));
        checkIn = LocalDate.parse(obj.getString("checkIn"));
        checkOut = LocalDate.parse(obj.getString("checkOut"));
        consumos = (List<Servicio>) obj.get("consumos");
    }
}
