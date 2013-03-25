package tinyregex.pattern;

import tinyregex.pattern.nfa.State;
import tinyregex.pattern.nfa.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class AltPattern extends Pattern {
    private final List<Pattern> patterns = new ArrayList<Pattern>();

    public AltPattern(List<? extends Pattern> patterns) {
        if (patterns.isEmpty())
            throw new IllegalArgumentException("No nested patterns inside AltPattern");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<AltPattern: ");
        sb.append(patterns.get(0));
        for (Pattern p : patterns.subList(1, patterns.size())) {
            sb.append(" | ").append(p);
        }
        sb.append(">");
        return sb.toString();
    }
}
