import Enums.Rol;
import Exceptions.DatosIncorrectosException;
import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Interfaces.iToJSON;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import Modelo.Persona.Administrador;
import Modelo.Persona.Pasajero;
import Modelo.Persona.Recepcionista;
import Modelo.Persona.UsuarioBase;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Iniciar el gestor de hotel (carga automatica de datos)
        GestorHotel hotel= new GestorHotel();
        Scanner scanner= new Scanner(System.in);
        int opcion= -1;

        //Si es la primera vez
        if (hotel.getUsuarios().isEmpty())
        {
            hotel.guardarTodosLosDatos();
        }


        System.out.println("--- BIENVENIDO AL SISTEMA DE GESTION HOTELERA ---");
        do
        {
            System.out.println("\n--------------------------------");
            System.out.println("      MENU PRINCIPAL    ");
            System.out.println("--------------------------------");
            System.out.println("1. Iniciar Sesion.");
            System.out.println("2. Registrarse como Pasajero.");
            System.out.println("3. Ver habitaciones disponibles.");
            System.out.println("0. Salir y Guardar.");
            System.out.print("Elija una opcion: ");

            try
            {
                opcion= Integer.parseInt(scanner.nextLine());

                switch (opcion)
                {
                    case 1:
                    {
                        iniciarSesion(scanner,hotel);
                        break;
                    }
                    case 2:
                    {
                        registrarPasajero(scanner,hotel);
                        break;
                    }
                    case 3:
                    {
                        hotel.mostrarHabitacionesDisponibles();
                        break;
                    }
                    case 0:
                    {
                        System.out.println("Guardando datos y cerrando sistema...");
                        hotel.guardarTodosLosDatos();
                        break;
                    }
                    default:
                    {
                        System.out.println("Opcion no valida.");
                    }
                }
            }catch (NumberFormatException e)
            {
                System.out.println("Ingrese un numero valido.");
            }catch (Exception e)
            {
                System.out.println("Error en el sistema: " + e.getMessage());
            }
        }while (opcion!=0);
    }





    /// METODOS

    // --- Metodo de Login ---
    private static void iniciarSesion(Scanner scanner, GestorHotel hotel){
        try {
            //Solicitar datos de inicio de sesion
            System.out.println("\n--- INICIO DE SESION ---");
            System.out.println("Username: ");
            String username= scanner.nextLine();
            System.out.println("Password: ");
            String password= scanner.nextLine();

            UsuarioBase usuarioLogueado = hotel.getUsuarios().get(username);

            if (usuarioLogueado != null && usuarioLogueado.autenticar(username,password))
            {
                System.out.println("Ingreso exitoso. Rol: "+ usuarioLogueado.getRol());

                //Si es administrador
                if (usuarioLogueado.getRol()== Rol.ADMINISTRADOR)
                {
                    Administrador admin = hotel.buscarAdministradorPorUsername(username);
                    menuAdministrador(scanner, hotel, admin);
                }
                //Si es recepcionista
                else if (usuarioLogueado.getRol()==Rol.RECEPCIONISTA)
                {
                    Recepcionista recep = hotel.buscarReceopcionistaPorUsername(username);
                    menuRecepcionista(scanner, hotel, recep);
                }
                //Si es pasajero
                else if (usuarioLogueado.getRol()==Rol.PASAJERO)
                {
                    Pasajero pasajero = hotel.buscarPasajeroPorUsername(username);
                    menuPasajero(scanner, hotel, pasajero);
                }
                else
                {
                    System.out.println("El usuario no tiene un rol asignado valido.");
                }
            }
            else
            {
                throw new DatosIncorrectosException("Usuario o contrasenia incorrectos.");
            }

        }catch (DatosIncorrectosException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Error en el sistema: " + e.getMessage());
        }
    }

    // --- Metodo de RegistrarPasajero ---
    private static void registrarPasajero(Scanner scanner, GestorHotel hotel) {
        try
        {
            System.out.println("--- REGISTRO DE PASAJERO ---");
            System.out.println("Nombre: ");
            String nombre= scanner.nextLine();

            System.out.println("Apellido: ");
            String apellido= scanner.nextLine();

            System.out.println("DNI: ");
            String dni= scanner.nextLine();

            System.out.println("Direccion: ");
            String direccion= scanner.nextLine();

            System.out.println("Origen");
            String origen= scanner.nextLine();

            System.out.println("Telefono: ");
            String telefono= scanner.nextLine();

            System.out.println("Correo: ");
            String correo= scanner.nextLine();

            System.out.println("Username: ");
            String username= scanner.nextLine();

            System.out.println("Password: ");
            String password= scanner.nextLine();

            //Creamos el usuario pasajero
            Pasajero nuevo= new Pasajero(nombre, apellido, dni, direccion, origen, telefono, correo, false, hotel, username, password);

            //Lo registramos en el hotel
            hotel.registrarPasajero(nuevo);
            System.out.println("Registro exitoso. Ahora puede iniciar sesion.");

        }catch (UsuarioYaExisteException e)
        {
            System.out.println(" El usuario ya existe.");
        }catch (Exception e)
        {
            System.out.println("Error al registrarse en el sistema: " + e.getMessage());
        }
    }

    // --- Metodo Realizar Reserva Interactivo ---
    /// FALTA PROBARLO
    private static void realizarReservaInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){
        try {
            System.out.println("\n--- CREAR RESERVA ---");

            // Obtiene la habitacion a reservar
            System.out.println("Ingrese el número de habitación deseado: ");
            int nroHabitacion = scanner.nextInt();
            Habitacion habitacion = hotel.getHabitaciones().get(nroHabitacion);

            if (habitacion == null) {
                System.out.println("Error: La habitación " + nroHabitacion + " no existe.");
                return;
            }

            // Busca al pasajero que quiere realizar la reserva
            System.out.println("Ingrese el username del pasajero para la reserva: ");
            String usernamePasajero = scanner.nextLine();
            Pasajero pasajero= hotel.buscarPasajeroPorUsername(usernamePasajero);

            if (pasajero == null){
                System.out.println("Error pasajero no encontrado.");
                return;
            }

            // Fechas de ingreso y egreso
            System.out.println("Ingrese la fecha de ingreso (YYYY-MM-DD): ");
            LocalDate fechaIngreso = LocalDate.parse(scanner.nextLine());
            System.out.println("Ingrese la fecha de egreso (YYYY-MM-DD): ");
            LocalDate fechaEgreso = LocalDate.parse(scanner.nextLine());

            // Validaciones de fechas
            if (fechaIngreso.isBefore(LocalDate.now()) || fechaEgreso.isBefore(fechaIngreso) || fechaIngreso.isEqual(fechaEgreso)) {
                System.out.println("Error: Las fechas de reserva son inválidas. Verifique que la fecha de ingreso sea futura y la de egreso posterior a la de ingreso.");
                return;
            }

            // Crea la reserva
            boolean check = false;
            Reserva nuevaReserva = new Reserva(habitacion, pasajero, fechaIngreso, fechaEgreso, true);
            check = recepcionista.crearReserva(nuevaReserva, nroHabitacion);

            if (check) System.out.println("Reserva creada con éxito.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Ingrese un número válido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al crear la reserva: " + e.getMessage());
            }
    }

    // --- Metodo Cancelar Reserva Interactivo ---
    /// FALTA PROBARLO
    private static void cancelarReservaInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){
        try {
            System.out.println("\n--- CANCELAR RESERVA ---");

            // Recibe el número de reserva a cancelar
            System.out.println("Ingrese el número de la reserva a cancelar: ");
            int nroReserva = scanner.nextInt();

            // Busca la reserva
            Reserva reservaACancelar = null;
            for (Reserva r : hotel.getReservas()) {
                if (r.getNroReserva() == nroReserva) {
                    reservaACancelar = r;
                    break;
                }
            }

            // Si la encuentra la cancela, sino muestra mensaje de error
            boolean check = false;
            if (reservaACancelar != null) {
                check = recepcionista.cancelarReserva(reservaACancelar);
            } else {
                System.out.println("Error: No se encontró una reserva activa con el número " + nroReserva + ".");
            }

            if (check) System.out.println("Reserva cancelada con éxito.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Ingrese un número válido para la reserva.");
        } catch (Exception e) {
            System.out.println("Error inesperado al cancelar la reserva: " + e.getMessage());
        }
    }

    // --- Metodo Ver Reservas Existentes ---
    /// FALTA PROBARLO
    private static void verReservasActivas(GestorHotel hotel){
        List<Reserva> reservas = hotel.getReservas();
        System.out.println("\n--- RESERVAS ACTIVAS ---");

        // Verifica si hay reservas
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas activas en el sistema.");
            return;
        }

        // Muestra las reservas
        for (Reserva r : reservas) {
            System.out.println(r.toString());
        }
    }

    // --- Check-In Interactivo ---
    /// FALTA PROBARLO
        private static void checkInInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){
        try {
            System.out.println("\n--- REALIZAR CHECK-IN ---");

            // Recibe el número de la reserva
            System.out.println("Ingrese el número de la reserva para Check-In: ");
            int nroReserva = scanner.nextInt();

            // Busca la reserva en el hotel
            Reserva reservaParaCheckIn = null;
            for (Reserva r : hotel.getReservas()) {
                if (r.getNroReserva() == nroReserva) {
                    reservaParaCheckIn = r;
                    break;
                }
            }

            // Si existe hace el check-in, sino muestra un mensaje de error
            boolean check = false;
            if (reservaParaCheckIn != null) {
                check = recepcionista.hacerCheckIn(reservaParaCheckIn);
            } else {
                System.out.println("Error: No se encontró una reserva activa con el número " + nroReserva + ".");
            }

            if (check) System.out.println("Check-In realizado con éxito.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Ingrese un número válido para la reserva.");
        } catch (Exception e) {
            System.out.println("Error inesperado durante el Check-In: " + e.getMessage());
        }
    }

    // --- Check-Out Interactivo ---
    /// FALTA EL CODIGO
    private static void checkOutInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){
        try
        {
            System.out.println("\n--- REALIZAR CHECK-OUT ---");
            System.out.println("Ingrese el número de habitación a liberar: ");
            int nroHabitacion = Integer.parseInt(scanner.nextLine());

            boolean checkOutExitoso= recepcionista.hacerCheckOut(null, nroHabitacion);

            if (checkOutExitoso)
            {
                System.out.println("Check-Out exitoso.");
            }
        } catch (NumberFormatException e)
        {
            System.out.println("Ingrese un número válido.");
        } catch (Exception e) {
            System.out.println("Error en Check-Out: " + e.getMessage());
        }
    }

    // ---Crear Usuario Staff ---
    /// FALTA PROBARLO
    private static void crearNuevoUsuarioStaff(Scanner scanner, GestorHotel hotel){
        try {
            System.out.println("\n--- CREAR NUEVO USUARIO STAFF ---");

            // Ingreso de datos de la persona
            System.out.println("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.println("DNI: ");
            String dni = scanner.nextLine();
            System.out.println("Dirección: ");
            String direccion = scanner.nextLine();
            System.out.println("Origen: ");
            String origen = scanner.nextLine();

            // Ingreso de username y contraseña
            System.out.println("Username: ");
            String username = scanner.nextLine();
            System.out.println("Password: ");
            String password = scanner.nextLine();

            // Ingreso del rol que cumple en el hotel
            System.out.println("Rol (ADMINISTRADOR/RECEPCIONISTA): ");
            String rolString = scanner.nextLine().toUpperCase();
            Rol rol;

            try {
                rol = Rol.valueOf(rolString);
                if (rol == Rol.PASAJERO) {
                    System.out.println("Error: No se puede crear un Pasajero desde este menú.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Rol no válido. Debe ser ADMINISTRADOR o RECEPCIONISTA.");
                return;
            }

            // Crea la instancia apropiada
            UsuarioBase creedenciales= new UsuarioBase(username, password, rol);
            hotel.agregarStaff(creedenciales, nombre, apellido, dni, direccion, origen);
            System.out.println("Usuario staff creado y guardado con exito.");

        } catch (UsuarioYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al crear usuario staff: " + e.getMessage());
        }
    }

    /// METODOS PASAJERO

    // --- Ver Historial ---
    private static void verHistorial(Pasajero pasajero)
    {
        System.out.println("\n--- HISTORIAL DE ESTADIAS ---");
        if (pasajero.getHistoriaHotel().isEmpty())
        {
            System.out.println("El pasajero no tiene estadias registradas.");
        } else
        {
            for (RegistroEstadia registroEstadia: pasajero.getHistoriaHotel() )
            {
                System.out.println(registroEstadia.toString());
            }
        }
    }

    // --- Realizar Reserva (Pasajero)
    private static void pasajeroRealizarReserva (Scanner scanner, GestorHotel hotel, Pasajero pasajero ) {
        try
        {
            System.out.println("\n--- SOLICITAR NUEVA RESERVA ---");

            //mostramos habitaciones disponibles
            hotel.mostrarHabitacionesDisponibles();

            System.out.println("Ingrese el numero de la habitacion que desea reservar: ");
            int nroHabitacion = Integer.parseInt(scanner.nextLine());

            Habitacion habitacion = hotel.getHabitaciones().get(nroHabitacion);
            if (habitacion == null)
            {
                System.out.println("Error: La habitacion " + nroHabitacion + " no existe.");
                return;
            }

            System.out.println("Ingrese la fecha de ingreso (YYYY-MM-DD): ");
            LocalDate fechaIngreso = LocalDate.parse(scanner.nextLine());
            System.out.println("Ingrese la fecha de egreso (YYYY-MM-DD): ");
            LocalDate fechaEgreso = LocalDate.parse(scanner.nextLine());

            if (fechaIngreso.isBefore(LocalDate.now()))
            {
                System.out.println("Error: La fecha ya paso.");
                return;
            }
            if (!fechaEgreso.isAfter(fechaIngreso))
            {
                System.out.println("Error: La fecha de egreso debe ser posterior a la de ingreso.");
                return;
            }

            //Creamos la reserva
            Reserva nuevaReserva = new Reserva(habitacion, pasajero, fechaIngreso, fechaEgreso, true);

            boolean exito= pasajero.crearReserva(nuevaReserva, nroHabitacion);

            if (exito)
            {
                //Ya se imprime directo del metodo crearReserva
            } else
            {
                System.out.println("Error: No se pudo crear la reserva.");
            }

        }catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e)
            {
            System.out.println("Error inesperado al crear la reserva: " + e.getMessage());
            }
    }

    // --- Cancelar Reserva (Pasajero)
    private static void pasajeroCancelarReserva(Scanner scanner, GestorHotel hotel, Pasajero pasajero){

        System.out.println("\n--- MIS RESERVAS ACTIVAS ---");

        List<Reserva> misReservas= hotel.buscarReservasPorPasajero(pasajero);

        if (misReservas.isEmpty())
        {
            System.out.println("No hay reservas activas para este pasajero.");
            return;
        }

        for (int i=0; i<misReservas.size(); i++)
        {
            System.out.println( (i) + ". " + misReservas.get(i).toString());
        }

        System.out.println("Ingrese el numero de la reserva a cancelar: ");
        try
        {
            int nroReserva = Integer.parseInt(scanner.nextLine());
            if (nroReserva>=0 && nroReserva<misReservas.size())
            {
                Reserva reservaACancelar = misReservas.get(nroReserva);

                boolean exitoCancelar= pasajero.cancelarReserva(reservaACancelar);

                if (exitoCancelar)
                {
                    System.out.println("Reserva cancelada correctamente.");
                }
                else
                {
                    System.out.println("Indice invalido.");
                }
            }

        }catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Error inesperado al cancelar la reserva: " + e.getMessage());
        }
    }

    // --- CheckIn (Pasajero)
    private static void pasajeroCheckIn (Scanner scanner, GestorHotel hotel, Pasajero pasajero)
    {
        System.out.println("\n--- REALIZAR CHECK-IN ---");

        List<Reserva> misReservas= hotel.buscarReservasPorPasajero(pasajero);

        if (misReservas.isEmpty())
        {
            System.out.println("No tiene reservas pendientes de ingresar.");
            return;
        }

        //Mostrar las reservas que si puede en caso de tener mas de una
        for (int i=0; i<misReservas.size(); i++)
        {
            System.out.println( (i) + ". " + misReservas.get(i).toString());
        }

        System.out.println("Ingrese el numero de la reserva para el Check-In: ");
        try
        {
            int indiceReserva = Integer.parseInt(scanner.nextLine());
            if (indiceReserva>=0 && indiceReserva<misReservas.size())
            {
                Reserva reservaParaCheckIn = misReservas.get(indiceReserva);
                pasajero.hacerCheckIn(reservaParaCheckIn);
            }
            else
            {
                System.out.println("Indice invalido.");
            }
        }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }catch (Exception e)
        {
            System.out.println("Error inesperado al realizar el Check-In: " + e.getMessage());
        }
    }

    // --- CheckOut (Pasajero)
    private static void pasajeroCheckOut(Scanner scanner, GestorHotel hotel, Pasajero pasajero) {
        try {
            System.out.println("\n--- REALIZAR CHECK-OUT ---");
            System.out.println("Ingrese el numero de su habitacion para salir: ");

            int nroHabitacion = Integer.parseInt(scanner.nextLine());

            boolean exitoCheckOut= pasajero.hacerCheckOut(null, nroHabitacion);

            if (exitoCheckOut)
            {
                System.out.println("Check-Out exitoso.");
            }
        } catch (NumberFormatException e)
        {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Error en Check-Out: " + e.getMessage());
        }
    }

    //




    // --- Menu Pasajero --- (Solo puede ver, nada mas)
    /// FALTA TERMINAR
    private static void menuPasajero(Scanner scanner, GestorHotel hotel, Pasajero pasajero) {
        int opcion=-1;

        do
        {

            System.out.println("\n--- MENU PASAJERO ---");
            System.out.println("1. Mis Reservas/ Historial.");
            System.out.println("2. Solicitar Nueva Reserva.");
            System.out.println("3. Cancelar Reserva.");
            System.out.println("4. Realizar Check-In(Ingreso).");
            System.out.println("5. Realizar Check-Out(Salida).");
            System.out.println("6. Ver habitaciones disponibles.");
            System.out.println("0. Cerrar Sesion.");
            System.out.print("Elija una opcion: ");

            try
            {
                opcion= Integer.parseInt(scanner.nextLine());

                switch (opcion)
                {
                    case 1:
                    {
                        verHistorial(pasajero);
                        break;
                    }
                    case 2:
                    {
                        pasajeroRealizarReserva(scanner, hotel, pasajero);
                        break;
                    }
                    case 3:
                    {
                        pasajeroCancelarReserva(scanner, hotel, pasajero);
                        break;
                    }
                    case 4:
                    {
                        pasajeroCheckIn(scanner, hotel, pasajero);
                        break;
                    }
                    case 5:
                    {
                        pasajeroCheckOut(scanner, hotel, pasajero);
                        break;
                    }
                    case 6:
                    {
                        hotel.mostrarHabitacionesDisponibles();
                        break;
                    }
                    case 0:
                    {
                        System.out.println("Cerrando sesion...");
                        break;
                    }
                }

            } catch (Exception e)
            {
                System.out.println("Error en el sistema: " + e.getMessage());
            }

        }while (opcion!=0);
    }

    // --- Menu Administrador ---
    private static void menuAdministrador(Scanner scanner, GestorHotel hotel, Administrador admin) {
        int opcion=-1;

        do
        {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Realizar Backup del Sistema.");
            System.out.println("2. Crear Usuario Staff (Admin/Recep).");
            System.out.println("3. Ver Lista de Habitaciones.");
            System.out.println("0. Cerrar Sesion.");
            System.out.print("Elija una opcion: ");

            try
            {
                opcion= Integer.parseInt(scanner.nextLine()); //Limpia el buffer

                switch (opcion)
                {
                    case 1:
                    {
                        hotel.guardarTodosLosDatos(); //El admin lllama al backup
                        break;
                    }
                    case 2:
                    {
                        crearNuevoUsuarioStaff(scanner,hotel);
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Listado de habitaciones:");
                        hotel.listarHabitaciones();
                        break;
                    }
                    case 0:
                    {
                        System.out.println("Cerrando sesion...");
                        break;
                    }
                    default:
                    {
                        System.out.println("Opcion no valida.");
                    }
                }
            }catch (IllegalArgumentException e)
            {
                System.out.println("Ingrese un numero valido.");
            }catch (Exception e)
            {
                System.out.println("Error en el sistema: " + e.getMessage());
            }

        }while (opcion!=0);

    }

    // --- Menu Recepcionista ---
    /// FALTA TERMINAR
    private static void menuRecepcionista(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista) {
        int opcion=-1;


        do
        {
            System.out.println("\n--- MENU RECEPCIONISTA ---");
            System.out.println("1. Ver Habitaciones Disponibles.");
            System.out.println("2. Crear Reserva.");
            System.out.println("3. Cancelar Reserva.");
            System.out.println("4. Ver Reservas Activas.");
            System.out.println("5. Realizar Check-In(Ingreso).");
            System.out.println("6. Realizar Check-Out(Salida).");
            System.out.println("0. Cerrar Sesion.");

            try
            {
                opcion= Integer.parseInt(scanner.nextLine());

                switch (opcion)
                {
                    case 1:
                    {
                        hotel.mostrarHabitacionesDisponibles();
                        break;
                    }
                    case 2:
                    {
                        realizarReservaInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 3:
                    {
                        cancelarReservaInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 4:
                    {
                        verReservasActivas(hotel);
                        break;
                    }
                    case 5:
                    {
                        checkInInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 6:
                    {
                        checkOutInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 0:
                        System.out.println("Cerrando sesion...");
                }
            }catch (Exception e)
            {
                System.out.println("Error en el sistema: " + e.getMessage());
            }

        }while (opcion!=0);
    }

}