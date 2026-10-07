package Models;

public class Contacto {
    private String nombre;
    private String username;
    private int imagenResId;

    public Contacto(String nombre, String username, int imagenResId) {
        this.nombre = nombre;
        this.username = username;
        this.imagenResId = imagenResId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public int getImagenResId() {
        return imagenResId;
    }
}