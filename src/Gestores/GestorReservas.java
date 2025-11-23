package Gestores;

import Modelo.Hotel.Habitacion;
import Modelo.Hotel.Reserva;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorReservas extends GestoraGenerica<Reserva> {
    public GestorReservas(String nombreJSON) {
        super(nombreJSON);
    }

    public boolean cargarReservas(String nombreArchivo) {
        try {
            String jsonString= JsonUtiles.leer(nombreArchivo);

            if (jsonString == null) {
                System.out.println("No se pudo cargar el archivo");
                return false;
            }

            JSONArray arrayPrincipal= new JSONArray(jsonString);

            JSONObject jsonGestora= arrayPrincipal.getJSONObject(0);

            JSONArray jsonReservas= jsonGestora.getJSONArray(super.nombreJson);

            for (int i=0;  i< jsonReservas.length(); i++)
            {
                JSONObject jsonHabitacion =  jsonReservas.getJSONObject(i);
                Reserva reserva = new Reserva(jsonHabitacion);
                super.agregarElemento(reserva);
            }
            System.out.println("Reserva cargada correctamente.");
            return true;

        } catch (Exception e) {
            System.out.println("Error al cargar la reserva: "+ e.getMessage());
            return false;
        }
    }
}
