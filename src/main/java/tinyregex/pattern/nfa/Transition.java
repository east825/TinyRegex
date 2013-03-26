package tinyregex.pattern.nfa;

public class Transition {
    private final State source, destination;
    private final Condition condition;

    public Transition(State source, State destination, Condition condition) {
        this.source = source;
        this.destination = destination;
        this.condition = condition;
    }

    public State source() {
        return source;
    }

    public State destination() {
        return destination;
    }

    public boolean possibleFor(char c) {
        return condition.isTrue(c);
    }
}
