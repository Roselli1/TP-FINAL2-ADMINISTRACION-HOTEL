package Enums;

public enum Rol
{
    ADMINISTRADOR("Modelo.Administrador"),
    RECEPCIONISTA("Modelo.Recepcionista"),;

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
