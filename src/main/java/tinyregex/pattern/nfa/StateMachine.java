package tinyregex.pattern.nfa;

import java.util.HashSet;
import java.util.Set;

public class StateMachine {
    private State start, end;

    public static StateMachine fromTransition(Transition t) {
        return new StateMachine(t.source(), t.destination());
    }

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

    public void connect(StateMachine other) {
        end.addEpsilonTransition(other.startState());
        end = other.endState();
    }

    public boolean match(String input) {
        Set<State> reachable = new HashSet<State>();
        reachable.add(start);
        expand(reachable);
        if (containsTerminateState(reachable)) {
            return true;
        }
        for (char c : input.toCharArray()) {
            Set<State> nextReachable = new HashSet<State>();
            for (State s : reachable) {
                if (s.transition() == null)
                    continue;
                if (s.transition().possibleFor(c)) {
                    State next = s.nextState();
                    if (next == end) {
                        return true;
                    }
                    nextReachable.add(next);
                }
            }
            reachable = nextReachable;
            expand(reachable);
            if (containsTerminateState(reachable))
                return true;
        }
        return false;
    }

    private void expand(Set<State> reachable) {
        Set<State> epsilonReachable = new HashSet<State>();
        int oldSize;
        do {
            oldSize = reachable.size();
            for (State s : reachable) {
                epsilonReachable.addAll(s.epsilonAdjacent());
            }
            reachable.addAll(epsilonReachable);
        } while (reachable.size() != oldSize);
    }

    private boolean containsTerminateState(Set<State> states) {
        for (State s : states) {
            if (s == end) {
                return true;
            }
        }
        return false;
    }


}
