package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
    private String email;
    private String password;
    private boolean admin;

    public Usuario(String username, String email, String password, boolean admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
    public Usuario(String username) {
		super();
		this.username = username;
	}
    public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "usuario{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
