package Enums;

public enum Rol
{
    ADMINISTRADOR("Modelo.Usuario.Administrador"),
    RECEPCIONISTA("Modelo.Usuario.Recepcionista"),;

    private String rol;

    Rol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
