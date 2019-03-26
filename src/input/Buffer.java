package input;

import exceptions.*;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Buffer {
    private static final String defaultFile = "default.properties";
    private static final String xmlFile = "simulation-conf.xml";

    public Buffer() {
    }

    public Parameters getParameters(Properties properties) throws NoFile, IncorrectFileType, IncorrectValue, NoValue {
        try (FileInputStream stream = new FileInputStream(defaultFile);
             Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
            properties.load(reader);
        } catch (MalformedInputException | InvalidPropertiesFormatException e) {
            throw new IncorrectFileType(defaultFile, "text");
        } catch (IOException | NullPointerException e) {
            throw new NoFile(defaultFile);
        }
        new Parameters(properties);

        Properties additionalParameters = new Properties();
        try (FileInputStream stream = new FileInputStream(xmlFile)) {
            try {
                additionalParameters.loadFromXML(stream);
            } catch (MalformedInputException | InvalidPropertiesFormatException e) {
                throw new IncorrectFileType(xmlFile, "XML");
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            throw new NoFile(xmlFile);
        }

        properties.putAll(additionalParameters);
        Parameters parameters = new Parameters(properties);
        return parameters;
    }
}
