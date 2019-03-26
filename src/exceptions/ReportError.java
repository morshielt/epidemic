package exceptions;

@SuppressWarnings("serial")
public class ReportError extends Exception {

    public ReportError() {
        super("Unable to create report file.");
    }
}
