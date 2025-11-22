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
                        //hacer un menun de administrador
                    }
                    //Si es recepcionista
                    else if (usuarioLogueado.getRol()==Rol.RECEPCIONISTA)
                    {
                        //menu recepcionista
                    }
                }
            }



        }while (true);
    }


    private static void menuAdministrador(Scanner scanner, GestorHotel hotel, UsuarioBase administrador)
    {
        int opcion;
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

                    }
                    case 3:
                    {
                        System.out.println("Listado de habitaciones:");
                        hotel.listarHabitaciones();
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
            }

        }while (opcion!=0);

    }
}