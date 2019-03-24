package dane;

import bledy.BrakWartosci;
import bledy.NiedozwolonaWartosc;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;


public class Dane {
	private long seed;
	private int liczbaAgentow;
	private double prawdTowarzyski;
	private double prawdSpotkania;
	private double prawdZarazenia;
	private double prawdWyzdrowienia;
	private double smiertelnosc;
	private int liczbaDni;
	private int srZnajomych;
	private String plikZRaportem;
	private String parametryDoRaportu;

	public Dane(Properties parametry) throws NiedozwolonaWartosc, BrakWartosci {
		this.seed = parsujLong(parametry, "seed");
		this.prawdTowarzyski = parsujDouble(parametry, "prawdTowarzyski");
		this.prawdSpotkania = parsujDouble(parametry, "prawdSpotkania");
		this.prawdZarazenia = parsujDouble(parametry, "prawdZarażenia");
		this.prawdWyzdrowienia = parsujDouble(parametry, "prawdWyzdrowienia");
		this.smiertelnosc = parsujDouble(parametry, "śmiertelność");
		this.liczbaDni = parsujInt(parametry, "liczbaDni", 1, 1000);
		this.liczbaAgentow = parsujInt(parametry, "liczbaAgentów", 1, 1000000);
		this.srZnajomych = parsujInt(parametry, "śrZnajomych", 0, this.liczbaAgentow - 1);
		this.plikZRaportem = parsujString(parametry, "plikZRaportem");

		uzupelnijParametryDoRaportu();
	}

	private void uzupelnijParametryDoRaportu() {
		this.parametryDoRaportu = MessageFormat.format("seed={0}\n" +
						"liczbaAgentów={1}\n" +
						"prawdTowarzyski={2}\n" +
						"prawdSpotkania={3}\n" +
						"prawdZarażenia={4}\n" +
						"prawdWyzdrowienia={5}\n" +
						"śmiertelność={6}\n" +
						"liczbaDni={7}\n" +
						"śrZnajomych={8}\n\n",
				seed, liczbaAgentow, String.format(Locale.ROOT, "%s", prawdTowarzyski),
				String.format(Locale.ROOT, "%s", prawdSpotkania),
				String.format(Locale.ROOT, "%s", prawdZarazenia),
				String.format(Locale.ROOT, "%s", prawdWyzdrowienia),
				String.format(Locale.ROOT, "%s", smiertelnosc), liczbaDni, srZnajomych);
	}

	public String parametryDoRaportu() {
		return parametryDoRaportu;
	}

	private String parsujString(Properties parametry, String klucz) throws NiedozwolonaWartosc, BrakWartosci {
		try {
			if (parametry.containsKey(klucz)) {
				return parametry.getProperty(klucz);
			}
			else {
				throw new BrakWartosci(klucz);
			}
		}
		catch (NumberFormatException e) {
			throw new NiedozwolonaWartosc(parametry.getProperty(klucz), klucz);
		}
	}

	private int parsujInt(Properties parametry, String klucz, int ogrDolne, int ogrGorne)
			throws NiedozwolonaWartosc, BrakWartosci {

		try {
			int temp = 0;
			if (parametry.containsKey(klucz)) {
				temp = Integer.parseInt(parametry.getProperty(klucz));
			}
			else {
				throw new BrakWartosci(klucz);
			}

			if (temp < ogrDolne || temp > ogrGorne) {
				throw new NiedozwolonaWartosc(parametry.getProperty(klucz), klucz);
			}

			return temp;
		}
		catch (NumberFormatException e) {
			throw new NiedozwolonaWartosc(parametry.getProperty(klucz), klucz);
		}

	}

	private long parsujLong(Properties parametry, String klucz) throws NiedozwolonaWartosc, BrakWartosci {
		try {
			if (parametry.containsKey(klucz)) {
				return Long.parseLong(parametry.getProperty(klucz));
			}
			else {
				throw new BrakWartosci(klucz);
			}
		}
		catch (NumberFormatException e) {
			throw new NiedozwolonaWartosc(parametry.getProperty(klucz), klucz);
		}
	}

	private double parsujDouble(Properties parametry, String klucz) throws NiedozwolonaWartosc, BrakWartosci {
		try {
			if (parametry.containsKey(klucz)) {
				return Double.parseDouble(parametry.getProperty(klucz));
			}
			else {
				throw new BrakWartosci(klucz);
			}
		}
		catch (NumberFormatException e) {
			throw new NiedozwolonaWartosc(parametry.getProperty(klucz), klucz);
		}
	}

	public String plikZRaportem() {
		return plikZRaportem;
	}

	public int srZnajomych() {
		return srZnajomych;
	}

	public int liczbaDni() {
		return liczbaDni;
	}

	public double prawdWyzdrowienia() {
		return prawdWyzdrowienia;
	}

	public double smiertelnosc() {
		return smiertelnosc;
	}

	public double prawdZarazenia() {
		return prawdZarazenia;
	}

	public double prawdSpotkania() {
		return prawdSpotkania;
	}

	public int liczbaAgentow() {
		return liczbaAgentow;
	}

	public long seed() {
		return seed;
	}

	public double prawdTowarzyski() {
		return prawdTowarzyski;
	}
}

