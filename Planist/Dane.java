import java.util.Arrays;

public class Dane {
	private Proces[] procesy;
	private int[] parametryRR;

	public Dane(Proces[] procesy, int[] parametryRR) {
		this.procesy = Arrays.copyOf(procesy, procesy.length);
		this.parametryRR = Arrays.copyOf(parametryRR, parametryRR.length);
		sortujProcesy();
	}

	private void sortujProcesy() {
		Arrays.sort(this.procesy);
	}

	public Proces[] procesy() {
		return procesy;
	}

	public int[] parametryRR() {
		return parametryRR;
	}
}
