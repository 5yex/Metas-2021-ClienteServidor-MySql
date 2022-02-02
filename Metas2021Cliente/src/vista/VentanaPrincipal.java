package vista;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Principal.PrincipalCliente;
import controlador.Utils;
import modelo.Dato;
import modelo.Meta;
import modelo.Usuario;

public class VentanaPrincipal extends PrincipalCliente {
	@SuppressWarnings("unchecked")
	public static void menuAdministrador() throws IOException, ClassNotFoundException {
		boolean salir = false;
		while (salir == false) {
			System.out.println("Eres: "+VentanaPrincipal.user.getUsername()+"\n1. Nuevo Usuario \n2. Eliminar Usuarios \n3. Mostrar Usuarios \n4. Mostrar TODAS las metas \n 5.Menu Usuario");
			switch (Utils.leerNumero()) {
			case 1: {
				//Manda un dato al servidor, con el comando y en este caso un usuario
				out.writeObject(new Dato("NUEVO-USUARIO", false, VentanaUsuario.pedirDatosUsuario()));
				//Recibe la respuesta del servidor
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
				}
				break;
			}
			case 2: {
				System.out.println("Introduce el username para borrar");
				out.writeObject(new Dato("BORRAR-USUARIO", false, new Usuario(Utils.leerTexto())));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
				}
				break;
			}
			case 3: {
				out.writeObject(new Dato("LISTAR-USUARIOS", false));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
					VentanaUsuario.mostrarListaUsuarios((ArrayList<Usuario>) Recibido.getRespuesta());
				}
				break;
			}
			case 4: {
				out.writeObject(new Dato("TODAS-LAS-METAS", false));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
					VentanaMeta.mostrarListaMetas((ArrayList<Meta>) Recibido.getRespuesta());
				}
				break;
			}
			case 5: {
				menuUsuario();
				break;
			}
			case -1: {
				salir = true;
				break;
			}
			default:
				System.err.println("Introduce una opcion correcta");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void menuUsuario() throws IOException, ClassNotFoundException {
		boolean salir = false;
		while (salir == false) {
			System.out.println("Eres: "+user.getUsername()+"\n1. Nueva META\n2. Mostrar mis metas\n3. Borrar una meta\n4. Editar una meta");
			switch (Utils.leerNumero()) {
			case 1: {

				out.writeObject(new Dato("NUEVA-META", false, VentanaMeta.pedirDatosMeta(user)));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
				}
				break;
			}
			case 2: {
				out.writeObject(new Dato("MIS-METAS", false));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
					//Recibe arraylist de metas, y este método las muestra por pantalla
					VentanaMeta.mostrarListaMetas((ArrayList<Meta>) Recibido.getRespuesta());
				}
				break;
			}
			case 3: {
				System.out.println("Introduce el id de meta para borrar");
				out.writeObject(new Dato("BORRAR-META", false, new Meta(Utils.leerNumero(),user)));
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					System.err.println(Recibido.getComando());
				} else {
					System.out.println(Recibido.getComando());
				}
				break;
			}
			case 4: {
				System.out.println("Introduce el id de meta para EDITAR");
				//Manda un dato al servidor, con el comando y una meta solo con el id y el usuario actual
				out.writeObject(new Dato("EDITAR-META", false, new Meta(Utils.leerNumero(),user)));
				//Recibe la respuesta
				Dato Recibido = (Dato) in.readObject();
				if (Recibido.isError()) {
					//en caso de que la meta mandada no exista lee el error
					System.err.println(Recibido.getComando());
				} else {
					//en caso de que exista y no haya error, recibe la meta con todos sus datos para editarlos
					//despues de editarla mandamos al servidor la meta resultante
					out.writeObject(new Dato(false, VentanaMeta.editarDatosMeta(user,(Meta)Recibido.getRespuesta())));
					//Recibe una respuesta
					Recibido = (Dato) in.readObject();
					if (Recibido.isError()) {
						System.err.println(Recibido.getComando());
					} else {
						System.out.println(Recibido.getComando());
					}
				}
				break;
			}
			case -1: {
				salir = true;
				break;
			}
			default:
				System.err.println("Introduce una opcion correcta");
			}
		}
	}
}
