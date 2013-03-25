package tinyregex;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static tinyregex.Regex.match;

public class MatchTest {

    @Test
    public void starRegex() {
        assertTrue(match("a*", ""));
        assertTrue(match("a*", "aaa"));
        assertTrue(match("a*", "bbb"));
        assertTrue(match("a*b*", "aaa"));
        assertTrue(match("(a|b)*", "aaabbb"));
    }

    @Test
    public void plusRegex() {
        assertFalse(match("a+", ""));
        assertFalse(match("a+b+", "aaa"));
        assertTrue(match("a+b+", "aaabbb"));
    }

    @Test
    public void altRegex() {
        assertFalse(match("(foo|bar|baz)", ""));
        assertTrue(match("(foo|bar|baz)", "foo"));
        assertTrue(match("((foo|bar)|baz)", "baz"));
    }

    @Test
    public void charClasses() {
        assertTrue(match("\\s+a", "\t\t\n  \t a"));
        assertTrue(match("\\w+a", "aA_0a"));
        assertFalse(match("\\w+a", "\ta"));
    }

    @Test
    public void escapeSequences() {
        assertTrue(match(".*a", "\n\t\b\ra"));
        assertTrue(match("\\n", "\n"));
        assertTrue(match("\\t", "\t"));
        assertTrue(match("\\.\\(\\)\\*\\+\\|\\\\", ".()*+|\\"));
    }
}
