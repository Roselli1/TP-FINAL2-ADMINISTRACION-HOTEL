package Gestores;

import Modelo.Hotel.Habitacion;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraHabitaciones extends GestoraGenerica<Habitacion>
{

        public GestoraHabitaciones(String nombreJSON) {
            super(nombreJSON);
        }


        public void cargarHabitacion(String nombreArchivo)
        {
            try
            {
                //Leer el archivo JSON
                String jsonString= JsonUtiles.leer(nombreArchivo);

                if (jsonString==null)
                {
                    System.out.println("No se pudo cargar el archivo");
                    return;
                }

                JSONArray arrayPrincipal= new JSONArray(jsonString);

                JSONObject jsonGestora= arrayPrincipal.getJSONObject(0);

                JSONArray jsonHabitaciones= jsonGestora.getJSONArray(super.nombreJson);

                //Limpiar la lista actual antes de cargar los nuevos elementos
                super.getElementos().clear();

                for (int i=0;  i< jsonHabitaciones.length(); i++)
                {
                    JSONObject jsonHabitacion =  jsonHabitaciones.getJSONObject(i);
                    // Llama al constructor de carga de Habitacion
                    Habitacion habitacion = new Habitacion(jsonHabitacion);
                    super.agregarElemento(habitacion);
                }
                System.out.println("Habitaciones cargadas correctamente.");

            }catch (Exception e)
            {
                System.out.println("Error al cargar las habitaciones: "+ e.getMessage());
            }

        }



}
