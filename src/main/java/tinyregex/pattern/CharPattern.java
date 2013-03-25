package tinyregex.pattern;

import tinyregex.pattern.nfa.Condition;
import tinyregex.pattern.nfa.StateMachine;

public class CharPattern extends Pattern {
    private final char expected;

    private class SingleChar implements Condition {
        @Override
        public boolean isTrue(char c) {
            return c == expected;
        }
    }

    public CharPattern(char expected) {
        this.expected = expected;
    }

    @Override
    public StateMachine compile() {
        return StateMachine.fromCondition(new SingleChar());
    }

    @Override
    public String toString() {
        return "<CharPattern: '" + expected + "'>";
    }
}
