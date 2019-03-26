package agent;

import input.Parameters;

import java.util.ArrayList;
import java.util.Random;

public abstract class Agent implements Comparable<Agent> {
    protected int id;
    protected ArrayList<Agent> friends;
    protected State state;
    protected double probMeeting;
    protected double probInfecting;
    protected double probHealing;
    protected double mortality;
    protected String type;

    public Agent(int id, Parameters parameters) {
        this.id = id;
        this.state = State.HEALTHY;
        this.friends = new ArrayList<Agent>();
        this.probMeeting = parameters.probMeeting();
        this.probInfecting = parameters.probInfecting();
        this.probHealing = parameters.probHealing();
        this.mortality = parameters.mortality();
    }

    public void changeState(State state) {
        if (state == State.ILL) {
            this.state = State.ILL;
        } else if (state == State.IMMUNE) {
            this.state = State.IMMUNE;
            this.probInfecting = 0;
        }
    }

    public Agent randomFriend(Random generator) {
        int numberOfFriends = friends.size();
        if (numberOfFriends > 0) {
            return friends.get(generator.nextInt(numberOfFriends));
        }
        return null;
    }

    public State state() {
        return state;
    }

    public boolean die(Random generator) {
        return (generator.nextDouble() < this.mortality);
    }

    public boolean heal(Random generator) {
        return (generator.nextDouble() < this.probHealing);
    }

    public boolean fallSick(Random generator) {
        return (generator.nextDouble() < this.probInfecting);
    }

    public boolean meet(Random generator) {
        return (generator.nextDouble() < this.probMeeting);
    }

    public ArrayList<Agent> friends() {
        return this.friends;
    }

    @Override
    public String toString() {
        String agent = String.valueOf(this.id);
        if (this.state == State.ILL) {
            agent += "*";
        }
        agent += " " + this.type;

        return agent;
    }

    @Override
    public int compareTo(Agent o) {
        if (o.id() > this.id) {
            return -1;
        } else return 1;
    }

    public int id() {
        return id;
    }
}
