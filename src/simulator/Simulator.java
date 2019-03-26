package simulator;

import agent.*;
import input.Parameters;

import java.util.*;

public class Simulator {
    private Parameters parameters;
    private int ill;
    private int immune;
    private int healthy;
    private PriorityQueue<Meeting> meetings;
    private ArrayList<Agent> agents;
    private Random generator;
    private static final int infected = 1;
    private String quantityEachDay = "# statistics each day\n";
    private String agentsForReport = "";
    private String graph = "";

    public Simulator(Parameters parameters) {
        this.parameters = parameters;
        this.ill = infected;
        this.immune = 0;
        this.healthy = this.parameters.numberOfAgents() - infected;
        this.meetings = new PriorityQueue<>();
        this.agents = new ArrayList<>();
        this.generator = new Random(this.parameters.seed());
    }

    public void simulateEpidemic() {
        newSocialNetwork();

        for (int day = 1; day <= parameters.numberOfDays() && !agents.isEmpty(); day++) {
            printCurrentState();

            changeStatesOfAgents();
            planMeetings(day);
            goThroughMeetings(day);
        }
        printCurrentState();
    }

    private void changeStatesOfAgents() {
        ArrayList<Agent> morgue = new ArrayList<>();

        for (Agent agent : agents) {
            if (agent.state() == State.ILL) {
                if (agent.die(this.generator)) {
                    ill--;
                    ArrayList<Agent> friends = agent.friends();
                    for (Agent friend : friends) {
                        friend.friends().remove(agent);
                    }
                    morgue.add(agent);
                } else if (agent.heal(this.generator)) {
                    ill--;
                    immune++;
                    agent.changeState(State.IMMUNE);
                }
            }
        }

        agents.removeAll(morgue);
        morgue.clear();
    }

    private void planMeetings(int day) {
        if (day != parameters.numberOfDays()) {
            for (Agent agent : agents) {
                while (agent.meet(this.generator) && !(agent.friends().isEmpty())) {
                    Agent friend = agent.randomFriend(this.generator);
                    int dayOfMeeting = generator.nextInt(parameters.numberOfDays() - day) + day + 1;
                    meetings.add(new Meeting(agent, friend, dayOfMeeting));
                }
            }
        }
    }

    private void goThroughMeetings(int day) {
        boolean today = true;
        while (!(meetings.isEmpty()) && today) {
            Meeting meeting = meetings.poll();

            if (meeting.day() == day) {
                if (agents.contains(meeting.agent1()) && agents.contains(meeting.agent2())) {
                    if (meeting.goThroughMeeting(this.generator)) {
                        ill++;
                        healthy--;
                    }
                }
            } else {
                meetings.add(meeting);
                today = false;
            }
        }
    }

    private void newSocialNetwork() {
        for (int i = 1; i <= parameters.numberOfAgents(); i++) {
            randomNewAgent(i);
        }

        int numberOfFriends = Math.round((parameters.averageFriends() * parameters.numberOfAgents()) / 2);
        ArrayList<Friendship> possibleFriendships = possibleFriendships();
        int i = 0;
        while (i < numberOfFriends) {
            randomFriendship(possibleFriendships);
            i++;
        }
        fillGraph();

        int j = 0;
        while (j < infected) {
            if (randomIll()) {
                j++;
            }
        }
        fillAgentList();
    }

    private void randomNewAgent(int i) {
        Agent agent = null;
        if (generator.nextDouble() < parameters.probPopular()) {
            agent = new PopularAgent(i, parameters);
        } else {
            agent = new NormalAgent(i, parameters);
        }
        agents.add(agent);
    }

    private void randomFriendship(ArrayList<Friendship> potentialFriendships) {
        Friendship friendship = potentialFriendships.remove(generator.nextInt(potentialFriendships.size()));
        Agent agent1 = agents.get(friendship.who());
        Agent agent2 = agents.get(friendship.knowsWho());
        agent1.friends().add(agent2);
        agent2.friends().add(agent1);
    }

    private boolean randomIll() {
        int ill = generator.nextInt(parameters.numberOfAgents());
        if (agents.get(ill).state() != State.ILL) {
            agents.get(ill).changeState(State.ILL);
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<Friendship> possibleFriendships() {
        ArrayList<Friendship> friendships = new ArrayList<>();
        for (int i = 0; i < parameters.numberOfAgents(); i++) {
            for (int j = i + 1; j < parameters.numberOfAgents(); j++) {
                friendships.add(new Friendship(i, j));
            }
        }
        return friendships;
    }

    private void fillGraph() {
        this.graph += "# graph\n";

        for (Agent agent : agents) {
            this.graph += (agent.id() + " ");
            for (Agent friend : agent.friends()) {
                this.graph += (friend.id() + " ");
            }
            this.graph += ('\n');
        }
        this.graph += '\n';
    }

    public String graph() {
        return graph;
    }

    private void fillAgentList() {
        for (Agent agent : agents) {
            this.agentsForReport += (agent.toString() + '\n');
        }
        this.agentsForReport += '\n';
    }

    public String agentList() {
        return agentsForReport;
    }

    private void printCurrentState() {
        this.quantityEachDay += (this.healthy + " " + this.ill + " " + this.immune + '\n');
    }

    public String quantityEachDay() {
        return quantityEachDay;
    }
}
