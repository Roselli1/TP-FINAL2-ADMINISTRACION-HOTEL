import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.ReservaInvalidaException;
import Gestores.GestorHotel;
import Gestores.GestoraHabitaciones;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.Reserva;
import Modelo.Persona.Pasajero;
import Modelo.Persona.Recepcionista;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args)
    {
        GestorHotel hotel = new GestorHotel();
        GestoraHabitaciones gestoraHabitaciones = new GestoraHabitaciones("habitaciones");
        String ARCHIVO_HABITACIONES = "habitacionesData";

        // Habitaciones
        System.out.println("--- Creando Habitaciones ---");
        Habitacion h101 = new Habitacion(101, TipoHabitacion.SIMPLE, 5000.0, EstadoHabitacion.DISPONIBLE);
        Habitacion h205 = new Habitacion(205, TipoHabitacion.SUITE, 15000.0, EstadoHabitacion.DISPONIBLE);
        Habitacion h301 = new Habitacion(301, TipoHabitacion.DOBLE, 8000.0, EstadoHabitacion.MANTENIMIENTO);

        // Agregar al gestor de habitaciones y al GestorHotel para manejar el estado
        gestoraHabitaciones.agregarElemento(h101);
        hotel.getHabitaciones().put(h101.getNumero(), h101);

        gestoraHabitaciones.agregarElemento(h205);
        hotel.getHabitaciones().put(h205.getNumero(), h205);

        gestoraHabitaciones.agregarElemento(h301);
        hotel.getHabitaciones().put(h301.getNumero(), h301);
        System.out.println("Habitaciones creadas y agregadas.");

        // Pasajero
        Pasajero pasajero1 = new Pasajero("Juan", "Perez", "12345678", "Calle Falsa 123", "Argentina", "1122334455", "juan@mail.com", false);

        // Recepcionista (Necesita el GestorHotel)
        Recepcionista recepcionista = new Recepcionista("Ana", "Gomez", "98765432", "Domicilio Rec", "Chile", hotel);

        System.out.println("\n--- PRUEBA DE GUARDADO ---");
        gestoraHabitaciones.guardar(ARCHIVO_HABITACIONES);

        System.out.println("\n--- PRUEBA DE RESERVA Y CHECK-IN ---");
        LocalDate ingreso = LocalDate.now().plusDays(5);
        LocalDate egreso = LocalDate.now().plusDays(10);

        Reserva r101 = new Reserva(h101, pasajero1, ingreso, egreso, true);

        try {
            // Intentar reservar la habitación 101
            hotel.realizarReserva(r101, h101.getNumero());
            System.out.println("Reserva de H101 exitosa. Estado actual: " + h101.getEstadoHabitacion().getEstado());
            // Se debe cambiar el estado de la habitacion
            h101.setEstadoHabitacion(EstadoHabitacion.RESERVADA);

            System.out.println("Habitación 101 después de reservar: " + h101);

        } catch (HabitacionNoDisponibleException | ReservaInvalidaException e) {
            System.err.println("Error al reservar: " + e.getMessage());
        }


        // Prueba de Check-In (H101)
        System.out.println("\n--- PRUEBA DE CHECK-IN ---");
        boolean checkInExitoso = recepcionista.hacerCheckIn(r101);

        if (checkInExitoso) {
            System.out.println("Check-In de H101 realizado con éxito.");
            System.out.println("Habitación 101 después de Check-In: " + h101);
            System.out.println("Historial de " + pasajero1.getNombre() + ": " + pasajero1.getHistoriaHotel());
            System.out.println("Estadías activas en Hotel: " + hotel.getRegistrosEstadias().size());

            // Buscar estadía activa
            System.out.println("Estadia activa encontrada: " + hotel.buscarEstadiaActiva(h101.getNumero()));
        } else {
            System.err.println("Fallo el Check-In de H101.");
        }

        // Prueba de Check-Out (H101)
        System.out.println("\n--- PRUEBA DE CHECK-OUT ---");
        boolean checkOutExitoso = recepcionista.hacerCheckOut(r101, h101.getNumero());

        if (checkOutExitoso) {
            System.out.println("Check-Out de H101 realizado con éxito.");
            System.out.println("Habitación 101 después de Check-Out: " + h101);
            System.out.println("Estadía activa encontrada (Debe ser null): " + hotel.buscarEstadiaActiva(h101.getNumero()));

        } else {
            System.err.println("Fallo el Check-Out de H101.");
        }

        // Esto cargará las habitaciones y mostrará el estado después de la última carga.
        System.out.println("\n--- PRUEBA DE CARGA ---");
        gestoraHabitaciones.cargarHabitacion(ARCHIVO_HABITACIONES);
        System.out.println("Habitaciones cargadas desde JSON. Total: " + gestoraHabitaciones.getElementos().size());

        // La H101 debería aparecer con su estado final (DISPONIBLE) si el constructor JSON está corregido
        System.out.println("H101 después de recargar: " + gestoraHabitaciones.getElementos().get(0));

    }
}