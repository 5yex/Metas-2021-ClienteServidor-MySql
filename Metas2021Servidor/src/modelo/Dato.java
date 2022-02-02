package modelo;

import java.io.Serializable;

public class Dato implements Serializable {
	private static final long serialVersionUID = 1L;
    private String comando;
    private boolean error;
    private Object respuesta;

    public Dato(String comando, boolean error, Object respuesta) {
        this.comando = comando;
        this.error = error;
        this.respuesta = respuesta;
    }

    public Dato(String comando) {
		super();
		this.comando = comando;
	}

	public Dato(String comando, boolean error) {
        this.comando = comando;
        this.error = error;
    }

    public Dato(boolean error, Object respuesta) {
        this.error = error;
        this.respuesta = respuesta;
    }

   
	public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Object respuesta) {
        this.respuesta = respuesta;
    }
}


