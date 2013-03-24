package tinyregex.pattern;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static tinyregex.pattern.Patterns.*;

public class RegexTest {

    @Test
    public void starPattern() {
        Pattern p = star(ch('a'));
        assertThat(p.match("aa"), is(true));
        assertThat(p.match(""), is(true));
        assertThat(p.match("b"), is(true));
    }

    @Test
    public void seqPattern() {
        Pattern p = seq(ch('a'), ch('b'));
        assertThat(p.match("abc"), is(true));
        assertThat(p.match("acb"), is(false));
        assertThat(p.match(""), is(false));

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
