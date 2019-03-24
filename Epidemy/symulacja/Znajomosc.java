package symulacja;

public class Znajomosc {
	private int kto;
	private int kogo;

	public Znajomosc(int kto, int kogo) {
		this.kto = kto;
		this.kogo = kogo;
	}

	public int kogo() {
		return kogo;
	}

	public int kto() {
		return kto;
	}
}
