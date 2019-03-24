package agent;

import dane.Dane;

import java.util.ArrayList;
import java.util.Random;

public class AgentTowarzyski extends Agent {
	public AgentTowarzyski(int id, Dane parametry) {
		super(id, parametry);
		this.typ = "towarzyski";
	}

	@Override
	public Agent losujZnajomego(Random generator) {
		if (this.stan == Stan.CHORY) return super.losujZnajomego(generator);
		else {
			ArrayList<Agent> znajomiZnajomych = new ArrayList<>(this.znajomi);
			for (Agent znajomy : this.znajomi) {
				znajomiZnajomych.addAll(znajomy.znajomi());
			}

			int liczbaZnajomych = znajomiZnajomych.size();
			if (liczbaZnajomych > 0) {
				return znajomiZnajomych.get(generator.nextInt(liczbaZnajomych));
			}
			else return null;
		}
	}
}
