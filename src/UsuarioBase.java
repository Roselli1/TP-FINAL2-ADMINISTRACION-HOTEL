public abstract class UsuarioBase
{
    //Atributos
    protected String username;
    protected String password;
    protected Rol rol;

    //Constructor
    public UsuarioBase(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }


    //Metodo Abstracto
    public abstract boolean autenticar(String username, String password);


    @Override
    public String toString() {
        return "UsuarioBase{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }
}
