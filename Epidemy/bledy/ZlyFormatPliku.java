package bledy;

@SuppressWarnings("serial")
public class ZlyFormatPliku extends Exception {

	public ZlyFormatPliku(String plik, String typ) {
		super(plik + " nie jest " + typ);
	}
}


