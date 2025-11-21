package Modelo.Persona;

import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Gestores.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONObject;

public class Administrador extends Persona
{
    private GestorHotel hotel;

    //Constructor
    public Administrador(String nombre, String apellido, String dni, String domicilio, String origen,GestorHotel hotel) {
        super(nombre, apellido, dni, domicilio, origen);
        this.hotel = hotel;
    }

    public Administrador(JSONObject obj,GestorHotel hotel) {
        super(obj);
        this.hotel = hotel;
    }

    /// ACA PODEMOS PONER 2 EXCEPTION Usuario y Contrasenia
    /// pero que si la contrasenia esta bn y el usuario mal diga q usuario/contrasenia incorrecta algo asi



    //Metodos
    public boolean realizarBackup(String nombreArchivo)
    {
        try {
            if (hotel == null) {
                System.out.println("Error: El Administrador no tiene acceso al GestorHotel.");
                return false;
            }

            JSONObject backupData = hotel.toJSON();

            JSONArray arrayFinal = new JSONArray();
            arrayFinal.put(backupData);

            JsonUtiles.grabarUnJson(arrayFinal, nombreArchivo + ".json");

            System.out.println("Backup del sistema realizado con éxito en: " + nombreArchivo + ".json");
            return true;

        } catch (Exception e) {
            System.out.println("Error al realizar el backup: " + e.getMessage());
            return false;
        }
    }

    public boolean crearUsuario(UsuarioBase usuario) throws UsuarioYaExisteException
    {
        if (hotel == null) return false;

        if (usuario== null) return false;

        if (!hotel.agregarUsuario(usuario)) {
            throw new UsuarioYaExisteException("El usuario con el nombre " + usuario.getUsername() + " ya existe");
        }

        return true;
    }


    public void asignarPermisos(UsuarioBase usuario)
    {
        if (usuario == null) return; ///PODEMOS AGREGAR EXCEPTION

    }



}
