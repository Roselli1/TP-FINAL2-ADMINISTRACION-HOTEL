package Modelo.Persona;

import Exceptions.UsuarioYaExisteException;
import org.json.JSONObject;

public class Administrador extends Persona
{

    //Constructor
    public Administrador(String nombre, String apellido, String dni, String domicilio, String origen) {
        super(nombre, apellido, dni, domicilio, origen);
    }

    public Administrador(JSONObject obj) {
        super(obj);
    }

    /// ACA PODEMOS PONER 2 EXCEPTION Usuario y Contrasenia
    /// pero que si la contrasenia esta bn y el usuario mal diga q usuario/contrasenia incorrecta algo asi



    //Metodos
    public void realizarBackup()
    {
        JSONObject json = super.toJSON();
    }

   /* public boolean crearUsuario(UsuarioBase usuario) throws UsuarioYaExisteException
    {
        if (usuario== null) return false;

        if (//Logica de donde se guarda(hotel o gestora ))
                {
                        throw new UsuarioYaExisteException("El usuario con el nombre " + usuario.getUsername() + " ya existe");
                }

                //metodo agregar usuario
        return true;
    }

    */

    public void asignarPermisos(UsuarioBase usuario)
    {
        if (usuario== null) return; ///PODEMOS AGREGAR EXCEPTION

    }



}
