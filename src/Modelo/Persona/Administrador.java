package Modelo.Persona;

import Enums.Rol;
import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Gestores.JsonUtiles;
import Interfaces.iToJSON;
import org.json.JSONArray;
import org.json.JSONObject;

public class Administrador extends Persona implements iToJSON
{
    private GestorHotel hotel;
    private UsuarioBase credenciales;

    //Constructor
    public Administrador(String nombre, String apellido, String dni, String domicilio, String origen, GestorHotel hotel, String username, String password) {
        super(nombre, apellido, dni, domicilio, origen);
        this.hotel = hotel;
        this.credenciales = new UsuarioBase(username, password, Rol.ADMINISTRADOR);
    }

    public Administrador(JSONObject obj,GestorHotel hotel) {
        super(obj);
        this.hotel = hotel;
        this.credenciales = new UsuarioBase(obj.getJSONObject("usuario"));
    }

    @Override
    public JSONObject toJSON() {
        //la clase padre se encarga de mostrar sus propios atributos
        JSONObject json = super.toJSON();
        json.put("usuario", credenciales.toJSON());

        return json;
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

            hotel.guardarTodosLosDatos();

            System.out.println("Backup realizado correctamente.");
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


    public boolean autenticar(String username, String password) throws UsuarioYaExisteException {
        return this.credenciales.autenticar(username, password);
    }

    public UsuarioBase getCredenciales() {
        return credenciales;
    }

    public void setHotel(GestorHotel hotel) {
        this.hotel = hotel;
    }
}
