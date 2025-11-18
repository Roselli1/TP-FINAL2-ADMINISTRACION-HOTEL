package Modelo;

import Enums.Rol;

import java.util.Objects;

public abstract class UsuarioBase
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


    //Metodo Abstracto
    public abstract boolean autenticar(String username, String password);


    @Override
    public String toString() {
        return "Modelo.UsuarioBase{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
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
}
