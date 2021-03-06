package tinyregex.pattern.nfa;

import java.util.HashSet;
import java.util.Set;

public class State {
    private final Set<State> epsilonTransitions = new HashSet<State>();
    private Transition toNext;


    public void setConditionalTransition(Condition c, State s) {
        toNext = new Transition(this, s, c);
    }

    public void addEpsilonTransition(State s) {
        epsilonTransitions.add(s);
    }

    public Set<State> epsilonAdjacent() {
        return epsilonTransitions;
    }

    public Transition transition() {
        return toNext;
    }

    public State nextState() {
        return toNext.destination();
    }
}
