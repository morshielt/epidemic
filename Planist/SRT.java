import java.util.Arrays;
import java.util.LinkedList;

public class SRT extends StrategiaPlanisty {

	public SRT() {
		super();
		this.nazwaStrategii = "SRT";
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
		boolean tenSam = true;
		double ileMogeMax = -1;

		while (!kolejka.isEmpty()) {
			Proces najkrotszy = null;
			for (int indeks = 0; indeks < kolejka.size(); indeks++) {
				Proces proces = kolejka.get(indeks);

				if (najkrotszy != null && proces.momentPojawienia() <= czas
						&& proces.pozostaleZapotrzebowanie() <= najkrotszy.pozostaleZapotrzebowanie()) {
					if (!tenSam && proces.pozostaleZapotrzebowanie() == najkrotszy.pozostaleZapotrzebowanie()
							&& proces.identyfikator() < najkrotszy.identyfikator())
						najkrotszy = proces;
					else if (proces.pozostaleZapotrzebowanie() < najkrotszy.pozostaleZapotrzebowanie())
						najkrotszy = proces;
				}
				else if (najkrotszy == null)
					najkrotszy = proces;
			}

			ileMogeMax = najkrotszy.pozostaleZapotrzebowanie();
			for (Proces proces : kolejka) {
				if (czas < proces.momentPojawienia() && proces.momentPojawienia() < (czas + najkrotszy.zapotrzebowanie())) {
					if (proces.momentPojawienia() - czas < ileMogeMax
							&& proces.zapotrzebowanie() < najkrotszy.pozostaleZapotrzebowanie() - (proces.momentPojawienia() - czas)) {
						ileMogeMax = proces.momentPojawienia() - czas;
					}
				}
			}

			najkrotszy.zrobPostep(ileMogeMax);
			czas += ileMogeMax;

			if (najkrotszy.pozostaleZapotrzebowanie() == 0) {
				wynik[i] = najkrotszy;
				wynik[i].ustawMomentZakonczenia(czas);
				sumaObrotow += wynik[i].momentZakonczenia() - wynik[i].momentPojawienia();
				sumaOczekiwania += wynik[i].momentZakonczenia() - wynik[i].momentPojawienia() - wynik[i].zapotrzebowanie();
				kolejka.remove(najkrotszy);
				i++;
				tenSam = false;
			}
			else {
				tenSam = true;
			}
		}

		this.uaktualnijWartosci(wynik, sumaOczekiwania, sumaObrotow);
		this.wypiszWynikPrzydzielania();
		System.out.println();
		this.wyczyscPostepy(dane.procesy());
	}
}
