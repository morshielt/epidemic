import bledy.*;
import dane.Bufor;
import dane.Dane;
import dane.Raport;
import symulacja.Symulator;

import java.util.Properties;

public class Symulacja {
	public static void main(String args[]) {

		try {
			Bufor bufor = new Bufor();
			Properties parametry = new Properties();

			Dane dane = bufor.pobierzParametry(parametry);

			Symulator symulator = new Symulator(dane);
			symulator.symulujEpidemie();

			Raport raport = new Raport(dane.plikZRaportem());
			raport.uzupelnijRaport(dane, symulator);
		}
		catch (BrakPliku brakPliku) {
			System.out.println("Brak pliku " + brakPliku.getMessage());
		}
		catch (BladRaport | ZlyFormatPliku blad) {
			System.out.println(blad.getMessage());
		}
		catch (NiedozwolonaWartosc niedozwolonaWartosc) {
			System.out.println("Niedozwolona wartość " + niedozwolonaWartosc.getMessage());
		}
		catch (BrakWartosci brakWartosci) {
			System.out.println("Brak wartości dla klucza " + brakWartosci.getMessage());
		}
	}
}
