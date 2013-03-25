package tinyregex.pattern;

import tinyregex.pattern.nfa.State;
import tinyregex.pattern.nfa.StateMachine;

public class StarPattern extends Pattern {
    private final Pattern inner;

    public StarPattern(Pattern repeatable) {
        this.inner = repeatable;
    }

    @Override
    public StateMachine compile() {
        State start = new State();
        State end = new State();
        start.addEpsilonTransition(end);
        StateMachine machine = inner.compile();
        start.addEpsilonTransition(machine.startState());
        machine.endState().addEpsilonTransition(end);
        end.addEpsilonTransition(start);
        return new StateMachine(start, end);
    }

    @Override
    public String toString() {
        return "<StarPattern: " + inner + ">";
    }
}
