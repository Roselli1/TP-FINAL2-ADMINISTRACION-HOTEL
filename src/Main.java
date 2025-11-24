import Enums.EstadoHabitacion;
import Enums.Rol;
import Enums.TipoHabitacion;
import Exceptions.DatosIncorrectosException;
import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Interfaces.iToJSON;
import Modelo.Hotel.Habitacion;
import Modelo.Hotel.RegistroEstadia;
import Modelo.Hotel.Reserva;
import Modelo.Persona.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Iniciar el gestor de hotel (carga automática de datos)
        GestorHotel hotel= new GestorHotel();
        Scanner scanner= new Scanner(System.in);
        int opcion= -1;

        //Si es la primera vez
        if (hotel.getUsuarios().isEmpty())
        {
            inicializarDatos(hotel);
            hotel.guardarTodosLosDatos();
        }


        System.out.println("\n\n\n--- BIENVENIDO AL SISTEMA DE GESTION HOTELERA ---");
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
                    if (pasajero !=null)
                    {
                        pasajero.setHotel(hotel);
                    }
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
            System.out.println("Error en el sistema. ");
        }
    }

    // --- Metodo de RegistrarPasajero ---
    private static void registrarPasajero(Scanner scanner, GestorHotel hotel) {
        boolean error;

        try
        {
            System.out.println("--- REGISTRO DE PASAJERO ---");
            String nombre;
            do {
                System.out.println("Nombre: ");
                nombre = scanner.nextLine().toLowerCase().trim();

                if (nombre.isEmpty())
                    {
                        System.out.println("No puede estar vacio");
                        error = true;
                    }
                else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
                    {
                        System.out.println("Solo se permiten letras.");
                        error = true;
                    }
                else
                    {
                        error = false;
                    }
                }while (error);


            String apellido;
            do {
                    System.out.println("Apellido: ");
                    apellido = scanner.nextLine().toLowerCase().trim();

                if (apellido.isEmpty())
                {
                    System.out.println("No puede estar vacio");
                    error = true;
                }
                else if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
                {
                    System.out.println("Solo se permiten letras.");
                    error = true;
                }
                else
                {
                    error = false;
                }
                }while (error);




            String dni;
            do {
                System.out.println("DNI: ");
                dni = scanner.nextLine().trim(); //por si escribe algun espacio sin querer
                if(dni.isEmpty())
                {
                    System.out.println("No puede estar vacio.");
                    error = true;

                }else if (!dni.matches("\\d+")){  // "\\d+" significa "uno o más dígitos"
                    error = true;
                    System.out.println("Solo puede tener números.");
                }else {
                    error = false;
                }
            } while (error);


            String direccion;
            do
            {
                System.out.println("Direccion: ");
                 direccion = scanner.nextLine().toLowerCase().trim();
                 if (direccion.isEmpty())
                 {
                     System.out.println("No puede estar vacio.");
                     error = true;
                 }else
                 {
                     error = false;
                 }
            }while (error);

            String origen;
            do {


                System.out.println("Origen");
                 origen = scanner.nextLine().toLowerCase().trim();
                 if (origen.isEmpty())
                 {
                     System.out.println("No puede estar vacio.");
                     error = true;
                 } else
                 {
                     error = false;
                 }
            }while (error);


            String telefono;
            do {
                System.out.println("Telefono: ");
                 telefono = scanner.nextLine().toLowerCase().trim();

                 if (telefono.isEmpty())
                 {
                     System.out.println("No puede estar vacio.");
                     error = true;

                 } else if (!telefono.matches("\\d+"))
                 {
                     System.out.println("Solo puede tener numeros");
                     error = true;
                 }
                 else
                 {
                     error = false;
                 }
                }while (error);

            String correo;
            do {

                System.out.println("Correo: ");
                correo = scanner.nextLine().toLowerCase().trim();
                if (correo.isEmpty())
                {
                    System.out.println("No puede estar vacio.");
                    error = true;
                }else if (correo.contains(" ")) {
                    System.out.println("El correo no puede contener espacios.");
                    error = true;
                }
                else
                {
                    error = false;
                }
            }while (error);



            String username;
            do {
                System.out.println("Username: ");
                username= scanner.nextLine().trim().toLowerCase();

                if (username.isEmpty()) {
                    System.out.println("Error: No puede estar vacío.");
                    error = true;
                }
                else if (!username.matches("^[a-zA-Z0-9ñÑ]+$"))
                {
                    System.out.println("Error: Solo se permiten letras y números (sin espacios).");
                    error = true;
                }else
                {
                    error = false;
                }
            } while (error);


            String password;
            do {
                System.out.println("Password: ");
                password= scanner.nextLine();

                if (password.isEmpty()) {
                    System.out.println("Error: La contraseña no puede estar vacía.");
                    error = true;
                } else if (password.contains(" ")){
                    error = true;
                    System.out.println("Error: la contraseña no puede contener espacios.");
                }else
                {
                    error = false;
                }
            }while (error);

            //Creamos el usuario pasajero
            Pasajero nuevo= new Pasajero(nombre, apellido, dni, direccion, origen, telefono, correo, false, hotel, username, password);

            //Lo registramos en el hotel
            hotel.registrarPasajero(nuevo);
            System.out.println("Registro exitoso. Ahora puede iniciar sesion.");
            System.out.println("Su nombre de usuario es:" + username + " y su contrasenia: "+ password);

        }catch (UsuarioYaExisteException e)
        {
            System.out.println(" El usuario ya existe.");
        }catch (Exception e)
        {
            System.out.println("Error al registrarse en el sistema. ");
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
    /// FALTA PROBARLO
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
        } catch (IllegalArgumentException e)
        {
            System.out.println("Ingrese un número válido.");
        } catch (Exception e) {
            System.out.println("Error en Check-Out: " + e.getMessage());
        }
    }

    // ---Crear Usuario Staff ---
    private static void crearNuevoUsuarioStaff(Scanner scanner, GestorHotel hotel){
        boolean error;

        try {
            System.out.println("\n--- CREAR NUEVO USUARIO STAFF ---");

            // Ingreso de datos de la persona
            String nombre;
            do {
                System.out.println("Nombre: ");
                nombre = scanner.nextLine().toLowerCase().trim();

                if (nombre.isEmpty())
                {
                    System.out.println("No puede estar vacio");
                    error = true;
                }
                else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
                {
                    System.out.println("Solo se permiten letras.");
                    error = true;
                }
                else
                {
                    error = false;
                }
            }while (error);


            String apellido;
            do {
                System.out.println("Apellido: ");
                apellido = scanner.nextLine().toLowerCase().trim();

                if (apellido.isEmpty())
                {
                    System.out.println("No puede estar vacio");
                    error = true;
                }
                else if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"))
                {
                    System.out.println("Solo se permiten letras.");
                    error = true;
                }
                else
                {
                    error = false;
                }
            }while (error);




            String dni;
            do {
                System.out.println("DNI: ");
                dni = scanner.nextLine().trim(); //por si escribe algun espacio sin querer
                if(dni.isEmpty())
                {
                    System.out.println("No puede estar vacio.");
                    error = true;

                }else if (!dni.matches("\\d+")){  // "\\d+" significa "uno o más dígitos"
                    error = true;
                    System.out.println("Solo puede tener números.");
                }else {
                    error = false;
                }
            } while (error);


            String direccion;
            do
            {
                System.out.println("Direccion: ");
                direccion = scanner.nextLine().toLowerCase().trim();
                if (direccion.isEmpty())
                {
                    System.out.println("No puede estar vacio.");
                    error = true;
                }else
                {
                    error = false;
                }
            }while (error);

            String origen;
            do {


                System.out.println("Origen");
                origen = scanner.nextLine().toLowerCase().trim();
                if (origen.isEmpty())
                {
                    System.out.println("No puede estar vacio.");
                    error = true;
                } else
                {
                    error = false;
                }
            }while (error);


            String username;
            do {
                System.out.println("Username: ");
                username= scanner.nextLine().trim().toLowerCase();

                if (username.isEmpty()) {
                    System.out.println("Error: No puede estar vacío.");
                    error = true;
                }
                else if (!username.matches("^[a-zA-Z0-9ñÑ]+$"))
                {
                    System.out.println("Error: Solo se permiten letras y números (sin espacios).");
                    error = true;
                }else
                {
                    error = false;
                }
            } while (error);


            String password;
            do {
                System.out.println("Password: ");
                password= scanner.nextLine();

                if (password.isEmpty()) {
                    System.out.println("Error: La contraseña no puede estar vacía.");
                    error = true;
                } else if (password.contains(" ")){
                    error = true;
                    System.out.println("Error: la contraseña no puede contener espacios.");
                }else
                {
                    error = false;
                }
            }while (error);

            Rol rol = null;

            do {

                // Ingreso del rol que cumple en el hotel
                    System.out.println("Seleccione el Rol:");
                    System.out.println("1. ADMINISTRADOR");
                    System.out.println("2. RECEPCIONISTA");
                    System.out.print("Opción: ");

                    try {
                        String input = scanner.nextLine();
                        int opcionRol = Integer.parseInt(input);

                        switch (opcionRol) {
                            case 1:
                                rol = Rol.ADMINISTRADOR;
                                break;
                            case 2:
                                rol = Rol.RECEPCIONISTA;
                                break;
                            default:
                                System.out.println("Opción inválida. Ingrese 1 o 2.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Debe ingresar un número.");
                    }
                }while (rol == null);

            // Crea la instancia apropiada
            UsuarioBase creedenciales= new UsuarioBase(username, password, rol);
            hotel.agregarStaff(creedenciales, nombre, apellido, dni, direccion, origen);
            System.out.println("Usuario staff creado y guardado con exito.");
            System.out.println("Su nombre de usuario es:" + username + " y su contrasenia: "+ password);

        } catch (UsuarioYaExisteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al crear usuario staff: " + e.getMessage());
        }
    }

    /// METODOS PASAJERO

    // --- Ver Historial ---
    private static void verHistorial(Pasajero pasajero) {
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
        boolean error= false;
        LocalDate fechaIngreso= null;
        LocalDate fechaEgreso= null;
        int nroHabitacion= 0;

        try
        {
            System.out.println("\n--- SOLICITAR NUEVA RESERVA ---");

            //mostramos habitaciones disponibles
            hotel.mostrarHabitacionesDisponibles();

            do {
                try {


                    System.out.println("Ingrese el numero de la habitacion que desea reservar: ");
                    nroHabitacion = Integer.parseInt(scanner.nextLine().trim());


                    Habitacion habitacion = hotel.getHabitaciones().get(nroHabitacion);
                    if (habitacion == null) {
                        System.out.println("Error: La habitacion " + nroHabitacion + " no existe.");
                        error = true;
                    } else {
                        error = false;
                    }
                }catch (NumberFormatException e)
                {
                    System.out.println("Error: Debe ingresar un numero valido.");
                    error = true;
                }

            }while (error);




            do
            {
                try {
                    System.out.println("Ingrese la fecha de ingreso (YYYY-MM-DD): ");
                    fechaIngreso = LocalDate.parse(scanner.nextLine().trim());
                    if (fechaIngreso.isBefore(LocalDate.now()))
                    {
                        System.out.println("Error: La fecha ya paso.");
                        error = true;
                    }
                    else
                    {
                        error = false;
                    }
                }catch (Exception e)
                {
                    System.out.println("Error de formato.");
                    error = true;
                }
            }while (error);



            do {
                try {
                    System.out.println("Ingrese la fecha de egreso (YYYY-MM-DD): ");
                    fechaEgreso = LocalDate.parse(scanner.nextLine().trim());

                    if (fechaEgreso.isBefore(LocalDate.now()))
                    {
                        System.out.println("Error: La fecha ya paso.");
                        error = true;
                    } else if (!fechaEgreso.isAfter(fechaIngreso))
                    {
                        System.out.println("Error: La fecha de egreso debe ser posterior a la de ingreso.");
                        error = true;
                    }
                    else
                    {
                        error = false;
                    }
                }catch (Exception e)
                {
                    System.out.println("Error de formato.");
                    error = true;
                }
            }while (error);

            //Creamos la reserva
            Habitacion habitacion = hotel.getHabitaciones().get(nroHabitacion);
            Reserva nuevaReserva = new Reserva(habitacion, pasajero, fechaIngreso, fechaEgreso, true);

            boolean exito= pasajero.crearReserva(nuevaReserva, nroHabitacion);

            if (!exito)
            {
                System.out.println("Error: No se pudo crear la reserva.");
            }

        } catch (Exception e)
            {
            System.out.println("Error inesperado al crear la reserva. ");
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
        try {
            int nroReserva = Integer.parseInt(scanner.nextLine());
            if (nroReserva >= 0 && nroReserva < misReservas.size()) {
                Reserva reservaACancelar = misReservas.get(nroReserva);

                boolean exitoCancelar = pasajero.cancelarReserva(reservaACancelar);

                if (exitoCancelar) {
                    System.out.println("Reserva cancelada correctamente.");
                } else {
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
    private static void pasajeroCheckIn (Scanner scanner, GestorHotel hotel, Pasajero pasajero) {
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
        } catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Error en Check-Out: " + e.getMessage());
        }
    }

    // --- Iniciar Datos ---
    private static void inicializarDatos(GestorHotel hotel) {
        try
        {
            // HABITACIONES (30 habitaciones)
            //Piso 1: Simples
            for (int i=101; i<=110;i++)
            {
                hotel.agregarHabitacion(new Habitacion(i, TipoHabitacion.SIMPLE, 5000, EstadoHabitacion.DISPONIBLE));
            }

            //Piso 2: Doble
            for (int i=201; i<=210; i++)
            {
                hotel.agregarHabitacion(new Habitacion(i, TipoHabitacion.DOBLE, 9000, EstadoHabitacion.DISPONIBLE));
            }

            //Piso 3: Suite
            for (int i=301; i<=310; i++)
            {
                hotel.agregarHabitacion(new Habitacion(i, TipoHabitacion.SUITE, 15000, EstadoHabitacion.DISPONIBLE));
            }

            // --- ADMINISTRADORES ---
            hotel.agregarStaff(new UsuarioBase("admin1", "admin1", Rol.ADMINISTRADOR), "Tomas", "Roselli", "47472036", "Av. Corrientes 1234", "Argentina");
            hotel.agregarStaff(new UsuarioBase("admin2", "admin2", Rol.ADMINISTRADOR), "Juan", "Martinez", "38456721", "Calle San Martin 567", "Argentina");
            hotel.agregarStaff(new UsuarioBase("admin3", "admin3", Rol.ADMINISTRADOR), "Sofia", "Gonzalez", "42183945", "Boulevard Kennedy 890", "Chile");
            hotel.agregarStaff(new UsuarioBase("admin4", "admin4", Rol.ADMINISTRADOR), "Maria", "Aranda", "35276489", "Pasaje Libertad 2345", "Argentina");
            hotel.agregarStaff(new UsuarioBase("admin5", "admin5", Rol.ADMINISTRADOR), "Nicolas", "Ferreyra", "40912573", "Ruta Nacional 456", "Paraguay");
            hotel.agregarStaff(new UsuarioBase("admin6", "admin6", Rol.ADMINISTRADOR), "Claudia", "Lopez", "33658142", "Diagonal Norte 678", "Argentina");
            hotel.agregarStaff(new UsuarioBase("admin7", "admin7", Rol.ADMINISTRADOR), "Carlos", "Benitez", "39847256", "Av. Rivadavia 3210", "Uruguay");
            hotel.agregarStaff(new UsuarioBase("admin8", "admin8", Rol.ADMINISTRADOR), "Lucia", "Mastromarino", "44521398", "Calle Florida 1567", "Brasil");
            hotel.agregarStaff(new UsuarioBase("admin9", "admin9", Rol.ADMINISTRADOR), "Miguel", "Desimone", "36704825", "Av. 9 de Julio 4321", "Argentina");
            hotel.agregarStaff(new UsuarioBase("admin10", "admin10", Rol.ADMINISTRADOR), "Patricia", "Campos", "41285674", "Calle Lavalle 2890", "México");

            // --- RECEPCIONISTAS ---
            hotel.agregarStaff(new UsuarioBase("recep1", "recep1", Rol.RECEPCIONISTA), "Andrea", "Molina", "29384756", "Calle Belgrano 1523", "Argentina");
            hotel.agregarStaff(new UsuarioBase("recep2", "recep2", Rol.RECEPCIONISTA), "Fernando", "Ramirez", "31567892", "Av. Santa Fe 2987", "Colombia");
            hotel.agregarStaff(new UsuarioBase("recep3", "recep3", Rol.RECEPCIONISTA), "Valentina", "Suarez", "43729158", "Calle Mitre 765", "Argentina");
            hotel.agregarStaff(new UsuarioBase("recep4", "recep4", Rol.RECEPCIONISTA), "Diego", "Peralta", "37891245", "Paseo Colon 4532", "Perú");
            hotel.agregarStaff(new UsuarioBase("recep5", "recep5", Rol.RECEPCIONISTA), "Romina", "Castro", "45218973", "Av. Independencia 3456", "Argentina");
            hotel.agregarStaff(new UsuarioBase("recep6", "recep6", Rol.RECEPCIONISTA), "Gabriel", "Torres", "32654987", "Calle Sarmiento 1890", "Ecuador");
            hotel.agregarStaff(new UsuarioBase("recep7", "recep7", Rol.RECEPCIONISTA), "Florencia", "Vega", "40573281", "Av. Callao 2134", "Argentina");
            hotel.agregarStaff(new UsuarioBase("recep8", "recep8", Rol.RECEPCIONISTA), "Matias", "Romero", "34912658", "Calle Tucuman 987", "Bolivia");
            hotel.agregarStaff(new UsuarioBase("recep9", "recep9", Rol.RECEPCIONISTA), "Camila", "Acosta", "46837512", "Av. Pueyrredon 5678", "Argentina");
            hotel.agregarStaff(new UsuarioBase("recep10", "recep10", Rol.RECEPCIONISTA), "Sebastian", "Morales", "39165824", "Calle Cordoba 3721", "Venezuela");

            // --- PASAJEROS ---
            hotel.registrarPasajero(new Pasajero("Roberto", "Silva", "28745913", "Calle Alsina 1245", "Argentina", "1145678234", "roberto.silva@email.com", false, hotel, "pasajero1", "pasajero1"));
            hotel.registrarPasajero(new Pasajero("Ana", "Mendoza", "33926481", "Av. Jujuy 3456", "España", "1156329847", "ana.mendoza@email.com", false, hotel, "pasajero2", "pasajero2"));
            hotel.registrarPasajero(new Pasajero("Lucas", "Ortiz", "41583726", "Calle Chacabuco 876", "Argentina", "1147852369", "lucas.ortiz@email.com", false, hotel, "pasajero3", "pasajero3"));
            hotel.registrarPasajero(new Pasajero("Martina", "Ruiz", "36214895", "Av. Entre Rios 2341", "Uruguay", "1159638412", "martina.ruiz@email.com", false, hotel, "pasajero4", "pasajero4"));
            hotel.registrarPasajero(new Pasajero("Ezequiel", "Diaz", "44657182", "Calle Defensa 1567", "Argentina", "1143217856", "ezequiel.diaz@email.com", false, hotel, "pasajero5", "pasajero5"));
            hotel.registrarPasajero(new Pasajero("Julia", "Navarro", "30492817", "Av. Boedo 4523", "Chile", "1152847196", "julia.navarro@email.com", false, hotel, "pasajero6", "pasajero6"));
            hotel.registrarPasajero(new Pasajero("Facundo", "Herrera", "42918573", "Calle Piedras 2198", "Argentina", "1148529637", "facundo.herrera@email.com", false, hotel, "pasajero7", "pasajero7"));
            hotel.registrarPasajero(new Pasajero("Gabriela", "Jimenez", "35847291", "Av. Caseros 3267", "Brasil", "1157418293", "gabriela.jimenez@email.com", false, hotel, "pasajero8", "pasajero8"));
            hotel.registrarPasajero(new Pasajero("Ignacio", "Blanco", "39512748", "Calle Balcarce 1789", "Paraguay", "1146293581", "ignacio.blanco@email.com", false, hotel, "pasajero9", "pasajero9"));
            hotel.registrarPasajero(new Pasajero("Victoria", "Rojas", "43786152", "Av. San Juan 4012", "Argentina", "1154926783", "victoria.rojas@email.com", false, hotel, "pasajero10", "pasajero10"));





        } catch (Exception e)
        {
            System.out.println("Error al inicializar los datos: " + e.getMessage());
        }

        /// FALTA CREAR 10 ADMIN, 10 RECEPCIONISTAS Y 10 PASAJEROS
        /// como minimo si son mas mejor
    }




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