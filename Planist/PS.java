import java.util.LinkedList;

public class PS extends StrategiaPlanisty {

	public PS() {
		super();
		this.nazwaStrategii = "PS";
	}

	@Override
	public void przydzielajProcesor(Dane dane) {
		double czas = 0;
		Proces[] procesy = dane.procesy();
		LinkedList<Proces> kolejka = new LinkedList<>();
		LinkedList<Integer> czasy = new LinkedList<>();
		Proces[] wynik = new Proces[procesy.length];
		int indexGlobalny = 0;
		int index = 0;
		double sumaObrotow = 0;
		int cosSieDoda = 0;

		for (Proces proces : procesy) {
			if (!czasy.contains(proces.momentPojawienia())) {
				czasy.add(proces.momentPojawienia());
			}
		}

		cosSieDoda = czasy.poll();
		if (!czasy.isEmpty()) {
			cosSieDoda = czasy.poll();
		}
		else cosSieDoda = -1;

		while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
			kolejka.add(procesy[indexGlobalny]);
			indexGlobalny++;
		}

		while (!kolejka.isEmpty() || czas <= cosSieDoda) {
			while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
				kolejka.add(procesy[indexGlobalny]);
				indexGlobalny++;
			}
			if (!kolejka.isEmpty()) {
				double przydzial = 1.0 / kolejka.size();
				double najmniejszeZapotrzebowanie = kolejka.get(0).zapotrzebowanie();

				for (Proces proces : kolejka) {
					if (proces.pozostaleZapotrzebowanie() <= najmniejszeZapotrzebowanie) {
						najmniejszeZapotrzebowanie = proces.pozostaleZapotrzebowanie();
					}
				}

				if (najmniejszeZapotrzebowanie <= przydzial) {
					if (cosSieDoda >= 0 && cosSieDoda < (czas + najmniejszeZapotrzebowanie)) {
						double postep = (cosSieDoda - czas) * przydzial;
						double przesuniecieCzasu = cosSieDoda - czas;
						czas += przesuniecieCzasu;

						for (Proces proces : kolejka) {
							proces.zrobPostep(postep);
						}

						if (!czasy.isEmpty() && cosSieDoda <= czas) {
							cosSieDoda = czasy.poll();
						}
						else if (cosSieDoda <= czas && czasy.isEmpty()) cosSieDoda = -1;
					}
					else {
						double przesuniecieCzasu = 1 * (najmniejszeZapotrzebowanie / przydzial);
						czas += przesuniecieCzasu;
						LinkedList<Proces> zakonczone = new LinkedList<>();

						for (Proces proces : kolejka) {
							proces.zrobPostep(najmniejszeZapotrzebowanie);
							if (proces.pozostaleZapotrzebowanie() == 0) {
								proces.ustawMomentZakonczenia(czas);
								wynik[index] = proces;
								sumaObrotow += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia();
								index++;
								zakonczone.add(proces);
							}
						}

						for (Proces zakonczony : zakonczone) {
							kolejka.remove(zakonczony);
						}
					}
				}
				else {
					if (cosSieDoda >= 0 && cosSieDoda < (czas + 1)) {
						double postep = (cosSieDoda - czas) * przydzial;
						czas += cosSieDoda - czas;

						for (Proces proces : kolejka) {
							proces.zrobPostep(postep);
						}

						if (!czasy.isEmpty() && cosSieDoda <= czas) {
							cosSieDoda = czasy.poll();
						}
						else if (cosSieDoda <= czas && czasy.isEmpty()) cosSieDoda = -1;
					}
					else {
						czas += 1;
						for (Proces proces : kolejka) {
							proces.zrobPostep(przydzial);
						}
					}

					if (cosSieDoda <= czas && !czasy.isEmpty()) {
						cosSieDoda = czasy.poll();
					}
					else if (cosSieDoda <= czas && czasy.isEmpty()) cosSieDoda = -1;
				}
			}
			else {
				czas = cosSieDoda;
				if (cosSieDoda <= czas && !czasy.isEmpty()) {
					cosSieDoda = czasy.poll();
				}
				else if (cosSieDoda <= czas && czasy.isEmpty()) cosSieDoda = -1;

				while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
					kolejka.add(procesy[indexGlobalny]);
					indexGlobalny++;
				}
			}
		}

		this.uaktualnijWartosci(wynik, 0, sumaObrotow);
		this.wypiszWynikPrzydzielania();
		this.wyczyscPostepy(dane.procesy());
		System.out.println();
	}
}
