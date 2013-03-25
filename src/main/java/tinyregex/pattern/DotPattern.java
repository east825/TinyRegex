package tinyregex.pattern;

import tinyregex.pattern.nfa.Condition;
import tinyregex.pattern.nfa.StateMachine;

public class DotPattern extends Pattern {
    private static Condition ANY = new Condition() {
        @Override
        public boolean isTrue(char c) {
            return true;
        }
    };

    @Override
    public StateMachine compile() {
        return StateMachine.fromCondition(ANY);
    }

    @Override
    public String toString() {
        return "<DotPattern>";
    }
}
