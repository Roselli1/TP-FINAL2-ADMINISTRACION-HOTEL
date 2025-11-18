package Enums;

public enum EstadoHabitacion {
    DISPONIBLE("Disponible"),
    OCUPADA("Ocupada"),
    RESERVADA("Reservada"),
    MANTENIMIENTO("Mantenimiento"),
    LIMPIEZA("Limpieza");

    private final String estado;

    EstadoHabitacion(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
