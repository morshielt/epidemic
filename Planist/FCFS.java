import java.util.Arrays;
import java.util.LinkedList;

public class FCFS extends StrategiaPlanisty {

	public FCFS() {
		super();
		this.nazwaStrategii = "FCFS";
	}

	@Override
	public void przydzielajProcesor(Dane dane) {
		double czas = 0;
		Proces[] procesy = dane.procesy();
		LinkedList<Proces> kolejka = new LinkedList<>(Arrays.asList(procesy));
		Proces[] wynik = new Proces[kolejka.size()];
		int index = 0;
		double sumaObrotow = 0;
		double sumaOczekiwania = 0;

		while (!kolejka.isEmpty()) {
			wynik[index] = kolejka.removeFirst();
			czas = wynik[index].ustawMomentZakonczenia(wynik[index].zapotrzebowanie() + czas);
			sumaOczekiwania += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia() - wynik[index].zapotrzebowanie();
			sumaObrotow += wynik[index].momentZakonczenia() - wynik[index].momentPojawienia();
			index++;
		}

		this.uaktualnijWartosci(wynik, sumaOczekiwania, sumaObrotow);
		this.wypiszWynikPrzydzielania();
		System.out.println();
		this.wyczyscPostepy(dane.procesy());
	}
}
