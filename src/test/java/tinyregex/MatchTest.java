package tinyregex;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tinyregex.Regex.match;

public class MatchTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private void checkMalformedRegex(String regex) {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Illegal regular expression");
        match(regex, "");
    }

    @Test
    public void starRegex() {
        assertTrue(match("a*", ""));
        assertTrue(match("a*", "aaa"));
        assertTrue(match("a*", "bbb"));
        assertTrue(match("a*b*c", "aaac"));
        assertTrue(match("(a|b)*c", "aaabbbc"));
    }

    @Test
    public void plusRegex() {
        assertFalse(match("a+", ""));
        assertTrue(match("a+", "aaa"));
        assertFalse(match("a+b+", "aaa"));
        assertTrue(match("a+b+", "aaabbb"));
    }

    @Test
    public void altRegex() {
        assertFalse(match("(foo|bar|baz)", ""));
        assertFalse(match("(foo|bar|baz)", "quux"));
        assertTrue(match("(foo|bar|baz)", "foo"));
        assertTrue(match("((foo|bar)|baz)", "baz"));
    }

    @Test
    public void charClasses() {
        assertTrue(match("\\s+a", "\t\t\n  \t a"));
        assertFalse(match("\\s", "a"));
        assertTrue(match("\\w+a", "aA_0a"));
        assertFalse(match("\\w+a", "\ta"));
    }

    @Test
    public void escapeSequences() {
        assertTrue(match(".*a", "\n\t\b\ra"));
        assertTrue(match("\\n", "\n"));
        assertTrue(match("\r\n", "\r\n"));
        assertTrue(match("\\t", "\t"));
        assertTrue(match("\t", "\t"));
        assertTrue(match("\\.\\(\\)\\*\\+\\|\\\\", ".()*+|\\"));
    }

    @Test
    public void illegalEscape() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Illegal escape sequence at 2: \\p");
        match(".*\\p.*", "");
    }

    @Test
    public void trailingBackslash() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Trailing backslash at 0");
        match("\\", "");
    }

    @Test
    public void unclosedParenthesis() {
        checkMalformedRegex("(foo");
    }

    @Test
    public void startOfStar() {
        checkMalformedRegex("a**");
    }

    @Test
    public void plusOfPlus() {
        checkMalformedRegex("a++");
    }

    @Test
    public void starOfNothing() {
        checkMalformedRegex("*");
    }

    @Test
    public void emptyRegex() {
        checkMalformedRegex("");
    }

    @Test
    public void trailingBar() {
        checkMalformedRegex("a|");
    }

    @Test(timeout = 500)
    public void memoized() {
        int depth = 100;
        StringBuilder sb = new StringBuilder(".");
        for (int i = 0; i < depth; i++)
            sb.insert(0, '(').append(')');
        match(sb.toString(), "a");
    }


}
