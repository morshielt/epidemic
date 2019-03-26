package exceptions;

@SuppressWarnings("serial")
public class IncorrectValue extends Exception {

    public IncorrectValue(String value, String key) {
        super(value + " for " + key);
    }
}


