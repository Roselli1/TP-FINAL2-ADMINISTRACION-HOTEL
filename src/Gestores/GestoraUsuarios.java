package Gestores;

import Modelo.Hotel.Habitacion;
import Modelo.Persona.UsuarioBase;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestoraUsuarios extends GestoraGenerica<UsuarioBase>
{
    public GestoraUsuarios(String nombreJSON) {
        super(nombreJSON);
    }


    public void cargarUsuario(String nombreArchivo)
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

            JSONArray jsonUsuarios= jsonGestora.getJSONArray(super.nombreJson);

            //Limpiar la lista actual antes de cargar los nuevos elementos
            super.getElementos().clear();

            for (int i=0;  i< jsonUsuarios.length(); i++)
            {
                JSONObject jsonUsuario =  jsonUsuarios.getJSONObject(i);
                UsuarioBase usuario;

                String rol= jsonUsuario.getString("rol");

                usuario = new UsuarioBase(jsonUsuario);

                super.agregarElemento(usuario);
            }
            System.out.println("Usuarios cargados correctamente.");

        }catch (Exception e)
        {
            System.out.println("Error al cargar los usuarios: "+ e.getMessage());
        }

    }

}
