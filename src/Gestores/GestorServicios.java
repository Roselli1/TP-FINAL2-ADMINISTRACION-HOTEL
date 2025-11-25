package Gestores;

import Modelo.Hotel.Reserva;
import Modelo.Hotel.Servicio;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorServicios extends GestoraGenerica<Servicio> {
    public GestorServicios(String nombreJSON) {
        super(nombreJSON);
    }

    public boolean cargarServicio(String nombreArchivo) {
        try {
            String jsonString= JsonUtiles.leer(nombreArchivo);

            if (jsonString == null) {
                System.out.println("No se pudo cargar el archivo");
                return false;
            }

            JSONArray arrayPrincipal= new JSONArray(jsonString);

            JSONObject jsonGestora = arrayPrincipal.getJSONObject(0);

            JSONArray jsonServicios = jsonGestora.getJSONArray(super.nombreJson);

            for (int i=0;i< jsonServicios.length(); i++)
            {
                JSONObject jsonServicio =  jsonServicios.getJSONObject(i);
                Servicio servicio = new Servicio(jsonServicio);
                super.agregarElemento(servicio);
            }
            System.out.println("Servicio cargado correctamente.");
            return true;

        } catch (Exception e) {
            System.out.println("Error al cargar el servicio: "+ e.getMessage());
            return false;
        }
    }
}
