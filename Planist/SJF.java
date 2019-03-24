import java.util.Arrays;
import java.util.LinkedList;

public class SJF extends StrategiaPlanisty {

	public SJF() {
		super();
		this.nazwaStrategii = "SJF";
	}

	@Override
	public void przydzielajProcesor(Dane dane) {
		double czas = 0;
		Proces[] procesy = dane.procesy();
		LinkedList<Proces> kolejka = new LinkedList<>(Arrays.asList(procesy));
		Proces[] wynik = new Proces[kolejka.size()];
		int i = 0;
		double sumaObrotow = 0;
		double sumaOczekiwania = 0;

		while (!kolejka.isEmpty()) {
			Proces najkrotszy = null;
			for (int index = 0; index < kolejka.size(); index++) {
				Proces proces = kolejka.get(index);

				if (najkrotszy != null && proces.momentPojawienia() <= czas
						&& proces.zapotrzebowanie() <= najkrotszy.zapotrzebowanie()) {
					if (proces.zapotrzebowanie() == najkrotszy.zapotrzebowanie()
							&& proces.identyfikator() < najkrotszy.identyfikator()) {
						najkrotszy = proces;
					}
					else if (proces.zapotrzebowanie() < najkrotszy.zapotrzebowanie()){
						najkrotszy = proces;
					}
				} else if (najkrotszy == null) {
					najkrotszy = proces;
				}
			}

			wynik[i] = najkrotszy;
			kolejka.remove(najkrotszy);
			wynik[i].ustawMomentZakonczenia(wynik[i].zapotrzebowanie() + czas);
			czas += najkrotszy.zapotrzebowanie();
			sumaObrotow += wynik[i].momentZakonczenia() - wynik[i].momentPojawienia();
			sumaOczekiwania += wynik[i].momentZakonczenia() - wynik[i].momentPojawienia() - wynik[i].zapotrzebowanie();
			i++;
		}

		this.uaktualnijWartosci(wynik, sumaOczekiwania, sumaObrotow);
		this.wypiszWynikPrzydzielania();
		System.out.println();
		this.wyczyscPostepy(dane.procesy());

	}
}
