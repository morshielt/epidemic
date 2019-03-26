package simulator;

import agent.Agent;
import agent.State;

import java.util.Random;

public class Meeting implements Comparable<Meeting> {
	private Agent agent1;
	private Agent agent2;
	private int dzien;

	public Meeting(Agent agent1, Agent agent2, int dzien) {
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

	public int day() {
		return dzien;
	}

	public boolean goThroughMeeting(Random generator) {
		if (agent1.state() == State.ILL && agent2.state() == State.HEALTHY) {
			if (agent2().fallSick(generator)) {
				agent2.changeState(State.ILL);
				return true;
			}
		}
		else if (agent2.state() == State.ILL && agent1.state() == State.HEALTHY) {
			if (agent1().fallSick(generator)) {
				agent1.changeState(State.ILL);
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Meeting o) {
		if (o.day() > this.dzien) {
			return -1;
		}
		else return 1;
	}
}

