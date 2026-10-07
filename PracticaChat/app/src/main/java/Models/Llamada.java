package Models;

public class Llamada {
    private Long id;
    private String usuario;
    private String fechaLlamada;
    private int imagenUsuario;

    public Llamada(Long id, String usuario, String fechaLlamada, int imagenUsuario) {
        this.id = id;
        this.usuario = usuario;
        this.fechaLlamada = fechaLlamada;
        this.imagenUsuario = imagenUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaLlamada() {
        return fechaLlamada;
    }

    public void setFechaLlamada(String fechaLlamada) {
        this.fechaLlamada = fechaLlamada;
    }

    public int getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(int imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }
}