package tinyregex.parser;

import java.util.List;

public final class MaybeParser<T>  extends Parser<T> {
    private final Parser<T> parser;

    public MaybeParser(Parser<T> p) {
        parser = p;
        // if inner parser can't match anything, null will be returned by default
        result = null;
    }

    @Override
    protected int parse(List<Token> toks, int pos) throws NoParseException {
        try {
            pos = parser.parse(toks, pos);
        } catch (NoParseException e) {
            // ignored
        }
        parsed = true;
        return pos;
    }
}
