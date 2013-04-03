package tinyregex.pattern.nfa;

import java.util.HashSet;
import java.util.Set;

public class StateMachine {
    private final State start, end;

    public static StateMachine fromCondition(Condition c) {
        State start = new State();
        State end = new State();
        start.setConditionalTransition(c, end);
        return new StateMachine(start, end);
    }

    public StateMachine(State start, State end) {
        this.start = start;
        this.end = end;
    }

    public State startState() {
        return start;
    }

    public State endState() {
        return end;
    }

    public boolean match(String input) {
        // without overriden hashCode() method, it should behave just like IdentitySet
        Set<State> reachable = new HashSet<State>();
        reachable.add(start);
        // collect all reachable states (adding epsilon transitions)
        if (expandEpsilon(reachable))
            return true;
        for (char c : input.toCharArray()) {
            Set<State> nextReachable = new HashSet<State>();
            for (State s : reachable) {
                // skip intermediate states
                if (s.transition() == null)
                    continue;
                if (s.transition().possibleFor(c))
                    nextReachable.add(s.nextState());
            }
            reachable = nextReachable;
            if (expandEpsilon(reachable))
                return true;
        }
        return false;
    }

    private boolean expandEpsilon(Set<State> reachable) {
        Set<State> fringe = new HashSet<State>(reachable);
        while (!fringe.isEmpty()) {
            if (fringe.contains(end))
                return true;
            Set<State> unvisited = new HashSet<State>();
            for (State s : fringe) {
                unvisited.addAll(s.epsilonAdjacent());
            }
            // new states, that not considered yet
            unvisited.removeAll(reachable);
            fringe = unvisited;
            reachable.addAll(unvisited);
        }
        return false;
    }
}
