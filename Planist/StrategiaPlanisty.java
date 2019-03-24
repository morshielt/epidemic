import java.util.Locale;

public abstract class StrategiaPlanisty {
	protected String nazwaStrategii;
	private double sredniCzasObrotu;
	private double sredniCzasOczekiwania;
	private Proces[] wykonaneProcesy;

	public StrategiaPlanisty() {
		this.sredniCzasObrotu = 0;
		this.sredniCzasOczekiwania = 0;
		this.wykonaneProcesy = null;
	}

	public abstract void przydzielajProcesor(Dane dane);

	private void wypiszSredniCzasObrotu() {
		System.out.println("Średni czas obrotu: " + String.format(Locale.ROOT, "%.2f", sredniCzasObrotu));
	}

	private void wypiszSredniCzasOczekiwania() {
		System.out.println("Średni czas oczekiwania: "
				+ String.format(Locale.ROOT, "%.2f", sredniCzasOczekiwania));
	}

	protected void wypiszWynikPrzydzielania() {
		System.out.println("Strategia: " + this.nazwaStrategii);
		for (Proces proces : wykonaneProcesy) proces.print();
		System.out.println();
		wypiszSredniCzasObrotu();
		wypiszSredniCzasOczekiwania();
	}

	protected void uaktualnijWartosci(Proces[] wynik, double sumaOczekiwania, double sumaObrotow) {
		this.wykonaneProcesy = wynik;
		this.sredniCzasOczekiwania = sumaOczekiwania / wynik.length;
		this.sredniCzasObrotu = sumaObrotow / wynik.length;
	}

	protected void wyczyscPostepy(Proces[] procesy) {
		for (Proces proces : procesy) {
			proces.zrobPostep((-(proces.postep())));
			proces.ustawMomentZakonczenia(0);
		}
	}
}
