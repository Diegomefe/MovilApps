package Models;

public class Mensaje {
    private String texto;
    private String hora;
    private boolean esMio;

    public Mensaje(String texto, String hora, boolean esMio) {
        this.texto = texto;
        this.hora = hora;
        this.esMio = esMio;
    }

    public String getTexto() {
        return texto;
    }

    public String getHora() {
        return hora;
    }

    public boolean isEsMio() {
        return esMio;
    }
}