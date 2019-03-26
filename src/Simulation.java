import exceptions.*;
import input.Report;
import simulator.*;
import input.Buffer;
import input.Parameters;

import java.util.Properties;

public class Simulation {
    public static void main(String args[]) {

        try {
            Buffer buffer = new Buffer();
            Properties parametry = new Properties();

            Parameters parameters = buffer.getParameters(parametry);

            Simulator simulator = new Simulator(parameters);
            simulator.simulateEpidemic();

            Report report = new Report(parameters.reportFile());
            report.fillReport(parameters, simulator);
        } catch (NoFile noFile) {
            System.out.println("No file " + noFile.getMessage());
        } catch (ReportError | IncorrectFileType error) {
            System.out.println(error.getMessage());
        } catch (IncorrectValue incorrectValue) {
            System.out.println("Incorrect value " + incorrectValue.getMessage());
        } catch (NoValue noValue) {
            System.out.println("No value for key " + noValue.getMessage());
        }
    }
}
