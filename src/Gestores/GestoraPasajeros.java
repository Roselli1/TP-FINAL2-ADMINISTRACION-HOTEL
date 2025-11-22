package Gestores;

import Modelo.Hotel.RegistroEstadia;
import Modelo.Persona.Pasajero;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraPasajeros extends GestoraGenerica<Pasajero>
{
    public GestoraPasajeros(String nombreJSON)
    {
        super(nombreJSON);
    }

    public void cargarPasajero(String nombreArchivo)
    {
        try
        {
            //Leer el archivo JSON
            String jsonString= JsonUtiles.leer(nombreArchivo+ ".json");

            if (jsonString==null)
            {
                System.out.println("No se pudo cargar el archivo");
                return;
            }

            JSONArray arrayPrincipal= new JSONArray(jsonString);

            JSONObject jsonGestora= arrayPrincipal.getJSONObject(0);

            JSONArray jsonPasajeros= jsonGestora.getJSONArray(super.nombreJson);

            //Limpiar la lista actual antes de cargar los nuevos elementos
            super.getElementos().clear();

            for (int i=0;  i< jsonPasajeros.length(); i++)
            {
                JSONObject jsonPasajero =  jsonPasajeros.getJSONObject(i);
                Pasajero pasajero = new Pasajero(jsonPasajero);
                super.agregarElemento(pasajero);
            }
            System.out.println("Pasajeros cargados correctamente.");

        }catch (Exception e)
        {
            System.out.println("Error al cargar a los pasajeros: "+ e.getMessage());
        }
    }
}
