package Modelo.Hotel;

import Interfaces.iToJSON;
import org.json.JSONObject;

public class Ocupacion implements iToJSON {
    private Reserva reserva;

    public Ocupacion(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void calcularCosto(){

    }

    public void calcularDias(){

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("reserva", reserva.toJSON());

        return json;
    }

    public Ocupacion(JSONObject obj) {
        this.reserva = (Reserva) obj.get("reserva");
    }
}
