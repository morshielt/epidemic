package agent;

import input.Parameters;

public class NormalAgent extends Agent {

	public NormalAgent(int id, Parameters parameters) {
		super(id, parameters);
		this.type = "normal";
	}

	@Override
	public void changeState(State state) {
		if (state == State.ILL) {
			this.state = State.ILL;
			this.probMeeting *= 0.5;
		}
		else if (state == State.IMMUNE) {
			this.state = State.IMMUNE;
			this.probMeeting *= 2;
			this.probInfecting = 0;
		}
	}
}
