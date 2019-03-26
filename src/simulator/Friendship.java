package simulator;

public class Friendship {
	private int kto;
	private int kogo;

	public Friendship(int kto, int kogo) {
		this.kto = kto;
		this.kogo = kogo;
	}

	public int knowsWho() {
		return kogo;
	}

	public int who() {
		return kto;
	}
}
