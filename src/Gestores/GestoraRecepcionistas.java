package Gestores;

import Modelo.Persona.Administrador;
import Modelo.Persona.Recepcionista;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraRecepcionistas extends GestoraGenerica<Recepcionista>
{
    public GestoraRecepcionistas(String nombreJSON)
    {
        super(nombreJSON);
    }

    public void cargarRecepcionista(String nombreArchivo)
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

            JSONArray jsonRecepcionistas= jsonGestora.getJSONArray(super.nombreJson);

            //Limpiar la lista actual antes de cargar los nuevos elementos
            super.getElementos().clear();

            for (int i=0;  i< jsonRecepcionistas.length(); i++)
            {
                JSONObject jsonRecep =  jsonRecepcionistas.getJSONObject(i);
                Recepcionista recepcionista= new Recepcionista(jsonRecep, null);
                super.agregarElemento(recepcionista);
            }
            System.out.println("Recepcionistas cargados correctamente.");

        }catch (Exception e)
        {
            System.out.println("Error al cargar a los recepcionistas: "+ e.getMessage());
        }
    }
}
