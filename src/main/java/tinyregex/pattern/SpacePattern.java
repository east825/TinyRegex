package tinyregex.pattern;

import tinyregex.pattern.nfa.Condition;
import tinyregex.pattern.nfa.StateMachine;

public class SpacePattern extends Pattern {
    private static final Condition SPACE = new Condition() {
        @Override
        public boolean isTrue(char c) {
            return Character.isWhitespace(c);
        }
    };

    @Override
    public StateMachine compile() {
        return StateMachine.fromCondition(SPACE);
    }

    @Override
    public String toString() {
        return "<SpacePattern>";
    }
}
