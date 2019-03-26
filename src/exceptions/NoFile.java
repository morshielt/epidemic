package exceptions;

@SuppressWarnings("serial")
public class NoFile extends Exception {

    public NoFile(String file) {
        super(file);
    }
}
