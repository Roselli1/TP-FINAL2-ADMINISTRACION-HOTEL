package Gestores;

import Modelo.Persona.Administrador;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraAdministradores extends GestoraGenerica<Administrador>
{

        public GestoraAdministradores(String nombreJSON)
        {
            super(nombreJSON);
        }

        public void cargarAdministador(String nombreArchivo)
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

                JSONArray jsonAdmins= jsonGestora.getJSONArray(super.nombreJson);

                //Limpiar la lista actual antes de cargar los nuevos elementos
                super.getElementos().clear();

                for (int i=0;  i< jsonAdmins.length(); i++)
                {
                    JSONObject jsonAdmin =  jsonAdmins.getJSONObject(i);
                    Administrador administrador = new Administrador(jsonAdmin, null);
                    super.agregarElemento(administrador);
                }
                System.out.println("Administradores cargados correctamente.");

            }catch (Exception e)
            {
                System.out.println("Error al cargar a los administradores: "+ e.getMessage());
            }
        }
    }

