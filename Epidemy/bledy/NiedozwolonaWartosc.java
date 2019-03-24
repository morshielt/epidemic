package bledy;

@SuppressWarnings("serial")
public class NiedozwolonaWartosc extends Exception {

	public NiedozwolonaWartosc(String wartosc, String klucz) {
		super(wartosc + " dla klucza " + klucz);
	}
}


