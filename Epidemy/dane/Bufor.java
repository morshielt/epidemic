package dane;

import bledy.*;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Bufor {
	private static final String plikDomyslne = "default.properties";
	private static final String plikXml = "simulation-conf.xml";

	public Bufor() {
	}

	public Dane pobierzParametry(Properties parametry) throws BrakPliku, ZlyFormatPliku, NiedozwolonaWartosc, BrakWartosci {
		try (FileInputStream stream = new FileInputStream(plikDomyslne);
		     Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
			parametry.load(reader);
		}
		catch (MalformedInputException | InvalidPropertiesFormatException e) {
			throw new ZlyFormatPliku(plikDomyslne, "tekstowy");
		}
		catch (IOException | NullPointerException e) {
			throw new BrakPliku(plikDomyslne);
		}
		new Dane(parametry);

		Properties parametryDodatkowe = new Properties();
		try (FileInputStream stream = new FileInputStream(plikXml)) {
			try {
				parametryDodatkowe.loadFromXML(stream);
			}
			catch (MalformedInputException | InvalidPropertiesFormatException e) {
				throw new ZlyFormatPliku(plikXml, "XML");
			}
			finally {
				stream.close();
			}
		}
		catch (IOException e) {
			throw new BrakPliku(plikXml);
		}

		parametry.putAll(parametryDodatkowe);
		Dane dane = new Dane(parametry);
		return dane;
	}
}
