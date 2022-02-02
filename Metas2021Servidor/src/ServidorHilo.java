import modelo.Dato;
import modelo.Meta;
import modelo.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import controlador.GestionMeta;
import controlador.GestionUsuario;

class ServidorHilo extends Thread {

	private Socket sc;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Usuario usuario;

	public ServidorHilo(Socket sc, ObjectInputStream in, ObjectOutputStream out, Usuario Usuario) {
		this.sc = sc;
		this.in = in;
		this.out = out;
		this.usuario = Usuario;
	}

	@Override
	public void run() {
		//indica en el terminal del servidor quien se ha conectado y su rol
		if (usuario.isAdmin()) {
			System.err.println("SE HA CONECTADO EL USUARIO ADMINISTRADOR " + usuario.getUsername());
		} else {
			System.err.println("SE HA CONECTADO EL USUARIO " + usuario.getUsername());
		}
		//Inicia el bucle de recibir comandos
		funcionesUsuario();
	}

	private void funcionesUsuario() {
		try {
			boolean salir = false;
			while (!salir) {
				Dato entrada = (Dato) in.readObject();
				switch (entrada.getComando()) {
				case "NUEVO-USUARIO": {
					boolean resultado = GestionUsuario.nuevoUsuario((Usuario) entrada.getRespuesta());
					if (resultado) {
						out.writeObject(new Dato("Se ha creado un nuevo usuario"));
					} else {
						out.writeObject(new Dato("Error al crear el usuario", true));
					}
					break;
				}
				case "BORRAR-USUARIO": {
					boolean resultado = GestionUsuario.eliminarUsuario((Usuario) entrada.getRespuesta());
					if (resultado) {
						out.writeObject(new Dato("Se ha eliminado el usuario"));
					} else {
						out.writeObject(new Dato("Error al eliminar el usuario", true));
					}
					break;
				}
				case "LISTAR-USUARIOS": {
					ArrayList<Usuario> lista = GestionUsuario.obtenerListaUsuarios();
					if (!lista.isEmpty()) {
						out.writeObject(new Dato("Se han listado " + lista.size() + " usuarios", false, lista));
					} else {
						out.writeObject(new Dato("No hay usuarios", true));
					}
					break;
				}
				case "NUEVA-META": {
					boolean resultado = GestionMeta.nuevaMeta((Meta) entrada.getRespuesta());
					if (resultado) {
						out.writeObject(new Dato("Se ha introducido una nueva meta"));
					} else {
						out.writeObject(new Dato("Error al crear una meta", true));
					}
					break;
				}
				case "MIS-METAS": {
					ArrayList<Meta> lista = GestionMeta.obtenerMisMetas(usuario);
					if (!lista.isEmpty()) {
						out.writeObject(new Dato("Se han listado " + lista.size() + " metas", false, lista));
					} else {
						out.writeObject(new Dato("No tienes metas ", true));
					}
					break;
				}
				case "TODAS-LAS-METAS": {
					ArrayList<Meta> lista = GestionMeta.obtenerTodasLasMetas();
					if (!lista.isEmpty()) {
						out.writeObject(new Dato("Se han listado " + lista.size() + " metas", false, lista));
					} else {
						out.writeObject(new Dato("No hay metas en el programa", true));
					}
					break;
				}
				case "BORRAR-META": {
					boolean resultado = GestionMeta.eliminarMeta((Meta) entrada.getRespuesta());
					if (resultado) {
						out.writeObject(new Dato("Se ha eliminado tu meta"));
					} else {
						out.writeObject(new Dato("Error al eliminar tu meta", true));
					}
					break;
				}
				case "EDITAR-META": {
					Meta meta = GestionMeta.obtenerMeta((Meta) entrada.getRespuesta(),usuario);
					if (meta != null) {
						out.writeObject(new Dato(false,meta));
						Meta metaNueva = (Meta) ((Dato) in.readObject()).getRespuesta();
						metaNueva.setIdMeta(meta.getIdMeta());
						boolean Resultado = GestionMeta.editarMeta(metaNueva);
						if (Resultado) {
							out.writeObject(new Dato("Edicion completada\n La " + meta.toString()
									+ "\n ha pasado a ser:\n " + metaNueva.toString()));
						} else {
							out.writeObject(new Dato("Error al editar la meta", true));
						}
					} else {
						out.writeObject(new Dato("Esta meta no existe", true));
					}
					break;
				}
				}
			}
			sc.close();
		} catch (IOException | ClassNotFoundException ex) {
			//Indica el terminal del servidor que cliente se ha desconectado
			System.err.println("SE HA DESCONECTADO EL USUARIO " + usuario.getUsername());
			//Borra el cliente del array list de conectados
			PrincipalServidor.clientesConectados.remove(usuario.getUsername());
		}
	}
}