package agent;

import dane.Dane;

public class AgentZwykly extends Agent {

	public AgentZwykly(int id, Dane parametry) {
		super(id, parametry);
		this.typ = "zwykły";
	}

	@Override
	public void zmienStan(Stan stan) {
		if (stan == Stan.CHORY) {
			this.stan = Stan.CHORY;
			this.prawdSpotkania *= 0.5;
		}
		else if (stan == Stan.ODPORNY) {
			this.stan = Stan.ODPORNY;
			this.prawdSpotkania *= 2;
			this.prawdZarazenia = 0;
		}
	}
}
