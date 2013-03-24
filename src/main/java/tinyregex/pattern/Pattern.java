package tinyregex.pattern;

import tinyregex.pattern.nfa.StateMachine;

public abstract class Pattern {

    public boolean match(String input) {
        return compile().match(input);
    }

    public abstract StateMachine compile();
}
