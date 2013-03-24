package tinyregex.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Patterns {
    public static final DotPattern dot = new DotPattern();

    public static StarPattern star(Pattern p) {
        return new StarPattern(p);
    }

    public static CharPattern ch(char c) {
        return new CharPattern(c);
    }

    public static SequencePattern seq(Pattern... ps) {
        return new SequencePattern(Arrays.asList(ps));
    }

    public static SequencePattern seq(String s) {
        List<CharPattern> chars = new ArrayList<CharPattern>();
        for (char c : s.toCharArray()) {
            chars.add(ch(c));
        }
        return new SequencePattern(chars);
    }

    public static AltPattern alt(Pattern... ps) {
        return new AltPattern(Arrays.asList(ps));
    }
}
