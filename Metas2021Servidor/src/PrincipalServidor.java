import modelo.Dato;
import modelo.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import controlador.GestionUsuario;

public class PrincipalServidor {
	static ArrayList<String> clientesConectados = new ArrayList<>();

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			Socket sc;
			System.out.println("Servidor iniciado");
			while (true) {
				// Espero la conexion del cliente
				sc = server.accept();

				ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
				//recibe un dato con el usuario que introduce para hacer login el cliente
				Dato Dato = (Dato) in.readObject();
				//obtiene un usuario con todos los demás datos
				Usuario user = GestionUsuario.obtenerUsuario((Usuario) Dato.getRespuesta());
				//Si el usuario obtenido esta vacio, será que no existe
				//Si es un usuario, estará correcto
				if (user != null) {
					//Comprueba que no esté ya conectado al servidor desde otro cliente
					if (!clientesConectados.contains(user.getUsername())) {
						//Crea el hilo para ese usuario si todo esta correecto
						ServidorHilo hilo = new ServidorHilo(sc, in, out, user);
						hilo.start();
						//Se añade a la lista de usuarios conectados
						clientesConectados.add(user.getUsername());
						//Le pasa un mensaje al cliente, indicando que no hay error, en el boolean error del dato
						if (user.isAdmin()) {
							out.writeObject(new Dato("Te has conectado como Administrador, " + leerCLientesConectados(),
									false, user));
						} else {
							out.writeObject(new Dato("Te has conectado como Usuario, " + leerCLientesConectados(),
									false, user));
						}
					} else {
						out.writeObject(new Dato("El usuario que has utilizado ya está conectado en el servidor", true));

					}
				}else {
					out.writeObject(new Dato("El usuario que has utilizado no existe", true));
				}
			}

		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(PrincipalServidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//Recorre el array de clientes conectados y lo devuelve en una cadena
	public static String leerCLientesConectados() {
		String resultado = "Hay " + clientesConectados.size()
				+ " clientes conectados en el servidor en este momento\nUsuarios:";
		for (String usuario : clientesConectados) {
			resultado += "  " + usuario;
		}
		resultado += "\n";
		return resultado;
	}
}
