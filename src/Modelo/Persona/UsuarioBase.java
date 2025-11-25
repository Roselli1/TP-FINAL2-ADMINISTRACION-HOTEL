package Modelo.Persona;

import Enums.Rol;
import Exceptions.UsuarioYaExisteException;
import Interfaces.iToJSON;
import org.json.JSONObject;

import java.util.Objects;

public class UsuarioBase implements iToJSON
{
    //Atributos
    protected String username;
    protected String password;
    protected Rol rol;

    //Constructor
    public UsuarioBase(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }


    public boolean autenticar(String username, String password)
    {
        if (username.equals(this.username) && password.equals(this.password)) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Usuario: " + username + " (" + rol + ")"+ " | Password: " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UsuarioBase that)) return false;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("username", username);
        json.put("password", password);
        json.put("rol", rol.getRol());

        return json;
    }

    public UsuarioBase(JSONObject obj) {
        username = obj.getString("username");
        password = obj.getString("password");
        rol = Rol.valueOf(obj.getString("rol").toUpperCase());
    }
}
