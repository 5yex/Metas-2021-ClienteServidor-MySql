package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Meta implements Serializable {
	private static final long serialVersionUID = 1L;

    int idMeta;
    String titulo;
    String descripcion;
    String prioridad;
    LocalDate fechalimite;
    String categoria;
    Usuario user;

    public Meta(int idMeta, String titulo, String descripcion, String prioridad, LocalDate fechalimite, String categoria, Usuario username) {
        this.idMeta = idMeta;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechalimite = fechalimite;
        this.categoria = categoria;
        this.user = username;
    }
    
    public Meta(int idMeta, Usuario user) {
		super();
		this.idMeta = idMeta;
		this.user = user;
	}

	public Meta(String titulo, String descripcion, String prioridad, LocalDate fechalimite, String categoria,
			Usuario user) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.fechalimite = fechalimite;
		this.categoria = categoria;
		this.user = user;
	}

    @Override
    public String toString() {
        return "meta{" +
                "idMeta=" + idMeta +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", fechalimite=" + fechalimite +
                ", categoria='" + categoria + '\'' +
                ", username='" + user.getUsername() + '\'' +
                '}';
    }

    public Meta(int idMeta) {
        this.idMeta = idMeta;
    }

    public int getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDate getFechalimite() {
        return fechalimite;
    }

    public void setFechalimite(LocalDate fechalimite) {
        this.fechalimite = fechalimite;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsername() {
        return user;
    }

    public void setUsername(Usuario username) {
        this.user = username;
    }
}
