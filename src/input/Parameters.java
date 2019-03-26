package input;

import exceptions.NoValue;
import exceptions.IncorrectValue;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;


public class Parameters {
    private long seed;
    private int numberOfAgents;
    private double probPopular;
    private double probMeeting;
    private double probInfection;
    private double probHealing;
    private double mortality;
    private int numberOfDays;
    private int averageFriends;
    private String reportFile;
    private String reportParameters;

    public Parameters(Properties properties) throws IncorrectValue, NoValue {
        this.seed = parseLong(properties, "seed");
        this.probPopular = parseDouble(properties, "probPopular");
        this.probMeeting = parseDouble(properties, "probMeeting");
        this.probInfection = parseDouble(properties, "probInfection");
        this.probHealing = parseDouble(properties, "probHealing");
        this.mortality = parseDouble(properties, "mortality");
        this.numberOfDays = parseInt(properties, "numberOfDays", 1, 1000);
        this.numberOfAgents = parseInt(properties, "numberOfAgents", 1, 1000000);
        this.averageFriends = parseInt(properties, "averageFriends", 0, this.numberOfAgents - 1);
        this.reportFile = parseString(properties, "reportFile");

        fillReportParameters();
    }

    private void fillReportParameters() {
        this.reportParameters = MessageFormat.format("seed={0}\n" +
                        "numberOfAgents={1}\n" +
                        "probPopular={2}\n" +
                        "probMeeting={3}\n" +
                        "probInfection={4}\n" +
                        "probHealing={5}\n" +
                        "mortality={6}\n" +
                        "numberOfDays={7}\n" +
                        "averageFriends={8}\n\n",
                seed, numberOfAgents, String.format(Locale.ROOT, "%s", probPopular),
                String.format(Locale.ROOT, "%s", probMeeting),
                String.format(Locale.ROOT, "%s", probInfection),
                String.format(Locale.ROOT, "%s", probHealing),
                String.format(Locale.ROOT, "%s", mortality), numberOfDays, averageFriends);
    }

    public String reportParameters() {
        return reportParameters;
    }

    private String parseString(Properties properties, String key) throws IncorrectValue, NoValue {
        try {
            if (properties.containsKey(key)) {
                return properties.getProperty(key);
            } else {
                throw new NoValue(key);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectValue(properties.getProperty(key), key);
        }
    }

    private int parseInt(Properties properties, String key, int min, int max)
            throws IncorrectValue, NoValue {

        try {
            int temp = 0;
            if (properties.containsKey(key)) {
                temp = Integer.parseInt(properties.getProperty(key));
            } else {
                throw new NoValue(key);
            }

            if (temp < min || temp > max) {
                throw new IncorrectValue(properties.getProperty(key), key);
            }

            return temp;
        } catch (NumberFormatException e) {
            throw new IncorrectValue(properties.getProperty(key), key);
        }

    }

    private long parseLong(Properties properties, String key) throws IncorrectValue, NoValue {
        try {
            if (properties.containsKey(key)) {
                return Long.parseLong(properties.getProperty(key));
            } else {
                throw new NoValue(key);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectValue(properties.getProperty(key), key);
        }
    }

    private double parseDouble(Properties properties, String key) throws IncorrectValue, NoValue {
        try {
            if (properties.containsKey(key)) {
                return Double.parseDouble(properties.getProperty(key));
            } else {
                throw new NoValue(key);
            }
        } catch (NumberFormatException e) {
            throw new IncorrectValue(properties.getProperty(key), key);
        }
    }

    public String reportFile() {
        return reportFile;
    }

    public int averageFriends() {
        return averageFriends;
    }

    public int numberOfDays() {
        return numberOfDays;
    }

    public double probHealing() {
        return probHealing;
    }

    public double mortality() {
        return mortality;
    }

    public double probInfecting() {
        return probInfection;
    }

    public double probMeeting() {
        return probMeeting;
    }

    public int numberOfAgents() {
        return numberOfAgents;
    }

    public long seed() {
        return seed;
    }

    public double probPopular() {
        return probPopular;
    }
}

