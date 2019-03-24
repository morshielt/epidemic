import java.util.Locale;

public class Proces implements Comparable<Proces> {
	private int identyfikator;
	private int momentPojawienia;
	private int zapotrzebowanie;
	private double momentZakonczenia;
	private double postep;

	public Proces(int identyfikator, int momentPojawienia, int zapotrzebowanie) {
		this.identyfikator = identyfikator;
		this.momentPojawienia = momentPojawienia;
		this.zapotrzebowanie = zapotrzebowanie;
		this.momentZakonczenia = 0;
		this.postep = 0;
	}

	public void print() {
		System.out.print("[" + identyfikator + " " + momentPojawienia + " " + String.format(Locale.ROOT, "%.2f", momentZakonczenia) + "]");
	}

	public int identyfikator() {
		return identyfikator;
	}

	public int zapotrzebowanie() {
		return this.zapotrzebowanie;
	}

	public double ustawMomentZakonczenia(double momentZakonczenia) {
		this.momentZakonczenia = momentZakonczenia;
		return this.momentZakonczenia;
	}

	public int momentPojawienia() {
		return this.momentPojawienia;
	}

	public double pozostaleZapotrzebowanie() {
		return this.zapotrzebowanie - this.postep;
	}

	public void zrobPostep(double postep) {
		this.postep += postep;
	}

	public double momentZakonczenia() {
		return momentZakonczenia;
	}

	public double postep() {
		return postep;
	}

	@Override
	public int compareTo(Proces o) {
		if (this.momentPojawienia > o.momentPojawienia())
			return 1;
		else if (this.momentPojawienia == o.momentPojawienia())
			return 0;
		else
			return -1;
	}
}
