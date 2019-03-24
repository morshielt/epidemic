import java.util.LinkedList;

public class RR extends StrategiaPlanisty {

	public RR() {
		super();
		this.nazwaStrategii = "RR-";
	}

	@Override
	public void przydzielajProcesor(Dane dane) {
		for (int i = 0; i < dane.parametryRR().length; i++) {
			this.nazwaStrategii = ("RR-" + dane.parametryRR()[i]);
			this.przydzielajProcesor(dane.procesy(), dane.parametryRR()[i]);
			this.wyczyscPostepy(dane.procesy());
		}
	}

	private void przydzielajProcesor(Proces[] procesy, int q) {
		double czas = 0;
		LinkedList<Proces> kolejka = new LinkedList<>();
		Proces[] wynik = new Proces[procesy.length];
		int indexGlobalny = 0;
		int index = 0;
		double sumaObrotow = 0;
		double sumaOczekiwania = 0;

		while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
			kolejka.add(procesy[indexGlobalny]);
			indexGlobalny++;
		}

		while (!kolejka.isEmpty()) {
			Proces biezacy = kolejka.peek();
			int gdzie = 0;
			biezacy.zrobPostep(q);
			double x = biezacy.pozostaleZapotrzebowanie();
			for (int i = 0; i < kolejka.size() && kolejka.get(i).momentPojawienia() <= czas; i++) {
				gdzie = kolejka.indexOf(kolejka.get(i));
			}

			if (x < 0) {
				czas += (q + biezacy.pozostaleZapotrzebowanie());
				biezacy.ustawMomentZakonczenia(czas);
				wynik[index] = biezacy;
				sumaObrotow += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia();
				sumaOczekiwania += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia() - wynik[index].zapotrzebowanie();
				index++;
				kolejka.remove(biezacy);

				while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
					if (procesy[indexGlobalny].momentPojawienia() < czas) {
						kolejka.add(gdzie, procesy[indexGlobalny]);
						gdzie++;
					}
					else kolejka.add(procesy[indexGlobalny]);
					indexGlobalny++;
				}
			}
			else if (x == 0) {
				czas += q;
				biezacy.ustawMomentZakonczenia(czas);
				wynik[index] = biezacy;
				sumaObrotow += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia();
				sumaOczekiwania += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia() - wynik[index].zapotrzebowanie();
				index++;
				kolejka.remove(biezacy);

				while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
					if (procesy[indexGlobalny].momentPojawienia() < czas) {
						kolejka.add(gdzie, procesy[indexGlobalny]);
						gdzie++;
					}
					else kolejka.add(procesy[indexGlobalny]);
					indexGlobalny++;
				}
			}
			else {
				czas += q;
				kolejka.remove(biezacy);
				while (indexGlobalny < procesy.length && procesy[indexGlobalny].momentPojawienia() <= czas) {
					if (procesy[indexGlobalny].momentPojawienia() < czas) {
						kolejka.add(gdzie, procesy[indexGlobalny]);
						gdzie++;
					}
					else kolejka.add(procesy[indexGlobalny]);
					indexGlobalny++;
				}
				kolejka.add(gdzie, biezacy);
			}
		}
		this.uaktualnijWartosci(wynik, sumaOczekiwania, sumaObrotow);
		this.wypiszWynikPrzydzielania();
		System.out.println();
	}
}



