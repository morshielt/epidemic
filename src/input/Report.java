package input;

import exceptions.ReportError;
import simulator.Simulator;

import java.io.*;

public class Report {
    PrintWriter report;

    public Report(String reportFile) throws ReportError {
        try {
            File report = new File(reportFile);
            report.createNewFile();
            this.report = new PrintWriter(report, "UTF-8");

        } catch (IOException e) {
            throw new ReportError();
        }
    }

    public void fillReport(Parameters parameters, Simulator simulator) {
        write(parameters.reportParameters());
        write(simulator.agentList());
        write(simulator.graph());
        write(simulator.quantityEachDay());

        this.report.close();
    }

    private void write(String s) {
        this.report.print(s);
    }
}

