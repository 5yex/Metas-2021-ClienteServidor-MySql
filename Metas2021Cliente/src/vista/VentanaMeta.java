package vista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import controlador.Utils;
import modelo.Meta;
import modelo.Usuario;

public class VentanaMeta {
	public static void mostrarListaMetas(ArrayList<Meta> metas) {
		for (Meta meta : metas) {
			System.out.println(meta.toString());
		}
	}
	//Devuelve una meta después de pedirle al usuario todos los datos
	public static Meta pedirDatosMeta(Usuario user) {
		String titulo = pedirTitulo();
		String descripcion = pedirDescripcion();
		String prioridad = pedirPrioridad();
		LocalDate fechalimite = pedirFechaLimite();
		String categoria = pedirCategoria();
		return new Meta(titulo, descripcion, prioridad, fechalimite, categoria, user);
	}

	private static String pedirTitulo() {
		String titulo;
		System.out.println("Introduce el titulo de la meta (Entre 3 y 20 caracteres): ");
		while (true) {
			titulo = Utils.leerTexto();
			if (!Pattern.matches(Utils.maxMinTxT(3, 20), titulo)) {
				System.out.println("Error: Min 3, max 20 caracteres");
			} else {
				break;
			}
		}
		return titulo;
	}

	private static String pedirDescripcion() {
		String descripcion;
		System.out.println("Introduce La descripcion de la meta (Entre 3 y 128 caracteres): ");
		while (true) {
			descripcion = Utils.leerTexto();
			if (!Pattern.matches(Utils.maxMinTxT(3, 128), descripcion)) {
				System.out.println("Error: Min 3, max 128 caracteres");
			} else {
				break;
			}
		}
		return descripcion;
	}

	private static String pedirPrioridad() {

		String prioridad = "";

		System.out.println("\nElige la prioridad: \n1.ALTA\n2.MEDIA\n3.BAJA");
		int opcion = Utils.leerNumero();
		do {
			switch (opcion) {
			case 1:
				prioridad = "ALTA";
				break;
			case 2:
				prioridad = "MEDIA";
				break;
			case 3:
				prioridad = "BAJA";
				break;
			default:
				System.out.println("Escoja una opcion disponible");
				break;
			}
		} while (opcion < 1 || opcion > 3);
		return prioridad;
	}

	private static LocalDate pedirFechaLimite() {
		System.out.println("Introduce la fecha límite {año-mes-dia}");
		while (true) {
			String fechaCadena = Utils.leerTexto();
			if (!Pattern.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", fechaCadena)) {
				System.out.println("Error: Formato de fecha incorrecto");
			} else {
				LocalDate Fecha = LocalDate.parse(fechaCadena);
				if(Fecha.isBefore(LocalDate.now())){
					System.out.println("Error: Introduce una fecha mayor a la actual!");
				}else {
					return Fecha;
				}
			}
		}
	}

	private static String pedirCategoria() {
		while (true) {
			System.out.println(
					"\nElige la Categoría: \n1.Deportes\n2.Trabajo\n3.Estudios\n4.Fortuna\n5.Juguetes\n6.Amor\n7.Otros");
			int opcion = Utils.leerNumero();
			switch (opcion) {
			case 1:
				return "Deportes";
			case 2:
				return "Trabajo";
			case 3:
				return "Estudios";
			case 4:
				return "Fortuna";
			case 5:
				return "Juguetes";
			case 6:
				return "Amor";
			case 7:
				return "Otros";
			default:
				System.out.println("Escoja una opcion disponible");
				break;
			}
		}
	}
	
	//Recibe una meta del usuario en activo, edita sus datos y la devuelve editada
	public static Meta editarDatosMeta(Usuario user, Meta metaEditada) {
		boolean salir = false;
		while (salir == false) {
			System.out.println("Editando: " + metaEditada.toString());
			System.out
					.println("\n1. Titulo \n2. Descripcion \n3. Prioridad \n4. Fecha Límite \n5. Categoria\n-1 SALIR");
			switch (Utils.leerNumero()) {
			case 1: {
				metaEditada.setTitulo(pedirTitulo());
				break;
			}
			case 2: {
				metaEditada.setDescripcion(pedirDescripcion());
				break;
			}
			case 3: {
				metaEditada.setPrioridad(pedirPrioridad());
				break;
			}
			case 4: {
				metaEditada.setFechalimite(pedirFechaLimite());
				break;
			}
			case 5: {
				metaEditada.setCategoria(pedirCategoria());
				break;
			}
			case -1: {
				salir = true;
				break;
			}
			default:
				System.err.println("Introduce una opcion existente");
			}
		}
		return metaEditada;
	}
}
