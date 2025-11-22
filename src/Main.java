import Enums.Rol;
import Exceptions.DatosIncorrectosException;
import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Interfaces.iToJSON;
import Modelo.Hotel.Habitacion;
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
                    menuAdministrador(scanner,hotel,usuarioLogueado);
                }
                //Si es recepcionista
                else if (usuarioLogueado.getRol()==Rol.RECEPCIONISTA)
                {
                    menuRecepcionista(scanner,hotel,usuarioLogueado);
                }
                //Si es pasajero
                else if (usuarioLogueado.getRol()==Rol.PASAJERO)
                {
                    menuPasajero(hotel);
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
    /// HABRIA QUE VERIFICAR SI ESTÁ BIEN LO DE LA LINEA DE CODIGO 206 Y 207
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
            UsuarioBase usuario = hotel.getUsuarios().get(usernamePasajero);

            if (usuario == null || !(usuario.getRol() == Rol.PASAJERO)) {
                System.out.println("Error: Pasajero con username '" + usernamePasajero + "' no encontrado o no es un pasajero.");
                System.out.println("Por favor, regístrelo en el Menú Principal si es necesario.");
                return;
            }
            iToJSON p = usuario;
            Pasajero pasajero = (Pasajero) p;

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
    private static void checkOutInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){}

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
            iToJSON nuevoStaff = null;
            if (rol == Rol.ADMINISTRADOR) {
                nuevoStaff = new Administrador(nombre, apellido, dni, direccion, origen, hotel, username, password);
            } else if (rol == Rol.RECEPCIONISTA) {
                nuevoStaff = new Recepcionista(nombre, apellido, dni, direccion, origen, hotel, username, password);
            }

            // Se agrega al hotel
            if (nuevoStaff != null) {
                UsuarioBase nuevo = (UsuarioBase) nuevoStaff;
                hotel.agregarUsuario(nuevo);
                System.out.println("Usuario staff '" + username + "' con rol " + rol.getRol() + " creado con éxito.");
            }

        } catch (UsuarioYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al crear usuario staff: " + e.getMessage());
        }
    }




    /// FALTA TODA LA PARTE DE MENUPASAJERO




    // --- Menu Pasajero --- (Solo puede ver, nada mas)
    /// FALTA TERMINAR
    private static void menuPasajero(GestorHotel hotel) {
        System.out.println("\n--- MENU PASAJERO ---");
        System.out.println("1. Mis Reservas/ Historial.");
        System.out.println("2. Solicitar Nueva Reserva.");
        System.out.println("3. Cancelar Reserva.");
        System.out.println("4. Realizar Check-In(Ingreso).");
        System.out.println("5. Realizar Check-Out(Salida).");
        System.out.println("6. Ver habitaciones disponibles.");
        System.out.println("0. Cerrar Sesion.");
        System.out.print("Elija una opcion: ");

    }

    // --- Menu Administrador ---
    private static void menuAdministrador(Scanner scanner, GestorHotel hotel, UsuarioBase admin) {
        int opcion=-1;
        iToJSON a = admin;
        Administrador administrador = (Administrador) a;

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
                        /*System.out.println("Ingrese el nombre del usuario: ");
                        String nombreUsuario= scanner.nextLine();
                        System.out.println("Ingrese el rol del usuario: ");
                        String rolUsuario= scanner.nextLine();
                        System.out.println("Ingrese el password del usuario: ");
                        String passwordUsuario= scanner.nextLine();*/

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
    private static void menuRecepcionista(Scanner scanner, GestorHotel hotel, UsuarioBase recepcion) {
        int opcion=-1;
        iToJSON r = recepcion;
        Recepcionista recepcionista = (Recepcionista) r;

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
                        //checkOutInteractivo(scanner,hotel,recepcionista);
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