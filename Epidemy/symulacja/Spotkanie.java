package symulacja;

import agent.Agent;
import agent.Stan;

import java.util.Random;

public class Spotkanie implements Comparable<Spotkanie> {
	private Agent agent1;
	private Agent agent2;
	private int dzien;

	public Spotkanie(Agent agent1, Agent agent2, int dzien) {
		this.agent1 = agent1;
		this.agent2 = agent2;
		this.dzien = dzien;
	}

	public Agent agent1() {
		return agent1;
	}

	public Agent agent2() {
		return agent2;
	}

	public int dzien() {
		return dzien;
	}

	public boolean przeprowadzSpotkanie(Random generator) {
		if (agent1.stan() == Stan.CHORY && agent2.stan() == Stan.ZDROWY) {
			if (agent2().zachoruj(generator)) {
				agent2.zmienStan(Stan.CHORY);
				return true;
			}
		}
		else if (agent2.stan() == Stan.CHORY && agent1.stan() == Stan.ZDROWY) {
			if (agent1().zachoruj(generator)) {
				agent1.zmienStan(Stan.CHORY);
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Spotkanie o) {
		if (o.dzien() > this.dzien) {
			return -1;
		}
		else return 1;
	}
}

