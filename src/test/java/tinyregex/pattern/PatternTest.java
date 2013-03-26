package tinyregex.pattern;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static tinyregex.pattern.Patterns.*;

public class PatternTest {

    @Test
    public void starPattern() {
        Pattern p = star(ch('a'));
        assertTrue(p.match("aa"));
        assertTrue(p.match(""));
        assertTrue(p.match("b"));
    }

    @Test
    public void seqPattern() {
        Pattern p = seq(ch('a'), ch('b'));
        assertTrue(p.match("abc"));
        assertFalse(p.match("acb"));
        assertFalse(p.match(""));

    }

    @Test
    public void altPattern() {
        Pattern p = alt(seq("foo"), seq("bar"));
        assertTrue(p.match("foo"));
        assertTrue(p.match("bar"));
        assertFalse(p.match("baz"));
    }

    @Test
    public void combined() {
        Pattern p = seq(ch('a'), star(dot), ch('b'));
        assertTrue(p.match("acb"));
        assertFalse(p.match("bbb"));
    }


}
