package exceptions;

@SuppressWarnings("serial")
public class NoValue extends Exception {

	public NoValue(String key) {
		super(key);
	}
}
