package Modelo.Persona;

import Enums.Rol;
import Exceptions.UsuarioYaExisteException;

public class Administrador extends UsuarioBase
{
    //Constructor
    public Administrador(String username, String password) {
        super(username, password, Rol.ADMINISTRADOR);
    }




    /// ACA PODEMOS PONER 2 EXCEPTION Usuario y Contrasenia
    /// pero que si la contrasenia esta bn y el usuario mal diga q usuario/contrasenia incorrecta algo asi


    //Implementacion del metodo abstracto
    @Override
    public boolean autenticar(String username, String password)throws UsuarioYaExisteException {
        if (username.equals(this.username) && password.equals(this.password)) {
            throw new UsuarioYaExisteException("Este usuario ya existe.");
        }
        return true;
    }




    //Metodos
    public void realizarBackup()
    {
        ///Implementar logica parte JSON
        ///guardar en archivo JSON
    }

   /* public boolean crearUsuario(Modelo.Persona.UsuarioBase usuario) throws Exceptions.UsuarioYaExisteException
    {
        if (usuario== null) return false;

        if (//Logica de donde se guarda(hotel o gestora ))
                {
                        throw new Exceptions.UsuarioYaExisteException("El usuario con el nombre " + usuario.getUsername() + " ya existe");
                }

                //metodo agregar usuario
        return true;
    }

    */

    public void asignarPermisos(UsuarioBase usuario)
    {
        if (usuario== null) return; ///PODEMOS AGREGAR EXCEPTION

    }





    @Override
    public String toString() {
        return "Modelo.Persona.Administrador{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }
}
