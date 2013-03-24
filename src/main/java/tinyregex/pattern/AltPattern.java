package tinyregex.pattern;

import tinyregex.pattern.nfa.State;
import tinyregex.pattern.nfa.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class AltPattern extends Pattern {
    private final List<Pattern> patterns = new ArrayList<Pattern>();

    public AltPattern(List<? extends Pattern> patterns) {
        this.patterns.addAll(patterns);
    }

    @Override
    public StateMachine compile() {
        State start = new State();
        State end = new State();
        for (Pattern p: patterns) {
            StateMachine machine = p.compile();
            start.addEpsilonTransition(machine.startState());
            machine.endState().addEpsilonTransition(end);
        }
        return new StateMachine(start, end);
    }
}
