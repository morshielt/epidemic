package exceptions;

@SuppressWarnings("serial")
public class IncorrectFileType extends Exception {

    public IncorrectFileType(String file, String type) {
        super(file + " isn't " + type);
    }
}


