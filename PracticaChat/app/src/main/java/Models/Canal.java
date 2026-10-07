package Models;

public class Canal {
    private Long id;
    private String nombre;
    private String ultimoMensaje;
    private String ultimaConexion;
    private int imagen;
    private int mensajesNoLeidos;

    public Canal(Long id, String nombre, String ultimoMensaje, String ultimaConexion, int imagen, int mensajesNoLeidos) {
        this.id = id;
        this.nombre = nombre;
        this.ultimoMensaje = ultimoMensaje;
        this.ultimaConexion = ultimaConexion;
        this.imagen = imagen;
        this.mensajesNoLeidos = mensajesNoLeidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public String getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(String ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getMensajesNoLeidos() {
        return mensajesNoLeidos;
    }

    public void setMensajesNoLeidos(int mensajesNoLeidos) {
        this.mensajesNoLeidos = mensajesNoLeidos;
    }
}