package Enums;

public enum Rol
{
    ADMINISTRADOR("Administrador"),
    RECEPCIONISTA("Recepcionista"),;

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
