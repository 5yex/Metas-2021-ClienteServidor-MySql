package vista;

import java.util.ArrayList;
import java.util.regex.Pattern;

import controlador.Utils;
import modelo.Usuario;

public class VentanaUsuario {

	public static Usuario pedirUsuarioLogin() {
		System.out.println("INICIAR SESIÓN a METAS 2021");
		while (true) {
			System.out.println("Introduce tu usuario");
			String usuario = Utils.leerTexto();
			System.out.println("Introduce tu contraseña");
			String password = Utils.leerTexto();
			return new modelo.Usuario(usuario, password);
		}
	}
	
	public static void mostrarListaUsuarios(ArrayList<Usuario> users) {
		for (Usuario usuario : users) {
			System.out.println(usuario.toString());
		}
	}
	
	public static Usuario pedirDatosUsuario() {

		String username = pedirNombreUsuario();
		String email = pedirEmail();
		String password = pedirPass();
		boolean admin = esAdmin();

		return new Usuario(username, email, password, admin);
	}

	private static boolean esAdmin() {
		
		while(true) {
			System.out.println("\n¿Es administrador?: \n1.Si\n2.No");
			int opcion = Utils.leerNumero();
			switch (opcion) {
			case 1:
				return true;
			case 2:
				return false;
			default:
				System.out.println("Escoja una opcion disponible");
			}
		}

	}

	private static String pedirPass() {
		String password;
		System.out.println("Introduce la contraseña del usuario (Entre 8 y 15 caracteres): ");
		while (true) {
			password = Utils.leerTexto();
			if (!Pattern.matches(Utils.maxMinTxT(8, 15), password)) {
				System.out.println("Error: Min 8, max 15 caracteres");
			} else {
				break;
			}
		}
		return password;
	}

	private static String pedirNombreUsuario() {
		String nombre;
		System.out.println("Introduce el nombre del usuario (Entre 3 y 20 caracteres): ");
		while (true) {
			nombre = Utils.leerTexto();
			if (!Pattern.matches(Utils.maxMinTxT(3, 20), nombre)) {
				System.out.println("Error: Min 3, max 20 caracteres");
			} else {
				break;
			}
		}
		return nombre;
	}

	private static String pedirEmail() {
		String email;
		System.out.println("Introduce el email del usuario: ");
		while (true) {
			email = Utils.leerTexto();
			if (!Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b", email)) {
				System.out.println("Error: Formato incorrecto de email");
			} else {
				break;
			}
		}
		return email;
	}
	
}
