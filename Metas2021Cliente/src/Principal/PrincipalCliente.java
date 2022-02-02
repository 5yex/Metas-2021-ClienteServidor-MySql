package Principal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import modelo.Dato;
import modelo.Usuario;
import vista.VentanaPrincipal;
import vista.VentanaUsuario;

public class PrincipalCliente {
	protected static ObjectInputStream in;
	protected static ObjectOutputStream out;
	protected static Usuario user;

	public static void main(String[] args) {
		try {
			Socket sc = new Socket("127.0.0.1", 5000);
			in = new ObjectInputStream(sc.getInputStream());
			out = new ObjectOutputStream(sc.getOutputStream());
			// pide el usuario y contraseña
			user = VentanaUsuario.pedirUsuarioLogin();
			// lo manda al servidor
			out.writeObject(new Dato(false, user));
			// lee la respuesta del servidor
			Dato entrada = (Dato) in.readObject();
			user = (Usuario) entrada.getRespuesta();
			// Si tiene error
			if (entrada.isError()) {
				// recibe y muestra el error
				System.err.println(entrada.getComando());
			} else {
				// si no tiene error, lee el mensaje del servidor
				System.out.println(entrada.getComando());
				// lee si el usuario es admin y muestra el menu correspondiente
				if (user.isAdmin()) {
					VentanaPrincipal.menuAdministrador();
				} else {
					VentanaPrincipal.menuUsuario();
				}
			}
		} catch (Exception e) {
			System.out.println("Error de conexión");
		}
	}
}
