import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Bufor {
	private static final String regex = "\\d+";

	public Bufor() {
	}

	public Dane pobierzDaneZPliku(String plik) {
		Dane dane = null;
		try {
			BufferedReader bufor = new BufferedReader(new InputStreamReader(new FileInputStream(plik)));
			dane = pobierzZPliku(bufor);
			bufor.close();
		}
		catch (IOException e) {
			System.out.println("Plik z danymi nie jest dostępny.");
		}
		return dane;
	}

	private Dane pobierzZPliku(BufferedReader bufor) {
		Proces[] procesy = null;
		int[] parametry = null;
		String linia;
		try {
			String[] liczbaProcesow = null;
			linia = bufor.readLine();
			if (linia != null) liczbaProcesow = linia.split(" ");
			else {
				System.out.println("Błąd w wierszu 1: brak danych w pliku.");
				System.exit(0);
			}

			int ileProcesow = 0;
			if (liczbaProcesow.length != 1) {
				System.out.println("Błąd w wierszu 1: za dużo danych.");
				System.exit(0);
			}
			else if (liczbaProcesow[0].matches(regex)) {
				ileProcesow = Integer.parseInt(liczbaProcesow[0]);
			}
			else {
				System.out.println("Błąd w wierszu 1: zły format danych.");
				System.exit(0);
			}

			procesy = new Proces[ileProcesow];
			int nrLinii = 2;
			while ((linia = bufor.readLine()) != null && nrLinii <= ileProcesow + 1) {
				String[] proces = linia.split(" ");
				if (proces.length < 2) {
					System.out.println("Błąd w wierszu " + nrLinii + ": za mało danych.");
					System.exit(0);
				}
				else if (proces.length > 2) {
					System.out.println("Błąd w wierszu " + nrLinii + ": za dużo danych.");
					System.exit(0);
				}
				else if (proces[0].matches(regex) && proces[1].matches(regex)) {
					procesy[nrLinii - 2] = new Proces(nrLinii - 1,
							Integer.parseInt(proces[0]), Integer.parseInt(proces[1]));
				}
				else {
					System.out.println("Błąd w wierszu " + nrLinii + ": zły format danych.");
					System.exit(0);
				}
				nrLinii++;
			}

			String[] liczbaParametrow = null;
			if (linia != null) liczbaParametrow = linia.split(" ");
			else {
				System.out.println("Błąd w wierszu " + nrLinii + ": brak specyfikacji strategii Round Robin.");
				System.exit(0);
			}

			int ileParametrow = 0;
			if (liczbaParametrow.length != 1) {
				System.out.println("Błąd w wierszu " + nrLinii + ": za dużo danych.");
				System.exit(0);
			}
			else if (liczbaParametrow[0].matches(regex)) {
				ileParametrow = Integer.parseInt(liczbaParametrow[0]);
			}
			else {
				System.out.println("Błąd w wierszu " + nrLinii + ": zły format danych.");
				System.exit(0);
			}
			nrLinii++;

			if (ileParametrow > 0 && (linia = bufor.readLine()) != null) {
				String[] parametryTekstowo = linia.split(" ");

				if (parametryTekstowo.length == ileParametrow) {
					parametry = new int[ileParametrow];
					for (int i = 0; i < ileParametrow; i++) {
						if (parametryTekstowo[i].matches(regex))
							parametry[i] = Integer.parseInt(parametryTekstowo[i]);
						else {
							System.out.println("Błąd w wierszu " + nrLinii + ": zły format danych.");
							System.exit(0);
						}
					}
				}
				else {
					System.out.println("Błąd w wierszu " + nrLinii + ": za dużo parametrów strategii Round Robin.");
					System.exit(0);
				}
			}
			else {
				System.out.println("Błąd w wierszu " + nrLinii + ": brak parametrów strategii Round Robin.");
				System.exit(0);
			}
		}
		catch (IOException e) {
			System.out.println("Błąd w wierszu 1: brak danych w pliku.");
		}
		return new Dane(procesy, parametry);
	}
}
