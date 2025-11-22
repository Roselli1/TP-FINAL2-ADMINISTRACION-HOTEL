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
}