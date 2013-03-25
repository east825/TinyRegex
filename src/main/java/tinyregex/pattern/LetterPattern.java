package tinyregex.pattern;

import tinyregex.pattern.nfa.Condition;
import tinyregex.pattern.nfa.StateMachine;

public class LetterPattern extends Pattern {
    private static final Condition LETTER = new Condition() {
        @Override
        public boolean isTrue(char c) {
            return Character.isLetter(c) || Character.isDigit(c) || c == '_';
        }
    };

    @Override
    public StateMachine compile() {
        return StateMachine.fromCondition(LETTER);
    }

    @Override
    public String toString() {
        return "<LetterPattern>";
    }
}
