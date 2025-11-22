import Enums.Rol;
import Exceptions.DatosIncorrectosException;
import Exceptions.UsuarioYaExisteException;
import Gestores.GestorHotel;
import Modelo.Persona.Pasajero;
import Modelo.Persona.Recepcionista;
import Modelo.Persona.UsuarioBase;

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

            UsuarioBase usuarioLogueado= hotel.getUsuarios().get(username);

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
    /// FALTA EL CODIGO
    private static void realizarReservaInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){}

    // --- Metodo Cancelar Reserva Interactivo ---
    /// FALTA EL CODIGO
    private static void cancelarReservaInteractivo(Scanner scanner, GestorHotel hotel, Recepcionista recepcionista){}

    /// FALTA EL CODIGO
    private static void verReservasActivas(){}

    // --- Check-In Interactivo ---
    /// FALTA EL CODIGO
    private static void checkInInteractivo(Scanner scanner, GestorHotel hotel, UsuarioBase recepcionista){}

    // --- Check-Out Interactivo ---
    /// FALTA EL CODIGO
    private static void checkOutInteractivo(Scanner scanner, GestorHotel hotel, UsuarioBase recepcionista){}

    // ---Crear Usuario Staff ---
    /// FALTA EL CODIGO
    private static void crearNuevoUsuarioStaff(Scanner scanner, GestorHotel hotel){}




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
    /// FALTA CASE 2 (CREAR USUARIO STAFF)
    private static void menuAdministrador(Scanner scanner, GestorHotel hotel, UsuarioBase administrador) {
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
                        //crear metodo: crearNuevoUsuarioStaff(scanner,hotel);
                        System.out.println("Ingrese el nombre del usuario: ");
                        String nombreUsuario= scanner.nextLine();
                        System.out.println("Ingrese el rol del usuario: ");
                        String rolUsuario= scanner.nextLine();
                        System.out.println("Ingrese el password del usuario: ");
                        String passwordUsuario= scanner.nextLine();

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
            }catch (NumberFormatException e)
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
    private static void menuRecepcionista(Scanner scanner, GestorHotel hotel, UsuarioBase recepcionista) {
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
                        //realizarReservaInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 3:
                    {
                        //cancelarReservaInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 4:
                    {
                        //verReservasActivas()
                        break;
                    }
                    case 5:
                    {
                        //checkInInteractivo(scanner,hotel,recepcionista);
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