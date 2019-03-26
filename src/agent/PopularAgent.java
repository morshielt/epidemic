package agent;

import input.Parameters;

import java.util.ArrayList;
import java.util.Random;

public class PopularAgent extends Agent {
    public PopularAgent(int id, Parameters parameters) {
        super(id, parameters);
        this.type = "popular";
    }

    @Override
    public Agent randomFriend(Random generator) {
        if (this.state == State.ILL) return super.randomFriend(generator);
        else {
            ArrayList<Agent> friendsOfFriends = new ArrayList<>(this.friends);
            for (Agent friend : this.friends) {
                friendsOfFriends.addAll(friend.friends());
            }

            int numberOfFriends = friendsOfFriends.size();
            if (numberOfFriends > 0) {
                return friendsOfFriends.get(generator.nextInt(numberOfFriends));
            } else return null;
        }
    }
}
