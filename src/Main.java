import Enums.Rol;
import Gestores.GestorHotel;
import Modelo.Persona.UsuarioBase;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Iniciar el gestor de hotel (carga automatica de datos)
        GestorHotel hotel= new GestorHotel();
        Scanner scanner= new Scanner(System.in);
        int opcion;


        System.out.println("--- BIENVENIDO AL SISTEMA DE GESTION HOTELERA ---");
        do
        {
            //ANTES DE ESTO ESTARIA BUENO PONER 3 OPCIONES INICIAR SESION, VISTA DE PASAJERO(SERIA UN MENU DE PASAJERO PERO SIN LOGUEAR) Y SALIR
            try
            {
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
                }
            }catch (Exception e)
            {
                System.out.println("Error en el sistema: " + e.getMessage());
            }



        }while (true);
    }

    // --- Menu Administrador ---
    private static void menuAdministrador(Scanner scanner, GestorHotel hotel, UsuarioBase administrador) {
        int opcion=-1;
        do
        {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Realizar Backup del Sistema.");
            System.out.println("2. Crear Nuevo Usuario.");
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
    private static void menuRecepcionista(Scanner scanner, GestorHotel hotel, UsuarioBase recepcionista) {
        int opcion=-1;
        do
        {
            System.out.println("\n--- MENU RECEPCIONISTA ---");
            System.out.println("1. Ver Habitaciones Disponibles.");
            System.out.println("2. Crear Reserva.");
            System.out.println("3. Cancelar Reserva.");
            System.out.println("4. Realizar Check-In.");
            System.out.println("5. Realizar Check-Out.");
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
                        //checkInInteractivo(scanner,hotel,recepcionista);
                        break;
                    }
                    case 5:
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