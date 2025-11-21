package Modelo.Hotel;

import Interfaces.iToJSON;
import org.json.JSONObject;

import java.time.temporal.ChronoUnit;

public class Ocupacion implements iToJSON {
    private Reserva reserva;

    public Ocupacion(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public long calcularCosto(){
        long cantDias = calcularDias();
        return reserva.getPrecioPorDia() * cantDias;
    }

    public long calcularDias(){
        return ChronoUnit.DAYS.between(reserva.getFechaIngreso(),reserva.getFechaEgreso());
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
