package tinyregex.pattern;

import tinyregex.pattern.nfa.State;
import tinyregex.pattern.nfa.StateMachine;

import java.util.ArrayList;
import java.util.List;

public class SequencePattern extends Pattern {
    private final List<Pattern> patterns = new ArrayList<Pattern>();

    public SequencePattern(List<? extends Pattern> patterns) {
        this.patterns.addAll(patterns);
    }

    @Override
    public StateMachine compile() {
        State start = new State();
        State next = start;
        for (Pattern p: patterns) {
            StateMachine machine = p.compile();
            next.addEpsilonTransition(machine.startState());
            next = machine.endState();
        }
        return new StateMachine(start, next);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<SequencePattern: ");
        if (!patterns.isEmpty()) {
            sb.append(patterns.get(0));
            for (Pattern p : patterns.subList(1, patterns.size())) {
                sb.append(", ").append(p);
            }
        }
        sb.append(">");
        return sb.toString();
    }
}
