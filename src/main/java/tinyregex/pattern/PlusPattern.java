package tinyregex.pattern;

import tinyregex.pattern.nfa.State;
import tinyregex.pattern.nfa.StateMachine;

public class PlusPattern extends Pattern {
    private final Pattern inner;

    public PlusPattern(Pattern inner) {
        this.inner = inner;
    }

    @Override
    public StateMachine compile() {
        State start = new State();
        State end = new State();
        StateMachine machine = inner.compile();
        start.addEpsilonTransition(machine.startState());
        machine.endState().addEpsilonTransition(end);
        end.addEpsilonTransition(start);
        return new StateMachine(start, end);
    }
}
