package bledy;

@SuppressWarnings("serial")
public class BrakWartosci extends Exception {

	public BrakWartosci(String klucz) {
		super(klucz);
	}
}
