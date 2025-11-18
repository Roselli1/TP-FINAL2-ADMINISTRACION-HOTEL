package Enums;

public enum TipoHabitacion
{
    SIMPLE("Simple"),
    DOBLE("Doble"),
    SUITE("Suite");

    private String tipoHabitacion;

    TipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
}
