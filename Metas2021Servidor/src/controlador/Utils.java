package controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
   
    public static int leerNumero() {
		int num;
		while (true) {
			try {
				num = Integer.parseInt(teclado.readLine());
				break;
			} catch (Exception e) {
				System.err.println("Error: Introduce un numero");
			}
		}
		return num;
	}

	public static String leerTexto() {
		String txt;
		while (true) {
			try {
				txt = teclado.readLine();
				break;
			} catch (Exception e) {
				System.err.println("Error: Introduce texto correcto");
			}
		}
		return txt;
	}
	
	public static boolean validar(String campo, String patron) {

        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(campo);

        if (m.matches()) {
            return true;
        }

        return false;
    }
	
	public static String maxMinTxT(int min, int max){
		return "[A-Za-z ]{"+min+","+max+"}";
	}
}