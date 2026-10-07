package Models;

public class Estado {
    private Long id;
    private int imagenEstado;
    private int fotoPerfil;
    private boolean visto;

    public Estado(Long id, int imagenEstado, int fotoPerfil, boolean visto) {
        this.id = id;
        this.imagenEstado = imagenEstado;
        this.fotoPerfil = fotoPerfil;
        this.visto = visto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImagenEstado() {
        return imagenEstado;
    }

    public void setImagenEstado(int imagenEstado) {
        this.imagenEstado = imagenEstado;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    public boolean isVisto() {
        return visto;
    }
    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}