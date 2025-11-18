package Exceptions;

public class UsuarioYaExisteException extends Exception
{
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
