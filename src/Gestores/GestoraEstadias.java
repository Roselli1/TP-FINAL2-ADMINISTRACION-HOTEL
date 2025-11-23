package Gestores;

import Modelo.Hotel.RegistroEstadia;
import Modelo.Persona.UsuarioBase;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraEstadias extends GestoraGenerica<RegistroEstadia>
{
    public GestoraEstadias(String nombreJSON)
    {
        super(nombreJSON);
    }

    public void cargarEstadia(String nombreArchivo)
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

            JSONArray jsonEstadias= jsonGestora.getJSONArray(super.nombreJson);

            //Limpiar la lista actual antes de cargar los nuevos elementos
            super.getElementos().clear();

            for (int i=0;  i< jsonEstadias.length(); i++)
            {
                JSONObject jsonEstadia =  jsonEstadias.getJSONObject(i);
                RegistroEstadia estadia = new RegistroEstadia(jsonEstadia);
                super.agregarElemento(estadia);
            }
            System.out.println("Estadias cargadas correctamente.");

        }catch (Exception e)
        {
            System.out.println("Error al cargar las estadias: "+ e.getMessage());
        }
    }
}
